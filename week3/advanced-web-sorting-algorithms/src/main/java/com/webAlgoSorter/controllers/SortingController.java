package com.webAlgoSorter.controllers;

import com.webAlgoSorter.models.ArrayEntry;
import com.webAlgoSorter.services.ArrayEntryService;
import com.webAlgoSorter.services.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SortingController {

    @Autowired
    private SortingService sortingService;

    @Autowired
    private ArrayEntryService arrayEntryService;

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

            // Save the array entry
            ArrayEntry arrayEntry = new ArrayEntry(numberList);
            arrayEntryService.addArrayEntry(arrayEntry);

            return "sortResult";
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Please enter a valid list of integers.");
            return "sortForm";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            return "sortForm";
        }
    }

    @GetMapping("/arrays")
    public String showArrays(Model model) {
        List<ArrayEntry> arrayEntries = arrayEntryService.getAllArrayEntries();
        model.addAttribute("arrayEntries", arrayEntries);
        return "arrayList";
    }

    @GetMapping("/arrays/edit/{id}")
    public String showEditArrayForm(@PathVariable("id") long id, Model model) {
        ArrayEntry arrayEntry = arrayEntryService.getArrayEntryById(id);
        if (arrayEntry != null) {
            model.addAttribute("arrayEntry", arrayEntry);
            return "editArrayForm";
        } else {
            return "redirect:/arrays";
        }
    }

    @PostMapping("/arrays/edit/{id}")
    public String editArray(@PathVariable("id") long id,
                            @RequestParam("numbers") String numbers,
                            Model model) {
        try {
            List<Integer> numberList = parseNumbers(numbers);
            arrayEntryService.updateArrayEntry(id, numberList);
            return "redirect:/arrays";
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Please enter a valid list of integers.");
            return "editArrayForm";
        }
    }

    @PostMapping("/arrays/delete/{id}")
    public String deleteArray(@PathVariable("id") long id) {
        arrayEntryService.deleteArrayEntry(id);
        return "redirect:/arrays";
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
