package sk.umb.fpv.dain142demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.umb.fpv.dain142demo.controller.dto.BasicDto;

@RestController
@RequestMapping("/hello-world")
public class BasicController {

    @GetMapping
    public BasicDto get(){
        BasicDto basicDto = new BasicDto();
        basicDto.setMessage("Hello World!");
        return basicDto;
    }

}
