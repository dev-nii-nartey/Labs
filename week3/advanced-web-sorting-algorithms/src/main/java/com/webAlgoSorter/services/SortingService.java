package com.webAlgoSorter.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SortingService {

    public List<Integer> sort(String algorithm, List<Integer> numbers) {
        // Sorting logic based on the algorithm
        switch (algorithm) {
            case "heap":
                return heapSort(numbers);
            case "quick":
                return quickSort(numbers);
            case "merge":
                return mergeSort(numbers);
            case "radix":
                return radixSort(numbers);
            case "bucket":
                return bucketSort(numbers);
            default:
                throw new IllegalArgumentException("Invalid algorithm: " + algorithm);
        }
    }


    public String getTimeComplexity(String algorithm) {
        switch (algorithm) {
            case "heap":
                return "Heap Sort: A comparison-based sorting technique based on a binary heap data structure. "
                        + "Time Complexity: O(n log n), Space Complexity: O(1)";
            case "quick":
                return "Quick Sort: An efficient divide-and-conquer sorting algorithm that uses a pivot to partition the array. "
                        + "Time Complexity: Best/Average: O(n log n), Worst: O(n^2), Space Complexity: O(log n)";
            case "merge":
                return "Merge Sort: A stable, divide-and-conquer, comparison-based sorting algorithm that merges two sorted halves. "
                        + "Time Complexity: O(n log n), Space Complexity: O(n)";
            case "radix":
                return "Radix Sort: A non-comparative sorting algorithm that sorts numbers by processing individual digits. "
                        + "Time Complexity: O(nk), Space Complexity: O(n+k)";
            case "bucket":
                return "Bucket Sort: A distribution sort that distributes elements into buckets and then sorts each bucket. "
                        + "Time Complexity: O(n+k), Space Complexity: O(n+k)";
            default:
                throw new IllegalArgumentException("Invalid algorithm: " + algorithm);
        }
    }

    private List<Integer> heapSort(List<Integer> numbers) {
        int n = numbers.size();

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(numbers, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            Collections.swap(numbers, 0, i);

            // call max heapify on the reduced heap
            heapify(numbers, i, 0);
        }
        return numbers;
    }

    private void heapify(List<Integer> numbers, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (left < n && numbers.get(left) > numbers.get(largest)) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && numbers.get(right) > numbers.get(largest)) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            Collections.swap(numbers, i, largest);

            // Recursively heapify the affected subtree
            heapify(numbers, n, largest);
        }
    }

    private List<Integer> quickSort(List<Integer> numbers) {
        quickSortHelper(numbers, 0, numbers.size() - 1);
        return numbers;
    }

    private void quickSortHelper(List<Integer> numbers, int low, int high) {
        if (low < high) {
            int pi = partition(numbers, low, high);

            quickSortHelper(numbers, low, pi - 1); // Before pi
            quickSortHelper(numbers, pi + 1, high); // After pi
        }
    }

    private int partition(List<Integer> numbers, int low, int high) {
        int pivot = numbers.get(high);
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (numbers.get(j) <= pivot) {
                i++;

                // swap numbers[i] and numbers[j]
                Collections.swap(numbers, i, j);
            }
        }

        // swap numbers[i + 1] and numbers[high] (or pivot)
        Collections.swap(numbers, i + 1, high);

        return i + 1;
    }

    private List<Integer> mergeSort(List<Integer> numbers) {
        if (numbers.size() < 2) {
            return numbers;
        }
        int mid = numbers.size() / 2;
        List<Integer> left = new ArrayList<>(numbers.subList(0, mid));
        List<Integer> right = new ArrayList<>(numbers.subList(mid, numbers.size()));

        mergeSort(left);
        mergeSort(right);

        return merge(numbers, left, right);
    }

    private List<Integer> merge(List<Integer> numbers, List<Integer> left, List<Integer> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                numbers.set(k++, left.get(i++));
            } else {
                numbers.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) {
            numbers.set(k++, left.get(i++));
        }
        while (j < right.size()) {
            numbers.set(k++, right.get(j++));
        }
        return numbers;
    }

    private List<Integer> radixSort(List<Integer> numbers) {
        int max = Collections.max(numbers);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(numbers, exp);
        }
        return numbers;
    }

    private void countSort(List<Integer> numbers, int exp) {
        int n = numbers.size();
        int[] output = new int[n];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (int i = 0; i < n; i++) {
            count[(numbers.get(i) / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(numbers.get(i) / exp) % 10] - 1] = numbers.get(i);
            count[(numbers.get(i) / exp) % 10]--;
        }

        for (int i = 0; i < n; i++) {
            numbers.set(i, output[i]);
        }
    }

    private List<Integer> bucketSort(List<Integer> numbers) {
        int n = numbers.size();
        if (n <= 0) {
            return numbers;
        }

        int max = Collections.max(numbers);
        int min = Collections.min(numbers);

        int bucketCount = (max - min) / n + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int num : numbers) {
            int bucketIndex = (num - min) / n;
            buckets.get(bucketIndex).add(num);
        }

        numbers.clear();
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            numbers.addAll(bucket);
        }
        return numbers;
    }
}
