package dev.vality.questionary.aggr.proxy;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.vality.questionary.aggr.proxy.serialize.dadata.*;
import dev.vality.questionary.aggr.proxy.serialize.model.*;
import dev.vality.questionary_proxy_aggr.base_dadata.*;
import dev.vality.questionary_proxy_aggr.dadata_address.Address;
import dev.vality.questionary_proxy_aggr.dadata_address.AddressResponse;
import dev.vality.questionary_proxy_aggr.dadata_bank.BankContent;
import dev.vality.questionary_proxy_aggr.dadata_fio.FioContent;
import dev.vality.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedContent;
import dev.vality.questionary_proxy_aggr.dadata_party.PartyContent;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DaDataDeserializationTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void daDataAddressDeserializerTest() throws IOException, TException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Address.class, new DaDataAddressDeserializer());
        objectMapper.registerModule(simpleModule);
        final AddressResponse addressResponse =
                objectMapper.readValue(TestResponse.daDataAddress(), AddressResponse.class);
        Assert.assertEquals(10, addressResponse.getSuggestions().size());
        final Address address = addressResponse.getSuggestions().get(0);
        Assert.assertEquals("г Москва, ул Хабаровская", address.getValue());
        Assert.assertEquals("г Москва, ул Хабаровская", address.getUnrestrictedValue());
        Assert.assertEquals("Россия", address.getCountry());
        Assert.assertEquals("0c5b2444-70a0-4932-980c-b4dc0d3f02b5", address.getRegion().getRegionFiasId());
        Assert.assertEquals("7700000000000", address.getRegion().getRegionKladrId());
        Assert.assertEquals("0c5b2444-70a0-4932-980c-b4dc0d3f02b5", address.getCity().getCityFiasId());
        Assert.assertEquals("32fcb102-2a50-44c9-a00e-806420f448ea", address.getStreet().getStreetFiasId());
        Assert.assertEquals("45263564000", address.getOkato());
        Assert.assertEquals("45305000", address.getOktmo());
        Assert.assertEquals("7718", address.getTaxOffice());
        Assert.assertEquals("7718", address.getTaxOfficeLegal());
    }

    @Test
    public void daDataPartyDeserializerTest() throws IOException, TException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Opf.class, new DaDataOpfDeserializer());
        simpleModule.addDeserializer(OrgName.class, new DaDataOrgNameDeserializer());
        simpleModule.addDeserializer(DaDataState.class, new DaDataStateDeserializer());
        simpleModule.addDeserializer(License.class, new DaDataLicenseDeserializer());
        objectMapper.registerModule(simpleModule);
        final PartyResponseWrapper partyContentHolder =
                objectMapper.readValue(TestResponse.daDataParty(), PartyResponseWrapper.class);
        Assert.assertEquals(10, partyContentHolder.getPartyContentWrapperList().size());
        final PartyContent partyContent = partyContentHolder.getPartyContentWrapperList().get(0).getPartyContent();
        Assert.assertEquals("773601001", partyContent.getKpp());
        Assert.assertEquals("Греф Герман Оскарович", partyContent.getManagement().getName());
        Assert.assertEquals("ПРЕЗИДЕНТ, ПРЕДСЕДАТЕЛЬ ПРАВЛЕНИЯ", partyContent.getManagement().getPost());
        Assert.assertEquals("MAIN", partyContent.getBranchType().name());
        Assert.assertEquals(88, partyContent.getBranchCount());
        Assert.assertEquals(
                "145a83ab38c9ad95889a7b894ce57a97cf6f6d5f42932a71331ff18606edecc6",
                partyContent.getHid()
        );
        Assert.assertEquals("LEGAL", partyContent.getType().name());
        Assert.assertEquals("ACTIVE", partyContent.getState().getStatus().name());
        Assert.assertEquals("2019-09-10T03:00", partyContent.getState().getActualityDate());
        Assert.assertEquals("1991-06-20T03:00", partyContent.getState().getRegistrationDate());
        Assert.assertEquals("2014", partyContent.getOpf().getType());
        Assert.assertEquals("12247", partyContent.getOpf().getCode());
        Assert.assertEquals("Публичное акционерное общество", partyContent.getOpf().getFullName());
        Assert.assertEquals("ПАО", partyContent.getOpf().getShortName());
        Assert.assertEquals("7707083893", partyContent.getInn());
        Assert.assertEquals("1027700132195", partyContent.getOgrn());
        Assert.assertEquals("64.19", partyContent.getOkved());
        Assert.assertEquals("2002-08-16T04:00", partyContent.getOgrnDate());
        Assert.assertNotNull("г Москва, ул Вавилова, д 19", partyContent.getAddress().getValue());
        Assert.assertNotNull(
                "г Москва, Академический р-н, ул Вавилова, д 19",
                partyContent.getAddress().getUnrestrictedValue()
        );
        Assert.assertNotNull("0.8", partyContent.getAddress().getMetroList().get(0).getDistance());
    }

    @Test
    public void daDataBankDeserializerTest() throws IOException, TException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(BankContent.class, new DaDataBankDeserializer());
        simpleModule.addDeserializer(DaDataState.class, new DaDataStateDeserializer());
        simpleModule.addDeserializer(Payment.class, new DaDataPaymentDeserializer());
        objectMapper.registerModule(simpleModule);
        final BankResponseWrapper bankResponseWrapper =
                objectMapper.readValue(TestResponse.daDataBank(), BankResponseWrapper.class);
        Assert.assertEquals(10, bankResponseWrapper.getBankContentWrapperList().size());
        final BankContent bankContent = bankResponseWrapper.getBankContentWrapperList().get(0).getBankContent();
        Assert.assertEquals("СБЕРБАНК РОССИИ", bankContent.getValue());
        Assert.assertEquals("СБЕРБАНК РОССИИ", bankContent.getUnrestrictedValue());
        Assert.assertEquals("BANK", bankContent.getOpf().getType());
        Assert.assertEquals("ПАО СБЕРБАНК", bankContent.getPayment().getName());
        Assert.assertEquals("СБЕРБАНК РОССИИ", bankContent.getPayment().getShortName());
        Assert.assertEquals("044525225", bankContent.getBic());
        Assert.assertEquals("SABRRUMM", bankContent.getSwift());
        Assert.assertEquals("30101810400000000225", bankContent.getCorrespondentAccount());
        Assert.assertEquals("1481", bankContent.getRegistrationNumber());
    }

    @Test
    public void daDataFioDeserializerTest() throws IOException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(FioContent.class, new DaDataFioDeserializer());
        objectMapper.registerModule(simpleModule);
        final FioResponseWrapper fioResponseWrapper =
                objectMapper.readValue(TestResponse.daDataFio(), FioResponseWrapper.class);
        Assert.assertEquals(10, fioResponseWrapper.getFioContentWrapperList().size());
        final FioContent fioContent = fioResponseWrapper.getFioContentWrapperList().get(0).getFioContent();
        Assert.assertEquals("Виктор", fioContent.getValue());
        Assert.assertEquals("Виктор", fioContent.getUnrestrictedValue());
        Assert.assertEquals("Виктор", fioContent.getName());
        Assert.assertEquals("MALE", fioContent.getGender().name());
        Assert.assertEquals(0, fioContent.getQc());
    }

    @Test
    public void daDataFmsUnitTest() throws IOException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(FmsUnitContent.class, new DaDataFmsUnitDeserializer());
        objectMapper.registerModule(simpleModule);
        final FmsUnitResponseWrapper fmsUnitResponseWrapper =
                objectMapper.readValue(TestResponse.daDataFmsUnit(), FmsUnitResponseWrapper.class);
        Assert.assertEquals(10, fmsUnitResponseWrapper.getFmsUnitContentWrapperList().size());
        final FmsUnitContent fmsUnitContent = fmsUnitResponseWrapper.getFmsUnitContentWrapperList().get(0).getFmsUnitContent();
        Assert.assertEquals("ИВАНОВСКИМ ПОСЕЛКОВЫЙ ОТДЕЛ МИЛИЦИИ ОВД КРАСНОАРМЕЙСКОГО РАЙОНА", fmsUnitContent.getValue());
        Assert.assertEquals("ИВАНОВСКИМ ПОСЕЛКОВЫЙ ОТДЕЛ МИЛИЦИИ ОВД КРАСНОАРМЕЙСКОГО РАЙОНА", fmsUnitContent.getUnrestrictdValue());
        Assert.assertEquals("233-046", fmsUnitContent.getCode());
        Assert.assertEquals("ИВАНОВСКИМ ПОСЕЛКОВЫЙ ОТДЕЛ МИЛИЦИИ ОВД КРАСНОАРМЕЙСКОГО РАЙОНА", fmsUnitContent.getName());
        Assert.assertEquals("23", fmsUnitContent.getRegionCode());
        Assert.assertEquals("3", fmsUnitContent.getType());
    }

    @Test
    public void daDataOkvedTest() throws IOException {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(OkvedContent.class, new DaDataOkvedDeserializer());
        objectMapper.registerModule(simpleModule);
        final OkvedResponseWrapper okvedResponseWrapper =
                objectMapper.readValue(TestResponse.daDataOkved(), OkvedResponseWrapper.class);
        Assert.assertEquals(10, okvedResponseWrapper.getOkvedContentWrapperList().size());
        final OkvedContent okvedContent = okvedResponseWrapper.getOkvedContentWrapperList().get(0).getOkvedContent();
        Assert.assertEquals(
                "Добыча абразивных материалов, асбеста, кремнеземистой каменной муки, природных графитов," +
                " мыльного камня (талька), полевого шпата и т. д.",
                okvedContent.getValue());
        Assert.assertEquals("B.08.99.2", okvedContent.getIdx());
        Assert.assertEquals("B", okvedContent.getSection());
        Assert.assertEquals("08.99.2", okvedContent.getCode());
        Assert.assertEquals(
                "Добыча абразивных материалов, асбеста, кремнеземистой каменной муки, природных графитов," +
                " мыльного камня (талька), полевого шпата и т. д.",
                okvedContent.getName());
    }
}
