package sbd.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("data", "Hello!!");
        return "hello";
    }

    @GetMapping("/hello-mvc")
        public String helloMvc(@RequestParam("sion")/*sion은 param 곧 통로(키네임)*/ String name, Model model){
            model.addAttribute("name", name);//name=키값
            return "hello-template";
        }
}
