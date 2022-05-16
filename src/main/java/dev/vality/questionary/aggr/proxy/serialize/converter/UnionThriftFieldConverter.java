package dev.vality.questionary.aggr.proxy.serialize.converter;

public interface UnionThriftFieldConverter<W, T> {

    /**
     * Wrap thrift instance
     *
     * @return Wrapped thrift instance
     */
    W union(T thriftInstance);

    /**
     * @return union thrift type
     */
    Class<T> type();

}
