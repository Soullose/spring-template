package com.w2.springtemplate.framework.handlers.login;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class LoggedInUser {
    private String id;
    private String username;
    private String name;
    private String host;


    public static LoggedInUser fromMap(Map<String, Object> map) {
        LoggedInUser self = new LoggedInUser();
        self.id = (String) map.get("id");
        self.username = (String) map.get("username");
        self.name = (String) map.get("name");
        self.host = (String) map.get("host");

        return self;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", this.id);
        map.put("username", this.username);
        map.put("name", this.name);
        map.put("host", this.host);
        return map;
    }
}
