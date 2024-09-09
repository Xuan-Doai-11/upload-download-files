package com.example.uploaddownloadfile.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiMessage implements Serializable {

    private static final long serialVersionUID = 92374712921381283L;

    private String code;

    private String title;

    private String message;

    private String data;

    private boolean success;

    @Override
    public String toString() {
        return "ApiMessage{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", success=" + success +
                '}';
    }
}
