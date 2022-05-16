package dev.vality.questionary.aggr.proxy.serialize.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class FioResponseWrapper {

    @JsonProperty(value = "suggestions")
    private List<FioContentWrapper> fioContentWrapperList;

}
