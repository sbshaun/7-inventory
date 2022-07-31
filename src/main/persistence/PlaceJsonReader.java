package persistence;

import model.Item;
import model.Place;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

// Read a Place from JSON object
// Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class PlaceJsonReader {
    String name;
    LocalDate createdDate;
    String importantDate;
    int degreeOfImportance;
    ArrayList<Item> keptItems;
    ArrayList<String> keywords;

    private final JSONObject jsonObject;

    // EFFECTS: constructs reader to read from source file
    public PlaceJsonReader(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    // MODIFIES:this
    // EFFECTS: parses Place from JSON object and returns it
    public Place parse() {
        keywords = new ArrayList<>();
        name = jsonObject.getString("name");
        createdDate = LocalDate.parse(jsonObject.getString("createdDate"));
        importantDate = jsonObject.getString("importantDate");
        degreeOfImportance = jsonObject.getInt("degreeOfImportance");

        Place place = new Place(name, importantDate, degreeOfImportance,
                ItemJsonReader.addKeywords(keywords, jsonObject));
        place.setCreatedDate(createdDate);

        keptItems = ListOfObjectsJsonReader.parseKeptItems(jsonObject);

        for (Item item: keptItems) {
            place.add(item);
        }

        return place;
    }
}
