package dev.vality.questionary.aggr.proxy.handler.dadata;

import dev.vality.questionary_proxy_aggr.dadata_api.DaDataRequest;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataResponse;

public interface DaDataRequestHandler {

    DaDataResponse handle(DaDataRequest request);

}
