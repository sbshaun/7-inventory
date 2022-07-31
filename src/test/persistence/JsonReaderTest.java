package persistence;

import model.ListOfObjects;
import model.Place;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        ListOfObjectsJsonReader reader = new ListOfObjectsJsonReader("./data/noSuchFile.json");
        try {
            ListOfObjects listOfObjects = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testReaderEmptyWorkRoom() {
        ListOfObjectsJsonReader reader = new ListOfObjectsJsonReader("./data/testReaderEmptyTopLevel.json");
        try {
            ListOfObjects topLevel = reader.read();
            ListOfObjects emptyListOfObjects = new ListOfObjects();

            assertEquals(emptyListOfObjects, topLevel);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        try {
            ListOfObjectsJsonReader reader = new ListOfObjectsJsonReader("./data/testReaderGeneralTopLevel.json");
            ListOfObjects newTopLevel = reader.read();

            assertEquals("place1", newTopLevel.get(0).getName());
            assertEquals(1, newTopLevel.size());

            assertEquals("item1", ((Place) newTopLevel.get(0)).getKeptItems().get(0).getName());
            assertEquals("2001-01-01", ((Place) newTopLevel.get(0)).getKeptItems().get(0).getImportantDate());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
