package dev.vality.questionary.aggr.proxy.handler.dadata.util;

import dev.vality.questionary.aggr.proxy.service.api.model.DaDataFilter;
import dev.vality.questionary.aggr.proxy.service.api.model.DaDataQuery;
import dev.vality.questionary_proxy_aggr.base_dadata.*;
import dev.vality.questionary_proxy_aggr.dadata_address.AddressLocationFilter;
import dev.vality.questionary_proxy_aggr.dadata_address.AddressQuery;
import dev.vality.questionary_proxy_aggr.dadata_address.BoundType;
import dev.vality.questionary_proxy_aggr.dadata_bank.BankQuery;
import dev.vality.questionary_proxy_aggr.dadata_fio.FioQuery;
import dev.vality.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQuery;
import dev.vality.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQueryFilter;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedQuery;
import dev.vality.questionary_proxy_aggr.dadata_okved2.OkvedQueryFilter;
import dev.vality.questionary_proxy_aggr.dadata_party.PartyQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DaDataQueryMapperTest {

    @Test
    public void addressToQueryTest() {
        final AddressQuery addressQuery = new AddressQuery();
        addressQuery.setQuery("test");
        addressQuery.setCount((byte) 15);
        addressQuery.setFromBound(BoundType.house);
        addressQuery.setToBound(BoundType.area);
        final AddressLocationFilter addressLocationFilter = new AddressLocationFilter();
        addressLocationFilter.setKladrId("65");
        final AddressLocationFilter addressLocationFilter1 = new AddressLocationFilter();
        addressLocationFilter1.setCityFiasId("110d6ad9-0b64-47cf-a2ee-7e935228799c");
        addressQuery.setLocations(Arrays.asList(addressLocationFilter, addressLocationFilter1));
        addressQuery.setRestrictValue(true);
        final LocationBoostFilter locationBoostFilter = new LocationBoostFilter();
        locationBoostFilter.setKladrId("78");
        addressQuery.setLocationsBoost(Collections.singletonList(locationBoostFilter));

        final DaDataQuery query = DaDataQueryMapper.toQuery(addressQuery);
        Assert.assertEquals(addressQuery.getQuery(), query.getQuery());
        Assert.assertEquals(addressQuery.getCount(), query.getCount().byteValue());
        Assert.assertEquals(addressQuery.getFromBound().name(), query.getFromBound().getValue());
        Assert.assertEquals(addressQuery.getToBound().name(), query.getToBound().getValue());
        Assert.assertEquals(addressQuery.getLocations().size(), query.getLocations().size());
        Assert.assertEquals(addressLocationFilter.getKladrId(), query.getLocations().get(0).getKladrId());
        Assert.assertEquals(addressLocationFilter1.getCityFiasId(), query.getLocations().get(1).getCityFiasId());
        Assert.assertEquals(addressQuery.getLocationsBoost().size(), query.getLocationsBoost().size());
        Assert.assertEquals(locationBoostFilter.getKladrId(), query.getLocationsBoost().get(0).getKladrId());
    }

    @Test
    public void partyToQueryTest() {
        final PartyQuery partyQuery = new PartyQuery();
        partyQuery.setQuery("test");
        partyQuery.setCount((byte) 17);
        final LocationFilter locationFilter = new LocationFilter();
        locationFilter.setKladrId("02");
        partyQuery.setLocations(Collections.singletonList(locationFilter));
        final List<OrgStatus> orgStatusList = Collections.singletonList(OrgStatus.LIQUIDATED);
        partyQuery.setStatus(orgStatusList);
        partyQuery.setType(OrgType.LEGAL);
        final LocationBoostFilter locationBoostFilter = new LocationBoostFilter();
        locationBoostFilter.setKladrId("78");
        partyQuery.setLocationsBoost(Collections.singletonList(locationBoostFilter));

        final DaDataQuery query = DaDataQueryMapper.toQuery(partyQuery);
        Assert.assertEquals(partyQuery.getQuery(), query.getQuery());
        Assert.assertEquals(partyQuery.getCount(), query.getCount().byteValue());
        Assert.assertEquals(partyQuery.getType().name(), query.getType());
        Assert.assertEquals(locationFilter.getKladrId(), query.getLocations().get(0).getKladrId());
        Assert.assertEquals(orgStatusList.get(0).name(), query.getStatus().get(0));
        Assert.assertEquals(locationBoostFilter.getKladrId(), query.getLocationsBoost().get(0).getKladrId());
    }

    @Test
    public void bankToQueryTest() {
        final BankQuery bankQuery = new BankQuery();
        bankQuery.setQuery("test");
        bankQuery.setCount((byte) 14);
        bankQuery.setType(OrgType.INDIVIDUAL);
        final List<OrgStatus> orgStatusList = Collections.singletonList(OrgStatus.ACTIVE);
        bankQuery.setStatus(orgStatusList);

        final DaDataQuery query = DaDataQueryMapper.toQuery(bankQuery);
        Assert.assertEquals(bankQuery.getQuery(), query.getQuery());
        Assert.assertEquals(bankQuery.getCount(), query.getCount().byteValue());
        Assert.assertEquals(bankQuery.getType().name(), query.getType());
        Assert.assertEquals(orgStatusList.get(0).name(), query.getStatus().get(0));
    }

    @Test
    public void fioToQueryTest() {
        final FioQuery fioQuery = new FioQuery();
        fioQuery.setQuery("test");
        fioQuery.setCount((byte) 10);
        fioQuery.setGender(Gender.MALE);
        final List<String> partList = Collections.singletonList("NAME");
        fioQuery.setParts(partList);

        final DaDataQuery query = DaDataQueryMapper.toQuery(fioQuery);
        Assert.assertEquals(fioQuery.getQuery(), query.getQuery());
        Assert.assertEquals(fioQuery.getCount(), query.getCount().byteValue());
        Assert.assertEquals(fioQuery.getGender().name(), query.getGender());
        Assert.assertEquals(partList.get(0), query.getParts().get(0));
    }

    @Test
    public void fmsUnitQueryTest() {
        final FmsUnitQuery fmsUnitQuery = new FmsUnitQuery();
        fmsUnitQuery.setQuery("test");
        final FmsUnitQueryFilter fmsUnitQueryFilter = new FmsUnitQueryFilter();
        fmsUnitQueryFilter.setRegionCode("37");
        fmsUnitQueryFilter.setType("2");
        fmsUnitQuery.setFilters(Collections.singletonList(fmsUnitQueryFilter));

        final DaDataQuery query = DaDataQueryMapper.toQuery(fmsUnitQuery);
        Assert.assertEquals(fmsUnitQuery.getQuery(), query.getQuery());
        final DaDataFilter filters = query.getFilters().get(0);
        Assert.assertEquals(fmsUnitQueryFilter.getRegionCode(), filters.getRegionCode());
        Assert.assertEquals(fmsUnitQueryFilter.getType(), filters.getType());
    }

    @Test
    public void okvedQueryTest() {
        final OkvedQuery okvedQuery = new OkvedQuery();
        okvedQuery.setQuery("test");
        final OkvedQueryFilter okvedQueryFilter = new OkvedQueryFilter();
        okvedQueryFilter.setSection("С");
        okvedQuery.setFilters(Collections.singletonList(okvedQueryFilter));

        final DaDataQuery query = DaDataQueryMapper.toQuery(okvedQuery);
        Assert.assertEquals(okvedQuery.getQuery(), query.getQuery());
        Assert.assertEquals(okvedQueryFilter.getSection(), query.getFilters().get(0).getSection());
    }

}
