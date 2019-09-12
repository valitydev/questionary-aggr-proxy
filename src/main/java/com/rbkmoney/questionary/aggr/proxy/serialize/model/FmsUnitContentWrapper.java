package com.rbkmoney.questionary.aggr.proxy.serialize.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rbkmoney.questionary.aggr.proxy.serialize.dadata.DaDataFmsUnitDeserializer;
import com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent;

public class FmsUnitContentWrapper {

    @JsonProperty
    private String value;

    @JsonProperty(value = "unrestricted_value")
    private String unrestrictedValue;

    @JsonDeserialize(using = DaDataFmsUnitDeserializer.class)
    @JsonProperty(value = "data")
    private FmsUnitContent fmsUnitContent;

    public FmsUnitContent getFmsUnitContent() {
        fmsUnitContent.setValue(value);
        fmsUnitContent.setUnrestrictdValue(unrestrictedValue);
        return fmsUnitContent;
    }
}
