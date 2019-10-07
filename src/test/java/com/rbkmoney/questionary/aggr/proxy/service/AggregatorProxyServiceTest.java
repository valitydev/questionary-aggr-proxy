package com.rbkmoney.questionary.aggr.proxy.service;

import com.rbkmoney.questionary.aggr.proxy.TestResponse;
import com.rbkmoney.questionary.aggr.proxy.service.api.DaDataApi;
import com.rbkmoney.questionary.aggr.proxy.service.api.KonturFocusApi;
import com.rbkmoney.questionary.aggr.proxy.service.api.model.DaDataQuery;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.QueryType;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedQuery;
import com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyQuery;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsQuery;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesQuery;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqQuery;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class AggregatorProxyServiceTest {

    private static final List<String> OGRN_LIST = Collections.emptyList();

    private static final List<String> INN_LIST = Arrays.asList("561100409545", "6663003127");

    private DaDataApi daDataApiMock;

    private KonturFocusApi konturFocusApiMock;

    private AggregatorProxyService aggregatorProxyService;

    @Before
    public void setUp() throws Exception {
        this.daDataApiMock = mock(DaDataApi.class);
        this.konturFocusApiMock = mock(KonturFocusApi.class);
        this.aggregatorProxyService = new AggregatorProxyServiceImpl(konturFocusApiMock, daDataApiMock);
    }

    @Test
    public void konturFocusReqTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.kfReqResp(), HttpStatus.OK);
        when(konturFocusApiMock.reqRequest(anyList(), anyList())).thenReturn(responseEntity);
        final KonturFocusRequest konturFocusRequest = new KonturFocusRequest();
        final ReqQuery reqQuery = new ReqQuery();
        reqQuery.setInn(INN_LIST);
        reqQuery.setOgrn(OGRN_LIST);
        konturFocusRequest.setReqQuery(reqQuery);
        final KonturFocusResponse konturFocusResponse = aggregatorProxyService.requestKonturFocus(konturFocusRequest, KonturFocusEndPoint.req);
        verify(konturFocusApiMock, atLeastOnce()).reqRequest(anyList(), anyList());
        Assert.assertNotNull(konturFocusResponse);
    }

    @Test
    public void konturFocusEgrDetailsTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.kfEgrDetails(), HttpStatus.OK);
        when(konturFocusApiMock.egrDetailsRequest(anyList(), anyList())).thenReturn(responseEntity);
        final KonturFocusRequest konturFocusRequest = new KonturFocusRequest();
        final EgrDetailsQuery egrDetailsQuery = new EgrDetailsQuery();
        egrDetailsQuery.setInn(INN_LIST);
        egrDetailsQuery.setOgrn(OGRN_LIST);
        konturFocusRequest.setEgrDetailsQuery(egrDetailsQuery);
        final KonturFocusResponse konturFocusResponse = aggregatorProxyService.requestKonturFocus(konturFocusRequest, KonturFocusEndPoint.egrDetails);
        verify(konturFocusApiMock, atLeastOnce()).egrDetailsRequest(anyList(), anyList());
        Assert.assertNotNull(konturFocusResponse);
    }

    @Test
    public void konturFocusLicenseTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.kfLicenseResp(), HttpStatus.OK);
        when(konturFocusApiMock.licenseRequest(anyList(), anyList())).thenReturn(responseEntity);
        final KonturFocusRequest konturFocusRequest = new KonturFocusRequest();
        final LicencesQuery licencesQuery = new LicencesQuery();
        licencesQuery.setInn(INN_LIST);
        licencesQuery.setOgrn(OGRN_LIST);
        konturFocusRequest.setLicencesQuery(licencesQuery);
        final KonturFocusResponse konturFocusResponse = aggregatorProxyService.requestKonturFocus(konturFocusRequest, KonturFocusEndPoint.licences);
        verify(konturFocusApiMock, atLeastOnce()).licenseRequest(anyList(), anyList());
        Assert.assertNotNull(konturFocusResponse);
    }

    @Test
    public void daDataPartyRequestTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.daDataParty(), HttpStatus.OK);
        when(daDataApiMock.partyRequest(any(DaDataQuery.class))).thenReturn(responseEntity);
        DaDataRequest daDataRequest = new DaDataRequest();
        daDataRequest.setPartyQuery(new PartyQuery());
        DaDataResponse daDataResponse = aggregatorProxyService.requestDaData(daDataRequest, DaDataEndpoint.suggest_party);
        verify(daDataApiMock, atLeastOnce()).partyRequest(any(DaDataQuery.class));
        Assert.assertNotNull(daDataResponse);
    }

    @Test
    public void daDataAddressRequestTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.daDataAddress(), HttpStatus.OK);
        when(daDataApiMock.addressRequest(any(DaDataQuery.class))).thenReturn(responseEntity);
        final DaDataRequest daDataRequest = new DaDataRequest();
        daDataRequest.setAddressQuery(new AddressQuery());
        final DaDataResponse daDataResponse = aggregatorProxyService.requestDaData(daDataRequest, DaDataEndpoint.suggest_address);
        verify(daDataApiMock, atLeastOnce()).addressRequest(any(DaDataQuery.class));
        Assert.assertNotNull(daDataResponse);
    }

    @Test
    public void daDataBankRequestTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.daDataBank(), HttpStatus.OK);
        when(daDataApiMock.bankRequest(any(DaDataQuery.class))).thenReturn(responseEntity);
        final DaDataRequest daDataRequest = new DaDataRequest();
        daDataRequest.setBankQuery(new BankQuery());
        final DaDataResponse daDataResponse = aggregatorProxyService.requestDaData(daDataRequest, DaDataEndpoint.suggest_bank);
        verify(daDataApiMock, atLeastOnce()).bankRequest(any(DaDataQuery.class));
        Assert.assertNotNull(daDataResponse);
    }

    @Test
    public void daDataFioRequestTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.daDataFio(), HttpStatus.OK);
        when(daDataApiMock.fioRequest(any(DaDataQuery.class))).thenReturn(responseEntity);
        final DaDataRequest daDataRequest = new DaDataRequest();
        daDataRequest.setFioQuery(new FioQuery());
        final DaDataResponse daDataResponse = aggregatorProxyService.requestDaData(daDataRequest, DaDataEndpoint.suggest_fio);
        verify(daDataApiMock, atLeastOnce()).fioRequest(any(DaDataQuery.class));
        Assert.assertNotNull(daDataResponse);
    }

    @Test
    public void daDataFmsUnitRequestTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.daDataFmsUnit(), HttpStatus.OK);
        when(daDataApiMock.fmsUnitRequest(any(DaDataQuery.class))).thenReturn(responseEntity);
        final DaDataRequest daDataRequest = new DaDataRequest();
        final FmsUnitQuery fmsUnitQuery = new FmsUnitQuery();
        fmsUnitQuery.setQueryType(QueryType.FULL_TEXT_SEARCH);
        daDataRequest.setFmsUnitQuery(fmsUnitQuery);
        final DaDataResponse daDataResponse = aggregatorProxyService.requestDaData(daDataRequest, DaDataEndpoint.suggest_fms_unit);
        verify(daDataApiMock, atLeastOnce()).fmsUnitRequest(any(DaDataQuery.class));
        Assert.assertNotNull(daDataResponse);
    }

    @Test
    public void daDataFmsUnitRequestByIdTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.daDataFmsUnit(), HttpStatus.OK);
        when(daDataApiMock.fmsUnitByIdRequest(any(DaDataQuery.class))).thenReturn(responseEntity);
        final DaDataRequest daDataRequest = new DaDataRequest();
        final FmsUnitQuery fmsUnitQuery = new FmsUnitQuery();
        fmsUnitQuery.setQueryType(QueryType.BY_INDENTIFIRE);
        daDataRequest.setFmsUnitQuery(fmsUnitQuery);
        final DaDataResponse daDataResponse = aggregatorProxyService.requestDaData(daDataRequest, DaDataEndpoint.suggest_fms_unit);
        verify(daDataApiMock, atLeastOnce()).fmsUnitByIdRequest(any(DaDataQuery.class));
        Assert.assertNotNull(daDataResponse);
    }

    @Test
    public void daDataOkvedRequestTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.daDataOkved(), HttpStatus.OK);
        when(daDataApiMock.okvedRequest(any(DaDataQuery.class))).thenReturn(responseEntity);
        final DaDataRequest daDataRequest = new DaDataRequest();
        final OkvedQuery okvedQuery = new OkvedQuery();
        okvedQuery.setQueryType(QueryType.FULL_TEXT_SEARCH);
        daDataRequest.setOkvedQuery(okvedQuery);
        final DaDataResponse daDataResponse = aggregatorProxyService.requestDaData(daDataRequest, DaDataEndpoint.okved2);
        verify(daDataApiMock, atLeastOnce()).okvedRequest(any(DaDataQuery.class));
        Assert.assertNotNull(daDataResponse);
    }

    @Test
    public void daDataOkvedRequestByIdTest() throws TException {
        final ResponseEntity<String> responseEntity = new ResponseEntity<>(TestResponse.daDataOkved(), HttpStatus.OK);
        when(daDataApiMock.okvedByIdRequest(any(DaDataQuery.class))).thenReturn(responseEntity);
        final DaDataRequest daDataRequest = new DaDataRequest();
        final OkvedQuery okvedQuery = new OkvedQuery();
        okvedQuery.setQueryType(QueryType.BY_INDENTIFIRE);
        daDataRequest.setOkvedQuery(okvedQuery);
        final DaDataResponse daDataResponse = aggregatorProxyService.requestDaData(daDataRequest, DaDataEndpoint.okved2);
        verify(daDataApiMock, atLeastOnce()).okvedByIdRequest(any(DaDataQuery.class));
        Assert.assertNotNull(daDataResponse);
    }

}
