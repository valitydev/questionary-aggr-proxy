package dev.vality.questionary.aggr.proxy.handler.dadata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.vality.questionary.aggr.proxy.serialize.dadata.*;
import dev.vality.questionary.aggr.proxy.service.api.DaDataApi;
import dev.vality.questionary_proxy_aggr.base_dadata.*;
import dev.vality.questionary_proxy_aggr.dadata_address.Address;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataRequest;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataResponse;
import dev.vality.questionary_proxy_aggr.dadata_bank.BankContent;
import dev.vality.questionary_proxy_aggr.dadata_fio.FioContent;
import dev.vality.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDaDataHandler implements DaDataRequestHandler {

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        final SimpleModule simpleModule = new SimpleModule();
        // Address deserializer
        simpleModule.addDeserializer(Address.class, new DaDataAddressDeserializer());

        // Party deserializer
        simpleModule.addDeserializer(Opf.class, new DaDataOpfDeserializer());
        simpleModule.addDeserializer(OrgName.class, new DaDataOrgNameDeserializer());
        simpleModule.addDeserializer(DaDataState.class, new DaDataStateDeserializer());
        simpleModule.addDeserializer(License.class, new DaDataLicenseDeserializer());

        // Bank deserializer
        simpleModule.addDeserializer(BankContent.class, new DaDataBankDeserializer());
        simpleModule.addDeserializer(DaDataState.class, new DaDataStateDeserializer());
        simpleModule.addDeserializer(Payment.class, new DaDataPaymentDeserializer());

        // Fio deserializer
        simpleModule.addDeserializer(FioContent.class, new DaDataFioDeserializer());

        // Fms unit deserializer
        simpleModule.addDeserializer(FmsUnitContent.class, new DaDataFmsUnitDeserializer());

        // Okved deserializer
        simpleModule.addDeserializer(OkvedContent.class, new DaDataOkvedDeserializer());

        OBJECT_MAPPER.registerModule(simpleModule);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected final DaDataApi daDataApi;
    protected final Logger log;

    public AbstractDaDataHandler(DaDataApi daDataApi) {
        this.daDataApi = daDataApi;
        this.log = LoggerFactory.getLogger(getClass());
    }

    @Override
    public final DaDataResponse handle(DaDataRequest request) {
        try {
            return handleRequest(request);
        } catch (Exception e) {
            log.error("Exception while handling request", e);
            throw new DaDataHandlerException("Unexpected exception", e);
        }
    }

    protected abstract DaDataResponse handleRequest(DaDataRequest request) throws Exception;

    protected final ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}
