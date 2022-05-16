package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.ReqIndividualEntity;

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
