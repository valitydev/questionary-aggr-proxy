package com.rbkmoney.questionary.aggr.proxy.service.api;

import com.rbkmoney.questionary.aggr.proxy.config.settings.KonturFocusSettings;
import com.rbkmoney.questionary.aggr.proxy.exception.KonturFocusRequestException;
import com.rbkmoney.questionary.aggr.proxy.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KonturFocusApi {

    private static final String REQ_URL = "https://focus-api.kontur.ru/api3/req?key={token}";

    private static final String EGR_DETAILS = "https://focus-api.kontur.ru/api3/egrDetails?key={token}";

    private static final String LICENCES = "https://focus-api.kontur.ru/api3/licences?key={token}";

    private static final String BENEFICIAL_OWNERS = "https://focus-api.kontur.ru/api3/beneficialOwners?key={token}";

    private final RestTemplate restTemplate;

    private final KonturFocusSettings konturFocusSettings;

    public ResponseEntity<String> reqRequest(List<String> ogrnList, List<String> innList) {
        final URI uri = buildUri(REQ_URL, ogrnList, innList);
        return sendRequest(uri, String.class);
    }

    public ResponseEntity<String> licenseRequest(List<String> ogrnList, List<String> innList) {
        final URI uri = buildUri(LICENCES, ogrnList, innList);
        return sendRequest(uri, String.class);
    }

    public ResponseEntity<String> egrDetailsRequest(List<String> ogrnList, List<String> innList) {
        final URI uri = buildUri(EGR_DETAILS, ogrnList, innList);
        return sendRequest(uri, String.class);
    }

    public ResponseEntity<String> beneficialOwnerRequest(List<String> ogrnList, List<String> innList) {
        final URI uri = buildUri(BENEFICIAL_OWNERS, ogrnList, innList);
        return sendRequest(uri, String.class);
    }

    private URI buildUri(String url, List<String> ogrnList, List<String> innList) {
        final Map<String, String> uriParams = new HashMap<>();
        uriParams.put("token", konturFocusSettings.getToken());
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        if (ogrnList != null && !ogrnList.isEmpty()) {
            uriComponentsBuilder.queryParam("ogrn", String.join(",", ogrnList));
        }
        if (innList != null && !innList.isEmpty()) {
            uriComponentsBuilder.queryParam("inn", String.join(",", innList));
        }

        return uriComponentsBuilder.buildAndExpand(uriParams).toUri();
    }

    private <T> ResponseEntity<T> sendRequest(URI uri, Class<T> responseType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<T> response = getResponse(uri, responseType, entity);
        HttpStatus statusCode = response.getStatusCode();
        switch (statusCode) {
            case OK:
                return response;
            case NOT_FOUND:
                throw new NotFoundException("Http client throw " + statusCode.name());
            default:
                throw new KonturFocusRequestException("Http client throw " + statusCode.name());
        }
    }

    private <T> ResponseEntity<T> getResponse(URI uri, Class<T> responseType, HttpEntity<?> entity) {
        try {
            return restTemplate.exchange(uri, HttpMethod.GET, entity, responseType);
        } catch (Exception ex) {
            throw new KonturFocusRequestException("Http client exchange exception", ex);
        }
    }
}
