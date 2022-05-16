package dev.vality.questionary.aggr.proxy.config.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Configuration
@ConfigurationProperties(prefix = "da-data")
@Validated
@Getter
@Setter
public class DaDataSettings {

    @NotEmpty
    private String token;

}
