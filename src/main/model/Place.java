package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


// a place to keep items. A place can also be an item and can be kept at a place
// e.g. a bag can be kept at a bigger bag
public class Place extends Item {
    private final ListOfObjects keptItems; // items kept in this place



    // Constructors below ========================
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
        keptItems.add(item);
    }

    // MODIFIES: this
    // EFFECTS: remove an item from keptItems
    public void remove(Item item) {
        keptItems.remove(item);
    }

    // EFFECTS: return index if the item's name is matched,
    // -1 if not found
    public int find(String name) {
        for (Item i: keptItems) {
            if (i.getName().equals(name)) {
                return keptItems.indexOf(i);
            }
            // return tryFind(name);
            // if change tryFind to private, uncomment the above statement
        }
        return -1;
    }

    // EFFECTS: return index if the item matches the given date,
    // -1 if not found
    public int find(LocalDate date) {
        for (Item i: keptItems) {
            if (i.getCreatedDate().isEqual(date)) {
                return keptItems.indexOf(i);
            }
            // return tryFind(date);
            // if change tryFind to private, uncomment the above statement
        }
        return -1;
    }

    // TODO:
    // Fuzzy seach/ approximate string search. use Levenshtein Distance
    //    // EFFECT: return index of the item if any keyword is match by an item , -1 if not found
    //    public int tryFind(String keyword) {
    //        // can be private
    //        int index = -1;
    //        for (Item i : keptItems) {
    //            if (tryMatchAny(keyword, i.getKeywords())) {
    //                return keptItems.indexOf(i);
    //            }
    //            // if the item is an object of Place, tryFind a match inside the object
    //            if (i.getClass().equals(Place.class)) {
    //                index = tryFind(keyword);
    //            }
    //        }
    //        return index;
    //    }
    //
    //    // EFFECTS: return true if an item matches any of the keywords
    //    private boolean tryMatchAny(String name, ArrayList<String> keywords) {
    //        for (String k: keywords) {
    //            if (isSimilarString(name, k)) {
    //                return true;
    //            }
    //        }
    //        return false;
    //    }
    //
    //    // EFFECTS: return true if the two string is a similar match
    //    private boolean isSimilarString(String s1, String s2) {
    // TODO:
    //return true; // stub
    //    }

    // TODO:
    // Approximate for date.
    // public int tryFind(LocalDate date) {
    //      return -1;
    // }


    // EFFECTS: return all items kept in the place
    public String getAll() {
        if (keptItems.isEmpty()) {
            return "";
        }

        StringBuilder allItems = new StringBuilder();

        for (Item i: keptItems) {
            if (i.getClass().equals(Place.class)) {
                allItems.append(i.getName());
                allItems.append("\n");
                allItems.append(((Place) i).getAll());
            } else {
                allItems.append(i.getName());
                allItems.append("\n");
            }
        }

        return allItems.toString();   // replace ", " in the end with "."
    }



    // EFFECTS: return all dates for importantDate of all items in the place
    public String getTimeline() {
        if (keptItems.isEmpty()) {
            return "";
        }
        if (keptItems.size() == 1) {
            return "\"" + this.getName() + "\" " + keptItems.get(0).getImportantDate() + "\n";
        }

        Comparator<Item> comparator = Comparator.comparing(Item::getImportantDate); // Custom comparator
        keptItems.sort(comparator); // Sort in natural order by customized comparator

        StringBuilder timeline = new StringBuilder();
        for (Item i: keptItems) {
            timeline.append(i.getName());
            timeline.append(" ");
            timeline.append(i.getImportantDate());
            timeline.append("\n");
        }

        return timeline.toString();
    }

    // Methods above =========================




    // Getters and setters below ========================
    public ListOfObjects getKeptItems() {
        return keptItems;
    }
}

