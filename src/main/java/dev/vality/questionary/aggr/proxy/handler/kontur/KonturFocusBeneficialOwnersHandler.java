package dev.vality.questionary.aggr.proxy.handler.kontur;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.vality.questionary.aggr.proxy.service.api.KonturFocusApi;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import dev.vality.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerQuery;
import dev.vality.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerResponse;
import dev.vality.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class KonturFocusBeneficialOwnersHandler extends AbstractKonturFocusHandler {

    public KonturFocusBeneficialOwnersHandler(KonturFocusApi konturFocusApi) {
        super(konturFocusApi);
    }

    @Override
    protected KonturFocusResponse handleRequest(KonturFocusRequest request) throws Exception {
        if (!request.isSetBeneficialOwnerQuery()) {
            throw new IllegalArgumentException("Need to specify beneficialOwner query");
        }
        final BeneficialOwnerQuery beneficialOwnerQuery = request.getBeneficialOwnerQuery();
        log.info("BeneficialOwner query {}", beneficialOwnerQuery);
        final ResponseEntity<String> responseEntity = konturFocusApi.beneficialOwnerRequest(
                beneficialOwnerQuery.getOgrn(), beneficialOwnerQuery.getInn());
        final List<BeneficialOwnerResponse> beneficialOwnerResponseList =
                getObjectMapper().readValue(responseEntity.getBody(),
                        new TypeReference<List<BeneficialOwnerResponse>>() {
                        });

        return KonturFocusResponse.beneficial_owner_responses(
                new BeneficialOwnerResponses(beneficialOwnerResponseList));
    }

}
