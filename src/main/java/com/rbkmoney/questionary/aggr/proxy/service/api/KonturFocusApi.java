package com.rbkmoney.questionary.aggr.proxy.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.damsel.questionary_proxy_aggr.KonturFocusRequestException;
import com.rbkmoney.questionary.aggr.proxy.config.settings.KonturFocusSettings;
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
public class KonturFocusApi {

    private static final String REQ_URL = "https://focus-api.kontur.ru/api3/req?key={token}";

    private static final String EGR_DETAILS = "https://focus-api.kontur.ru/api3/egrDetails?key={token}";

    private static final String LICENCES = "https://focus-api.kontur.ru/api3/licences?key={token}";

    private final RestTemplate restTemplate;

    private final KonturFocusSettings konturFocusSettings;

    public KonturFocusApi(RestTemplate restTemplate,
                          KonturFocusSettings konturFocusSettings,
                          ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.konturFocusSettings = konturFocusSettings;
    }

    public ResponseEntity<String> reqRequest(List<String> ogrnList, List<String> innList) throws KonturFocusRequestException {
        final URI uri = buildUri(REQ_URL, ogrnList, innList);
        return sendRequest(uri, String.class);
    }

    public ResponseEntity<String> licenseRequest(List<String> ogrnList, List<String> innList) throws KonturFocusRequestException {
        final URI uri = buildUri(LICENCES, ogrnList, innList);
        return sendRequest(uri, String.class);
    }

    public ResponseEntity<String> egrDetailsRequest(List<String> ogrnList, List<String> innList) throws KonturFocusRequestException {
        final URI uri = buildUri(EGR_DETAILS, ogrnList, innList);
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

    private <T> ResponseEntity<T> sendRequest(URI uri, Class<T> responseType) throws KonturFocusRequestException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            return restTemplate.exchange(uri, HttpMethod.GET, entity, responseType);
        } catch (Exception e) {
            log.error("Request exception", e);
            throw new KonturFocusRequestException(e.getMessage());
        }
    }

}
