package com.rbkmoney.questionary.aggr.proxy.serialize.dadata;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary.aggr.proxy.serialize.extractor.AddressDataFieldExtractor;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.Address;

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
