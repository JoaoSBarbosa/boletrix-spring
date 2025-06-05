package com.joaobarbosadev.boletrix.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://api.dropbox.com/", name = "dropbox")
public interface DropboxApiV1 {

    @PostMapping("/oauth2/token")
    String getAccessToken(
            @RequestParam String code,
            @RequestParam String grant_type,
            @RequestParam String client_id,
            @RequestParam String client_secret,
            @RequestParam String redirect_uri
    );

    @PostMapping( "/oauth2/token" )
    String refreshAccessToken(
            @RequestParam String refresh_token,
            @RequestParam String grant_type,
            @RequestParam String client_id,
            @RequestParam String client_secret
    );
}
