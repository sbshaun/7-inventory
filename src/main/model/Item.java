package model;


import java.time.LocalDate;
import java.util.ArrayList;

// An item with necessary information
public class Item {
    private String name;
    private LocalDate addedDate; // date when an item is added
    private LocalDate importantDate; //if time sensitive. e.g. effective date, expiry date...
    private int degreeOfImportance; // if important, how important it is
    private ArrayList<String> keywords; // related to the item for easy lookup

    // Constructors below=======================================
    public Item(String name) {
        this.name = name;
        this.addedDate = LocalDate.now(); // in the format of yyyy-MM-dd
    }

    public Item(String name, String importantDate) {
        this.name = name;
        this.addedDate = LocalDate.now(); // in the format of yyyy-MM-dd
        this.importantDate = LocalDate.parse(importantDate); // in the format of yyyy-MM-dd
    }

    public Item(String name, String importantDate, int degreeOfImportance) {
        this.name = name;
        this.addedDate = LocalDate.now(); // in the format of yyyy-MM-dd
        this.importantDate = LocalDate.parse(importantDate); // in the format of yyyy-MM-dd
        this.degreeOfImportance = degreeOfImportance;
    }

    public Item(String name, String importantDate, int degreeOfImportance,
                ArrayList<String> keywords) {
        this.name = name;
        this.addedDate = LocalDate.now(); // in the format of yyyy-MM-dd
        this.importantDate = LocalDate.parse(importantDate); // in the format of yyyy-MM-dd
        this.degreeOfImportance = degreeOfImportance;
        this.keywords = keywords;
    }
    // Constructors above===========================



    // Getters and setters below =======================
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddedDate() {
        return addedDate.toString();
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = LocalDate.parse(addedDate);
    }

    public String getImportantDate() {
        return importantDate.toString();
    }

    public void setImportantDate(String importantDate) {
        this.importantDate = LocalDate.parse(importantDate);
    }

    public int getDegreeOfImportance() {
        return degreeOfImportance;
    }

    public void setDegreeOfImportance(int degreeOfImportance) {
        this.degreeOfImportance = degreeOfImportance;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }
}
