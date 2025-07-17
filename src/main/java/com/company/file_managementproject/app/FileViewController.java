package com.company.file_managementproject.app;
import com.company.file_managementproject.entity.FileEntity;
import io.jmix.core.DataManager;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileViewController {
    private final DataManager dataManager;

    public FileViewController(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable UUID id) {
        FileEntity fileEntity = dataManager.load(FileEntity.class).id(id).one();

        if (fileEntity.getFile_data() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(detectMimeType(fileEntity.getFile_name()));
            headers.setContentDisposition(ContentDisposition.inline().filename(fileEntity.getFile_name()).build());

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(fileEntity.getFile_data());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private MediaType detectMimeType(String filename) {
        if (filename.endsWith(".pdf")) return MediaType.APPLICATION_PDF;
        if (filename.endsWith(".png")) return MediaType.IMAGE_PNG;
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return MediaType.IMAGE_JPEG;
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}