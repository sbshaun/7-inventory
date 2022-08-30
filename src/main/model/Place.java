package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Comparator;


// a place to keep items. A place can also be an item and can be kept at a place
// e.g. a bag can be kept at a bigger bag
public class Place extends Item implements Writable {
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

        EventLog.getInstance().logEvent(new Event("Added item-\"" + item.getName() + "\""
                + " to place-\"" + this.getName() + "\"."));
    }

    // MODIFIES: this
    // EFFECTS: remove an item from keptItems
    public void remove(Item item) {
        keptItems.remove(item);

        EventLog.getInstance().logEvent(new Event("Deleted item-\"" + item.getName() + "\""
                + " from place-\"" + this.getName() + "\"."));
    }

    // EFFECTS: return all items kept in the place (at all deeper levels)
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

    @Override
    // EFFECTS: return this as a JSON object
    // Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject jsonObject;
        jsonObject = super.toJson();
        jsonObject.put("keptItems", keptItems.listOfObjectsToJson());
        return jsonObject;
    }

    // Methods above =========================

    // Getters and setters below ========================
    public ListOfObjects getKeptItems() {
        return keptItems;
    }
}

