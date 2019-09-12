package com.rbkmoney.questionary.aggr.proxy.handler.dadata;

import com.rbkmoney.questionary.aggr.proxy.handler.dadata.util.DaDataQueryMapper;
import com.rbkmoney.questionary.aggr.proxy.serialize.model.FioContentWrapper;
import com.rbkmoney.questionary.aggr.proxy.serialize.model.FioResponseWrapper;
import com.rbkmoney.questionary.aggr.proxy.service.api.DaDataApi;
import com.rbkmoney.questionary.aggr.proxy.service.api.model.DaDataQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioContent;
import com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DaDataFioHandler extends AbstractDaDataHandler {

    public DaDataFioHandler(DaDataApi daDataApi) {
        super(daDataApi);
    }

    @Override
    protected DaDataResponse handleRequest(DaDataRequest request) throws Exception {
        if (!request.isSetFioQuery()) {
            throw new IllegalArgumentException("Need to specify fio query");
        }
        final FioQuery fioQuery = request.getFioQuery();
        log.debug("FioQuery: {}", fioQuery);
        final DaDataQuery daDataQuery = DaDataQueryMapper.toQuery(fioQuery);
        log.debug("FioQuery after converting: {}", daDataQuery);
        final ResponseEntity<String> responseEntity = daDataApi.fioRequest(daDataQuery);
        final FioResponseWrapper fioResponseWrapper = getObjectMapper().readValue(responseEntity.getBody(), FioResponseWrapper.class);
        final List<FioContent> fioContents = fioResponseWrapper.getFioContentWrapperList().stream()
                .map(FioContentWrapper::getFioContent)
                .collect(Collectors.toList());
        final FioResponse fioResponse = new FioResponse();
        fioResponse.setSuggestions(fioContents);

        return DaDataResponse.fio_response(fioResponse);
    }
}
