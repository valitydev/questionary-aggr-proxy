package dev.vality.questionary.aggr.proxy.handler.kontur;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.vality.questionary.aggr.proxy.service.api.KonturFocusApi;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsQuery;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class KonturFocusEgrDetailsHandler extends AbstractKonturFocusHandler {

    public KonturFocusEgrDetailsHandler(KonturFocusApi konturFocusApi) {
        super(konturFocusApi);
    }

    @Override
    protected KonturFocusResponse handleRequest(KonturFocusRequest request) throws Exception {
        if (!request.isSetEgrDetailsQuery()) {
            throw new IllegalArgumentException("Need to specify egrDetails query");
        }
        final EgrDetailsQuery egrDetailsQuery = request.getEgrDetailsQuery();
        log.info("EgrDetailsQuery: {}", egrDetailsQuery);
        final ResponseEntity<String> responseEntity = konturFocusApi.egrDetailsRequest(egrDetailsQuery.getOgrn(), egrDetailsQuery.getInn());
        final List<EgrDetailsResponse> egrDetailsResponseList = getObjectMapper().readValue(responseEntity.getBody(),
                new TypeReference<List<EgrDetailsResponse>>() {
                });

        return KonturFocusResponse.egr_details_responses(new EgrDetailsResponses(egrDetailsResponseList));
    }
}
