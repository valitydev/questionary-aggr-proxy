package dev.vality.questionary.aggr.proxy.handler.dadata;

import dev.vality.questionary.aggr.proxy.handler.dadata.util.DaDataQueryMapper;
import dev.vality.questionary.aggr.proxy.serialize.model.BankContentWrapper;
import dev.vality.questionary.aggr.proxy.serialize.model.BankResponseWrapper;
import dev.vality.questionary.aggr.proxy.service.api.DaDataApi;
import dev.vality.questionary.aggr.proxy.service.api.model.DaDataQuery;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataRequest;
import dev.vality.questionary_proxy_aggr.dadata_api.DaDataResponse;
import dev.vality.questionary_proxy_aggr.dadata_bank.BankContent;
import dev.vality.questionary_proxy_aggr.dadata_bank.BankQuery;
import dev.vality.questionary_proxy_aggr.dadata_bank.BankResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DaDataBankHandler extends AbstractDaDataHandler {

    public DaDataBankHandler(DaDataApi daDataApi) {
        super(daDataApi);
    }

    @Override
    protected DaDataResponse handleRequest(DaDataRequest request) throws Exception {
        if (!request.isSetBankQuery()) {
            throw new IllegalArgumentException("Need to specify bank query");
        }
        final BankQuery bankQuery = request.getBankQuery();
        log.info("Convert bankQuery to request obj: {}", bankQuery);
        final DaDataQuery daDataQuery = DaDataQueryMapper.toQuery(bankQuery);
        log.info("Converted bankQuery: {}", daDataQuery);
        final ResponseEntity<String> responseEntity = daDataApi.bankRequest(daDataQuery);
        log.info("Read DaData bank response");
        final BankResponseWrapper bankResponseWrapper = getObjectMapper().readValue(responseEntity.getBody(), BankResponseWrapper.class);
        final List<BankContentWrapper> bankContentWrapperList = bankResponseWrapper.getBankContentWrapperList();
        final List<BankContent> bankContentList = bankContentWrapperList.stream()
                .map(BankContentWrapper::getBankContent)
                .collect(Collectors.toList());
        final BankResponse bankResponse = new BankResponse();
        bankResponse.setSuggestions(bankContentList);

        return DaDataResponse.bank_response(bankResponse);
    }
}
