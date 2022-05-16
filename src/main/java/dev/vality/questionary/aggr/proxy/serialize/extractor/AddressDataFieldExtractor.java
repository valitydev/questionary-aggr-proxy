package dev.vality.questionary.aggr.proxy.serialize.extractor;

import com.fasterxml.jackson.databind.JsonNode;
import dev.vality.questionary.aggr.proxy.util.JsonHelper;
import dev.vality.questionary_proxy_aggr.dadata_address.*;

import java.util.List;

public class AddressDataFieldExtractor implements FieldExtractor<Address> {
    @Override
    public void extract(Address instance, JsonNode node) {
        instance.setPostalCode(JsonHelper.safeGet(node, "postal_code", JsonNode::textValue));
        instance.setPostalBox(JsonHelper.safeGet(node, "postal_box", JsonNode::textValue));
        instance.setCountry(JsonHelper.safeGet(node, "country", JsonNode::textValue));
        instance.setKladrId(JsonHelper.safeGet(node, "kladr_id", JsonNode::textValue));
        instance.setFiasId(JsonHelper.safeGet(node, "fias_id", JsonNode::textValue));
        instance.setFiasCode(JsonHelper.safeGet(node, "fias_code", JsonNode::textValue));
        final String fiasLevel = JsonHelper.safeGet(node, "fias_level", JsonNode::textValue);
        if (fiasLevel != null) {
            instance.setFiasLevel(Byte.parseByte(fiasLevel));
        }
        final String fiasActualityState =
                JsonHelper.safeGet(node, "fias_actuality_state", JsonNode::textValue);
        if (fiasActualityState != null) {
            instance.setFiasActualityState(Byte.parseByte(fiasActualityState));
        }
        instance.setGeonameId(JsonHelper.safeGet(node, "geoname_id", JsonNode::textValue));
        final String capitalMarket = JsonHelper.safeGet(node, "capital_marker", JsonNode::textValue);
        if (capitalMarket != null) {
            instance.setCapitalMarker(Byte.parseByte(capitalMarket));
        }
        instance.setOkato(JsonHelper.safeGet(node, "okato", JsonNode::textValue));
        instance.setOktmo(JsonHelper.safeGet(node, "oktmo", JsonNode::textValue));
        instance.setTaxOffice(JsonHelper.safeGet(node, "tax_office", JsonNode::textValue));
        instance.setTaxOfficeLegal(JsonHelper.safeGet(node, "tax_office_legal", JsonNode::textValue));
        instance.setTimezone(JsonHelper.safeGet(node, "timezone", JsonNode::textValue));
        instance.setGeoLat(JsonHelper.safeGet(node, "geo_lat", JsonNode::textValue));
        final String qcGeo = JsonHelper.safeGet(node, "qc_geo", JsonNode::textValue);
        if (qcGeo != null) {
            instance.setQcGeo(Byte.parseByte(qcGeo));
        }
        instance.setGeoLon(JsonHelper.safeGet(node, "geo_lon", JsonNode::textValue));
        instance.setBeltwayHit(JsonHelper.safeGet(node, "beltway_hit", JsonNode::textValue));
        instance.setBeltwayDistance(JsonHelper.safeGet(node, "beltway_distance", JsonNode::textValue));

        final AddressRegionData addressRegionData = new AddressRegionData();
        addressRegionData.setRegionFiasId(JsonHelper.safeGet(node, "region_fias_id", JsonNode::textValue));
        addressRegionData.setRegionKladrId(JsonHelper.safeGet(node, "region_kladr_id", JsonNode::textValue));
        addressRegionData.setRegionWithType(
                JsonHelper.safeGet(node, "region_with_type", JsonNode::textValue)
        );
        addressRegionData.setRegionType(JsonHelper.safeGet(node, "region_type", JsonNode::textValue));
        addressRegionData.setRegionTypeFull(
                JsonHelper.safeGet(node, "region_type_full", JsonNode::textValue)
        );
        addressRegionData.setRegion(JsonHelper.safeGet(node, "region", JsonNode::textValue));
        instance.setRegion(addressRegionData);

        final AddressAreaData addressAreaData = new AddressAreaData();
        addressAreaData.setAreaFiasId(JsonHelper.safeGet(node, "area_fias_id", JsonNode::textValue));
        addressAreaData.setAreaKladrId(JsonHelper.safeGet(node, "area_kladr_id", JsonNode::textValue));
        addressAreaData.setAreaWithType(JsonHelper.safeGet(node, "area_with_type", JsonNode::textValue));
        addressAreaData.setAreaType(JsonHelper.safeGet(node, "area_type", JsonNode::textValue));
        addressAreaData.setAreaTypeFull(JsonHelper.safeGet(node, "area_type_full", JsonNode::textValue));
        addressAreaData.setArea(JsonHelper.safeGet(node, "area", JsonNode::textValue));
        instance.setArea(addressAreaData);

        final AddressCityData addressCityData = new AddressCityData();
        addressCityData.setCityFiasId(JsonHelper.safeGet(node, "city_fias_id", JsonNode::textValue));
        addressCityData.setCityKladrId(JsonHelper.safeGet(node, "city_kladr_id", JsonNode::textValue));
        addressCityData.setCityWithType(JsonHelper.safeGet(node, "city_with_type", JsonNode::textValue));
        addressCityData.setCityType(JsonHelper.safeGet(node, "city_type", JsonNode::textValue));
        addressCityData.setCityTypeFull(JsonHelper.safeGet(node, "city_type_full", JsonNode::textValue));
        addressCityData.setCity(JsonHelper.safeGet(node, "city", JsonNode::textValue));
        addressCityData.setCityArea(JsonHelper.safeGet(node, "city_area", JsonNode::textValue));
        instance.setCity(addressCityData);

        final AddressCityDistrictData addressCityDistrictData = new AddressCityDistrictData();
        addressCityDistrictData.setCityDistrictFiasId(
                JsonHelper.safeGet(node, "city_district_fias_id", JsonNode::textValue)
        );
        addressCityDistrictData.setCityDistrictKladrId(
                JsonHelper.safeGet(node, "city_district_kladr_id", JsonNode::textValue)
        );
        addressCityDistrictData.setCityDistrictWithType(
                JsonHelper.safeGet(node, "city_district_with_type", JsonNode::textValue)
        );
        addressCityDistrictData.setCityDistrictType(
                JsonHelper.safeGet(node, "city_district_type", JsonNode::textValue)
        );
        addressCityDistrictData.setCityDistrictTypeFull(
                JsonHelper.safeGet(node, "city_district_type_full", JsonNode::textValue)
        );
        addressCityDistrictData.setCityDistrict(
                JsonHelper.safeGet(node, "city_district", JsonNode::textValue)
        );
        instance.setCityDistrict(addressCityDistrictData);

        final AddressSettlementData addressSettlementData = new AddressSettlementData();
        addressSettlementData.setSettlementFiasId(
                JsonHelper.safeGet(node, "settlement_fias_id", JsonNode::textValue)
        );
        addressSettlementData.setSettlementKladrId(
                JsonHelper.safeGet(node, "settlement_kladr_id", JsonNode::textValue)
        );
        addressSettlementData.setSettlementWithType(
                JsonHelper.safeGet(node, "settlement_with_type", JsonNode::textValue)
        );
        addressSettlementData.setSettlementType(
                JsonHelper.safeGet(node, "settlement_type", JsonNode::textValue)
        );
        addressSettlementData.setSettlementTypeFull(
                JsonHelper.safeGet(node, "settlement_type_full", JsonNode::textValue)
        );
        addressSettlementData.setSettlement(
                JsonHelper.safeGet(node, "settlement", JsonNode::textValue)
        );
        instance.setSettlement(addressSettlementData);

        final AddressStreetData addressStreetData = new AddressStreetData();
        addressStreetData.setStreetFiasId(JsonHelper.safeGet(node, "street_fias_id", JsonNode::textValue));
        addressStreetData.setStreetKladrId(JsonHelper.safeGet(node, "street_kladr_id", JsonNode::textValue));
        addressStreetData.setStreetWithType(
                JsonHelper.safeGet(node, "street_with_type", JsonNode::textValue)
        );
        addressStreetData.setStreetTypeFull(
                JsonHelper.safeGet(node, "street_type_full", JsonNode::textValue)
        );
        addressStreetData.setStreetType(JsonHelper.safeGet(node, "street_type", JsonNode::textValue));
        addressStreetData.setStreet(JsonHelper.safeGet(node, "street", JsonNode::textValue));
        instance.setStreet(addressStreetData);

        final AddressHouseData addressHouseData = new AddressHouseData();
        addressHouseData.setHouseFiasId(JsonHelper.safeGet(node, "house_fias_id", JsonNode::textValue));
        addressHouseData.setHouseKladrId(JsonHelper.safeGet(node, "house_kladr_id", JsonNode::textValue));
        addressHouseData.setHouseType(JsonHelper.safeGet(node, "house_type", JsonNode::textValue));
        addressHouseData.setHouseTypeFull(JsonHelper.safeGet(node, "house_type_full", JsonNode::textValue));
        addressHouseData.setHouse(JsonHelper.safeGet(node, "house", JsonNode::textValue));
        instance.setHouse(addressHouseData);

        final AddressBlockData addressBlockData = new AddressBlockData();
        addressBlockData.setBlockType(JsonHelper.safeGet(node, "block_type", JsonNode::textValue));
        addressBlockData.setBlockTypeFull(JsonHelper.safeGet(node, "block_type_full", JsonNode::textValue));
        addressBlockData.setBlock(JsonHelper.safeGet(node, "block", JsonNode::textValue));
        instance.setBlock(addressBlockData);

        final AddressFlatData addressFlatData = new AddressFlatData();
        addressFlatData.setFlatType(JsonHelper.safeGet(node, "flat_type", JsonNode::textValue));
        addressFlatData.setFlatTypeFull(JsonHelper.safeGet(node, "flat_type_full", JsonNode::textValue));
        addressFlatData.setFlat(JsonHelper.safeGet(node, "flat", JsonNode::textValue));
        addressFlatData.setFlatArea(JsonHelper.safeGet(node, "flat_area", JsonNode::textValue));
        addressFlatData.setFlatPrice(JsonHelper.safeGet(node, "flat_price", JsonNode::textValue));

        final List<AddressMetro> metroList = JsonHelper.nodeToList(node.get("metro"), metroNode -> {
            final AddressMetro addressMetro = new AddressMetro();
            addressMetro.setName(JsonHelper.safeGet(metroNode, "name", JsonNode::textValue));
            addressMetro.setLine(JsonHelper.safeGet(metroNode, "line", JsonNode::textValue));
            final Double distance = JsonHelper.safeGet(metroNode, "distance", JsonNode::doubleValue);
            if (distance != null) {
                addressMetro.setDistance(String.valueOf(distance));
            }
            return addressMetro;
        });
        instance.setMetroList(metroList);

        final List<String> historyValues = JsonHelper.nodeToList(node.get("history_values"), JsonNode::textValue);
        instance.setHistoryValues(historyValues);
    }
}
