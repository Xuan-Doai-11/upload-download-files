package com.example.uploaddownloadfile.entity.master;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@Table(name = "attachment")
public class AttachmentEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "created_date", updatable = false , columnDefinition = "DATETIME(0)")
    private Date createdDate;

    @Column(name = "file_name", length = 255 )
    private String fileName;

    @Column(name = "file_type", length = 255 )
    private String fileType;

    @Column(name = "file_size", length = 255 )
    private Long fileSize;

    @Column(name = "data", columnDefinition = "MEDIUMBLOB")
    private byte[] data;


//    @PrePersist
//    @PreUpdate
//    public void prePersist() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDateTime = this.createdDate.format(formatter);
//        this.createdDate = LocalDateTime.parse(formattedDateTime, formatter);
//    }


}
