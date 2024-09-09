package com.example.uploaddownloadfile.repository;

import com.example.uploaddownloadfile.entity.master.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentEntityRepository extends JpaRepository<AttachmentEntity, String> {
}