package dev.vality.questionary.aggr.proxy.service.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DaDataLocationBoostFilter {

    @JsonProperty(value = "kladr_id")
    private String kladrId;

}
