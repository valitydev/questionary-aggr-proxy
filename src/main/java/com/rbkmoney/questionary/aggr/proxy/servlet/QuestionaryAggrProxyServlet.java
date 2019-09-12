package com.rbkmoney.questionary.aggr.proxy.servlet;

import com.rbkmoney.damsel.questionary_proxy_aggr.QuestionaryAggrProxyHandlerSrv;
import com.rbkmoney.questionary.aggr.proxy.handler.QuestionaryAggrProxyHandler;
import com.rbkmoney.woody.thrift.impl.http.THServiceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/questionary/proxy")
public class QuestionaryAggrProxyServlet extends GenericServlet {

    @Autowired
    private QuestionaryAggrProxyHandler handler;

    private Servlet servlet;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servlet = new THServiceBuilder().build(QuestionaryAggrProxyHandlerSrv.Iface.class, handler);
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        servlet.service(request, response);
    }
}
