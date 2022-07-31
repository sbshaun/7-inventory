package persistence;

import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

// Read an Item from JSON object
// Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class ItemJsonReader {
    String name;
    LocalDate createdDate;
    String importantDate;
    int degreeOfImportance;
    ArrayList<String> keywords;
    private final JSONObject jsonObject;

    // EFFECTS: constructs reader to read from source file
    public ItemJsonReader(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    // MODIFIES: this
    // EFFECTS: parses Item from JSON object and returns it
    public Item parse() {
        keywords = new ArrayList<>();
        name = jsonObject.getString("name");
        createdDate = LocalDate.parse(jsonObject.getString("createdDate"));
        importantDate = jsonObject.getString("importantDate");
        degreeOfImportance = jsonObject.getInt("degreeOfImportance");

        Item item = new Item(name, importantDate, degreeOfImportance,
                addKeywords(keywords, jsonObject));
        item.setCreatedDate(createdDate);

        return item;
    }

    // MODIFIES: keywords
    // EFFECTS: parses  from JSON object and adds them to keywords
    public static ArrayList<String> addKeywords(ArrayList<String> keywords, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("keywords");

        for (int i = 0;  i < jsonArray.length(); i++) {
            keywords.add((String) jsonArray.get(i));
        }

        return keywords;
    }
}
