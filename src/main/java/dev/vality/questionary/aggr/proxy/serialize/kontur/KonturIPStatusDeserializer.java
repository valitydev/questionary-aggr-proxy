package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.PrivateEntityStatusDetail;

public class KonturIPStatusDeserializer extends AbstractThriftDeserializer<PrivateEntityStatusDetail._Fields, PrivateEntityStatusDetail> {

    public KonturIPStatusDeserializer() {
        addFieldNameConverter("statusString", field -> {
            return "status";
        });
    }

    @Override
    protected PrivateEntityStatusDetail._Fields getField(String fieldName) {
        return PrivateEntityStatusDetail._Fields.findByName(fieldName);
    }

    @Override
    protected PrivateEntityStatusDetail newInstance() {
        return new PrivateEntityStatusDetail();
    }
}
