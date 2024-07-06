package com.webAlgoSorter.interfaces;

import java.util.List;

public interface SortingService {
    List<Integer> sort(String algorithm, List<Integer> numbers);
    String getTimeComplexity(String algorithm);
    List<String> getBetterAlgorithms(String algorithm);
}
