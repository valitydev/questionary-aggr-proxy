package com.rbkmoney.questionary.aggr.proxy.handler.dadata;

import com.rbkmoney.questionary.aggr.proxy.handler.dadata.util.DaDataQueryMapper;
import com.rbkmoney.questionary.aggr.proxy.service.api.DaDataApi;
import com.rbkmoney.questionary.aggr.proxy.service.api.model.DaDataQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class DaDataAddressHandler extends AbstractDaDataHandler {

    public DaDataAddressHandler(DaDataApi daDataApi) {
        super(daDataApi);
    }

    @Override
    protected DaDataResponse handleRequest(DaDataRequest request) throws Exception {
        if (!request.isSetAddressQuery()) {
            throw new IllegalArgumentException("Need to specify address query");
        }
        final AddressQuery addressQuery = request.getAddressQuery();
        log.debug("AddressQuery: {}", addressQuery);
        final DaDataQuery daDataQuery = DaDataQueryMapper.toQuery(addressQuery);
        log.debug("AddressQuery after converting: {}", daDataQuery);
        final ResponseEntity<String> responseEntity = daDataApi.addressRequest(daDataQuery);

        final AddressResponse addressResponse = getObjectMapper().readValue(responseEntity.getBody(), AddressResponse.class);

        return DaDataResponse.address_response(addressResponse);
    }


}
