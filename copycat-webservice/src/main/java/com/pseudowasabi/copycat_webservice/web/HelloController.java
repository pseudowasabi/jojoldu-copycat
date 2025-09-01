package com.pseudowasabi.copycat_webservice.web;

import com.pseudowasabi.copycat_webservice.web.dto.HelloResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello.";
    }

    @GetMapping("/hello-dto")
    public ResponseEntity<?> helloDto(@RequestParam("name") String name,
                                      @RequestParam("age") int amount) {

        HelloResponseDto helloResponseDto = new HelloResponseDto(name, amount);
        return ResponseEntity.status(HttpStatus.OK).body(helloResponseDto);
    }
}
