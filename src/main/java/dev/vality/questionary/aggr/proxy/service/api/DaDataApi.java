package dev.vality.questionary.aggr.proxy.service.api;

import dev.vality.questionary.aggr.proxy.config.settings.DaDataSettings;
import dev.vality.questionary.aggr.proxy.exception.DaDataRequestException;
import dev.vality.questionary.aggr.proxy.exception.NotFoundException;
import dev.vality.questionary.aggr.proxy.service.api.model.DaDataQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    public ResponseEntity<String> addressRequest(DaDataQuery query) {
        return sendRequest(ADDRESS_URL, query, String.class);
    }

    public ResponseEntity<String> partyRequest(DaDataQuery query) {
        return sendRequest(PARTY_URL, query, String.class);
    }

    public ResponseEntity<String> bankRequest(DaDataQuery query) {
        return sendRequest(BANK_URL, query, String.class);
    }

    public ResponseEntity<String> fioRequest(DaDataQuery query) {
        return sendRequest(FIO_URL, query, String.class);
    }

    public ResponseEntity<String> fmsUnitRequest(DaDataQuery query) {
        return sendRequest(FMS_UNIT_URL, query, String.class);
    }

    public ResponseEntity<String> fmsUnitByIdRequest(DaDataQuery query) {
        return sendRequest(FMS_UNIT_BY_ID_URL, query, String.class);
    }

    public ResponseEntity<String> okvedRequest(DaDataQuery query) {
        return sendRequest(OKVED_URL, query, String.class);
    }

    public ResponseEntity<String> okvedByIdRequest(DaDataQuery query) {
        return sendRequest(OKVED_BY_ID_URL, query, String.class);
    }

    private <T, R> ResponseEntity<R> sendRequest(String url, T body, Class<R> responseType) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Token " + daDataSettings.getToken());
        final HttpEntity<T> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<R> response = getResponseEntity(url, responseType, httpEntity);
        HttpStatus statusCode = response.getStatusCode();
        switch (statusCode) {
            case OK:
                return response;
            case NOT_FOUND:
                throw new NotFoundException("Http client throw " + statusCode.name());
            default:
                throw new DaDataRequestException("Http client throw " + statusCode.name());
        }
    }

    private <T, R> ResponseEntity<R> getResponseEntity(String url, Class<R> responseType, HttpEntity<T> httpEntity) {
        try {
            return restTemplate.postForEntity(url, httpEntity, responseType);
        } catch (Exception ex) {
            throw new DaDataRequestException("Http client exchange exception", ex);
        }
    }
}
