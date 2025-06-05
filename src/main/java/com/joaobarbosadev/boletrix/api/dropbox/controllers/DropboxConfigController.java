package com.joaobarbosadev.boletrix.api.dropbox.controllers;

import com.joaobarbosadev.boletrix.api.dropbox.services.config.DropboxConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dropbox_configs")
public class DropboxConfigController {


    private final DropboxConfigService dropboxConfigService;

    public DropboxConfigController(DropboxConfigService dropboxConfigService) {
        this.dropboxConfigService = dropboxConfigService;
    }

    @GetMapping("/get_refresh_code")
    public String getRefreshToken() throws Exception {
        return dropboxConfigService.getRefreshToken();
    }
}
