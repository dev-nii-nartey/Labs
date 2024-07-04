package com.webAlgoSorter.controller;

import com.webAlgoSorter.service.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private SortingService sortingService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/sort")
    public String sortForm() {
        return "sortForm";
    }

    @PostMapping("/sort")
    public String sort(@RequestParam("algorithm") String algorithm, @RequestParam("numbers") String numbers, Model model) {
        // Parse the numbers
        String[] numberStrings = numbers.split(",");
        int[] array = new int[numberStrings.length];
        for (int i = 0; i < numberStrings.length; i++) {
            array[i] = Integer.parseInt(numberStrings[i].trim());
        }

        // Perform sorting
        int[] sortedArray = new int[0];
        switch (algorithm) {
            case "heap":
                sortedArray = sortingService.heapSort(array);
                break;
            case "quick":
                sortedArray = sortingService.quickSort(array, 0, array.length - 1);
                break;
            case "merge":
                sortedArray = sortingService.mergeSort(array);
                break;
            case "radix":
                sortedArray = sortingService.radixSort(array);
                break;
            case "bucket":
                sortedArray = sortingService.bucketSort(array);
                break;
        }

        model.addAttribute("sortedArray", sortedArray);
        return "sortResult";
    }
}
