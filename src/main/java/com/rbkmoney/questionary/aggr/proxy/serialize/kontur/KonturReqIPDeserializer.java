package com.rbkmoney.questionary.aggr.proxy.serialize.kontur;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqIndividualEntity;

public class KonturReqIPDeserializer extends AbstractThriftDeserializer<ReqIndividualEntity._Fields, ReqIndividualEntity> {

    public KonturReqIPDeserializer() {
        addFieldNameConverter("status", field -> {
            return "status_detail";
        });
    }

    @Override
    protected ReqIndividualEntity._Fields getField(String fieldName) {
        return ReqIndividualEntity._Fields.findByName(fieldName);
    }

    @Override
    protected ReqIndividualEntity newInstance() {
        return new ReqIndividualEntity();
    }

}
