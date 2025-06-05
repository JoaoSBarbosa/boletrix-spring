package com.joaobarbosadev.boletrix.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentStatus;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentStatusRequest;

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
    public static String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }
    public static LocalDateTime expiresDateTime() {
        return LocalDateTime.now().plusHours(3);
    }

    public static Instant getInstant() {
        return Instant.now();
    }

    public static InstallmentStatus converter(String jsonString) {
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           objectMapper.registerModule(new JavaTimeModule());
           return objectMapper.readValue(jsonString, InstallmentStatus.class);
       }catch (Exception e) {
           throw new IllegalArgumentException("Erro ao processar a conversão de string para entidade");
       }
    }
}
