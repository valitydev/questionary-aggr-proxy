package dev.vality.questionary.aggr.proxy.serialize.dadata;

import com.fasterxml.jackson.databind.JsonNode;
import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary.aggr.proxy.serialize.extractor.AddressFieldExtractor;
import dev.vality.questionary.aggr.proxy.serialize.extractor.FieldExtractor;
import dev.vality.questionary_proxy_aggr.dadata_address.Address;
import dev.vality.questionary_proxy_aggr.dadata_bank.BankContent;

public class DaDataBankDeserializer extends AbstractThriftDeserializer<BankContent._Fields, BankContent> {

    public DaDataBankDeserializer() {
        addFieldNameConverter("phones", field -> {
            return "phone";
        });
        addFieldNameConverter("state", field -> {
            return "status";
        });
        addFieldNameConverter("name", field -> {
            return "payment";
        });
        addCustomFieldExtractor("address", new BankAddressFieldExtractor());
    }

    @Override
    protected BankContent._Fields getField(String fieldName) {
        return BankContent._Fields.findByName(fieldName);
    }

    @Override
    protected BankContent newInstance() {
        return new BankContent();
    }

    private static final class BankAddressFieldExtractor implements FieldExtractor<BankContent> {

        private final AddressFieldExtractor addressFieldExtractor;

        private BankAddressFieldExtractor() {
            this.addressFieldExtractor = new AddressFieldExtractor();
        }

        @Override
        public void extract(BankContent instance, JsonNode node) {
            final Address address = new Address();
            addressFieldExtractor.extract(address, node);
            instance.setAddress(address);
        }
    }

}
