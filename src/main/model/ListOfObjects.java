package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Comparator;

// A list of Places
public class ListOfObjects extends ArrayList<Item> implements Writable {

    // Methods below ========================

    // TODO: tests
//    // EFFECTS: return all places in Top Level
//    public String getAllPlaces() {
//        if (this.isEmpty()) {
//            return "";
//        }
//
//        StringBuilder allPlaces = new StringBuilder();
//
//        for (Item i: this) {
//            allPlaces.append("\n");
//            allPlaces.append(i.getName());
//        }
//
//        return allPlaces.toString();
//    }

    // EFFECTS: return items kept in the current place, not going into items
    public String getCurrentAll() {
        if (this.isEmpty()) {
            return "";
        }

        StringBuilder allItems = new StringBuilder();

        for (Item i: this) {
            allItems.append("\n");
            allItems.append(i.getName());
        }

        return allItems.toString();   // replace ", " in the end with "."
    }

    // EFFECTS: return all places and items in all places (going into deeper levels)
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

    // EFFECTS: return all dates for importantDate of all items in this
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
            String impDate = i.getImportantDate();
            if (!impDate.equals("7777-07-17")) {
                timeline.append("\n");
                timeline.append(i.getName());
                timeline.append(" ");
                timeline.append(impDate);
            }
        }

        return timeline.toString();
    }

    // TODO: after printing out timeline, Top Level messed up
    // TODO: remove dates for items without importantDate
    // EFFECTS: get all kept items in this (at all levels)
    private ListOfObjects addAllItems(ListOfObjects loob) {
        ListOfObjects copy = new ListOfObjects();
        for (Item item: loob) { // TODO: would Place be casted as Item implicitly?
            if ((item.getClass().equals(Place.class))) {
                copy.add(item);
                ListOfObjects itemsInsidePlace = addAllItems(((Place) item).getKeptItems());
                copy.addAll(itemsInsidePlace);
            } else {
                copy.add(item);
            }
        }
        return copy;
    }


    @Override
    // EFFECTS: return this as a JSON object
    // Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("listOfObjects", listOfObjectsToJson());
        return jsonObject;
    }

    // EFFECTS: return this as a JSON array
    public JSONArray listOfObjectsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : this) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }
    // Methods above ========================
}
