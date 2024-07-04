package com.webAlgoSorter.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/testing")
    public static String test() {
        return "Amplify";
    }
}
