package com.example.uploaddownloadfile.controller;

import com.example.uploaddownloadfile.dto.AttachmentEntityDto;
import com.example.uploaddownloadfile.dto.ResponseData;
import com.example.uploaddownloadfile.entity.master.AttachmentEntity;
import com.example.uploaddownloadfile.service.AttachmentService;
import com.example.uploaddownloadfile.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController()
public class AttachmentController extends ApiController{

    @Autowired
    private AttachmentService attachmentService;

    private static final String MEDIA_TYPE_STREAM = "application/octet-stream";

    private static final String CONTENT_DISPOSITION = "attachment;filename=";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("files") List<MultipartFile> files) throws Exception {
       ResponseEntity<String> response;

        try {
            List<AttachmentEntityDto> attachmentDTOs = new ArrayList<>();

            // Lưu tệp và gán kết quả vào danh sách
            List<AttachmentEntity> attachments = attachmentService.saveAllAttachment(files);

            if (!ObjectUtils.isEmpty(attachments)) {
                for (AttachmentEntity attachment : attachments) {
                    AttachmentEntityDto attachmentDTO = new AttachmentEntityDto();

                    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/download/")
                            .path(attachment.getId())
                            .toUriString();

                    attachmentDTO.setFileName(attachment.getFileName());
                    attachmentDTO.setDownloadURL(downloadURL);
                    attachmentDTO.setFileType(attachment.getFileType());
                    attachmentDTO.setFileSize(attachment.getFileSize());
                    attachmentDTOs.add(attachmentDTO);
                }
            }
            ResponseData responseData = new ResponseData();
            responseData.setAttachmentDTOS(attachmentDTOs);
            return ok(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        AttachmentEntity attachment = null;
        attachment = attachmentService.getAttachmentById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        CONTENT_DISPOSITION + URLEncoder.encode(attachment.getFileName(), StandardCharsets.UTF_8).replace("+", "%20")
                )
                .body(new ByteArrayResource(attachment.getData()));

    }


    @GetMapping("/download/file-to-zip/{fileId}")
    public ResponseEntity<?> downloadFileToZip(@PathVariable String fileId) throws Exception {
        Map<String, byte[]> files = new HashMap<>();
        AttachmentEntity attachment = null;
        attachment = attachmentService.getAttachmentById(fileId);

        if (!ObjectUtils.isEmpty(attachment)) {
            byte[] bytes = attachment.getData();
            files.put(attachment.getFileName(), bytes);
        }

        byte[] res = FileUtil.fileToZip(files);


        return ResponseEntity.ok()
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileSize() + ".zip\"")
                .header(HttpHeaders.CONTENT_DISPOSITION, CONTENT_DISPOSITION + attachment.getFileSize() + ".zip\"")
                .contentType(MediaType.parseMediaType(MEDIA_TYPE_STREAM))
                .body(res);




    }
}
