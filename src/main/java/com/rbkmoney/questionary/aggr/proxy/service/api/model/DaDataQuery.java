package com.rbkmoney.questionary.aggr.proxy.service.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DaDataQuery {

    private String query;

    private Short count;

    private List<DaDataFilter> filters;

    private List<String> parts;

    private String gender;

    private List<String> status;

    private String type;

    private List<DaDataLocationFilter> locations;

    @JsonProperty(value = "location_boost")
    private List<DaDataLocationBoostFilter> locationsBoost;

    @JsonProperty(value = "from_bound")
    private DaDataBound fromBound;

    @JsonProperty(value = "to_bound")
    private DaDataBound toBound;

    @JsonProperty(value = "restrict_value")
    private boolean restrictValue;

}
