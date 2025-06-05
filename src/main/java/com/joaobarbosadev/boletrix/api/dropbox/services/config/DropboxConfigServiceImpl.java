package com.joaobarbosadev.boletrix.api.dropbox.services.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEntityNotFoundException;
import com.joaobarbosadev.boletrix.core.models.domain.Dropbox;
import com.joaobarbosadev.boletrix.core.repository.DropboxRepository;
import com.joaobarbosadev.boletrix.core.utils.Util;
import com.joaobarbosadev.boletrix.external.DropboxApiV1;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DropboxConfigServiceImpl implements DropboxConfigService {

    private final DropboxRepository repository;
    private final RestTemplate restTemplate;
    private final DropboxApiV1 dropboxApiV1;

    public DropboxConfigServiceImpl(DropboxRepository repository, RestTemplate restTemplate, DropboxApiV1 dropboxApiV1) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.dropboxApiV1 = dropboxApiV1;
    }

    @Override
    public void refreshAccessToken() {
        Dropbox dropboxToken = repository.findById(1L).orElseThrow(() -> new CustomEntityNotFoundException("Não foi possível localizar nenhuma configuração."));
        String refreshAccessToken = dropboxApiV1.refreshAccessToken(
                dropboxToken.getRefreshKey(),
                "refresh_token",
                dropboxToken.getAppKey(),
                dropboxToken.getAppSecretKey()
        );
        JsonObject refreshTokenObj = JsonParser.parseString(refreshAccessToken).getAsJsonObject();
        dropboxToken.setAccessKey(refreshTokenObj.get("access_token").getAsString());
        dropboxToken.setExpiresIn(refreshTokenObj.get("expires_in").getAsString());
        dropboxToken.setCreateAccessDateTime(Util.getLocalDateTime());
        dropboxToken.setExpiresAccessDateTime(Util.expiresDateTime());
        repository.save(dropboxToken);
    }


    @Override
    public void checkoutValidate() {
        Dropbox dropbox = getDropbox();
        LocalDateTime dataExpires = dropbox.getExpiresAccessDateTime();
        LocalDateTime dataActual = LocalDateTime.now();
        handleRefreshToken(dataExpires, dataActual);
    }

    @Override
    public Dropbox checkoutValidateAndReturnDropbox() {
        Dropbox dropbox = getDropbox();
        LocalDateTime dataExpires = dropbox.getExpiresAccessDateTime();
        LocalDateTime dataActual = LocalDateTime.now();
        handleRefreshToken(dataExpires, dataActual);
        return dropbox;
    }

    @Transactional()
    public Dropbox update(Dropbox source, Long id) {
        try {
            Dropbox dropboxConfigurations = repository.getReferenceById(id);
            copyData(source, dropboxConfigurations);
            dropboxConfigurations = dropboxConfigurations = repository.save(dropboxConfigurations);

            return dropboxConfigurations;
        } catch (EntityNotFoundException e) {
            throw new CustomEntityNotFoundException("Não foi possivel localizar registros de configurações de Dropbox com o id informado: " + id);
        }
    }

    @Transactional(readOnly = true)
    public Page<Dropbox> page(Pageable pageable) {
        Page<Dropbox> dropboxConfigurations = repository.findAll(pageable);

        if (!dropboxConfigurations.isEmpty()) {
            return dropboxConfigurations;
        }
        return null;
    }

    @Transactional
    public void delete(Long dropboxConfigurationId) {
        Optional<Dropbox> consultDropboxConfiguration = repository.findById(dropboxConfigurationId);

        if (consultDropboxConfiguration.isEmpty())
            throw new CustomEntityNotFoundException("Não foi possivel localizar registros de configurações de Dropbox com o id informado: " + dropboxConfigurationId);
        repository.delete(consultDropboxConfiguration.get());
    }

    private void handleRefreshToken(LocalDateTime dataExpires, LocalDateTime dataActual) {
        if (dataExpires.isBefore(dataActual)) {
            try {
                refreshAccessToken();
            } catch (Exception e) {
                throw new RuntimeException("Erro na verificação de código de acesso do Dropbox: " + e.getMessage());
            }
        }
    }

    public String getRefreshToken() {
        // Busca o primeiro registro de configuração do Dropbox no repositório
        //
        Dropbox dropboxToken = repository.getDropbox().orElseThrow(() -> new CustomEntityNotFoundException("Não foi possível localizar registros de configuração do Dropbox"));

        // Obtendo o clientId (AppKey) e clientSecret
        String clientId = dropboxToken.getAppKey();
        String clientSecret = dropboxToken.getAppSecretKey();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Configura o corpo da requisição com os parâmetros necessários
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code"); // Tipo de grant para obter o token
//        body.add("code", authorizationCode); // Código de autorização recebido
        body.add("code", dropboxToken.getAuthCode()); // Código de autorização recebido
        body.add("client_id", clientId); // ID do cliente (AppKey)
        body.add("client_secret", clientSecret); // Segredo do cliente (AppSecretKey)
        // Cria a entidade de requisição HTTP com o corpo e os headers
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        // Envia a requisição POST para o endpoint do Dropbox para obter o token
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.dropbox.com/oauth2/token", requestEntity, String.class);
        // Verifica se a resposta da requisição é OK (200)
        if (response.getStatusCode() == HttpStatus.OK) {
            // Faz o parsing do corpo da resposta para um objeto JSON
            JsonObject responseBody = JsonParser.parseString(response.getBody()).getAsJsonObject();
            // Atualiza o objeto DropboxToken com os novos tokens recebidos
            dropboxToken.setAccessKey(responseBody.get("access_token").getAsString());
            dropboxToken.setRefreshKey(responseBody.get("refresh_token").getAsString());

            // Se houver informação sobre expiração do token, atualiza também
            if (responseBody.has("expires_in")) {
                // Tempo de expiração em segundos
                long expiresIn = responseBody.get("expires_in").getAsLong();
                // Calcula a data e hora de expiração
                LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiresIn);
                // Define a data de expiração no formato desejado
                dropboxToken.setExpiresAccessDateTime(expirationDateTime);
                // Define a data e hora de expiração do acesso

                dropboxToken.setExpiresAccessDateTime(expirationDateTime);
            }
            // Salva o objeto atualizado no repositório
            repository.save(dropboxToken);
            // Retorna o novo token de atualização (refresh_token)
            return responseBody.get("refresh_token").getAsString();
        } else {
            // Lança uma exceção se o status da resposta não for OK (200)
            throw new RuntimeException("Erro ao obter o token de atualização do Dropbox. Status: " + response.getStatusCodeValue());
        }
    }

    private void copyData(Dropbox source, Dropbox entity) {

        if (source.getId() != null) entity.setId(source.getId());
        if (source.getAccessKey() != null) entity.setAccessKey(source.getAccessKey());
        if (source.getAppKey() != null) entity.setAppKey(source.getAppKey());
        if (source.getAppSecretKey() != null) entity.setAppSecretKey(source.getAppSecretKey());
        if (source.getAuthCode() != null) entity.setAuthCode(source.getAuthCode());
        if (source.getRefreshKey() != null) entity.setRefreshKey(source.getRefreshKey());
        if (source.getExpiresIn() != null) entity.setExpiresIn(source.getExpiresIn());
        entity.setCreateAccessDateTime(LocalDateTime.now());
        if (source.getExpiresAccessDateTime() != null)
            entity.setExpiresAccessDateTime(source.getExpiresAccessDateTime());
    }

    private Dropbox getDropbox() {
        return repository.findById(1L).orElseThrow(() -> new CustomEntityNotFoundException("Não foi possível localizar nenhuma configuração."));
    }
}
