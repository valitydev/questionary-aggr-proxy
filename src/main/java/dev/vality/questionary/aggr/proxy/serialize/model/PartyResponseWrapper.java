package dev.vality.questionary.aggr.proxy.serialize.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class PartyResponseWrapper {

    @JsonProperty(value = "suggestions")
    private List<PartyContentWrapper> partyContentWrapperList;

}
