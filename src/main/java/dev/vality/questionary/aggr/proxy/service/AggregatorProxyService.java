package dev.vality.questionary.aggr.proxy.service;

import dev.vality.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataRequest;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataResponse;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import org.apache.thrift.TException;

public interface AggregatorProxyService {

    DaDataResponse requestDaData(DaDataRequest request, DaDataEndpoint endpoint) throws TException;

    KonturFocusResponse requestKonturFocus(KonturFocusRequest request,
                                           KonturFocusEndPoint endPoint) throws TException;

}
