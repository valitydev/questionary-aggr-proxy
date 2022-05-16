package dev.vality.questionary.aggr.proxy.service.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DaDataLocationFilter {

    @JsonProperty(value = "city_fias_id")
    private String cityFiasId;

    @JsonProperty(value = "area_fias_id")
    private String areaFiasId;

    @JsonProperty(value = "region_fias_id")
    private String regionFiasId;

    @JsonProperty(value = "settlement_fias_id")
    private String settlementFiasId;

    @JsonProperty(value = "street_fias_id")
    private String streetFiasId;

    private String region;

    private String city;

    @JsonProperty(value = "street_type_full")
    private String streetTypeFull;

    @JsonProperty(value = "settlement_type_full")
    private String settlementTypeFull;

    @JsonProperty(value = "city_district_type_full")
    private String cityDistrictTypeFull;

    @JsonProperty(value = "city_type_full")
    private String cityTypeFull;

    @JsonProperty(value = "area_type_full")
    private String areaTypeFull;

    @JsonProperty(value = "region_type_full")
    private String regionTypeFull;

    private String country;

    @JsonProperty(value = "kladr_id")
    private String kladrId;

}
