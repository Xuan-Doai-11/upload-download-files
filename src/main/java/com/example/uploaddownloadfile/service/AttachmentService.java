package com.example.uploaddownloadfile.service;

import com.example.uploaddownloadfile.entity.master.AttachmentEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface AttachmentService {
    List<AttachmentEntity> saveAllAttachment(List<MultipartFile> files) throws Exception;

    AttachmentEntity getAttachmentById(String id) throws Exception;
}
