package com.webAlgoSorter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SortController {
@RequestMapping("/sorter")
    public static String sorter(){
        return "sortForm";
    }

}
