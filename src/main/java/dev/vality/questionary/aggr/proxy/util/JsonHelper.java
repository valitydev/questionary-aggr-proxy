package dev.vality.questionary.aggr.proxy.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonHelper {

    public static <T> List<T> nodeToList(JsonNode jsonNode, Function<JsonNode, T> func) {
        final List<T> nodeList;
        if (jsonNode != null && jsonNode.getNodeType() == JsonNodeType.ARRAY) {
            nodeList = new ArrayList<>(jsonNode.size());
            for (JsonNode node : jsonNode) {
                if (node.getNodeType() == JsonNodeType.NULL) {
                    continue;
                }
                nodeList.add(func.apply(node));
            }
            return nodeList;
        }
        return Collections.emptyList();
    }

    public static <T> T safeGet(JsonNode node, String fieldName, Function<JsonNode, T> consumer) {
        final JsonNode jsonNode = node.get(fieldName);
        if (jsonNode != null) {
            return consumer.apply(jsonNode);
        }
        return null;
    }

}
