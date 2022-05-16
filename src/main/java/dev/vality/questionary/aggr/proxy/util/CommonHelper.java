package dev.vality.questionary.aggr.proxy.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonHelper {

    public static LocalDateTime stringToLocalDateTime(String timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp)),
                TimeZone.getDefault().toZoneId());
    }

}
