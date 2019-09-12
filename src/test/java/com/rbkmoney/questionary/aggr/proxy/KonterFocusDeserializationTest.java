package com.rbkmoney.questionary.aggr.proxy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rbkmoney.questionary.aggr.proxy.serialize.kontur.*;
import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch;
import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalAddress;
import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalName;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsIndividualEntity;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrRecord;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.License;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.LegalEntityStatusDetail;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.PrivateEntityStatusDetail;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqIndividualEntity;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class KonterFocusDeserializationTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void kfLicenseDeserializerTest() throws IOException {
        final List<LicencesResponse> licencesResponseList = objectMapper.readValue(TestResponse.kfLicenseResp(),
                new TypeReference<List<LicencesResponse>>() {
                });
        Assert.assertEquals(2, licencesResponseList.size());
        final LicencesResponse licencesResponse = licencesResponseList.get(1);
        Assert.assertEquals("6663003127", licencesResponse.getInn());
        Assert.assertEquals("1026605606620", licencesResponse.getOgrn());
        Assert.assertEquals("https://focus.kontur.ru/entity?query=1026605606620", licencesResponse.getFocusHref());
        Assert.assertEquals(7, licencesResponse.getLicenses().size());
        final License license = licencesResponse.getLicenses().get(0);
        Assert.assertEquals("№ 139066", license.getOfficialNum());
        Assert.assertEquals("2016-03-29", license.getDateStart());
        Assert.assertEquals("2021-03-29", license.getDateEnd());
        Assert.assertEquals("Действующая", license.getStatusDescription());
        Assert.assertEquals("Телематические услуги связи", license.getActivity());
    }

    @Test
    public void kfReqDeserializerTest() throws IOException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(LegalEntityStatusDetail.class, new KonturLegalStatusDetailDeserializer());
        simpleModule.addDeserializer(Branch.class, new KonturBranchDeserializer());
        simpleModule.addDeserializer(LegalAddress.class, new KonturLegalAddressDeserializer());
        simpleModule.addDeserializer(LegalName.class, new KonturLegalNameDeserializer());
        simpleModule.addDeserializer(PrivateEntityStatusDetail.class, new KonturIPStatusDeserializer());
        simpleModule.addDeserializer(ReqResponse.class, new KonturReqResponseDeserializer());
        simpleModule.addDeserializer(ReqIndividualEntity.class, new KonturReqIPDeserializer());
        objectMapper.registerModule(simpleModule);
        final List<ReqResponse> regResponseList = objectMapper.readValue(TestResponse.kfReqResp(),
                new TypeReference<List<ReqResponse>>() {
                });
        Assert.assertEquals(2, regResponseList.size());
        final ReqResponse ipRegResponse = regResponseList.get(0);
        Assert.assertEquals("561100409545", ipRegResponse.getInn());
        Assert.assertEquals("309565808600188", ipRegResponse.getOgrn());
        Assert.assertEquals("https://focus.kontur.ru/entity?query=309565808600188", ipRegResponse.getFocusHref());
        final ReqIndividualEntity individualEntity = ipRegResponse.getPrivateEntity().getIndividualEntity();
        Assert.assertEquals("Иванов Иван Иванович", individualEntity.getFio());
        Assert.assertEquals("0164667059", individualEntity.getOkpo());
        Assert.assertEquals("53401364000", individualEntity.getOkato());
        Assert.assertEquals("16", individualEntity.getOkfs());
        Assert.assertEquals("4210015", individualEntity.getOkogu());
        Assert.assertEquals("50102", individualEntity.getOkopf());
        Assert.assertEquals("Индивидуальные предприниматели", individualEntity.getOpf());
        Assert.assertEquals("53701000001", individualEntity.getOktmo());
        Assert.assertEquals("2009-03-27", individualEntity.getRegistrationDate());
        Assert.assertEquals("2018-01-10", individualEntity.getDissolutionDate());
        Assert.assertEquals("Индивидуальный предприниматель прекратил деятельность в связи с принятием" +
                " им соответствующего решения", individualEntity.getStatusDetail().getStatus());
        Assert.assertTrue(individualEntity.getStatusDetail().isDissolved());
        Assert.assertEquals("2018-01-10", individualEntity.getStatusDetail().getDate());
    }

    @Test
    public void kfEgrDetailsDeserializerTest() throws IOException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(EgrDetailsResponse.class, new KonturEgrDetailsResponseDeserializer());
        objectMapper.registerModule(simpleModule);
        final List<EgrDetailsResponse> egrDetailsReponseList = objectMapper.readValue(TestResponse.kfEgrDetails(),
                new TypeReference<List<EgrDetailsResponse>>() {
                });
        Assert.assertEquals(2, egrDetailsReponseList.size());
        final EgrDetailsResponse egrDetailsResponse = egrDetailsReponseList.get(0);
        Assert.assertEquals("561100409545", egrDetailsResponse.getInn());
        Assert.assertEquals("309565808600188", egrDetailsResponse.getOgrn());
        Assert.assertEquals("https://focus.kontur.ru/entity?query=309565808600188", egrDetailsResponse.getFocusHref());
        final EgrDetailsIndividualEntity individualEntity = egrDetailsResponse.getContractor().getIndividualEntity();
        Assert.assertEquals("56", individualEntity.getShortenedAddress().getRegionCode());
        Assert.assertEquals("обл", individualEntity.getShortenedAddress().getRegionName().getTopoShortName());
        Assert.assertEquals("область", individualEntity.getShortenedAddress().getRegionName().getTopoFullName());
        Assert.assertEquals("Оренбургская", individualEntity.getShortenedAddress().getRegionName().getTopoValue());
        Assert.assertEquals("47.19", individualEntity.getActivities().getPrincipalActivity().getCode());
        Assert.assertEquals("Торговля розничная прочая в неспециализированных магазинах",
                individualEntity.getActivities().getPrincipalActivity().getText());
        Assert.assertEquals("2009-03-27", individualEntity.getActivities().getPrincipalActivity().getDate());
        Assert.assertEquals("2009-03-27", individualEntity.getRegInfo().getOgrnDate());
        Assert.assertEquals("5610", individualEntity.getNalogRegBody().getNalogCode());
        Assert.assertEquals("Инспекция Федеральной налоговой службы по Ленинскому району г.Оренбурга",
                individualEntity.getNalogRegBody().getNalogName());
        Assert.assertEquals("2009-03-27", individualEntity.getNalogRegBody().getNalogRegDate());
        Assert.assertEquals("2018-01-10", individualEntity.getNalogRegBody().getDate());
        final EgrRecord egrRecord = individualEntity.getEgrRecords().get(0);
        Assert.assertEquals("418565800035544", egrRecord.getGrn());
        Assert.assertEquals("2018-01-10", egrRecord.getDate());
        Assert.assertEquals("Представление сведений об учете в налоговом органе", egrRecord.getName());
        Assert.assertEquals("Межрайонная Инспекция Федеральной Налоговой Службы № 10 по Оренбургской Области", egrRecord.getRegName());
        Assert.assertEquals("5658", egrRecord.getRegCode());
    }

}
