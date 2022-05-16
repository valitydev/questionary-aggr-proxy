package dev.vality.questionary.aggr.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class QuestionaryAggrProxyApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionaryAggrProxyApplication.class, args);
    }

}
