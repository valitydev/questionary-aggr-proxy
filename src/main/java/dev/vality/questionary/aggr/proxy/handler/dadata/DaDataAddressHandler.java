package dev.vality.questionary.aggr.proxy.handler.dadata;

import dev.vality.questionary.aggr.proxy.handler.dadata.util.DaDataQueryMapper;
import dev.vality.questionary.aggr.proxy.service.api.DaDataApi;
import dev.vality.questionary.aggr.proxy.service.api.model.DaDataQuery;
import dev.vality.questionary_proxy_aggr.dadata_address.AddressQuery;
import dev.vality.questionary_proxy_aggr.dadata_address.AddressResponse;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataRequest;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataResponse;
import org.springframework.http.ResponseEntity;

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
        log.info("Convert addressQuery to request obj: {}", addressQuery);
        final DaDataQuery daDataQuery = DaDataQueryMapper.toQuery(addressQuery);
        log.info("Converted addressQuery: {}", daDataQuery);
        final ResponseEntity<String> responseEntity = daDataApi.addressRequest(daDataQuery);

        log.info("Read DaData address response");
        final AddressResponse addressResponse = getObjectMapper().readValue(responseEntity.getBody(), AddressResponse.class);

        return DaDataResponse.address_response(addressResponse);
    }


}
