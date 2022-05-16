package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse;

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
