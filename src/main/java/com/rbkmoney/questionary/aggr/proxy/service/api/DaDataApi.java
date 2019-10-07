package com.rbkmoney.questionary.aggr.proxy.service.api;

import com.rbkmoney.damsel.questionary_proxy_aggr.DaDataRequestException;
import com.rbkmoney.questionary.aggr.proxy.config.settings.DaDataSettings;
import com.rbkmoney.questionary.aggr.proxy.service.api.model.DaDataQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class DaDataApi {

    private static final String ADDRESS_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";

    private static final String PARTY_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/party";

    private static final String BANK_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/bank";

    private static final String FIO_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/fio";

    private static final String FMS_UNIT_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/fms_unit";

    private static final String FMS_UNIT_BY_ID_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/fms_unit";

    private static final String OKVED_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/okved2";

    private static final String OKVED_BY_ID_URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/okved2";

    private final RestTemplate restTemplate;

    private final DaDataSettings daDataSettings;

    @Autowired
    public DaDataApi(RestTemplate restTemplate,
                     DaDataSettings daDataSettings) {
        this.restTemplate = restTemplate;
        this.daDataSettings = daDataSettings;
    }

    public ResponseEntity<String> addressRequest(DaDataQuery query) throws DaDataRequestException {
        return sendRequest(ADDRESS_URL, query, String.class);
    }

    public ResponseEntity<String> partyRequest(DaDataQuery query) throws DaDataRequestException {
        return sendRequest(PARTY_URL, query, String.class);
    }

    public ResponseEntity<String> bankRequest(DaDataQuery query) throws DaDataRequestException {
        return sendRequest(BANK_URL, query, String.class);
    }

    public ResponseEntity<String> fioRequest(DaDataQuery query) throws DaDataRequestException {
        return sendRequest(FIO_URL, query, String.class);
    }

    public ResponseEntity<String> fmsUnitRequest(DaDataQuery query) throws DaDataRequestException {
        return sendRequest(FMS_UNIT_URL, query, String.class);
    }

    public ResponseEntity<String> fmsUnitByIdRequest(DaDataQuery query) throws DaDataRequestException {
        return sendRequest(FMS_UNIT_BY_ID_URL, query, String.class);
    }

    public ResponseEntity<String> okvedRequest(DaDataQuery query) throws DaDataRequestException {
        return sendRequest(OKVED_URL, query, String.class);
    }

    public ResponseEntity<String> okvedByIdRequest(DaDataQuery query) throws DaDataRequestException {
        return sendRequest(OKVED_BY_ID_URL, query, String.class);
    }

    private <T, R> ResponseEntity<R> sendRequest(String url, T body, Class<R> responseType) throws DaDataRequestException {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Token " + daDataSettings.getToken());
        final HttpEntity<T> httpEntity = new HttpEntity<>(body, httpHeaders);
        try {
            return restTemplate.postForEntity(url, httpEntity, responseType);
        } catch (Exception e) {
            log.error("Request exception", e);
            throw new DaDataRequestException(e.getMessage());
        }
    }

}
