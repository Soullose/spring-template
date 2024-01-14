package com.w2.springtemplate.framework.netty.study.common.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2.springtemplate.framework.netty.study.common.dispatcher.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class W2MsgBody {

    private final ObjectMapper om = new ObjectMapper();

    /**
     * 类型
     */
    private String type;
    /**
     * 消息，JSON 格式
     */
    private String message;

    public W2MsgBody(String type, Message message) {
        this.type = type;
        try {
            this.message = om.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
