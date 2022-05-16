package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.LegalEntityStatusDetail;

public class KonturLegalStatusDetailDeserializer extends AbstractThriftDeserializer<LegalEntityStatusDetail._Fields, LegalEntityStatusDetail> {

    public KonturLegalStatusDetailDeserializer() {
        addFieldNameConverter("statusString", field -> {
            return "status";
        });
    }

    @Override
    protected LegalEntityStatusDetail._Fields getField(String fieldName) {
        return LegalEntityStatusDetail._Fields.findByName(fieldName);
    }

    @Override
    protected LegalEntityStatusDetail newInstance() {
        return new LegalEntityStatusDetail();
    }
}
