package sbd.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name")String name){
        return "hello " + name;//?name=sion => hello sion
    }

    @GetMapping("hello-api")
    @ResponseBody//템블릿은 맞는 템플릿을 찾아 보내지만 responseBady는 HttpMassageConverter중 StringConverter작동하여 덤짐
                 //객체가 넘어오면 JsonConverter로 작동하여
    public Hello helloApi(@RequestParam("key") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }


}
