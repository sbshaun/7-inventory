package model;

import java.util.ArrayList;

// a place to keep items. A place can also be an item and can be kept at a place
// e.g. a bag can be kept at a bigger bag
public class Place extends Item {
    private ListOfObjects keptItems; // items kept in this place



    // Constructors below ========================
    public Place(String name) {
        super(name);
        keptItems = new ListOfObjects();
    }

    public Place(String name, String importantDate) {
        super(name, importantDate);
        keptItems = new ListOfObjects();
    }

    public Place(String name, String importantDate, int degreeOfImportance) {
        super(name, importantDate, degreeOfImportance);
        keptItems = new ListOfObjects();
    }

    public Place(String name, String importantDate, int degreeOfImportance,
                 ArrayList<String> keywords) {
        super(name, importantDate, degreeOfImportance, keywords);
        keptItems = new ListOfObjects();
    }
    // Constructors above ========================



    // Methods below =========================

    // MODIFIES: this
    // EFFECTS: add an item to keptItems
    public void add(Item item) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: remove an item from keptItems
    public void remove(Item item) {
        // stub
    }

    // EFFECTS: return index if the item is in keptItems
    public int find(Item item) {
        return 0; // stub
    }

    // EFFECT: return index of the item if any keyword is match by an item , -1 if not found
    public int tryFind(String keywords) {
        return 0; // stub
    }

    // EFFECTS: return true if an item matches any of the keywords
    private boolean maybe(Item item, ArrayList<String> keywords) {
        return false; // stub
    }

    // EFFECTS: return all items kept in the place
    public String getAll() {
        return ""; // stub
    }

    // EFFECTS: return all dates for importantDate of all items in the place
    public String getTimeline() {
        return ""; // stub
    }

    // Methods above =========================




    // Getters and setters below ========================
    public ListOfObjects getKeptItems() {
        return keptItems;
    }

    public void setKeptItems(ListOfObjects keptItems) {
        this.keptItems = keptItems;
    }
}

