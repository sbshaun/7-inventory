package persistence;


import model.ListOfObjects;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Item from JSON data stored in file
// Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class ListOfObjectsJsonReader {
    private final String source;
    ListOfObjects listOfObjects;


    // EFFECTS: constructs reader to read from source file
    public ListOfObjectsJsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads listOfObjects from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfObjects read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfObjects(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // REQUIRES: At TopLevel where elements of listOfObjects are all types of Place
    // EFFECTS: parses listOfObjects from JSON object and returns it
    public ListOfObjects parseListOfObjects(JSONObject jsonObject) {
        listOfObjects = new ListOfObjects();
        JSONArray jsonArray = jsonObject.getJSONArray("topLevel");

        for (int i = 0; i < jsonArray.length(); i++) {
            PlaceJsonReader placeJsonReader = new PlaceJsonReader((JSONObject) jsonArray.get(i));
            listOfObjects.add(placeJsonReader.parse());
        }

        return listOfObjects;
    }

    // EFFECTS: parse keptItems from JSON obejct
    public static ListOfObjects parseKeptItems(JSONObject jsonObject) {
        JSONArray  jsonArray = jsonObject.getJSONArray("keptItems"); // an JSON array containing JSON objects
        ListOfObjects keptItems = new ListOfObjects();

        for (int i = 0; i < jsonArray.length(); i++) {
            if (((JSONObject) jsonArray.get(i)).has("keptItems")) {
                PlaceJsonReader placeJsonReader = new PlaceJsonReader((JSONObject) jsonArray.get(i));
                keptItems.add(placeJsonReader.parse());
            } else {
                ItemJsonReader itemJsonReader = new ItemJsonReader((JSONObject) jsonArray.get(i));
                keptItems.add(itemJsonReader.parse());
            }
        }

        return keptItems;
    }
}
