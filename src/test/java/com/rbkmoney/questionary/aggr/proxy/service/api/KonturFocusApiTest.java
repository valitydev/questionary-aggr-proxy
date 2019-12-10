package com.rbkmoney.questionary.aggr.proxy.service.api;

import com.rbkmoney.damsel.questionary_proxy_aggr.KonturFocusRequestException;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KonturFocusApiTest {

    private static final List<String> OGRN_LIST = Collections.emptyList();

    private static final List<String> INN_LIST = Arrays.asList("561100409545", "6663003127");

    @Autowired
    private KonturFocusApi konturFocusApi;

    @MockBean
    private RestTemplate restTemplate;

    @Test(expected = KonturFocusRequestException.class)
    public void konturFocusClientErrorTest() throws TException {
        when(restTemplate.exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenThrow(HttpServerErrorException.create(
                        HttpStatus.NOT_FOUND, "Not found", new HttpHeaders(), new byte[0], StandardCharsets.UTF_8)
                );
        ResponseEntity<String> responseEntity = konturFocusApi.egrDetailsRequest(INN_LIST, OGRN_LIST);
    }

}
