package dev.vality.questionary.aggr.proxy.serialize.extractor;

import com.fasterxml.jackson.databind.JsonNode;
import dev.vality.questionary.aggr.proxy.util.JsonHelper;
import dev.vality.questionary_proxy_aggr.dadata_address.Address;

public class AddressFieldExtractor implements FieldExtractor<Address> {

    private final AddressDataFieldExtractor addressDataFieldExtractor;

    public AddressFieldExtractor() {
        this.addressDataFieldExtractor = new AddressDataFieldExtractor();
    }

    @Override
    public void extract(Address instance, JsonNode node) {
        final String value = JsonHelper.safeGet(node, "value", JsonNode::textValue);
        if (value != null) {
            instance.setValue(value);
        }
        final String unrestrictedValue = JsonHelper.safeGet(node, "unrestricted_value", JsonNode::textValue);
        if (unrestrictedValue != null) {
            instance.setUnrestrictedValue(unrestrictedValue);
        }

        final JsonNode dataNode = node.get("data");

        if (dataNode == null) {
            return;
        }

        addressDataFieldExtractor.extract(instance, dataNode);
    }
}
