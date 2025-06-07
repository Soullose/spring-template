package com.w2.springtemplate.interfaces.web.test;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BimLogoutRestParams {
    private boolean success;
    private boolean data;
    private String errorCode;
    private String errorName;
    private String errorMessage;
    private ErrorException errorException;

    @Data
    @Builder
    private static class ErrorException{
        private String name;
        private String message;
        private String trace;
    }
}
