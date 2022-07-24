package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// A list of Places
public class ListOfObjects extends ArrayList<Item> {

    // Methods below ========================

    // EFFECTS: return all places and items in all places
    public String getEverything() {
        if (this.isEmpty()) {
            return "";
        }

        StringBuilder everything = new StringBuilder();

        for (Item i: this) {
            if (i.getClass().equals(Place.class)) {
                everything.append(i.getName());
                everything.append("\n");
                everything.append(((Place) i).getAll());
            } else {
                everything.append(i.getName());
                everything.append("\n");
            }
        }

        return everything.toString(); // stub
    }

    // EFFECTS: return all dates for importantDate of all items in all places
    // EFFECTS: return all dates for importantDate of all items in the place
    public String getEveryTimeline() {
        ListOfObjects copy = new ListOfObjects();
        copy.addAll(this);

        if (copy.isEmpty()) {
            return "";
        }

        // add  items in places into copy
        for (Item i: this) {
            if (i.getClass().equals(Place.class)) {
                copy.addAll(((Place) i).getKeptItems());
            }
        }

        Comparator<Item> comparator = Comparator.comparing(Item::getImportantDate); // Custom comparator
        copy.sort(comparator); // Sort in natural order by customized comparator

        StringBuilder timeline = new StringBuilder();
        for (Item i: copy) {
            timeline.append(i.getName());
            timeline.append(" ");
            timeline.append(i.getImportantDate());
            timeline.append("\n");
        }

        return timeline.toString();
    }

    // Methods above ========================
}
