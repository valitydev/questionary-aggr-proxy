package dev.vality.questionary.aggr.proxy.serialize.dadata;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary.aggr.proxy.util.CommonHelper;
import dev.vality.questionary_proxy_aggr.base_dadata.DaDataState;

public class DaDataStateDeserializer extends AbstractThriftDeserializer<DaDataState._Fields, DaDataState> {

    public DaDataStateDeserializer() {
        addCustomFieldExtractor("actuality_date", (instance, node) -> {
            instance.setActualityDate(CommonHelper.stringToLocalDateTime(node.toString()).toString());
        });
        addCustomFieldExtractor("registration_date", (instance, node) -> {
            instance.setRegistrationDate(CommonHelper.stringToLocalDateTime(node.toString()).toString());
        });
        addCustomFieldExtractor("liquidation_date", (instance, node) -> {
            instance.setLiquidationDate(CommonHelper.stringToLocalDateTime(node.toString()).toString());
        });
    }

    @Override
    protected DaDataState._Fields getField(String fieldName) {
        return DaDataState._Fields.findByName(fieldName);
    }

    @Override
    protected DaDataState newInstance() {
        return new DaDataState();
    }
}
