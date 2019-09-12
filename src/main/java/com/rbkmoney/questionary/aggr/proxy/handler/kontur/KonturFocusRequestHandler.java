package com.rbkmoney.questionary.aggr.proxy.handler.kontur;

import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;

public interface KonturFocusRequestHandler {

    KonturFocusResponse handle(KonturFocusRequest request);

}
