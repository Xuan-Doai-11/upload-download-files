package com.example.uploaddownloadfile.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.uploaddownloadfile.entity.master.AttachmentEntity}
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentEntityDto implements Serializable {
    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}
