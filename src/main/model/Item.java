package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

// An item with necessary information
public class Item implements Writable {
    private final String name;

    private LocalDate createdDate;
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

    @Override
    // EFFECTS: return this as a JSON object
    // Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("createdDate", createdDate);
        jsonObject.put("importantDate", importantDate);
        jsonObject.put("degreeOfImportance", degreeOfImportance);
        jsonObject.put("keywords", keywordsToJson());
        return jsonObject;
    }

    // EFFECTS: return keywords as a JSON array
    private JSONArray keywordsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String k: keywords) {
            jsonArray.put(k);
        }
        return jsonArray;
    }

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

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return degreeOfImportance == item.degreeOfImportance
                && Objects.equals(name, item.name) && Objects.equals(createdDate, item.createdDate)
                && Objects.equals(importantDate, item.importantDate) && Objects.equals(keywords, item.keywords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, createdDate, importantDate, degreeOfImportance, keywords);
    }
}
