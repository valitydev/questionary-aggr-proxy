package com.rbkmoney.questionary.aggr.proxy.serialize.dadata;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgName;

public class DaDataOrgNameDeserializer extends AbstractThriftDeserializer<OrgName._Fields, OrgName> {

    public DaDataOrgNameDeserializer() {
        addFieldNameConverter("full", field -> {
            return "full_name";
        });
        addFieldNameConverter("short", field -> {
            return "short_name";
        });
    }

    @Override
    protected OrgName._Fields getField(String fieldName) {
        return OrgName._Fields.findByName(fieldName);
    }

    @Override
    protected OrgName newInstance() {
        return new OrgName();
    }
}
