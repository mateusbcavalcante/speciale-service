package br.com.a2dm.spdm.ativmob.builder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import br.com.a2dm.brcmn.dto.ativmob.EventDTO;
import br.com.a2dm.brcmn.dto.ativmob.MarcarLidoDTO;
import br.com.a2dm.brcmn.dto.ativmob.OrderDTO;

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

    public Object buildPayloadMarcarLido(List<EventDTO> events) throws AtivMobBuilderException {
        try {
            MarcarLidoDTO marcarLidoDTO = new MarcarLidoDTO();
            List<String> events_ids = new ArrayList<>();

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
