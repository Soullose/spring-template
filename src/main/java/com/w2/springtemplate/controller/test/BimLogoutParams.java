package com.w2.springtemplate.controller.test;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BimLogoutParams {
    private String token;
}
