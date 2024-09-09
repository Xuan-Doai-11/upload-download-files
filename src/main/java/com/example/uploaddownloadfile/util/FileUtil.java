package com.example.uploaddownloadfile.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static byte[] fileToZip(Map<String, byte[]> files) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        files.forEach((name, bytes) -> {
            try {
             InputStream is = new ByteArrayInputStream(bytes);
             ZipEntry zipEntry = new ZipEntry(name);
             zipEntry.setSize(bytes.length);
             zipOutputStream.putNextEntry(zipEntry);
                IOUtils.copy(is, zipOutputStream);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        });

        try {
            zipOutputStream.closeEntry();
            zipOutputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static File createFile(String fileName, Workbook workbook) throws Exception {
        workbook = new XSSFWorkbook();

        Path filePath = Paths.get("src/main/resources/templates", fileName);

        Files.createDirectories(filePath.getParent()); // Tạo thư mục nếu chưa tồn tại

        try (OutputStream out = Files.newOutputStream(filePath)) {
            workbook.write(out);
        }

        return filePath.toFile();
    }

    public static Workbook getWorkbookStream(MultipartFile importFile){
        InputStream inputStream = null;
        try {
            inputStream = importFile.getInputStream();

            Workbook workbook = WorkbookFactory.create(inputStream);

            return workbook;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
