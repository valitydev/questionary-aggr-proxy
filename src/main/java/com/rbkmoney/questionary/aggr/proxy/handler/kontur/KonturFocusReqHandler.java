package com.rbkmoney.questionary.aggr.proxy.handler.kontur;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rbkmoney.questionary.aggr.proxy.service.api.KonturFocusApi;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqQuery;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class KonturFocusReqHandler extends AbstractKonturFocusHandler {

    public KonturFocusReqHandler(KonturFocusApi konturFocusApi) {
        super(konturFocusApi);
    }

    @Override
    protected KonturFocusResponse handleRequest(KonturFocusRequest request) throws Exception {
        if (!request.isSetReqQuery()) {
            throw new IllegalArgumentException("Need to specify req query");
        }
        final ReqQuery reqQuery = request.getReqQuery();
        log.debug("ReqQuery: {}", reqQuery);
        final ResponseEntity<String> responseEntity = konturFocusApi.reqRequest(reqQuery.getOgrn(), reqQuery.getInn());

        final List<ReqResponse> regResponseList = getObjectMapper().readValue(responseEntity.getBody(), new TypeReference<List<ReqResponse>>() {
        });

        return KonturFocusResponse.req_responses(new ReqResponses(regResponseList));
    }
}
