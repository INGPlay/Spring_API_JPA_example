package api.jpa.practice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {


    @GetMapping
    public String api() { return "redirect:/swagger-ui/index.html"; }
}
