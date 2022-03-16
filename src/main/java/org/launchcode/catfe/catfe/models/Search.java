package org.launchcode.catfe.catfe.models;

import java.util.ArrayList;

public class Search {


    public static ArrayList<Cafe> findByName(String searchTerm, Iterable<Cafe> allCafes) {
        String lower_searchTerm = searchTerm.toLowerCase();

        ArrayList<Cafe> results = new ArrayList<>();

        for (Cafe cafe : allCafes) {
            if (cafe.getCafeName().toLowerCase().contains(lower_searchTerm)) {
                results.add(cafe);
            }
        }
        return results;
    }
}

