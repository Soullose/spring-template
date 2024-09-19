package com.w2.springtemplate.controller.test;

import lombok.Builder;
import lombok.Data;

/**
 * @author wsfzj 2024/9/19
 * @version 1.0
 * @description TODO
 */
@Data
@Builder
public class BimCountRestParams {
    private boolean success;
    private int data;
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
