package dev.vality.questionary.aggr.proxy.handler.dadata.util;

import dev.vality.questionary.aggr.proxy.service.api.model.*;
import dev.vality.questionary_proxy_aggr.base_dadata.LocationBoostFilter;
import dev.vality.questionary_proxy_aggr.base_dadata.OrgStatus;
import dev.vality.questionary_proxy_aggr.dadata_address.AddressQuery;
import dev.vality.questionary_proxy_aggr.dadata_bank.BankQuery;
import dev.vality.questionary_proxy_aggr.dadata_fio.FioQuery;
import dev.vality.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQuery;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedQuery;
import dev.vality.questionary_proxy_aggr.dadata_party.PartyQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DaDataQueryMapper {

    public static DaDataQuery toQuery(AddressQuery addressQuery) {
        final DaDataQuery query = new DaDataQuery();
        query.setQuery(addressQuery.getQuery());
        final short count = addressQuery.getCount();
        if (count > 0) {
            query.setCount((short) addressQuery.getCount());
        }
        if (addressQuery.isSetLocations() && !addressQuery.getLocations().isEmpty()) {
            final List<DaDataLocationFilter> dataLocationFilterList = addressQuery.getLocations().stream()
                    .map(addressLocationFilter -> {
                        final DaDataLocationFilter daDataLocationFilter = new DaDataLocationFilter();
                        daDataLocationFilter.setKladrId(addressLocationFilter.getKladrId());
                        daDataLocationFilter.setRegionFiasId(addressLocationFilter.getRegionFiasId());
                        daDataLocationFilter.setAreaFiasId(addressLocationFilter.getAreaFiasId());
                        daDataLocationFilter.setCityFiasId(addressLocationFilter.getCityFiasId());
                        daDataLocationFilter.setSettlementFiasId(addressLocationFilter.getSettlementFiasId());
                        daDataLocationFilter.setStreetFiasId(addressLocationFilter.getStreetFiasId());
                        return daDataLocationFilter;
                    })
                    .collect(Collectors.toList());
            query.setLocations(dataLocationFilterList);
        }
        if (addressQuery.isSetLocationsBoost() && !addressQuery.getLocationsBoost().isEmpty()) {
            final List<DaDataLocationBoostFilter> dataLocationBoostFilterList =
                    addressQuery.getLocationsBoost().stream()
                            .map(locationBoostFilter -> {
                                final DaDataLocationBoostFilter daDataLocationBoostFilter =
                                        new DaDataLocationBoostFilter();
                                daDataLocationBoostFilter.setKladrId(locationBoostFilter.getKladrId());
                                return daDataLocationBoostFilter;
                            })
                            .collect(Collectors.toList());
            query.setLocationsBoost(dataLocationBoostFilterList);
        }
        if (addressQuery.isSetFromBound()) {
            final DaDataBound daDataBound = new DaDataBound();
            daDataBound.setValue(addressQuery.getFromBound().name());
            query.setFromBound(daDataBound);
        }
        if (addressQuery.isSetToBound()) {
            final DaDataBound daDataBound = new DaDataBound();
            daDataBound.setValue(addressQuery.getToBound().name());
            query.setToBound(daDataBound);
        }
        return query;
    }

    public static DaDataQuery toQuery(PartyQuery partyQuery) {
        final DaDataQuery query = new DaDataQuery();
        query.setQuery(partyQuery.getQuery());
        final short count = partyQuery.getCount();
        if (count > 0) {
            query.setCount((short) partyQuery.getCount());
        }
        if (partyQuery.isSetStatus() && !partyQuery.getStatus().isEmpty()) {
            query.setStatus(orgStatusToDaData(partyQuery.getStatus()));
        }
        if (partyQuery.isSetType()) {
            query.setType(partyQuery.getType().name());
        }
        if (partyQuery.isSetLocations() && !partyQuery.getLocations().isEmpty()) {
            final List<DaDataLocationFilter> locationFilterList = partyQuery.getLocations().stream()
                    .map(locationFilter -> {
                        final DaDataLocationFilter daDataLocationFilter = new DaDataLocationFilter();
                        daDataLocationFilter.setKladrId(locationFilter.getKladrId());
                        return daDataLocationFilter;
                    })
                    .collect(Collectors.toList());
            query.setLocations(locationFilterList);
        }
        if (partyQuery.isSetLocationsBoost() && !partyQuery.getLocationsBoost().isEmpty()) {
            query.setLocationsBoost(locationBoostToDaData(partyQuery.getLocationsBoost()));
        }
        return query;
    }

    public static DaDataQuery toQuery(BankQuery bankQuery) {
        final DaDataQuery query = new DaDataQuery();
        query.setQuery(bankQuery.getQuery());
        final short count = bankQuery.getCount();
        if (count > 0) {
            query.setCount(count);
        }
        if (bankQuery.isSetStatus() && !bankQuery.getStatus().isEmpty()) {
            query.setStatus(orgStatusToDaData(bankQuery.getStatus()));
        }
        if (bankQuery.isSetType()) {
            query.setType(bankQuery.getType().name());
        }
        return query;
    }

    public static DaDataQuery toQuery(FioQuery fioQuery) {
        final DaDataQuery query = new DaDataQuery();
        query.setQuery(fioQuery.getQuery());
        final short count = fioQuery.getCount();
        if (count > 0) {
            query.setCount(count);
        }
        if (fioQuery.isSetParts() && !fioQuery.getParts().isEmpty()) {
            query.setParts(fioQuery.getParts());
        }
        if (fioQuery.isSetGender()) {
            query.setGender(fioQuery.getGender().name());
        }
        return query;
    }

    public static DaDataQuery toQuery(FmsUnitQuery fmsUnitQuery) {
        final DaDataQuery query = new DaDataQuery();
        query.setQuery(fmsUnitQuery.getQuery());
        if (fmsUnitQuery.isSetFilters() && !fmsUnitQuery.getFilters().isEmpty()) {
            final List<DaDataFilter> daDataFilterList = fmsUnitQuery.getFilters().stream()
                    .map(fmsUnitQueryFilter -> {
                        final DaDataFilter daDataFilter = new DaDataFilter();
                        daDataFilter.setRegionCode(fmsUnitQueryFilter.getRegionCode());
                        daDataFilter.setType(fmsUnitQueryFilter.getType());
                        return daDataFilter;
                    })
                    .collect(Collectors.toList());
            query.setFilters(daDataFilterList);
        }
        return query;
    }

    public static DaDataQuery toQuery(OkvedQuery okvedQuery) {
        final DaDataQuery query = new DaDataQuery();
        query.setQuery(okvedQuery.getQuery());
        if (okvedQuery.isSetFilters() && !okvedQuery.getFilters().isEmpty()) {
            final List<DaDataFilter> dataFilters = okvedQuery.getFilters().stream()
                    .map(okvedQueryFilter -> {
                        final DaDataFilter daDataFilter = new DaDataFilter();
                        daDataFilter.setSection(okvedQueryFilter.getSection());
                        return daDataFilter;
                    })
                    .collect(Collectors.toList());
            query.setFilters(dataFilters);
        }
        return query;
    }

    private static List<DaDataLocationBoostFilter> locationBoostToDaData(
            List<LocationBoostFilter> locationBoostFilterList) {
        return locationBoostFilterList.stream()
                .map(locationBoostFilter -> {
                    final DaDataLocationBoostFilter daDataLocationBoostFilter = new DaDataLocationBoostFilter();
                    daDataLocationBoostFilter.setKladrId(locationBoostFilter.getKladrId());
                    return daDataLocationBoostFilter;
                })
                .collect(Collectors.toList());
    }

    private static List<String> orgStatusToDaData(List<OrgStatus> orgStatusList) {
        return orgStatusList.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
