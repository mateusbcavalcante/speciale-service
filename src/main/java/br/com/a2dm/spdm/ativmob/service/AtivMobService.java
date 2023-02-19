package br.com.a2dm.spdm.ativmob.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.a2dm.brcmn.dto.ativmob.EventDTO;
import br.com.a2dm.brcmn.dto.ativmob.FormDTO;
import br.com.a2dm.brcmn.dto.ativmob.OrderDTO;
import br.com.a2dm.brcmn.entity.ativmob.Event;
import br.com.a2dm.brcmn.entity.ativmob.Form;
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
            this.makeEvents(events);
            return this.saveEvents(events);
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
        try {
        	List<Event> events = new ArrayList<>();
        	
        	if (eventsDTO != null && eventsDTO.size() > 0) {
            	for (EventDTO eventDTO : eventsDTO) {
            		Event event = buildEventDtoToEvent(eventDTO);
            		Event eventInserted = EventService.getInstancia().inserir(event);
            		
            		for (FormDTO formDTO : eventDTO.getForm()) {
            			Form form = buildFormDtoToForm(eventInserted, formDTO);
            			FormService.getInstancia().inserir(form);
            		}
            		events.add(eventInserted);
            	}
        	}
        	return events;
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    }

	private Event buildEventDtoToEvent(EventDTO eventDTO) {
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
		return event;
	}
	
	private Form buildFormDtoToForm(Event eventInserted, FormDTO formDTO) {
		Form form = new Form();
		form.setType(formDTO.getType());
		form.setLabel(formDTO.getLabel());
		form.setUrl(formDTO.getUrl());
		form.setValue(formDTO.getValue());
		form.setEvent(eventInserted);
		return form;
	}
}
