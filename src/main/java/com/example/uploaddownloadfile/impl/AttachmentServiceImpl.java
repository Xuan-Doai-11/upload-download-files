package com.example.uploaddownloadfile.impl;

import com.example.uploaddownloadfile.base.BaseServiceImpl;
import com.example.uploaddownloadfile.entity.master.AttachmentEntity;
import com.example.uploaddownloadfile.repository.AttachmentEntityRepository;
import com.example.uploaddownloadfile.service.AttachmentService;
import com.example.uploaddownloadfile.util.DateHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AttachmentServiceImpl extends BaseServiceImpl implements AttachmentService {

    @Override
    public List<AttachmentEntity> saveAllAttachment(List<MultipartFile> files) throws Exception {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<AttachmentEntity> attachments = new ArrayList<>();

        try {
            em = createEntityManager(1);
            AttachmentEntityRepository attachmentRepository = createRepository(em, AttachmentEntityRepository.class);
            tx = em.getTransaction();
            tx.begin();

            if (!ObjectUtils.isEmpty(files)) {

                for (MultipartFile file1 : files) {


                    String fileName = StringUtils.cleanPath(file1.getOriginalFilename());

                    if (fileName.contains("..")) {
                        throw new Exception("Filename contains invalid path sequence "
                                + fileName);
                    }

                    System.out.println("File bytes :" + file1.getBytes());
                    AttachmentEntity attachment = new AttachmentEntity();
                    attachment.setCreatedDate(DateHelper.truncateMilliseconds(DateHelper.now()));
                    attachment.setFileName(fileName);
                    attachment.setFileSize(file1.getSize());
                    attachment.setFileType(file1.getContentType());
                    attachment.setData(file1.getBytes());
                    attachments.add(attachment);


                }
            }
            attachmentRepository.saveAll(attachments);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw new Exception("Could not save File: " + files.toString());
            }

        } finally {
            closeEntityManager(em);
        }

        return attachments;
    }

    @Override
    public AttachmentEntity getAttachmentById(String fileId) throws Exception {
        EntityManager em = null;
        em = createEntityManager(1);
        AttachmentEntityRepository attachmentRepository = createRepository(em, AttachmentEntityRepository.class);
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }

}

