package com.example.uploaddownloadfile.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public final class DateHelper {

    public static Date now() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date truncateMilliseconds(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Chuyển đổi ngày thành chuỗi với định dạng mong muốn
        String formattedDate = formatter.format(date);
        try {
            // Phân tích chuỗi ngày giờ không có phần mili giây
            return formatter.parse(formattedDate);
        } catch (ParseException e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
            return date;
        }
    }

}
