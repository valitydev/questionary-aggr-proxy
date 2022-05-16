package dev.vality.questionary.aggr.proxy.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dev.vality.questionary.aggr.proxy.serialize.converter.DefaultThriftFieldNameConverter;
import dev.vality.questionary.aggr.proxy.serialize.converter.ThriftFieldNameConverter;
import dev.vality.questionary.aggr.proxy.serialize.converter.UnionThriftFieldConverter;
import dev.vality.questionary.aggr.proxy.serialize.extractor.FieldExtractor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TUnion;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @param <E> An implementation of the {@link TFieldIdEnum} interface.
 * @param <T> An implementation of the {@link TBase} interface.
 */
@Slf4j
public abstract class AbstractThriftDeserializer<E extends TFieldIdEnum, T extends TBase<T, E>> extends JsonDeserializer<T> {

    private static final ThriftFieldNameConverter DEF_FIELD_NAME_CONVERTER = new DefaultThriftFieldNameConverter();

    private final Map<String, ThriftFieldNameConverter> fieldNameConverterMap = new HashMap<>();
    private final Map<String, UnionThriftFieldConverter> unionThriftFieldConverterMap = new HashMap<>();
    private final Map<String, FieldExtractor<T>> fieldExtractorMap = new HashMap<>();

    // Default json field name converter
    private ThriftFieldNameConverter baseThriftFieldNameConverter = DEF_FIELD_NAME_CONVERTER;

    @Override
    public T deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        final T instance = newInstance();
        final ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        final ObjectNode rootNode = mapper.readTree(jp);
        final Iterator<Map.Entry<String, JsonNode>> iterator = rootNode.fields();

        while (iterator.hasNext()) {
            final Map.Entry<String, JsonNode> currentField = iterator.next();
            try {
                if (currentField.getValue().getNodeType() == JsonNodeType.NULL) {
                    log.debug("Skipping null value. Field: {}", currentField.getKey());
                    continue;
                }

                // Custom field converter
                final FieldExtractor<T> fieldExtractor = fieldExtractorMap.get(currentField.getKey());
                if (fieldExtractor != null) {
                    log.debug("Deserialize '{}' with custom field extractor", currentField.getKey());
                    fieldExtractor.extract(instance, currentField.getValue());
                    continue;
                }

                // Convert json field name to thrift field name
                ThriftFieldNameConverter fieldConverter = fieldNameConverterMap.get(currentField.getKey());
                if (fieldConverter == null) {
                    fieldConverter = baseThriftFieldNameConverter;
                }

                final E field = getField(fieldConverter.convert(currentField.getKey()));
                if (field == null) {
                    log.warn("Unknown field: '{}'", currentField.getKey());
                    continue;
                }

                final JsonParser parser = currentField.getValue().traverse();
                parser.setCodec(mapper);
                final JavaType generateValueType = generateValueType(instance, field, ctx.getTypeFactory());
                log.debug("Generated value type: {}", generateValueType);
                final boolean unionField = isUnion(generateValueType);
                final Object value;
                if (unionField) {
                    log.debug("Trying to convert thrift union field: {}", currentField.getKey());
                    final UnionThriftFieldConverter unionThriftFieldConverter = unionThriftFieldConverterMap.get(currentField.getKey());
                    if (unionThriftFieldConverter == null) {
                        log.error("Bad field '{}'. Need to specify field type for Union. Deserializer: {}",
                                currentField.getKey(), this.getClass());
                        continue;
                    }
                    final Object tmpValue = mapper.readValue(parser, unionThriftFieldConverter.type());
                    value = unionThriftFieldConverter.union(tmpValue);
                } else {
                    value = mapper.readValue(parser, generateValueType);
                }
                log.debug("Field '{}' produced value '{}' of type '{}'.", currentField.getKey(), value, value.getClass().getName());
                instance.setFieldValue(field, value);
            } catch (Exception e) {
                log.error("Unable to deserialize field '{}'.", currentField.getKey(), e);
            }
        }

        return instance;
    }

    protected final void addCustomFieldExtractor(String field, FieldExtractor<T> extractor) {
        fieldExtractorMap.put(field, extractor);
    }

    protected final void addFieldNameConverter(String field, ThriftFieldNameConverter converter) {
        fieldNameConverterMap.put(field, converter);
    }

    protected final void addUnionFieldConverter(String field, UnionThriftFieldConverter converter) {
        unionThriftFieldConverterMap.put(field, converter);
    }

    protected boolean isUnion(JavaType javaType) {
        return javaType.getSuperClass() != null && (javaType.getSuperClass().getRawClass() == TUnion.class);
    }

    protected abstract E getField(String fieldName);

    protected abstract T newInstance();

    protected JavaType generateValueType(T thriftInstance, E field, TypeFactory typeFactory) throws NoSuchFieldException {
        final Field declaredField = thriftInstance.getClass().getDeclaredField(field.getFieldName());
        if (declaredField.getType().equals(declaredField.getGenericType())) {
            log.debug("Generating JavaType for type '{}'.", declaredField.getType());
            return typeFactory.constructType(declaredField.getType());
        } else {
            final ParameterizedType type = (ParameterizedType) declaredField.getGenericType();
            final Class<?>[] parameterizedTypes = new Class<?>[type.getActualTypeArguments().length];
            for (int i = 0; i < type.getActualTypeArguments().length; i++) {
                parameterizedTypes[i] = (Class<?>) type.getActualTypeArguments()[i];
            }
            log.debug("Generating JavaType for type '{}' with generics '{}'", declaredField.getType(), parameterizedTypes);
            return typeFactory.constructParametricType(declaredField.getType(), parameterizedTypes);
        }
    }

    public void setBaseThriftFieldNameConverter(ThriftFieldNameConverter baseThriftFieldNameConverter) {
        this.baseThriftFieldNameConverter = baseThriftFieldNameConverter;
    }
}
