package dev.vality.questionary.aggr.proxy.serialize.dadata;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedContent;

public class DaDataOkvedDeserializer extends AbstractThriftDeserializer<OkvedContent._Fields, OkvedContent> {

    public DaDataOkvedDeserializer() {
        addFieldNameConverter("razdel", field -> {
            return "section";
        });
        addFieldNameConverter("kod", field -> {
            return "code";
        });
    }

    @Override
    protected OkvedContent._Fields getField(String fieldName) {
        return OkvedContent._Fields.findByName(fieldName);
    }

    @Override
    protected OkvedContent newInstance() {
        return new OkvedContent();
    }
}
