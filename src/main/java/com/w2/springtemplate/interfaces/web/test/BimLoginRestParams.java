package com.w2.springtemplate.interfaces.web.test;

import lombok.Builder;
import lombok.Data;

/**
 * @author wsfzj 2024/9/18
 * @version 1.0
 * @description TODO
 */
@Data
@Builder
public class BimLoginRestParams {
    private boolean success;
    private String data;
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
