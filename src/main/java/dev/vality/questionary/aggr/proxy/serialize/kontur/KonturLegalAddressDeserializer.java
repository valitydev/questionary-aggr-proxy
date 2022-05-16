package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.base_kontur_focus.LegalAddress;

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
