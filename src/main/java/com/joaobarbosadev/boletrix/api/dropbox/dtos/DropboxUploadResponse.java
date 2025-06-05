package com.joaobarbosadev.boletrix.api.dropbox.dtos;

public class DropboxUploadResponse {
    private  String dropboxPath;
    private String sharedUrl;

    public DropboxUploadResponse(){}

    public DropboxUploadResponse(String dropboxPath, String sharedUrl) {
        this.dropboxPath = dropboxPath;
        this.sharedUrl = sharedUrl;
    }

    public String getDropboxPath() {
        return dropboxPath;
    }

    public String getSharedUrl() {
        return sharedUrl;
    }
}
