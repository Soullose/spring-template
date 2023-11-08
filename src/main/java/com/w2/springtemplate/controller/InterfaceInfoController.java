package com.w2.springtemplate.controller;
import com.w2.springtemplate.model.InterfaceInfo;
import com.w2.springtemplate.repository.InterfaceInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interface-info")
@Slf4j
public class InterfaceInfoController {

    private final InterfaceInfoRepository repository;

    public  InterfaceInfoController(InterfaceInfoRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/save")
    public ResponseEntity save() {
        InterfaceInfo info = new InterfaceInfo();
        info.setName("测试");
        info.setUrl("https:aaa.cc");
        info.setMethod("xxxxxx");
        info.setDescription("ddddddd");
        repository.save(info);
        return ResponseEntity.ok().build();
    }
}
