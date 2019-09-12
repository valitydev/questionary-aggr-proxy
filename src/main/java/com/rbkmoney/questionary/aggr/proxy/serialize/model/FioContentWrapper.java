package com.rbkmoney.questionary.aggr.proxy.serialize.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rbkmoney.questionary.aggr.proxy.serialize.dadata.DaDataFioDeserializer;
import com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioContent;

public class FioContentWrapper {

    @JsonProperty
    private String value;

    @JsonProperty(value = "unrestricted_value")
    private String unrestrictedValue;

    @JsonDeserialize(using = DaDataFioDeserializer.class)
    @JsonProperty("data")
    private FioContent fioContent;

    public FioContent getFioContent() {
        fioContent.setValue(value);
        fioContent.setUnrestrictedValue(unrestrictedValue);
        return fioContent;
    }
}
