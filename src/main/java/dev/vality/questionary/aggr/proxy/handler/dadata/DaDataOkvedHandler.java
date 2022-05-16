package dev.vality.questionary.aggr.proxy.handler.dadata;

import dev.vality.questionary.aggr.proxy.exception.DaDataRequestException;
import dev.vality.questionary.aggr.proxy.handler.dadata.util.DaDataQueryMapper;
import dev.vality.questionary.aggr.proxy.serialize.model.OkvedContentWrapper;
import dev.vality.questionary.aggr.proxy.serialize.model.OkvedResponseWrapper;
import dev.vality.questionary.aggr.proxy.service.api.DaDataApi;
import dev.vality.questionary.aggr.proxy.service.api.model.DaDataQuery;
import dev.vality.questionary_proxy_aggr.base_dadata.QueryType;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataRequest;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataResponse;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedContent;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedQuery;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DaDataOkvedHandler extends AbstractDaDataHandler {

    public DaDataOkvedHandler(DaDataApi daDataApi) {
        super(daDataApi);
    }

    @Override
    protected DaDataResponse handleRequest(DaDataRequest request) throws Exception {
        if (!request.isSetOkvedQuery()) {
            throw new IllegalArgumentException("Need to specify okved query");
        }
        final OkvedQuery okvedQuery = request.getOkvedQuery();
        log.info("Convert okvedQuery to request obj: {}", okvedQuery);
        final DaDataQuery daDataQuery = DaDataQueryMapper.toQuery(okvedQuery);
        log.info("Converted okvedQuery: {}", daDataQuery);
        ResponseEntity<String> responseEntity = null;

        if (!okvedQuery.isSetQueryType()) {
            log.info("Query type not specified. 'Full text search' will be used");
            okvedQuery.setQueryType(QueryType.FULL_TEXT_SEARCH);
        }

        if (okvedQuery.getQueryType() == QueryType.FULL_TEXT_SEARCH) {
            log.info("Perform Okved request");
            responseEntity = daDataApi.okvedRequest(daDataQuery);
        } else if (okvedQuery.getQueryType() == QueryType.BY_INDENTIFIRE) {
            log.info("Perform Okved by id request");
            responseEntity = daDataApi.okvedByIdRequest(daDataQuery);
        } else {
            throw new DaDataRequestException("Unknown request type: " + okvedQuery.getQueryType());
        }

        log.info("Read DaData okved response");
        final OkvedResponseWrapper okvedResponseWrapper = getObjectMapper().readValue(responseEntity.getBody(), OkvedResponseWrapper.class);
        final List<OkvedContent> okvedContentList = okvedResponseWrapper.getOkvedContentWrapperList().stream()
                .map(OkvedContentWrapper::getOkvedContent)
                .collect(Collectors.toList());
        final OkvedResponse okvedResponse = new OkvedResponse();
        okvedResponse.setSuggestions(okvedContentList);

        return DaDataResponse.okved_response(okvedResponse);
    }
}
