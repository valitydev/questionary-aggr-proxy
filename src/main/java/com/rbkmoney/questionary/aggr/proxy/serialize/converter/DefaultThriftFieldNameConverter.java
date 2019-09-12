package com.rbkmoney.questionary.aggr.proxy.serialize.converter;

import com.google.common.base.CaseFormat;

public class DefaultThriftFieldNameConverter implements ThriftFieldNameConverter {
    @Override
    public String convert(String field) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field);
    }
}
