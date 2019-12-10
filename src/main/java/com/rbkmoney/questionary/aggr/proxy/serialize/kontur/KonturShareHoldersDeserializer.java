package com.rbkmoney.questionary.aggr.proxy.serialize.kontur;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolders;

public class KonturShareHoldersDeserializer extends AbstractThriftDeserializer<ShareHolders._Fields, ShareHolders> {

    public KonturShareHoldersDeserializer() {
        addFieldNameConverter("shareholdersFL", field -> {
            return "shareholders_fl";
        });
        addFieldNameConverter("shareholdersUL", field -> {
            return "shareholders_ul";
        });
    }

    @Override
    protected ShareHolders._Fields getField(String fieldName) {
        return ShareHolders._Fields.findByName(fieldName);
    }

    @Override
    protected ShareHolders newInstance() {
        return new ShareHolders();
    }

}
