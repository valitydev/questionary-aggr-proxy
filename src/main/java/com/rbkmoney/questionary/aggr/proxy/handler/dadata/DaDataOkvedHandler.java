package com.rbkmoney.questionary.aggr.proxy.handler.dadata;

import com.rbkmoney.damsel.questionary_proxy_aggr.DaDataRequestException;
import com.rbkmoney.questionary.aggr.proxy.handler.dadata.util.DaDataQueryMapper;
import com.rbkmoney.questionary.aggr.proxy.serialize.model.OkvedContentWrapper;
import com.rbkmoney.questionary.aggr.proxy.serialize.model.OkvedResponseWrapper;
import com.rbkmoney.questionary.aggr.proxy.service.api.DaDataApi;
import com.rbkmoney.questionary.aggr.proxy.service.api.model.DaDataQuery;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.QueryType;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedContent;
import com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedResponse;
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
        log.debug("OkvedQuery: {}", okvedQuery);
        final DaDataQuery daDataQuery = DaDataQueryMapper.toQuery(okvedQuery);
        log.debug("OkvedQuery after converting: {}", daDataQuery);
        ResponseEntity<String> responseEntity = null;

        if (!okvedQuery.isSetQueryType()) {
            okvedQuery.setQueryType(QueryType.FULL_TEXT_SEARCH);
        }

        if (okvedQuery.getQueryType() == QueryType.FULL_TEXT_SEARCH) {
            responseEntity = daDataApi.okvedRequest(daDataQuery);
        } else if (okvedQuery.getQueryType() == QueryType.BY_INDENTIFIRE) {
            responseEntity = daDataApi.okvedByIdRequest(daDataQuery);
        } else {
            throw new DaDataRequestException("Unknown request type: " + okvedQuery.getQueryType());
        }

        final OkvedResponseWrapper okvedResponseWrapper = getObjectMapper().readValue(responseEntity.getBody(), OkvedResponseWrapper.class);
        final List<OkvedContent> okvedContentList = okvedResponseWrapper.getOkvedContentWrapperList().stream()
                .map(OkvedContentWrapper::getOkvedContent)
                .collect(Collectors.toList());
        final OkvedResponse okvedResponse = new OkvedResponse();
        okvedResponse.setSuggestions(okvedContentList);

        return DaDataResponse.okved_response(okvedResponse);
    }
}
