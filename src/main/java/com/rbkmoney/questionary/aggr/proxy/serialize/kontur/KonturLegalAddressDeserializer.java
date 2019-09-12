package com.rbkmoney.questionary.aggr.proxy.serialize.kontur;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalAddress;

public class KonturLegalAddressDeserializer extends AbstractThriftDeserializer<LegalAddress._Fields, LegalAddress> {

    public KonturLegalAddressDeserializer() {
        addFieldNameConverter("parsedAddressRF", field -> {
            return "address_rf";
        });
    }

    @Override
    protected LegalAddress._Fields getField(String fieldName) {
        return LegalAddress._Fields.findByName(fieldName);
    }

    @Override
    protected LegalAddress newInstance() {
        return new LegalAddress();
    }
}
