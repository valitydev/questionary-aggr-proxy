package com.rbkmoney.questionary.aggr.proxy.serialize.kontur;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsLegalEntity;

public class KonturEgrDetailsLegalEntityDeserializer extends AbstractThriftDeserializer<EgrDetailsLegalEntity._Fields, EgrDetailsLegalEntity> {

    public KonturEgrDetailsLegalEntityDeserializer() {
        addFieldNameConverter("foundersFL", field -> {
            return "founders_fl";
        });
        addFieldNameConverter("foundersUL", field -> {
            return "founders_ul";
        });
    }

    @Override
    protected EgrDetailsLegalEntity._Fields getField(String fieldName) {
        return EgrDetailsLegalEntity._Fields.findByName(fieldName);
    }

    @Override
    protected EgrDetailsLegalEntity newInstance() {
        return new EgrDetailsLegalEntity();
    }

}
