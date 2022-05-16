package dev.vality.questionary.aggr.proxy.service.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DaDataFilter {

    @JsonProperty(value = "region_code")
    private String regionCode;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "razdel")
    private String section;

}
