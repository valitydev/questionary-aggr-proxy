package com.rbkmoney.questionary.aggr.proxy.serialize.dadata;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.Opf;

public class DaDataOpfDeserializer extends AbstractThriftDeserializer<Opf._Fields, Opf> {

    public DaDataOpfDeserializer() {
        addFieldNameConverter("full", field -> {
            return "full_name";
        });
        addFieldNameConverter("short", field -> {
            return "short_name";
        });
    }

    @Override
    protected Opf._Fields getField(String fieldName) {
        return Opf._Fields.findByName(fieldName);
    }

    @Override
    protected Opf newInstance() {
        return new Opf();
    }
}
