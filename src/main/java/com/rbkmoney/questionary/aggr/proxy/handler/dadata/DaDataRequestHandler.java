package com.rbkmoney.questionary.aggr.proxy.handler.dadata;

import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;

public interface DaDataRequestHandler {

    DaDataResponse handle(DaDataRequest request);

}
