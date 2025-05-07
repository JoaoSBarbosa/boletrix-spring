package com.joaobarbosadev.boletrix.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class Util {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.of("America/Sao_Paulo"));


    public static String getFormattedInstance(Object obj) {
        if (obj instanceof LocalDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return ((LocalDate) obj).format(formatter);
        } else if (obj instanceof LocalDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return ((LocalDateTime) obj).format(formatter);
        } else if (obj instanceof Date) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format((Date) obj);
        } else {
            return "Data inválida";
        }
    }


    public static Instant getInstant() {
        return Instant.now();
    }
}
