package com.joaobarbosadev.boletrix.api.dropbox.services.request;

import com.joaobarbosadev.boletrix.api.dropbox.dtos.DropboxUploadResponse;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

public interface DropboxService {
    void deleteFile(String dropboxPath);
    byte[] downloadFile(Long id);
    DropboxUploadResponse uploadFile(MultipartFile file, InstallmentFile installment );
}
