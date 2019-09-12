package com.rbkmoney.questionary.aggr.proxy.serialize.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class FmsUnitResponseWrapper {

    @JsonProperty(value = "suggestions")
    private List<FmsUnitContentWrapper> fmsUnitContentWrapperList;

}
