package com.rbkmoney.questionary.aggr.proxy.handler.kontur;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rbkmoney.questionary.aggr.proxy.service.api.KonturFocusApi;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesQuery;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class KonturFocusLicencesHandler extends AbstractKonturFocusHandler {

    public KonturFocusLicencesHandler(KonturFocusApi konturFocusApi) {
        super(konturFocusApi);
    }

    @Override
    protected KonturFocusResponse handleRequest(KonturFocusRequest request) throws Exception {
        if (!request.isSetLicencesQuery()) {
            throw new IllegalArgumentException("Need to specify licences query");
        }
        final LicencesQuery licencesQuery = request.getLicencesQuery();
        log.info("LicensesQuery: {}", licencesQuery);
        final ResponseEntity<String> responseEntity = konturFocusApi.licenseRequest(licencesQuery.getOgrn(), licencesQuery.getInn());
        final List<LicencesResponse> licencesResponseList = getObjectMapper().readValue(responseEntity.getBody(),
                new TypeReference<List<LicencesResponse>>() {
                });

        return KonturFocusResponse.licences_responses(new LicencesResponses(licencesResponseList));
    }
}
