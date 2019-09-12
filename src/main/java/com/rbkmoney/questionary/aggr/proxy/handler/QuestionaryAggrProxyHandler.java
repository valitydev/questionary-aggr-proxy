package com.rbkmoney.questionary.aggr.proxy.handler;

import com.rbkmoney.damsel.questionary_proxy_aggr.DaDataRequestException;
import com.rbkmoney.damsel.questionary_proxy_aggr.KonturFocusRequestException;
import com.rbkmoney.damsel.questionary_proxy_aggr.QuestionaryAggrProxyHandlerSrv;
import com.rbkmoney.questionary.aggr.proxy.service.AggregatorProxyServiceImpl;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionaryAggrProxyHandler implements QuestionaryAggrProxyHandlerSrv.Iface {

    private AggregatorProxyServiceImpl aggregatorProxyService;

    @Autowired
    public QuestionaryAggrProxyHandler(AggregatorProxyServiceImpl aggregatorProxyService) {
        this.aggregatorProxyService = aggregatorProxyService;
    }

    @Override
    public KonturFocusResponse requestKonturFocus(KonturFocusRequest request,
                                                  KonturFocusEndPoint endPoint) throws KonturFocusRequestException, TException {
        return aggregatorProxyService.requestKonturFocus(request, endPoint);
    }

    @Override
    public DaDataResponse requestDaData(DaDataRequest request,
                                        DaDataEndpoint endPoint) throws DaDataRequestException, TException {
        return aggregatorProxyService.requestDaData(request, endPoint);
    }
}
