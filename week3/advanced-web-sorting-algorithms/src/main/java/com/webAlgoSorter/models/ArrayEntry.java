package com.webAlgoSorter.models;

import java.util.List;

public class ArrayEntry {
    private static long idCounter = 0;

    private long id;
    private List<Integer> numbers;

    public ArrayEntry(List<Integer> numbers) {
        this.id = idCounter++;
        this.numbers = numbers;
    }

    public long getId() {
        return id;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }
}
