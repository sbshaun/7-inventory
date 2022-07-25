package model;

import java.util.ArrayList;
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
        ListOfObjects copy;

        if (this.isEmpty()) {
            return "";
        }

        // add  items in places into copy
        copy = addAllItems(this);

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

    private ListOfObjects addAllItems(ListOfObjects loob) {
        ListOfObjects listOfObjects = new ListOfObjects();
        for (Item item: loob) {
            if ((item.getClass().equals(Place.class))) {
                listOfObjects.add(item);
                addAllItems(((Place) item).getKeptItems());
            } else {
                listOfObjects.add(item);
            }
        }
        return listOfObjects;
    }
    // Methods above ========================
}
