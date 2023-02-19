package br.com.a2dm.spdm.ativmob.builder;

import br.com.a2dm.brcmn.dto.ativmob.EventDTO;
import br.com.a2dm.brcmn.dto.ativmob.FormDTO;
import br.com.a2dm.brcmn.dto.ativmob.MarcarLidoDTO;
import br.com.a2dm.brcmn.dto.ativmob.OrderDTO;
import br.com.a2dm.brcmn.entity.ativmob.Event;
import br.com.a2dm.brcmn.entity.ativmob.Form;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AtivMobBuilder {

    public OrderDTO buildBuscarOrderResponse(String json) throws AtivMobBuilderException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            OrderDTO orderDTO = objectMapper.readValue(json, OrderDTO.class);
            return orderDTO;
        } catch (Exception e) {
            throw new AtivMobBuilderException(e);
        }
    }

    public ArrayList<Event> buildEventsEntity(ArrayList<EventDTO> eventsDTO) throws AtivMobBuilderException {
        try {
            ArrayList<Event> events = new ArrayList<>();
            for (EventDTO eventDTO : eventsDTO) {
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

                ArrayList<FormDTO> formDTOs = eventDTO.getForm();
                ArrayList<Form> forms = new ArrayList<>();
                for (FormDTO formDTO : formDTOs) {
                    Form form = new Form();
                    form.setIdForm(form.getIdForm());
                    form.setType(formDTO.getType());
                    form.setLabel(formDTO.getLabel());
                    form.setUrl(formDTO.getUrl());
                    form.setValue(formDTO.getValue());
                    forms.add(form);
                }
                event.setForms(forms);
                events.add(event);
            }

            return events;
        } catch (Exception e) {
            throw new AtivMobBuilderException(e);
        }
    }

    public Object buildPayloadMarcarLido(ArrayList<EventDTO> events) throws AtivMobBuilderException {
        try {
            MarcarLidoDTO marcarLidoDTO = new MarcarLidoDTO();
            ArrayList<String> events_ids = new ArrayList<>();

            for (EventDTO event: events) {
                events_ids.add(event.getEvent_id().toString());
            }
            marcarLidoDTO.setEventsIds(events_ids);
            return marcarLidoDTO;
        } catch (Exception e) {
            throw new AtivMobBuilderException(e);
        }
    }

}
