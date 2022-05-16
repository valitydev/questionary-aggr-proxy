package dev.vality.questionary.aggr.proxy.serialize.kontur;

import dev.vality.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import dev.vality.questionary.aggr.proxy.serialize.converter.UnionThriftFieldConverter;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.Contractor;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.ReqIndividualEntity;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.ReqLegalEntity;
import dev.vality.questionary_proxy_aggr.kontur_focus_req.ReqResponse;

public class KonturReqResponseDeserializer extends AbstractThriftDeserializer<ReqResponse._Fields, ReqResponse> {

    private static final ContractorIPConverter CONTRACTOR_IP_CONVERTER = new ContractorIPConverter();

    private static final ContractorULConverter CONTRACTOR_UL_CONVERTER = new ContractorULConverter();

    public KonturReqResponseDeserializer() {
        addFieldNameConverter("IP", field -> {
            return "private_entity";
        });
        addFieldNameConverter("UL", field -> {
            return "private_entity";
        });
        addUnionFieldConverter("IP", CONTRACTOR_IP_CONVERTER);
        addUnionFieldConverter("UL", CONTRACTOR_UL_CONVERTER);
    }

    @Override
    protected ReqResponse._Fields getField(String fieldName) {
        return ReqResponse._Fields.findByName(fieldName);
    }

    @Override
    protected ReqResponse newInstance() {
        return new ReqResponse();
    }

    private static final class ContractorIPConverter
            implements UnionThriftFieldConverter<Contractor, ReqIndividualEntity> {

        @Override
        public Contractor union(ReqIndividualEntity thriftInstance) {
            return Contractor.individual_entity(thriftInstance);
        }

        @Override
        public Class<ReqIndividualEntity> type() {
            return ReqIndividualEntity.class;
        }

    }

    private static final class ContractorULConverter
            implements UnionThriftFieldConverter<Contractor, ReqLegalEntity> {

        @Override
        public Contractor union(ReqLegalEntity thriftInstance) {
            return Contractor.legal_entity(thriftInstance);
        }

        @Override
        public Class<ReqLegalEntity> type() {
            return ReqLegalEntity.class;
        }

    }

}
