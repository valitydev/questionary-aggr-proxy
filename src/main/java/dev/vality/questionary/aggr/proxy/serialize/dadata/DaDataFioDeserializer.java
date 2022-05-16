package dev.vality.questionary.aggr.proxy.serialize.dadata;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.dadata_fio.FioContent;

public class DaDataFioDeserializer extends AbstractThriftDeserializer<FioContent._Fields, FioContent> {
    @Override
    protected FioContent._Fields getField(String fieldName) {
        return FioContent._Fields.findByName(fieldName);
    }

    @Override
    protected FioContent newInstance() {
        return new FioContent();
    }
}
