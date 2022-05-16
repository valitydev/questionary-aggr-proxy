package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.base_kontur_focus.Branch;

public class KonturBranchDeserializer extends AbstractThriftDeserializer<Branch._Fields, Branch> {

    public KonturBranchDeserializer() {
        addFieldNameConverter("parsedAddressRF", field -> {
            return "address_rf";
        });
    }

    @Override
    protected Branch._Fields getField(String fieldName) {
        return Branch._Fields.findByName(fieldName);
    }

    @Override
    protected Branch newInstance() {
        return new Branch();
    }
}
