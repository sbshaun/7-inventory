package model;

import java.util.ArrayList;

// A list of Places
public class ListOfObjects {
    private ArrayList<? extends Item> listOfObjects;

    // Constructor below ================
    public ListOfObjects() {
        listOfObjects = new ArrayList<>();
    }
    // Constructor above ================



    // Methods below ========================

    // EFFECTS: return all places and items in all places
    public String getAll() {
        return null; // stub
    }

    // EFFECTS: return all dates for importantDate of all items in all places
    public String getTimeLine() {
        return null;// stub
    }


    // Methods above ========================


    // Getters and setters below ===================
    public ArrayList<? extends Item> getListOfObjects() {
        return listOfObjects;
    }

    public void setListOfObjects(ArrayList<? extends Item> listOfObjects) {
        this.listOfObjects = listOfObjects;
    }
}
