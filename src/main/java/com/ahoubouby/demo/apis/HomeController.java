package com.ahoubouby.demo.apis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {

    @GetMapping("/index")
    String index () {
        return "Index holo";
    }
}
