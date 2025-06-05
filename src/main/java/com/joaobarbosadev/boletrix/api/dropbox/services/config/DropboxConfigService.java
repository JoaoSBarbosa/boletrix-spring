package com.joaobarbosadev.boletrix.api.dropbox.services.config;

import com.joaobarbosadev.boletrix.core.models.domain.Dropbox;

public interface DropboxConfigService {

    void checkoutValidate();
    String getRefreshToken();
    void refreshAccessToken();
    Dropbox checkoutValidateAndReturnDropbox();

}
