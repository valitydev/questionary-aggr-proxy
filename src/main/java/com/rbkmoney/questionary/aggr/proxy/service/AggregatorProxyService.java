package com.rbkmoney.questionary.aggr.proxy.service;

import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import org.apache.thrift.TException;

public interface AggregatorProxyService {

    DaDataResponse requestDaData(DaDataRequest request, DaDataEndpoint endpoint) throws TException;

    KonturFocusResponse requestKonturFocus(KonturFocusRequest request,
                                           KonturFocusEndPoint endPoint) throws TException;

}
