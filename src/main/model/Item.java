package model;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.max;

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

    // TODO: not used for now
    // REQUIRES: !(both string1 and string2 are empty)
    // EFFECTS: return similarity between the input strings
    // Reference:
    // https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/LevenshteinDistance.html#apply(java.lang.CharSequence,java.lang.CharSequence)
    // https://medium.com/@saycchai/great-article-but-your-levenshtein-distance-to-percentage-formula-1-levenshtein-distance-is-5cef7b935f69
    public static float stringSimilarity(String string1, String string2) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        float distance = levenshteinDistance.apply(string1, string2);
        return (1 - distance / max(string1.length(), string2.length()));
    }

    // EFFECTS: compare the string to all strings in keywords and return the highest similarity
    public float keywordsSimilarity(String string) {
        if (keywords.isEmpty()) {
            return 0;
        }

        float highestSimilarity = 0;
        float similarity;

        for (String keyword: keywords) {
            similarity = stringSimilarity(string, keyword);
            if (similarity > highestSimilarity) {
                highestSimilarity = similarity;
            }
        }

        return highestSimilarity;
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
