package dev.vality.questionary.aggr.proxy.handler.kontur;

import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import dev.vality.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;

public interface KonturFocusRequestHandler {

    KonturFocusResponse handle(KonturFocusRequest request);

}
