package GraduationWorkSalesProject.graduation.com.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/")
    public String tomain(){
        return "index";
    }
}