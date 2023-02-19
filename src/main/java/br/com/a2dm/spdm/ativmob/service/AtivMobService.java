package br.com.a2dm.spdm.ativmob.service;

import br.com.a2dm.brcmn.dto.ativmob.EventDTO;
import br.com.a2dm.brcmn.dto.ativmob.OrderDTO;
import br.com.a2dm.brcmn.entity.ativmob.Event;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.ativmob.api.AtivMobApiClient;
import br.com.a2dm.spdm.ativmob.builder.AtivMobBuilder;
import br.com.a2dm.spdm.ativmob.repository.AtivMobRepository;

import java.math.BigInteger;
import java.util.ArrayList;


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

    public ArrayList<EventDTO> buscarEvents(BigInteger cnpj) throws AtivMobServiceException {
        try {
            AtivMobApiClient apiClient = new AtivMobApiClient();
            ApiClientResponse response = apiClient.get(resource + getEventsEndpoint + cnpj);
            OrderDTO orderDTO = new AtivMobBuilder().buildBuscarOrderResponse(response.getBody());
            ArrayList<EventDTO> events = orderDTO.getEvents();
            events.removeIf(event -> event.getEvent_code() == "sugestao");
            return events;
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    }

    public void salvarEvents(ArrayList<EventDTO> eventsDTO) throws AtivMobServiceException {
        try {
            AtivMobBuilder builder = new AtivMobBuilder();
            ArrayList<Event> events = builder.buildEventsEntity(eventsDTO);
            AtivMobRepository.getInstance().salvarEvents(events);
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    }

    public void marcarLidoEvents(ArrayList<EventDTO> events) throws AtivMobServiceException {
        try {
            AtivMobApiClient apiClient = new AtivMobApiClient();
            AtivMobBuilder builder = new AtivMobBuilder();
            Object payload = builder.buildPayloadMarcarLido(events);
            apiClient.post(resource + marcarLidoEndpoint, payload);
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    }
}
