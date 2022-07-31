package persistence;

import model.Item;
import model.ListOfObjects;
import model.Place;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testWriterEmptyTopLevel() {
        try {
            ListOfObjects topLevel = new ListOfObjects();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTopLevel.json");
            writer.open();
            writer.writeTopLevel(topLevel);
            writer.close();

            ListOfObjectsJsonReader reader = new ListOfObjectsJsonReader("./data/testWriterEmptyTopLevel.json");
            topLevel = reader.read();
            ListOfObjects emptyListOfObjects = new ListOfObjects();

            assertEquals(emptyListOfObjects, topLevel);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralTopLevel() {
        ArrayList<String> keywords = new ArrayList<>();
        Place place1 = new Place("place1", "1111-11-11", 1, keywords);
        Item item1 = new Item("item1", "2001-01-01", 2, keywords);
        place1.add(item1);

        ArrayList<String> keywords2 = new ArrayList<>();
        keywords2.add("abc");
        keywords2.add("def");
        keywords2.add("ghe");
        Place place2 = new Place("place2", "2022-02-02", 2, keywords2);

        place1.add(place2);

        try {
            ListOfObjects topLevel = new ListOfObjects();
            topLevel.add(place1);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTopLevel.json");
            writer.open();
            writer.writeTopLevel(topLevel);
            writer.close();

            ListOfObjectsJsonReader reader = new ListOfObjectsJsonReader("./data/testWriterGeneralTopLevel.json");
            ListOfObjects newTopLevel = reader.read();

            assertEquals(place1, newTopLevel.get(0));
            assertEquals(1, newTopLevel.size());

            assertEquals(keywords, newTopLevel.get(0).getKeywords());
            assertEquals(item1, ((Place) newTopLevel.get(0)).getKeptItems().get(0));
            assertEquals("2001-01-01", ((Place) newTopLevel.get(0)).getKeptItems().get(0).getImportantDate());
            assertEquals(keywords2, ((Place) newTopLevel.get(0)).getKeptItems().get(1).getKeywords());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
