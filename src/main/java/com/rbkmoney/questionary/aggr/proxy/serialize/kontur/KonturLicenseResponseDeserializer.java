package com.rbkmoney.questionary.aggr.proxy.serialize.kontur;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse;

public class KonturLicenseResponseDeserializer extends AbstractThriftDeserializer<LicencesResponse._Fields, LicencesResponse> {

    @Override
    protected LicencesResponse._Fields getField(String fieldName) {
        return LicencesResponse._Fields.findByName(fieldName);
    }

    @Override
    protected LicencesResponse newInstance() {
        return new LicencesResponse();
    }

}
