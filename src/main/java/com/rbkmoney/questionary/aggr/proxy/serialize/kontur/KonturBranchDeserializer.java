package com.rbkmoney.questionary.aggr.proxy.serialize.kontur;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch;

public class KonturBranchDeserializer extends AbstractThriftDeserializer<Branch._Fields, Branch> {

    public KonturBranchDeserializer() {
        addFieldNameConverter("parsedAddressRF", field -> {
            return "address_rf";
        });
    }

    @Override
    protected Branch._Fields getField(String fieldName) {
        return Branch._Fields.findByName(fieldName);
    }

    @Override
    protected Branch newInstance() {
        return new Branch();
    }
}
