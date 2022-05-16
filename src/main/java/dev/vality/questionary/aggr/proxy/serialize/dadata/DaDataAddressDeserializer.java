package dev.vality.questionary.aggr.proxy.serialize.dadata;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary.aggr.proxy.serialize.extractor.AddressDataFieldExtractor;
import dev.vality.questionary_proxy_aggr.dadata_address.Address;

public class DaDataAddressDeserializer extends AbstractThriftDeserializer<Address._Fields, Address> {

    public DaDataAddressDeserializer() {
        addCustomFieldExtractor("data", new AddressDataFieldExtractor());
    }

    @Override
    protected Address._Fields getField(String fieldName) {
        return Address._Fields.findByName(fieldName);
    }

    @Override
    protected Address newInstance() {
        return new Address();
    }

}
