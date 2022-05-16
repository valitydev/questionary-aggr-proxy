package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwners;

public class KonturBeneficialOwnersDeserializer extends AbstractThriftDeserializer<BeneficialOwners._Fields, BeneficialOwners> {

    public KonturBeneficialOwnersDeserializer() {
        addFieldNameConverter("beneficialOwnersFL", field -> {
            return "beneficial_owners_fl";
        });
        addFieldNameConverter("beneficialOwnersUL", field -> {
            return "beneficial_owners_ul";
        });
    }

    @Override
    protected BeneficialOwners._Fields getField(String fieldName) {
        return BeneficialOwners._Fields.findByName(fieldName);
    }

    @Override
    protected BeneficialOwners newInstance() {
        return new BeneficialOwners();
    }

}
