package dev.vality.questionary.aggr.proxy.handler.dadata;

import dev.vality.questionary.aggr.proxy.handler.dadata.util.DaDataQueryMapper;
import dev.vality.questionary.aggr.proxy.serialize.model.PartyContentWrapper;
import dev.vality.questionary.aggr.proxy.serialize.model.PartyResponseWrapper;
import dev.vality.questionary.aggr.proxy.service.api.DaDataApi;
import dev.vality.questionary.aggr.proxy.service.api.model.DaDataQuery;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataRequest;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataResponse;
import dev.vality.questionary_proxy_aggr.dadata_party.PartyContent;
import dev.vality.questionary_proxy_aggr.dadata_party.PartyQuery;
import dev.vality.questionary_proxy_aggr.dadata_party.PartyResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DaDataPartyHandler extends AbstractDaDataHandler {

    public DaDataPartyHandler(DaDataApi daDataApi) {
        super(daDataApi);
    }

    @Override
    protected DaDataResponse handleRequest(DaDataRequest request) throws Exception {
        if (!request.isSetPartyQuery()) {
            throw new IllegalArgumentException("Need to specify party query");
        }
        final PartyQuery partyQuery = request.getPartyQuery();
        log.info("Convert partyQuery to request obj: {}", partyQuery);
        final DaDataQuery daDataQuery = DaDataQueryMapper.toQuery(partyQuery);
        log.info("Converted partyQuery: {}", daDataQuery);
        final ResponseEntity<String> responseEntity = daDataApi.partyRequest(daDataQuery);
        log.info("Read DaData party response");
        final PartyResponseWrapper partyResponseWrapper =
                getObjectMapper().readValue(responseEntity.getBody(), PartyResponseWrapper.class);
        final List<PartyContent> partyContentList = partyResponseWrapper.getPartyContentWrapperList().stream()
                .map(PartyContentWrapper::getPartyContent)
                .collect(Collectors.toList());
        final PartyResponse partyResponse = new PartyResponse();
        partyResponse.setSuggestions(partyContentList);

        return DaDataResponse.party_response(partyResponse);
    }
}
