package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.base_kontur_focus.LegalName;

public class KonturLegalNameDeserializer extends AbstractThriftDeserializer<LegalName._Fields, LegalName> {

    public KonturLegalNameDeserializer() {
        addFieldNameConverter("short", field -> {
            return "short_name";
        });
        addFieldNameConverter("full", field -> {
            return "full_name";
        });
    }

    @Override
    protected LegalName._Fields getField(String fieldName) {
        return LegalName._Fields.findByName(fieldName);
    }

    @Override
    protected LegalName newInstance() {
        return new LegalName();
    }
}
