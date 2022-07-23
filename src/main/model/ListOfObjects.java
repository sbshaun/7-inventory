package model;

import java.util.ArrayList;

// A list of Places
public class ListOfObjects extends ArrayList<Item> {
    private ArrayList<Item> listOfObjects;



    // Constructor below ================
    public ListOfObjects() {
        listOfObjects = new ArrayList<>();
    }
    // Constructor above ================


    // Methods below ========================

    // EFFECTS: return all places and items in all places
    public String getAll() {
        return ""; // stub
    }

    // EFFECTS: return all dates for importantDate of all items in all places
    public String getTimeLine() {
        return "";// stub
    }


    // Methods above ========================


    // Getters and setters below ===================
    public ArrayList<Item> getListOfObjects() {
        return listOfObjects;
    }

    public void setListOfObjects(ArrayList<Item> listOfObjects) {
        this.listOfObjects = listOfObjects;
    }
}
