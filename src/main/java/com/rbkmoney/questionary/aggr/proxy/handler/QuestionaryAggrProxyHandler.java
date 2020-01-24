package com.rbkmoney.questionary.aggr.proxy.handler;

import com.rbkmoney.damsel.questionary_proxy_aggr.*;
import com.rbkmoney.questionary.aggr.proxy.exception.DaDataRequestException;
import com.rbkmoney.questionary.aggr.proxy.exception.KonturFocusRequestException;
import com.rbkmoney.questionary.aggr.proxy.exception.NotFoundException;
import com.rbkmoney.questionary.aggr.proxy.service.AggregatorProxyServiceImpl;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import com.rbkmoney.woody.api.flow.error.WUndefinedResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class QuestionaryAggrProxyHandler implements QuestionaryAggrProxyHandlerSrv.Iface {

    private final AggregatorProxyServiceImpl aggregatorProxyService;


    @Override
    public KonturFocusResponse requestKonturFocus(KonturFocusRequest request,
                                                  KonturFocusEndPoint endPoint) throws KonturFocusInvalidRequest, KonturFocusNotFound, org.apache.thrift.TException {
        try {
            return aggregatorProxyService.requestKonturFocus(request, endPoint);
        } catch (KonturFocusRequestException ex) {
            log.warn("Request exception", ex);
            throw new KonturFocusInvalidRequest(ex.getMessage());
        } catch (NotFoundException ex) {
            log.warn("Not found", ex);
            throw new KonturFocusNotFound();
        } catch (Exception ex) {
            log.warn("Error then 'requestKonturFocus'", ex);
            throw new WUndefinedResultException(ex);
        }
    }

    @Override
    public DaDataResponse requestDaData(DaDataRequest request,
                                        DaDataEndpoint endPoint) throws DaDataInvalidRequest, DaDataNotFound, org.apache.thrift.TException {
        try {
            return aggregatorProxyService.requestDaData(request, endPoint);
        } catch (DaDataRequestException ex) {
            log.warn("Request exception", ex);
            throw new DaDataInvalidRequest(ex.getMessage());
        } catch (NotFoundException ex) {
            log.warn("Not found", ex);
            throw new DaDataNotFound();
        } catch (Exception ex) {
            log.warn("Error then 'requestDaData'", ex);
            throw new WUndefinedResultException(ex);
        }
    }
}
