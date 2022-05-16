package dev.vality.questionary.aggr.proxy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.vality.questionary.aggr.proxy.serialize.kontur.*;
import dev.vality.questionary_proxy_aggr.base_kontur_focus.*;
import dev.vality.questionary_proxy_aggr.kontur_focus_beneficial_owner.*;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.*;
import dev.vality.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse;
import dev.vality.questionary_proxy_aggr.kontur_focus_licences.License;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.LegalEntityStatusDetail;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.PrivateEntityStatusDetail;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.ReqIndividualEntity;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.ReqResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class KonturFocusDeserializationTest {

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
        Assert.assertEquals(
                "https://focus.kontur.ru/entity?query=1026605606620",
                licencesResponse.getFocusHref()
        );
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
        Assert.assertEquals(
                "https://focus.kontur.ru/entity?query=309565808600188",
                ipRegResponse.getFocusHref()
        );
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
    public void kfReqLegalDeserializerTest() throws IOException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(LegalEntityStatusDetail.class, new KonturLegalStatusDetailDeserializer());
        simpleModule.addDeserializer(Branch.class, new KonturBranchDeserializer());
        simpleModule.addDeserializer(LegalAddress.class, new KonturLegalAddressDeserializer());
        simpleModule.addDeserializer(LegalName.class, new KonturLegalNameDeserializer());
        simpleModule.addDeserializer(PrivateEntityStatusDetail.class, new KonturIPStatusDeserializer());
        simpleModule.addDeserializer(ReqResponse.class, new KonturReqResponseDeserializer());
        simpleModule.addDeserializer(ReqIndividualEntity.class, new KonturReqIPDeserializer());
        objectMapper.registerModule(simpleModule);
        final List<ReqResponse> regResponseList = objectMapper.readValue(TestResponse.kfReqLegalResp(),
                new TypeReference<List<ReqResponse>>() {
                });
        Assert.assertEquals(2, regResponseList.size());
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
        Assert.assertEquals(
                "https://focus.kontur.ru/entity?query=309565808600188",
                egrDetailsResponse.getFocusHref()
        );
        final EgrDetailsIndividualEntity individualEntity = egrDetailsResponse.getContractor().getIndividualEntity();
        Assert.assertEquals("56", individualEntity.getShortenedAddress().getRegionCode());
        Assert.assertEquals("обл", individualEntity.getShortenedAddress().getRegionName().getTopoShortName());
        Assert.assertEquals(
                "область",
                individualEntity.getShortenedAddress().getRegionName().getTopoFullName()
        );
        Assert.assertEquals(
                "Оренбургская",
                individualEntity.getShortenedAddress().getRegionName().getTopoValue()
        );
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
        Assert.assertEquals(
                "Межрайонная Инспекция Федеральной Налоговой Службы № 10 по Оренбургской Области",
                egrRecord.getRegName()
        );
        Assert.assertEquals("5658", egrRecord.getRegCode());
    }

    @Test
    public void kfEgrDetailsLegalDeserializerTest() throws IOException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(EgrDetailsResponse.class, new KonturEgrDetailsResponseDeserializer());
        simpleModule.addDeserializer(ShareHolders.class, new KonturShareHoldersDeserializer());
        simpleModule.addDeserializer(EgrDetailsLegalEntity.class, new KonturEgrDetailsLegalEntityDeserializer());
        simpleModule.addDeserializer(EgrDetailsHistory.class, new KonturEgrDetailsHistoryDeserializer());
        objectMapper.registerModule(simpleModule);
        final List<EgrDetailsResponse> egrDetailsReponseList = objectMapper.readValue(TestResponse.kfEgrDetailsLegal(),
                new TypeReference<List<EgrDetailsResponse>>() {
                });
        Assert.assertEquals(2, egrDetailsReponseList.size());
        EgrDetailsResponse egrDetailsResponse = egrDetailsReponseList.get(0);
        EgrDetailsLegalEntity legalEntity = egrDetailsResponse.getContractor().getLegalEntity();

        // Check principal activities
        Assert.assertEquals("46.71", legalEntity.getActivities().getPrincipalActivity().getCode());
        Assert.assertEquals("Торговля оптовая твердым, жидким и газообразным топливом и подобными продуктами",
                legalEntity.getActivities().getPrincipalActivity().getText());
        Assert.assertEquals("2012-04-13", legalEntity.getActivities().getPrincipalActivity().getDate());

        // Check complementary activity
        ComplementaryActivity complementaryActivity = legalEntity.getActivities().getComplementaryActivities().get(0);
        Assert.assertEquals("06.10", complementaryActivity.getCode());
        Assert.assertEquals(
                "Добыча сырой нефти и нефтяного (попутного) газа",
                complementaryActivity.getText()
        );
        Assert.assertEquals("2012-04-13", complementaryActivity.getDate());

        // Check reg info
        RegInfo regInfo = legalEntity.getRegInfo();
        Assert.assertEquals(
                "Государственное учреждение Московская регистрационная палата",
                legalEntity.getRegInfo().getRegName()
        );
        Assert.assertEquals("2002-08-02", legalEntity.getRegInfo().getOgrnDate());

        // Check nalog registration
        Assert.assertEquals("7728", legalEntity.getNalogRegBody().getNalogCode());
        Assert.assertEquals(
                "Инспекция Федеральной налоговой службы № 28 по г.Москве",
                legalEntity.getNalogRegBody().getNalogName()
        );
        Assert.assertEquals("1996-02-19", legalEntity.getNalogRegBody().getNalogRegDate());
        Assert.assertEquals("772801001", legalEntity.getNalogRegBody().getKpp());
        Assert.assertEquals("2013-09-04", legalEntity.getNalogRegBody().getDate());

        // Check registration
        Assert.assertEquals("7746", legalEntity.getRegBody().getNalogCode());
        Assert.assertEquals(
                "Межрайонная инспекция Федеральной налоговой службы № 46 по г. Москве",
                legalEntity.getRegBody().getNalogName()
        );
        Assert.assertEquals("2008-01-01", legalEntity.getRegBody().getDate());

        // Check shareholdes
        Assert.assertEquals("2019-09-30", legalEntity.getShareholders().getDate());
        ShareHolderFl shareholderFl = legalEntity.getShareholders().getShareholdersFl().get(0);
        Assert.assertEquals("2019-09-30", shareholderFl.getDate());
        Assert.assertEquals("Штепа Сергей Вячеславович", shareholderFl.getFio());
        Assert.assertEquals(0, Double.compare(0.0067586, shareholderFl.getVotingSharesPercent()));
        Assert.assertEquals(0, Double.compare(0.0067586, shareholderFl.getCapitalSharesPercent()));

        Assert.assertEquals(9, legalEntity.getShareholders().getShareholdersUlSize());
        Assert.assertEquals(3, legalEntity.getShareholders().getShareholdersOtherSize());

        // Check stated capital
        Assert.assertEquals(118367564500L, legalEntity.getStatedCapital().getSum());
        Assert.assertEquals("2004-12-03", legalEntity.getStatedCapital().getDate());

        // Check egr record
        EgrRecord egrRecord = legalEntity.getEgrRecords().get(0);
        Assert.assertEquals("8197748492208", egrRecord.getGrn());
        Assert.assertEquals("2019-10-31", egrRecord.getDate());
        Assert.assertEquals("Изменение сведений о ЮЛ, содержащихся в ЕГРЮЛ", egrRecord.getName());
        Assert.assertEquals(
                "Межрайонная Инспекция Федеральной Налоговой Службы № 46 по г. Москве",
                egrRecord.getRegName()
        );
        Assert.assertEquals("7746", egrRecord.getRegCode());
        Assert.assertEquals(
                "Р14001 заявление об изм.сведений, не связанных с изм. учред.документов (п.2.1)",
                egrRecord.getDocuments().get(0).getName()
        );
        Assert.assertEquals("Доверенность роман в б", egrRecord.getDocuments().get(1).getName());
    }

    @Test
    public void kfBeneficialOwnersDeserializerTest() throws IOException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(BeneficialOwners.class, new KonturBeneficialOwnersDeserializer());
        objectMapper.registerModule(simpleModule);
        final List<BeneficialOwnerResponse> beneficialOwnerResponseList =
                objectMapper.readValue(TestResponse.kfBeneficialOwnerResp(),
                        new TypeReference<List<BeneficialOwnerResponse>>() {
                        });
        Assert.assertEquals(2, beneficialOwnerResponseList.size());
        BeneficialOwnerResponse beneficialOwnerResponse = beneficialOwnerResponseList.get(0);

        // Check beneficial owner FL
        BeneficialOwnerFl beneficialOwnerFl = beneficialOwnerResponse.getBeneficialOwners().getBeneficialOwnersFl().get(0);
        Assert.assertEquals("Штепа Сергей Вячеславович", beneficialOwnerFl.getFio());
        Assert.assertEquals(0, Double.compare(0.0067585999204311500, beneficialOwnerFl.getShare()));
        Assert.assertTrue(beneficialOwnerFl.isIsAccurate());

        // Check beneficial owner UL
        BeneficialOwnerUl beneficialOwnerUl =
                beneficialOwnerResponse.getBeneficialOwners().getBeneficialOwnersUl().get(0);
        Assert.assertEquals("1087746829994", beneficialOwnerUl.getOgrn());
        Assert.assertEquals("7710723134", beneficialOwnerUl.getInn());
        Assert.assertEquals(
                "Федеральное агентство по управлению государственным имуществом",
                beneficialOwnerUl.getFullName()
        );
        Assert.assertEquals(0, Double.compare(38.373455405235300, beneficialOwnerUl.getShare()));
        Assert.assertTrue(beneficialOwnerUl.isIsAccurate());

        // Check beneficial owner foreign
        BeneficialOwnerForeign beneficialOwnerForeign =
                beneficialOwnerResponse.getBeneficialOwners().getBeneficialOwnersForeign().get(0);
        Assert.assertEquals(
                "Вудико Холдинг Лимитед (Woodiko Holding Ltd)",
                beneficialOwnerForeign.getFullName()
        );
        Assert.assertEquals("Кипр", beneficialOwnerForeign.getCountry());
        Assert.assertEquals(0, Double.compare(0.037967871294025300, beneficialOwnerForeign.getShare()));
        Assert.assertTrue(beneficialOwnerForeign.isIsAccurate());

        BeneficialOwnerOther beneficialOwnerOther =
                beneficialOwnerResponse.getBeneficialOwners().getBeneficialOwnersOther().get(0);
        Assert.assertEquals("АО «Газпром газораспределение»", beneficialOwnerOther.getFullName());
        Assert.assertEquals(0, Double.compare(0.89197726920247100, beneficialOwnerOther.getShare()));
        Assert.assertTrue(beneficialOwnerOther.isIsAccurate());
    }

}
