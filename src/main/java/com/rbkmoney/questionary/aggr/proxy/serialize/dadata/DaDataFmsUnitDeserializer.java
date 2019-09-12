package com.rbkmoney.questionary.aggr.proxy.serialize.dadata;

import com.rbkmoney.questionary.aggr.proxy.serialize.AbstractThriftDeserializer;
import com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent;

public class DaDataFmsUnitDeserializer extends AbstractThriftDeserializer<FmsUnitContent._Fields, FmsUnitContent> {
    @Override
    protected FmsUnitContent._Fields getField(String fieldName) {
        return FmsUnitContent._Fields.findByName(fieldName);
    }

    @Override
    protected FmsUnitContent newInstance() {
        return new FmsUnitContent();
    }
}
