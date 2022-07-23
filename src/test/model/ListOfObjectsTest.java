package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListOfObjectsTest {
    private ListOfObjects listOfObjects;

    @BeforeEach
    public void setup() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("Charger");
        keywords.add("Iphone");
        keywords.add("White");
        keywords.add("Square");
        keywords.add("Thin");

        listOfObjects = new ListOfObjects();

        Place place1 = new Place("A place", "2022-07-17",
                7, keywords);
        Place place2 = new Place("2 place", "2022-11-11",
                5, keywords);
        Place place3 = new Place("3 place", "2022-15-17",
                11, keywords);

        Item item1 = new Item("Concert ticket", "2022-09-19", 7, keywords);
        Item item2 = new Item("Concert ticket", "", 5, keywords);
        Item item3 = new Item("Old ipad", "2022-09-27", 3, keywords);

        listOfObjects.add(place1);
        listOfObjects.add(place2);
        listOfObjects.add(place3);
        listOfObjects.add(item1);
        listOfObjects.add(item2);
        listOfObjects.add(item3);
    }

    @Test
    public void testConstructor1() {
        ListOfObjects listOfObjects = new ListOfObjects();

        ArrayList<Item> lobj = new ArrayList<>();

        assertEquals(lobj, listOfObjects);
    }

    @Test
    void getAllTest() {
        String expectedResult = "All objects here: A place, 2 place, " +
                "3 place, Concert ticket, Concert ticket, Old ipad";

        String result = listOfObjects.getAll();

        assertEquals(expectedResult, result);
    }

    @Test
    void getTimeLineTest() {
        String expectedResult = "Important dates: A place 2022-07-17, Concert ticket 2022-09-19, " +
                "Old ipad 2022-09-27, 2 place 2022-11-11, 3 place 2022-11-17.";

        String result = listOfObjects.getTimeLine();

        assertEquals(expectedResult, result);
    }
}