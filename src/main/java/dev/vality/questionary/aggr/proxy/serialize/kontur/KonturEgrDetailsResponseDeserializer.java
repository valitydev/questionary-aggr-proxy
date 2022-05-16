package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary.aggr.proxy.serialize.converter.UnionThriftFieldConverter;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.Contractor;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsIndividualEntity;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsLegalEntity;
import dev.vality.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse;

public class KonturEgrDetailsResponseDeserializer
        extends AbstractThriftDeserializer<EgrDetailsResponse._Fields, EgrDetailsResponse> {

    private static final UnionThriftFieldConverter CONTRACTOR_IP_CONVERTER = new ContractorIpConverter();

    private static final UnionThriftFieldConverter CONTRACTOR_UL_CONVERTER = new ContractorUlConverter();

    public KonturEgrDetailsResponseDeserializer() {
        addFieldNameConverter("IP", field -> {
            return "contractor";
        });
        addFieldNameConverter("UL", field -> {
            return "contractor";
        });
        addUnionFieldConverter("IP", CONTRACTOR_IP_CONVERTER);
        addUnionFieldConverter("UL", CONTRACTOR_UL_CONVERTER);
    }

    @Override
    protected EgrDetailsResponse._Fields getField(String fieldName) {
        return EgrDetailsResponse._Fields.findByName(fieldName);
    }

    @Override
    protected EgrDetailsResponse newInstance() {
        return new EgrDetailsResponse();
    }

    private static final class ContractorUlConverter
            implements UnionThriftFieldConverter<Contractor, EgrDetailsLegalEntity> {

        @Override
        public Contractor union(EgrDetailsLegalEntity thriftInstance) {
            return Contractor.legal_entity(thriftInstance);
        }

        @Override
        public Class<EgrDetailsLegalEntity> type() {
            return EgrDetailsLegalEntity.class;
        }
    }

    private static final class ContractorIpConverter
            implements UnionThriftFieldConverter<Contractor, EgrDetailsIndividualEntity> {

        @Override
        public Contractor union(EgrDetailsIndividualEntity thriftInstance) {
            return Contractor.individual_entity(thriftInstance);
        }

        @Override
        public Class<EgrDetailsIndividualEntity> type() {
            return EgrDetailsIndividualEntity.class;
        }
    }

}
