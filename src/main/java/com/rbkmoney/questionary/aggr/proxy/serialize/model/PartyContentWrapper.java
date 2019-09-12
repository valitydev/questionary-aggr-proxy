package com.rbkmoney.questionary.aggr.proxy.serialize.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rbkmoney.questionary.aggr.proxy.serialize.dadata.DaDataPartyContentDeserializer;
import com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyContent;

public class PartyContentWrapper {

    @JsonProperty
    private String value;

    @JsonProperty(value = "unrestricted_value")
    private String unrestrictedValue;

    @JsonProperty(value = "data")
    @JsonDeserialize(using = DaDataPartyContentDeserializer.class)
    private PartyContent partyContent;

    public PartyContent getPartyContent() {
        partyContent.setValue(value);
        partyContent.setUnrestrictedValue(unrestrictedValue);
        return partyContent;
    }
}
