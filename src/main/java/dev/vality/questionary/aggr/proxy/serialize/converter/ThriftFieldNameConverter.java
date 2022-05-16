package dev.vality.questionary.aggr.proxy.serialize.converter;

@FunctionalInterface
public interface ThriftFieldNameConverter {

    /**
     * @param field json field name
     * @return thrift field name
     */
    String convert(String field);

}
