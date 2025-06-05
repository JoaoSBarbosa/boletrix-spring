//package com.joaobarbosadev.boletrix.api.dropbox.services.request;
//
//import com.dropbox.core.DbxRequestConfig;
//import com.dropbox.core.v2.DbxClientV2;
//import com.dropbox.core.v2.files.FileMetadata;
//import com.dropbox.core.v2.sharing.SharedLinkMetadata;
//import com.joaobarbosadev.boletrix.api.dropbox.dtos.DropboxUploadResponse;
//import com.joaobarbosadev.boletrix.api.dropbox.services.config.DropboxConfigService;
//import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentFile;
//import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
//import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEntityNotFoundException;
//import com.joaobarbosadev.boletrix.core.models.domain.Dropbox;
//import com.joaobarbosadev.boletrix.core.models.domain.Installment;
//import com.joaobarbosadev.boletrix.core.repository.InstallmentRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.math.RoundingMode;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class DropboxServiceImpl implements DropboxService {
//    private final DropboxConfigService dropboxConfigService;
//    private final InstallmentRepository installmentRepository;
//    private final DbxRequestConfig config = DbxRequestConfig.newBuilder("bolextrix").build();
//    private final String rootFolderPath = "/comprovantes";
//
//    public DropboxServiceImpl(DropboxConfigService dropboxConfigService, InstallmentRepository installmentRepository) {
//        this.dropboxConfigService = dropboxConfigService;
//        this.installmentRepository = installmentRepository;
//    }
//
//    @Override
//    public byte[] downloadFile(Long id) {
//        if (id == null) {
//            throw new CustomEmptyFieldException("O ID da parcela está ausente. Não é possível prosseguir com o download do comprovante.");
//        }
//
//        // Recupera o registro da parcela
//        Installment installment = installmentRepository.findById(id).orElseThrow(()-> new CustomEntityNotFoundException("Não foi localizado parcela com o id informado: "+ id)); // supondo que esse método exista
//
//        String dropboxPath = installment.getReceiptPath();
//
//        if (dropboxPath == null || dropboxPath.isBlank()) {
//            throw new RuntimeException("O caminho do comprovante está vazio ou não foi registrado para a parcela de ID " + id + ".");
//        }
//
//        try (var outputStream = new java.io.ByteArrayOutputStream()) {
//            Dropbox dropbox = dropboxConfigService.checkoutValidateAndReturnDropbox();
//            DbxClientV2 client = new DbxClientV2(config, dropbox.getAccessKey());
//
//            client.files().downloadBuilder(dropboxPath).download(outputStream);
//            return outputStream.toByteArray();
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao baixar o comprovante no Dropbox para a parcela de ID " + id + ".", e);
//        }
//    }
//
/// /
/// /    @Override
/// /    public byte[] downloadFile(Long id) {
/// /        if ( id == null ) {
/// /            throw new CustomEmptyFieldException("ID da parcela esta ausente impossibilitando proseguir com o processo de downlaod");
/// /        }
/// /        Dropbox dropbox = dropboxConfigService.checkoutValidateAndReturnDropbox();
/// /        DbxClientV2 client = new DbxClientV2(config, dropbox.getAccessKey());
/// /
/// /        String dropboxPath = getRootFolderPathByInstallmentId(id);
/// /        try (var outputStream = new java.io.ByteArrayOutputStream()) {
/// /            client.files().downloadBuilder(dropboxPath).download(outputStream);
/// /            return outputStream.toByteArray();
/// /        } catch (Exception e) {
/// /            throw new RuntimeException("Erro ao baixar arquivo do Dropbox", e);
/// /        }
/// /    }
//
//    private String getRootFolderPathByInstallmentId(Long id) {
//        return installmentRepository.getReceiptById(id).orElseThrow(()-> new CustomEntityNotFoundException("Não foi localizado informação de diretorio de comprovante para o registro de parcela de id Nº: " + id));
//    }
//    @Override
//    public void deleteFile(String dropboxPath) {
//        System.out.println("PATH: " + dropboxPath);
//        Dropbox dropbox = dropboxConfigService.checkoutValidateAndReturnDropbox();
//        DbxClientV2 client = new DbxClientV2(config, dropbox.getAccessKey());
//
//        try {
//            client.files().deleteV2(dropboxPath);
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao excluir arquivo do Dropbox", e);
//        }
//    }
//
//    @Override
//    public DropboxUploadResponse uploadFile(MultipartFile file, InstallmentFile installment) {
//        Dropbox dropbox = dropboxConfigService.checkoutValidateAndReturnDropbox();
//        DbxClientV2 client = new DbxClientV2(config, dropbox.getAccessKey());
//
//        try {
//            String extension = extractExtension(file);
//            File tempFile = createTempFile(file, extension);
//            String dropboxPath = buildDropboxPath(installment, extension);
//
//            uploadToDropbox(client, tempFile, dropboxPath);
//            String sharedLink = createSharedLink(client, dropboxPath);
//
//            return new DropboxUploadResponse(dropboxPath, sharedLink);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao fazer upload para o Dropbox", e);
//        }
//    }
//
//    private String extractExtension(MultipartFile file) {
//        String originalFilename = file.getOriginalFilename();
//        if (originalFilename != null && originalFilename.contains(".")) {
//            return originalFilename.substring(originalFilename.lastIndexOf("."));
//        }
//        throw new IllegalArgumentException("Arquivo sem extensão válida.");
//    }
//
//    private File createTempFile(MultipartFile file, String extension) throws Exception {
//        File tempFile = File.createTempFile("comprovante_", extension);
//        file.transferTo(tempFile);
//        return tempFile;
//    }
//
//    private String buildDropboxPath(InstallmentFile installment, String extension) {
//        String filename = String.format("parcela_%d_%s_%s_%s%s",
//                installment.getId(),
//                installment.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//                installment.getPaymentTime(),
//                installment.getAmount().setScale(2, RoundingMode.HALF_UP).toString().replace(".", ","),
//                extension
//        ).replace(" ", "_");
//
//        return rootFolderPath + "/" + filename;
//    }
//
//    private void uploadToDropbox(DbxClientV2 client, File tempFile, String path) throws Exception {
//        try (FileInputStream in = new FileInputStream(tempFile)) {
//            client.files().uploadBuilder(path).uploadAndFinish(in);
//        }
//    }
//
//    private String createSharedLink(DbxClientV2 client, String path) throws Exception {
//        return client.sharing().createSharedLinkWithSettings(path).getUrl();
//    }
//
//
//}


package com.joaobarbosadev.boletrix.api.dropbox.services.request;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.InvalidAccessTokenException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.joaobarbosadev.boletrix.api.dropbox.dtos.DropboxUploadResponse;
import com.joaobarbosadev.boletrix.api.dropbox.services.config.DropboxConfigService;
import com.joaobarbosadev.boletrix.api.installment.dtos.InstallmentFile;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEmptyFieldException;
import com.joaobarbosadev.boletrix.core.exception.customizations.CustomEntityNotFoundException;
import com.joaobarbosadev.boletrix.core.models.domain.Dropbox;
import com.joaobarbosadev.boletrix.core.models.domain.Installment;
import com.joaobarbosadev.boletrix.core.repository.InstallmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.time.format.DateTimeFormatter;

@Service
public class DropboxServiceImpl implements DropboxService {

    private final DropboxConfigService dropboxConfigService;
    private final InstallmentRepository installmentRepository;
    private final DbxRequestConfig config = DbxRequestConfig.newBuilder("boletrix").build();

    // Correção: não incluir "/boletrix" no caminho pois já vem de /Apps/boletrix
    private final String rootFolderPath = "/comprovantes";

    public DropboxServiceImpl(DropboxConfigService dropboxConfigService, InstallmentRepository installmentRepository) {
        this.dropboxConfigService = dropboxConfigService;
        this.installmentRepository = installmentRepository;
    }

    @Override
    public byte[] downloadFile(Long id) {
        if (id == null) {
            throw new CustomEmptyFieldException("O ID da parcela está ausente. Não é possível prosseguir com o download do comprovante.");
        }

        Installment installment = installmentRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException("Não foi localizado parcela com o id informado: " + id));

        String dropboxPath = installment.getReceiptPath();

        if (dropboxPath == null || dropboxPath.isBlank()) {
            throw new RuntimeException("O caminho do comprovante está vazio ou não foi registrado para a parcela de ID " + id + ".");
        }

        try (var outputStream = new java.io.ByteArrayOutputStream()) {
            Dropbox dropbox = dropboxConfigService.checkoutValidateAndReturnDropbox();
            DbxClientV2 client = new DbxClientV2(config, dropbox.getAccessKey());
            client.files().downloadBuilder(dropboxPath).download(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao baixar o comprovante no Dropbox para a parcela de ID " + id + ".", e);
        }
    }

    //    @Override
//    public void deleteFile(String dropboxPath) {
//        System.out.println("----------------------EM DELETEFILE- TENTANDO EXCLUIR O PATH: " + dropboxPath);
//        Dropbox dropbox = dropboxConfigService.checkoutValidateAndReturnDropbox();
//        DbxClientV2 client = new DbxClientV2(config, dropbox.getAccessKey());
//
//        try {
//            client.files().deleteV2(dropboxPath);
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao excluir arquivo do Dropbox", e);
//        }
//    }
    @Override
    public void deleteFile(String dropboxPath) {
        System.out.println("Tentando excluir o arquivo do Dropbox: " + dropboxPath);
        Dropbox dropbox = dropboxConfigService.checkoutValidateAndReturnDropbox();
        DbxClientV2 client = new DbxClientV2(config, dropbox.getAccessKey());

        try {
            client.files().deleteV2(dropboxPath);
            System.out.println("Arquivo excluído com sucesso: " + dropboxPath);
        } catch (DeleteErrorException e) {
            // Se for erro do tipo "path_lookup" e a causa for "not_found", o arquivo já não existe
            if (e.errorValue.isPathLookup() &&
                    e.errorValue.getPathLookupValue().isNotFound()) {
                System.out.println("Arquivo já não existe no Dropbox: " + dropboxPath + " — ignorando.");
                return;
            }

            System.err.println("Erro ao excluir arquivo do Dropbox: " + e.getMessage());
            throw new RuntimeException("Erro específico ao excluir arquivo do Dropbox", e);
        } catch (InvalidAccessTokenException e) {
            System.err.println("Token inválido ao tentar excluir arquivo do Dropbox");
            throw new RuntimeException("Token inválido do Dropbox", e);
        } catch (Exception e) {
            System.err.println("Erro genérico ao excluir arquivo do Dropbox: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro inesperado ao excluir arquivo do Dropbox", e);
        }
    }


    @Override
    public DropboxUploadResponse uploadFile(MultipartFile file, InstallmentFile installmentFile) {
        Dropbox dropbox = dropboxConfigService.checkoutValidateAndReturnDropbox();
        DbxClientV2 client = new DbxClientV2(config, dropbox.getAccessKey());

        try {
            String extension = extractExtension(file);
            File tempFile = createTempFile(file, extension);
            String dropboxPath = buildDropboxPath(installmentFile, extension);

            uploadToDropbox(client, tempFile, dropboxPath);
            String sharedLink = createSharedLink(client, dropboxPath);

            return new DropboxUploadResponse(dropboxPath, sharedLink);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload para o Dropbox", e);
        }
    }

    private String extractExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        throw new IllegalArgumentException("Arquivo sem extensão válida.");
    }

    private File createTempFile(MultipartFile file, String extension) throws Exception {
        File tempFile = File.createTempFile("comprovante_", extension);
        file.transferTo(tempFile);
        return tempFile;
    }

//    private String buildDropboxPath(InstallmentFile installmentFile, String extension) {
//        String formattedDate = installmentFile.getPaymentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        String formattedTime = installmentFile.getPaymentTime().toString().replace(":", "-"); // ":" não é permitido em alguns sistemas
//        String formattedAmount = installmentFile.getAmount()
//                .setScale(2, RoundingMode.HALF_UP)
//                .toString()
//                .replace(".", ","); // para pt-BR
//
//        String filename = String.format("parcela_%d_%s_%s_%s%s",
//                installmentFile.getId(),
//                formattedDate,
//                formattedTime,
//                formattedAmount,
//                extension
//        ).replace(" ", "_");
//
//        return rootFolderPath + "/" + filename;
//    }

    private String buildDropboxPath(InstallmentFile installmentFile, String extension) {
        String formattedDate = installmentFile.getPaymentDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // pt-BR
        String formattedTime = installmentFile.getPaymentTime().toString().replace(":", "-");

        String formattedAmount = installmentFile.getAmount()
                .setScale(2, RoundingMode.HALF_UP)
                .toString()
                .replace(",", "") // garante que não vem do banco com vírgula
                .replace(".", "-"); // substitui ponto decimal por hífen

        String filename = String.format("parcela_%d_%s_%s_%s%s",
                installmentFile.getInstallmentNumber(),
                formattedDate,
                formattedTime,
                formattedAmount,
                extension
        ).replace(" ", "_");

        // Normaliza o nome do arquivo: remove acentos e deixa tudo minúsculo
        filename = Normalizer.normalize(filename, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();

        return rootFolderPath + "/" + filename;
    }

    private void uploadToDropbox(DbxClientV2 client, File tempFile, String path) throws Exception {
        try (FileInputStream in = new FileInputStream(tempFile)) {
            client.files().uploadBuilder(path).uploadAndFinish(in);
        }
    }

    private String createSharedLink(DbxClientV2 client, String path) throws Exception {
        SharedLinkMetadata metadata = client.sharing().createSharedLinkWithSettings(path);
        return metadata.getUrl();
    }
}
