package br.com.a2dm.spdm.ativmob.service;

import br.com.a2dm.brcmn.dto.ClienteDTO;
import br.com.a2dm.brcmn.dto.ativmob.EventsDTO;
import br.com.a2dm.spdm.ativmob.repository.AtivMobRepository;
import br.com.a2dm.spdm.omie.repository.OmieClientesRepository;
import br.com.a2dm.spdm.omie.service.OmieClienteService;
import br.com.a2dm.spdm.omie.service.OmieServiceException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AtivMobService {

    private static AtivMobService instance;

    private AtivMobService() {
    }

    public static AtivMobService getInstance() {
        if (instance == null) {
            instance = new AtivMobService();
        }
        return instance;
    }

    public EventsDTO buscarEventos(BigInteger cnpj) throws AtivMobServiceException {
        try {
            return AtivMobRepository.getInstance().buscarEventos(cnpj);
        } catch (Exception e) {
            throw new AtivMobServiceException(e);
        }
    }
}
