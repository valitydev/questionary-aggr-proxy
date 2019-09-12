package com.rbkmoney.questionary.aggr.proxy.service.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DaDataBound {

    private String value;

}
