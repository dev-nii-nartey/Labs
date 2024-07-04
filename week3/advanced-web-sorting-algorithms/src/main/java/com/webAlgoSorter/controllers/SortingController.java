package com.webAlgoSorter.controllers;

import com.webAlgoSorter.model.SortRequest;
import com.webAlgoSorter.service.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sorting")
public class SortingController {

    @Autowired
    private SortingService sortingService;

    @PostMapping("/heap")
    public EntityModel<int[]> heapSort(@RequestBody SortRequest request) {
        int[] sortedArray = sortingService.heapSort(request.getArray());
        EntityModel<int[]> model = EntityModel.of(sortedArray);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class).heapSort(request)).withSelfRel());
        return model;
    }

    @PostMapping("/quick")
    public EntityModel<int[]> quickSort(@RequestBody SortRequest request) {
        int[] sortedArray = sortingService.quickSort(request.getArray(), 0, request.getArray().length - 1);
        EntityModel<int[]> model = EntityModel.of(sortedArray);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class).quickSort(request)).withSelfRel());
        return model;
    }

    @PostMapping("/merge")
    public EntityModel<int[]> mergeSort(@RequestBody SortRequest request) {
        int[] sortedArray = sortingService.mergeSort(request.getArray());
        EntityModel<int[]> model = EntityModel.of(sortedArray);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class).mergeSort(request)).withSelfRel());
        return model;
    }

    @PostMapping("/radix")
    public EntityModel<int[]> radixSort(@RequestBody SortRequest request) {
        int[] sortedArray = sortingService.radixSort(request.getArray());
        EntityModel<int[]> model = EntityModel.of(sortedArray);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class).radixSort(request)).withSelfRel());
        return model;
    }

    @PostMapping("/bucket")
    public EntityModel<int[]> bucketSort(@RequestBody SortRequest request) {
        int[] sortedArray = sortingService.bucketSort(request.getArray());
        EntityModel<int[]> model = EntityModel.of(sortedArray);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SortingController.class).bucketSort(request)).withSelfRel());
        return model;
    }
}
