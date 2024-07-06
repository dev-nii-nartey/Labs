package com.webAlgoSorter.controllers;

import com.webAlgoSorter.services.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SortingController {

    @Autowired
    private SortingService sortingService;

    @GetMapping("/sort")
    public String showSortForm() {
        return "sortForm";
    }

    @PostMapping("/sort")
    public String sort(@RequestParam("algorithm") String algorithm,
                       @RequestParam("numbers") String numbers,
                       Model model) {
        try {
            List<Integer> numberList = parseNumbers(numbers);
            List<Integer> sortedList = new ArrayList<>(numberList); // Make a copy of the original list
            sortedList = sortingService.sort(algorithm, sortedList);
            String timeComplexity = sortingService.getTimeComplexity(algorithm);

            model.addAttribute("originalArray", numberList);
            model.addAttribute("sortedArray", sortedList);
            model.addAttribute("chosenAlgorithm", algorithm);
            model.addAttribute("timeComplexity", timeComplexity);

            Map<String, String> alternateAlgorithms = getAlternateAlgorithms(algorithm);
            model.addAttribute("alternateAlgorithms", alternateAlgorithms);

            return "sortResult";
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Please enter a valid list of integers.");
            return "sortForm";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            return "sortForm";
        }
    }


    private List<Integer> parseNumbers(String numbers) {
        return Arrays.stream(numbers.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private Map<String, String> getAlternateAlgorithms(String currentAlgorithm) {
        Map<String, String> allAlgorithms = new LinkedHashMap<>();
        allAlgorithms.put("heap", "Heap Sort");
        allAlgorithms.put("quick", "Quick Sort");
        allAlgorithms.put("merge", "Merge Sort");
        allAlgorithms.put("radix", "Radix Sort");
        allAlgorithms.put("bucket", "Bucket Sort");

        allAlgorithms.remove(currentAlgorithm); // Remove the current algorithm from alternatives
        return allAlgorithms;
    }
}
