package br.com.a2dm.spdm.ativmob.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.a2dm.brcmn.dto.ativmob.EventDTO;
import br.com.a2dm.brcmn.dto.ativmob.FormDTO;
import br.com.a2dm.brcmn.dto.ativmob.OrderDTO;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.ativmob.api.AtivMobApiClient;
import br.com.a2dm.spdm.ativmob.builder.AtivMobBuilder;
import br.com.a2dm.spdm.entity.Item;
import br.com.a2dm.spdm.entity.SugestaoPedido;
import br.com.a2dm.spdm.service.ItemService;
import br.com.a2dm.spdm.service.SugestaoPedidoService;

public class AtivMobService {

    private static AtivMobService instance;
    private static String resource = "/orders/delivery";
    private static String getEventsEndpoint = "/get_events?storeCNPJ=";
    private static String marcarLidoEndpoint = "/ack_events";

    private AtivMobService() {
    	
    }

    public static AtivMobService getInstance() {
        if (instance == null) {
            instance = new AtivMobService();
        }
        return instance;
    }
    
    public List<SugestaoPedido> proccessSugestoesPedido(BigInteger cnpj) throws AtivMobServiceException {
        try {
            List<EventDTO> sugestoesPedidoDTO = this.findSugestoesPedido(cnpj);
            List<SugestaoPedido> sugestoesInserted = this.saveSugestoesPedido(sugestoesPedidoDTO);
            this.makeEvents(sugestoesPedidoDTO);
            return sugestoesInserted;
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    } 
    
    private List<EventDTO> findSugestoesPedido(BigInteger cnpj) throws AtivMobServiceException {
        try {
            AtivMobApiClient apiClient = new AtivMobApiClient();
            ApiClientResponse response = apiClient.get(resource + getEventsEndpoint + cnpj);
            OrderDTO orderDTO = new AtivMobBuilder().buildBuscarOrderResponse(response.getBody());
            List<EventDTO> events = orderDTO.getEvents();
            events = filterSugestoesPedido(events);
            return events;
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    }

	private List<EventDTO> filterSugestoesPedido(List<EventDTO> events) {
		return events.stream()
				     .filter(event -> event.getEvent_code().equalsIgnoreCase("sugestao"))
				     .collect(Collectors.toList());
	}
    
    private void makeEvents(List<EventDTO> events) throws AtivMobServiceException {
        try {
            AtivMobApiClient apiClient = new AtivMobApiClient();
            AtivMobBuilder builder = new AtivMobBuilder();
            Object payload = builder.buildPayloadMarcarLido(events);
            apiClient.post(resource + marcarLidoEndpoint, payload);
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    }
    
    private List<SugestaoPedido> saveSugestoesPedido(List<EventDTO> sugestoesPedidoDTO) throws AtivMobServiceException {
    	Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		
		List<SugestaoPedido> sugestoesPedido = new ArrayList<>();
    	
        try 
        {	
        	if (sugestoesPedidoDTO != null && sugestoesPedidoDTO.size() > 0) {
            	for (EventDTO sugestaoPedidoDTO : sugestoesPedidoDTO) {
            		SugestaoPedido sugestaoPedidoInserted = this.saveSugestaoPedido(sessao, sugestaoPedidoDTO);
            		if (sugestaoPedidoInserted != null) {
            			sugestoesPedido.add(sugestaoPedidoInserted);
            		}
            	}
        	}
        	tx.commit();
        	return sugestoesPedido;
        } 
        catch (Exception e) 
        {
        	tx.rollback();
            throw new AtivMobServiceException(e);
        } 
        finally 
        {
			sessao.close();
		}
    }
    
    public SugestaoPedido saveSugestaoPedido(EventDTO sugestaoPedidoDTO) throws Exception {
    	Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		
        try 
        {	
			SugestaoPedido sugestaoPedido = this.buildSugestaoPedidoDTOToSugestaoPedido(sugestaoPedidoDTO);
			SugestaoPedido sugestaoPedidoInserted = this.insertSugestaoPedido(sessao, sugestaoPedido, sugestaoPedidoDTO.getForms());
			
			tx.commit();
        	return sugestaoPedidoInserted;
        } 
        catch (Exception e) 
        {
        	tx.rollback();
            throw new AtivMobServiceException(e);
        } 
        finally 
        {
			sessao.close();
		}
	}

	private SugestaoPedido saveSugestaoPedido(Session sessao, EventDTO sugestaoPedidoDTO) throws Exception {
		SugestaoPedido sugestaoPedido = this.buildSugestaoPedidoDTOToSugestaoPedido(sugestaoPedidoDTO);
		SugestaoPedido sugestaoPedidoInserted = this.insertSugestaoPedido(sessao, sugestaoPedido, sugestaoPedidoDTO.getForms());
		return sugestaoPedidoInserted;
	}
    
    private SugestaoPedido insertSugestaoPedido(Session sessao, SugestaoPedido sugestaoPedido, List<FormDTO> itensDTO) throws Exception {
    	try {
    		SugestaoPedidoService sugestaoPedidoService = SugestaoPedidoService.getInstancia();
    		
    		if (sugestaoPedidoService.get(sugestaoPedido, 0) == null) {
    			SugestaoPedido sugestaoPedidoInserted = sugestaoPedidoService.inserir(sessao, sugestaoPedido);
    			List<Item> itensInserted = new ArrayList<Item>();
    			for (FormDTO itemDTO : itensDTO) {
					Item itemInserted = this.insertItem(sessao, sugestaoPedidoInserted, itemDTO);
					itensInserted.add(itemInserted);
				}
    			sugestaoPedidoInserted.setItens(itensInserted);
        		return sugestaoPedidoInserted;
    		} else {
    			return null;
    		}
    	} catch (Exception e) {
            throw e;
        }
    }
    
    private Item insertItem(Session sessao, SugestaoPedido sugestaoPedidoInserted, FormDTO itemDTO) throws Exception {
    	try {
    		ItemService itemService = ItemService.getInstancia();
    		Item item = this.buildItemDTOToItem(sugestaoPedidoInserted.getIdSugestaoPedido(), itemDTO);
    		Item itemInserted = itemService.inserir(sessao, item);
    		return itemInserted;
    	} catch (Exception e) {
            throw e;
        }
    }
    
	private SugestaoPedido buildSugestaoPedidoDTOToSugestaoPedido(EventDTO sugestaoPedidoDTO) {
		SugestaoPedido sugestaoPedido = new SugestaoPedido();
		sugestaoPedido.setStoreCNPJ(sugestaoPedidoDTO.getStoreCNPJ());
		sugestaoPedido.setEventId(sugestaoPedidoDTO.getEvent_id());
		sugestaoPedido.setEventCode(sugestaoPedidoDTO.getEvent_code());
		sugestaoPedido.setEventTitle(sugestaoPedidoDTO.getEvent_title());
		sugestaoPedido.setEventDth(sugestaoPedidoDTO.getEvent_dth());
		sugestaoPedido.setOrderNumber(sugestaoPedidoDTO.getOrder_number());
		sugestaoPedido.setInvoiceNumber(sugestaoPedidoDTO.getInvoice_number());
		sugestaoPedido.setAgentCode(sugestaoPedidoDTO.getAgent_code());
		sugestaoPedido.setAgentName(sugestaoPedidoDTO.getAgent_name());
		sugestaoPedido.setLat(sugestaoPedidoDTO.getLat());
		sugestaoPedido.setLng(sugestaoPedidoDTO.getLng());
		sugestaoPedido.setCodigoRoteiro(sugestaoPedidoDTO.getCodigo_roteiro());
		sugestaoPedido.setLinkRastreamento(sugestaoPedidoDTO.getLink_rastreamento());
		sugestaoPedido.setRazaoSocialDest(sugestaoPedidoDTO.getRazao_social_dest());
		sugestaoPedido.setNomeFantasiaDest(sugestaoPedidoDTO.getNome_fantasia_dest());
		
		if (sugestaoPedidoDTO.getCodigo_destino() != null && sugestaoPedidoDTO.getCodigo_destino() != "") {
			sugestaoPedido.setCodigoDestino(new BigInteger(sugestaoPedidoDTO.getCodigo_destino()));
		} else {
			sugestaoPedido.setCodigoDestino(null);
		}
		
		sugestaoPedido.setStatus("Pendente");
		
	    return sugestaoPedido;
    }
	
	private Item buildItemDTOToItem(BigInteger idSugestaoPedido, FormDTO itemDTO) {
		Item item = new Item();
		item.setLabel(itemDTO.getLabel());
		item.setType(itemDTO.getType());
		item.setUrl(itemDTO.getUrl());
		
		if (itemDTO.getValue() != null && itemDTO.getValue() != "") {
			item.setValue(new BigInteger(itemDTO.getValue()));
		} else {
			item.setValue(null);
		}
		
		if (itemDTO.getCodigo() != null && itemDTO.getCodigo() != "") {
			item.setCodigo(Integer.parseInt(itemDTO.getCodigo()));
		} else {
			item.setCodigo(null);
		}
		
		if (itemDTO.getInteg_id() != null && itemDTO.getInteg_id() != "") {
			item.setIntegId(new BigInteger(itemDTO.getInteg_id()));
		} else {
			item.setIntegId(null);
		}
		
		item.setIdSugestaoPedido(idSugestaoPedido);
		
		return item;
	}
}
