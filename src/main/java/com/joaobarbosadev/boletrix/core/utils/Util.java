package com.joaobarbosadev.boletrix.core.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class Util {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.of("America/Sao_Paulo"));


    public static String getFormattedInstance() {
        Instant instant = Instant.now();
        return DATE_FORMAT.format(instant);
    }

    public static Instant getInstant() {
        return Instant.now();
    }
}
