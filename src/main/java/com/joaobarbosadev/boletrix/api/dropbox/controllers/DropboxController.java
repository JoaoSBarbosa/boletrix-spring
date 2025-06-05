package com.joaobarbosadev.boletrix.api.dropbox.controllers;

import com.joaobarbosadev.boletrix.api.dropbox.services.request.DropboxService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dropbox")
public class DropboxController {

    private final DropboxService service;

    public DropboxController(DropboxService service) {
        this.service = service;
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam Long id) {
        try {
            byte[] fileData = service.downloadFile(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileData);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .header("X-Error", e.getMessage())
                    .body(null);
        }
    }


//    @GetMapping("/download")
//    public ResponseEntity<byte[]> downloadFile(@RequestParam Long id) {
//        byte[] fileData = service.downloadFile(id);
//
//        // Extrai o nome do arquivo para Content-Disposition
//        String filename = path.substring(path.lastIndexOf("/") + 1);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(fileData);
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(@RequestParam String path) {
        service.deleteFile(path);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}
