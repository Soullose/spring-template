package com.w2.springtemplate.controller.test;

import lombok.Builder;
import lombok.Data;

/**
 * @author wsfzj 2024/9/18
 * @version 1.0
 * @description TODO
 */
@Data
@Builder
public class BimLoginParams {
    private String systemCode;
    private String integrationKey;
}
