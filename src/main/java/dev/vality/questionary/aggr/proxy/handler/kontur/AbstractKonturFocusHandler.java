package dev.vality.questionary.aggr.proxy.handler.kontur;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.vality.questionary.aggr.proxy.serialize.kontur.*;
import dev.vality.questionary.aggr.proxy.service.api.KonturFocusApi;
import dev.vality.questionary_proxy_aggr.base_kontur_focus.Branch;
import dev.vality.questionary_proxy_aggr.base_kontur_focus.LegalAddress;
import dev.vality.questionary_proxy_aggr.base_kontur_focus.LegalName;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import dev.vality.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwners;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsHistory;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsLegalEntity;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolders;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.LegalEntityStatusDetail;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.PrivateEntityStatusDetail;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.ReqIndividualEntity;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.ReqResponse;
import dev.vality.questionary.aggr.proxy.serialize.kontur.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractKonturFocusHandler implements KonturFocusRequestHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        final SimpleModule simpleModule = new SimpleModule();
        // Reg deserializer
        simpleModule.addDeserializer(LegalEntityStatusDetail.class, new KonturLegalStatusDetailDeserializer());
        simpleModule.addDeserializer(Branch.class, new KonturBranchDeserializer());
        simpleModule.addDeserializer(LegalAddress.class, new KonturLegalAddressDeserializer());
        simpleModule.addDeserializer(LegalName.class, new KonturLegalNameDeserializer());
        simpleModule.addDeserializer(PrivateEntityStatusDetail.class, new KonturIPStatusDeserializer());
        simpleModule.addDeserializer(ReqResponse.class, new KonturReqResponseDeserializer());
        simpleModule.addDeserializer(ReqIndividualEntity.class, new KonturReqIPDeserializer());
        simpleModule.addDeserializer(ShareHolders.class, new KonturShareHoldersDeserializer());
        simpleModule.addDeserializer(EgrDetailsLegalEntity.class, new KonturEgrDetailsLegalEntityDeserializer());
        simpleModule.addDeserializer(EgrDetailsHistory.class, new KonturEgrDetailsHistoryDeserializer());
        simpleModule.addDeserializer(BeneficialOwners.class, new KonturBeneficialOwnersDeserializer());

        // EgrDetails deserializer
        simpleModule.addDeserializer(EgrDetailsResponse.class, new KonturEgrDetailsResponseDeserializer());

        OBJECT_MAPPER.registerModule(simpleModule);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected final KonturFocusApi konturFocusApi;
    protected final Logger log;

    protected AbstractKonturFocusHandler(KonturFocusApi konturFocusApi) {
        this.konturFocusApi = konturFocusApi;
        this.log = LoggerFactory.getLogger(getClass());
    }

    static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    @Override
    public final KonturFocusResponse handle(KonturFocusRequest request) {
        try {
            return handleRequest(request);
        } catch (Exception e) {
            log.error("Exception while handling request", e);
            throw new KonturFocusHandlerException("Unexpected exception", e);
        }
    }

    protected abstract KonturFocusResponse handleRequest(KonturFocusRequest request) throws Exception;
}
