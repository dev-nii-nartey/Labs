package com.webAlgoSorter.services;

import com.webAlgoSorter.models.ArrayEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArrayEntryService {
    private final List<ArrayEntry> arrayEntries = new ArrayList<>();

    public List<ArrayEntry> getAllArrayEntries() {
        return arrayEntries;
    }

    public ArrayEntry getArrayEntryById(long id) {
        return arrayEntries.stream()
                .filter(entry -> entry.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addArrayEntry(ArrayEntry arrayEntry) {
        arrayEntries.add(arrayEntry);
    }

    public void updateArrayEntry(long id, List<Integer> newNumbers) {
        ArrayEntry entry = getArrayEntryById(id);
        if (entry != null) {
            entry.setNumbers(newNumbers);
        }
    }

    public void deleteArrayEntry(long id) {
        arrayEntries.removeIf(entry -> entry.getId() == id);
    }
}
