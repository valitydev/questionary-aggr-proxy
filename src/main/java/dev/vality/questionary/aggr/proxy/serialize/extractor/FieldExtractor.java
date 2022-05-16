package dev.vality.questionary.aggr.proxy.serialize.extractor;

import com.fasterxml.jackson.databind.JsonNode;

@FunctionalInterface
public interface FieldExtractor<T> {

    /**
     * @param instance object to fill
     * @param node     field json node
     */
    void extract(T instance, JsonNode node);

}
