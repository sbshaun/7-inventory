package model;


import java.time.LocalDate;
import java.util.ArrayList;

// An item with necessary information
public class Item {
    private final String name;
    private final LocalDate createdDate;
    private final LocalDate importantDate; //if time sensitive. e.g. effective date, expiry date...
    private final int degreeOfImportance; // if important, how important it is
    private final ArrayList<String> keywords; // related to the item for easy lookup

    // Constructors below=======================================
    public Item(String name, String importantDate, int degreeOfImportance,
                ArrayList<String> keywords) {
        this.name = name;
        this.createdDate = LocalDate.now(); // date when an item is added, in the format of yyyy-MM-dd
        this.importantDate = LocalDate.parse(importantDate); // in the format of yyyy-MM-dd
        this.degreeOfImportance = degreeOfImportance;
        this.keywords = keywords;
    }
    // Constructors above===========================



    // Getters and setters below =======================
    public String getName() {
        return name;
    }

    public String getImportantDate() {
        return importantDate.toString();
    }

    public int getDegreeOfImportance() {
        return degreeOfImportance;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
