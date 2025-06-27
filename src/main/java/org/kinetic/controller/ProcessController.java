package org.kinetic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/process")
@Slf4j
public class ProcessController {

    @PostMapping("start")
    public ResponseEntity<String> processFiles() {
        log.info("Received request to start document processing.");
        return ResponseEntity.ok("process started");
    }

}
