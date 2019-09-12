package com.rbkmoney.questionary.aggr.proxy.serialize.dadata;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary.aggr.proxy.util.CommonHelper;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.License;

public class DaDataLicenseDeserializer extends AbstractThriftDeserializer<License._Fields, License> {

    public DaDataLicenseDeserializer() {
        addCustomFieldExtractor("issue_date", (instance, node) -> {
            instance.setIssueDate(CommonHelper.stringToLocalDateTime(node.toString()).toString());
        });
        addCustomFieldExtractor("issue_authority", (instance, node) -> {
            instance.setIssueAuthority(CommonHelper.stringToLocalDateTime(node.toString()).toString());
        });
    }

    @Override
    protected License._Fields getField(String fieldName) {
        return License._Fields.findByName(fieldName);
    }

    @Override
    protected License newInstance() {
        return new License();
    }
}
