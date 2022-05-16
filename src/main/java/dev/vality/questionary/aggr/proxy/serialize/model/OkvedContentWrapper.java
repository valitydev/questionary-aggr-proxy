package dev.vality.questionary.aggr.proxy.serialize.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedContent;

public class OkvedContentWrapper {

    @JsonProperty
    private String value;

    @JsonProperty(value = "data")
    private OkvedContent okvedContent;

    public OkvedContent getOkvedContent() {
        okvedContent.setValue(value);
        return okvedContent;
    }
}
