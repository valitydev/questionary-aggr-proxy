package com.rbkmoney.questionary.aggr.proxy.service;

import com.rbkmoney.damsel.questionary_proxy_aggr.DaDataRequestException;
import com.rbkmoney.damsel.questionary_proxy_aggr.KonturFocusRequestException;
import com.rbkmoney.questionary.aggr.proxy.handler.dadata.*;
import com.rbkmoney.questionary.aggr.proxy.handler.kontur.KonturFocusEgrDetailsHandler;
import com.rbkmoney.questionary.aggr.proxy.handler.kontur.KonturFocusLicencesHandler;
import com.rbkmoney.questionary.aggr.proxy.handler.kontur.KonturFocusReqHandler;
import com.rbkmoney.questionary.aggr.proxy.handler.kontur.KonturFocusRequestHandler;
import com.rbkmoney.questionary.aggr.proxy.service.api.DaDataApi;
import com.rbkmoney.questionary.aggr.proxy.service.api.KonturFocusApi;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Service
public class AggregatorProxyServiceImpl implements AggregatorProxyService {

    private final Map<DaDataEndpoint, DaDataRequestHandler> daDataRequestHandlerMap = new EnumMap<>(DaDataEndpoint.class);

    private final Map<KonturFocusEndPoint, KonturFocusRequestHandler> konturFocusRequestHandlerMap = new EnumMap<>(KonturFocusEndPoint.class);

    private final DaDataApi daDataApi;

    private final KonturFocusApi konturFocusApi;

    @Autowired
    public AggregatorProxyServiceImpl(KonturFocusApi konturFocusApi,
                                      DaDataApi daDataApi) {
        this.konturFocusApi = konturFocusApi;
        this.daDataApi = daDataApi;
        initDaDataRequestHandler();
        initKonturFocusRequestHandler();
    }

    private void initDaDataRequestHandler() {
        daDataRequestHandlerMap.put(DaDataEndpoint.suggest_address, new DaDataAddressHandler(daDataApi));
        daDataRequestHandlerMap.put(DaDataEndpoint.suggest_party, new DaDataPartyHandler(daDataApi));
        daDataRequestHandlerMap.put(DaDataEndpoint.suggest_bank, new DaDataBankHandler(daDataApi));
        daDataRequestHandlerMap.put(DaDataEndpoint.suggest_fio, new DaDataFioHandler(daDataApi));
        daDataRequestHandlerMap.put(DaDataEndpoint.suggest_fms_unit, new DaDataFmsUnitHandler(daDataApi));
        daDataRequestHandlerMap.put(DaDataEndpoint.okved2, new DaDataOkvedHandler(daDataApi));
    }

    private void initKonturFocusRequestHandler() {
        konturFocusRequestHandlerMap.put(KonturFocusEndPoint.req, new KonturFocusReqHandler(konturFocusApi));
        konturFocusRequestHandlerMap.put(KonturFocusEndPoint.egrDetails, new KonturFocusEgrDetailsHandler(konturFocusApi));
        konturFocusRequestHandlerMap.put(KonturFocusEndPoint.licences, new KonturFocusLicencesHandler(konturFocusApi));
    }

    @Override
    public DaDataResponse requestDaData(DaDataRequest request, DaDataEndpoint endpoint) throws TException {
        log.info("Execute DaData request '{}'", endpoint);
        final DaDataRequestHandler daDataRequestHandler = daDataRequestHandlerMap.get(endpoint);
        if (daDataRequestHandler == null) {
            throw new TException("Unknown endpoint: " + endpoint);
        }
        return daDataRequestHandler.handle(request);
    }

    @Override
    public KonturFocusResponse requestKonturFocus(KonturFocusRequest request,
                                                  KonturFocusEndPoint endPoint) throws TException {
        log.info("Execute KonturFocus request '{}'", endPoint);
        final KonturFocusRequestHandler konturFocusRequestHandler = konturFocusRequestHandlerMap.get(endPoint);
        if (konturFocusRequestHandler == null) {
            throw new TException("Unknown endpoint: " + endPoint);
        }
        return konturFocusRequestHandler.handle(request);
    }

}
