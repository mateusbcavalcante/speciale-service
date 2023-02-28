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
import br.com.a2dm.brcmn.entity.ativmob.Event;
import br.com.a2dm.brcmn.entity.ativmob.Form;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.ativmob.api.AtivMobApiClient;
import br.com.a2dm.spdm.ativmob.builder.AtivMobBuilder;
import br.com.a2dm.spdm.service.EventService;
import br.com.a2dm.spdm.service.FormService;

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
    
    public List<Event> proccessEvents(BigInteger cnpj) throws AtivMobServiceException {
        try {
            List<EventDTO> events = this.findEvents(cnpj);
            List<Event> eventsInserted = this.saveEvents(events);
            this.makeEvents(events);
            return eventsInserted;
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    } 
    
    private List<EventDTO> findEvents(BigInteger cnpj) throws AtivMobServiceException {
        try {
            AtivMobApiClient apiClient = new AtivMobApiClient();
            ApiClientResponse response = apiClient.get(resource + getEventsEndpoint + cnpj);
            OrderDTO orderDTO = new AtivMobBuilder().buildBuscarOrderResponse(response.getBody());
            List<EventDTO> events = orderDTO.getEvents();
            events = FilteredsEventsBySugestao(events);
            return events;
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    }

	private List<EventDTO> FilteredsEventsBySugestao(List<EventDTO> events) {
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
    
    private List<Event> saveEvents(List<EventDTO> eventsDTO) throws AtivMobServiceException {
    	Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		
		List<Event> events = new ArrayList<>();
    	
        try 
        {	
        	if (eventsDTO != null && eventsDTO.size() > 0) {
            	for (EventDTO eventDTO : eventsDTO) {
            		Event event = this.buildEventDTOToEvent(eventDTO);
            		Event eventInserted = this.insertEvent(sessao, event, eventDTO.getForms());
            		if (eventInserted != null) {
            			events.add(eventInserted);
            		}
            	}
        	}
        	tx.commit();
        	return events;
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
    
    private Event insertEvent(Session sessao, Event event, List<FormDTO> formsDTO) throws Exception {
    	try {
    		EventService eventService = EventService.getInstancia();
    		
    		if (eventService.get(event, 0) == null) {
    			Event eventInserted = eventService.inserir(sessao, event);
    			List<Form> formsInserted = new ArrayList<Form>();
    			for (FormDTO form : formsDTO) {
					Form formInserted = this.insertForm(sessao, eventInserted, form);
					formsInserted.add(formInserted);
				}
    			eventInserted.setForms(formsInserted);
        		return eventInserted;
    		} else {
    			return null;
    		}
    	} catch (Exception e) {
            throw e;
        }
    }
    
    private Form insertForm(Session sessao, Event eventInserted, FormDTO formDTO) throws Exception {
    	try {
    		FormService formService = FormService.getInstancia();
    		Form form = this.buildFormDTOToForm(eventInserted.getId_event(), formDTO);
    		Form formInserted = formService.inserir(sessao, form);
    		return formInserted;
    	} catch (Exception e) {
            throw e;
        }
    }
    
    private Event buildEventDTOToEvent(EventDTO eventDTO) {
		Event event = new Event();
		event.setStoreCNPJ(eventDTO.getStoreCNPJ());
		event.setEvent_id(eventDTO.getEvent_id());
		event.setEvent_code(eventDTO.getEvent_code());
		event.setEvent_title(eventDTO.getEvent_title());
		event.setEvent_dth(eventDTO.getEvent_dth());
		event.setOrder_number(eventDTO.getOrder_number());
		event.setInvoice_number(eventDTO.getInvoice_number());
		event.setAgent_code(eventDTO.getAgent_code());
		event.setAgent_name(eventDTO.getAgent_name());
		event.setLat(eventDTO.getLat());
		event.setLng(eventDTO.getLng());
		event.setCodigo_roteiro(eventDTO.getCodigo_roteiro());
		event.setLink_rastreamento(eventDTO.getLink_rastreamento());
		event.setStatus("Pendente");
	    return event;
    }
	
	private Form buildFormDTOToForm(BigInteger idEvent, FormDTO formDTO) {
		Form form = new Form();
		form.setLabel(formDTO.getLabel());
		form.setType(formDTO.getType());
		form.setUrl(formDTO.getUrl());
		form.setValue(form.getValue());
		form.setIdEvent(idEvent);
		return form;
	}
}
