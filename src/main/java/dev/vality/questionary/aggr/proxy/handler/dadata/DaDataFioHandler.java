package dev.vality.questionary.aggr.proxy.handler.dadata;

import dev.vality.questionary.aggr.proxy.handler.dadata.util.DaDataQueryMapper;
import dev.vality.questionary.aggr.proxy.serialize.model.FioContentWrapper;
import dev.vality.questionary.aggr.proxy.serialize.model.FioResponseWrapper;
import dev.vality.questionary.aggr.proxy.service.api.DaDataApi;
import dev.vality.questionary.aggr.proxy.service.api.model.DaDataQuery;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataRequest;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataResponse;
import dev.vality.questionary_proxy_aggr.dadata_fio.FioContent;
import dev.vality.questionary_proxy_aggr.dadata_fio.FioQuery;
import dev.vality.questionary_proxy_aggr.dadata_fio.FioResponse;
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
        log.info("Convert fioQuery to request obj: {}", fioQuery);
        final DaDataQuery daDataQuery = DaDataQueryMapper.toQuery(fioQuery);
        log.info("Converted fioQuery: {}", daDataQuery);
        final ResponseEntity<String> responseEntity = daDataApi.fioRequest(daDataQuery);
        log.info("Read DaData fio response");
        final FioResponseWrapper fioResponseWrapper =
                getObjectMapper().readValue(responseEntity.getBody(), FioResponseWrapper.class);
        final List<FioContent> fioContents = fioResponseWrapper.getFioContentWrapperList().stream()
                .map(FioContentWrapper::getFioContent)
                .collect(Collectors.toList());
        final FioResponse fioResponse = new FioResponse();
        fioResponse.setSuggestions(fioContents);

        return DaDataResponse.fio_response(fioResponse);
    }
}
