package dev.vality.questionary.aggr.proxy.serialize.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.vality.questionary.aggr.proxy.serialize.dadata.DaDataBankDeserializer;
import dev.vality.questionary_proxy_aggr.dadata_bank.BankContent;

public class BankContentWrapper {

    @JsonProperty
    private String value;

    @JsonProperty(value = "unrestricted_value")
    private String unrestrictedValue;

    @JsonDeserialize(using = DaDataBankDeserializer.class)
    @JsonProperty(value = "data")
    private BankContent bankContent;

    public BankContent getBankContent() {
        bankContent.setValue(value);
        bankContent.setUnrestrictedValue(unrestrictedValue);
        return bankContent;
    }
}
