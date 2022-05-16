package dev.vality.questionary.aggr.proxy.handler.dadata;

import dev.vality.questionary.aggr.proxy.exception.DaDataRequestException;
import dev.vality.questionary.aggr.proxy.handler.dadata.util.DaDataQueryMapper;
import dev.vality.questionary.aggr.proxy.serialize.model.FmsUnitContentWrapper;
import dev.vality.questionary.aggr.proxy.serialize.model.FmsUnitResponseWrapper;
import dev.vality.questionary.aggr.proxy.service.api.DaDataApi;
import dev.vality.questionary.aggr.proxy.service.api.model.DaDataQuery;
import dev.vality.questionary_proxy_aggr.base_dadata.QueryType;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataRequest;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataResponse;
import dev.vality.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent;
import dev.vality.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQuery;
import dev.vality.questionary_proxy_aggr.dadata_fms_unit.FmsUnitResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DaDataFmsUnitHandler extends AbstractDaDataHandler {

    public DaDataFmsUnitHandler(DaDataApi daDataApi) {
        super(daDataApi);
    }

    @Override
    protected DaDataResponse handleRequest(DaDataRequest request) throws Exception {
        if (!request.isSetFmsUnitQuery()) {
            throw new IllegalArgumentException("Need to specify fms unit query");
        }
        final FmsUnitQuery fmsUnitQuery = request.getFmsUnitQuery();
        final DaDataQuery dataQuery = DaDataQueryMapper.toQuery(fmsUnitQuery);
        ResponseEntity<String> responseEntity = null;

        if (!fmsUnitQuery.isSetQueryType()) {
            fmsUnitQuery.setQueryType(QueryType.FULL_TEXT_SEARCH);
        }

        if (fmsUnitQuery.getQueryType() == QueryType.FULL_TEXT_SEARCH) {
            responseEntity = daDataApi.fmsUnitRequest(dataQuery);
        } else if (fmsUnitQuery.getQueryType() == QueryType.BY_INDENTIFIRE) {
            responseEntity = daDataApi.fmsUnitByIdRequest(dataQuery);
        } else {
            throw new DaDataRequestException("Unknown request type: {}" + fmsUnitQuery.getQueryType());
        }
        final FmsUnitResponseWrapper fmsUnitResponseWrapper =
                getObjectMapper().readValue(responseEntity.getBody(), FmsUnitResponseWrapper.class);
        final List<FmsUnitContent> fmsUnitContentList = fmsUnitResponseWrapper.getFmsUnitContentWrapperList().stream()
                .map(FmsUnitContentWrapper::getFmsUnitContent)
                .collect(Collectors.toList());
        final FmsUnitResponse fmsUnitResponse = new FmsUnitResponse();
        fmsUnitResponse.setSuggestions(fmsUnitContentList);

        return DaDataResponse.fms_unit_response(fmsUnitResponse);
    }
}
