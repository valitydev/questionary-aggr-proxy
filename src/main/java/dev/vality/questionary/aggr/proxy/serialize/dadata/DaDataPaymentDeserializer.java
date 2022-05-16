package dev.vality.questionary.aggr.proxy.serialize.dadata;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.base_dadata.Payment;

public class DaDataPaymentDeserializer extends AbstractThriftDeserializer<Payment._Fields, Payment> {

    public DaDataPaymentDeserializer() {
        addFieldNameConverter("payment", field -> {
            return "name";
        });
        addFieldNameConverter("full", field -> {
            return "full_name";
        });
        addFieldNameConverter("short", field -> {
            return "short_name";
        });
    }

    @Override
    protected Payment._Fields getField(String fieldName) {
        return Payment._Fields.findByName(fieldName);
    }

    @Override
    protected Payment newInstance() {
        return new Payment();
    }
}
