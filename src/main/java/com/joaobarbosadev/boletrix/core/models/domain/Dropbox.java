package com.joaobarbosadev.boletrix.core.models.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_dropbox")
public class Dropbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "chave_aplicacao" )
    private String appKey;
    @Column( name = "chave_secreta_aplicacao" )
    private String appSecretKey;
    @Column( name = "codigo_autorizacao" )
    private String authCode;
    @Column( name = "chave_acesso" )
    private String accessKey;
    @Column( name = "revalidacao_chave_acesso" )
    private String refreshKey;
    @Column( name = "expira_em" )
    private String expiresIn;
    @Column( name = "data_hora_cadastro_acesso" )
    private LocalDateTime createAccessDateTime;
    @Column( name = "data_hora_vencimento_acesso" )
    private LocalDateTime expiresAccessDateTime;

    public Dropbox() {}

    public Dropbox(
            Long id,
            LocalDateTime expiresAccessDateTime,
            LocalDateTime createAccessDateTime,
            String expiresIn,
            String refreshKey,
            String accessKey,
            String authCode,
            String appSecretKey,
            String appKey
    ) {
        this.id = id;
        this.expiresAccessDateTime = expiresAccessDateTime;
        this.createAccessDateTime = createAccessDateTime;
        this.expiresIn = expiresIn;
        this.refreshKey = refreshKey;
        this.accessKey = accessKey;
        this.authCode = authCode;
        this.appSecretKey = appSecretKey;
        this.appKey = appKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getExpiresAccessDateTime() {
        return expiresAccessDateTime;
    }

    public void setExpiresAccessDateTime(LocalDateTime expiresAccessDateTime) {
        this.expiresAccessDateTime = expiresAccessDateTime;
    }

    public LocalDateTime getCreateAccessDateTime() {
        return createAccessDateTime;
    }

    public void setCreateAccessDateTime(LocalDateTime createAccessDateTime) {
        this.createAccessDateTime = createAccessDateTime;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshKey() {
        return refreshKey;
    }

    public void setRefreshKey(String refreshKey) {
        this.refreshKey = refreshKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAppSecretKey() {
        return appSecretKey;
    }

    public void setAppSecretKey(String appSecretKey) {
        this.appSecretKey = appSecretKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
