package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsHistory;

public class KonturEgrDetailsHistoryDeserializer extends AbstractThriftDeserializer<EgrDetailsHistory._Fields, EgrDetailsHistory> {

    public KonturEgrDetailsHistoryDeserializer() {
        addFieldNameConverter("foundersFL", field -> {
            return "founders_fl";
        });
        addFieldNameConverter("foundersUL", field -> {
            return "founders_ul";
        });
        addFieldNameConverter("shareholdersFL", field -> {
            return "founders_fl";
        });
        addFieldNameConverter("shareholdersUL", field -> {
            return "founders_ul";
        });
    }

    @Override
    protected EgrDetailsHistory._Fields getField(String fieldName) {
        return EgrDetailsHistory._Fields.findByName(fieldName);
    }

    @Override
    protected EgrDetailsHistory newInstance() {
        return new EgrDetailsHistory();
    }
}
