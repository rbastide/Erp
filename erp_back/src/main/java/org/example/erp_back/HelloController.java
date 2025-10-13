package org.example.erp_back;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "<h1>Bonjour Spring Boot !</h1>";
    }

    @GetMapping("/non")
    public String sayNo(){
        return "no";
    }
}
