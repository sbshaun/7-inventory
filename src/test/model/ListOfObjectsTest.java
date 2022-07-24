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
        Place place3 = new Place("3 place", "2022-11-17",
                11, keywords);

        Item item1 = new Item("Concert ticket", "2022-09-19", 7, keywords);
        Item item2 = new Item("Concert ticket", "2022-07-27", 5, keywords);
        Item item3 = new Item("Old ipad", "2022-09-27", 3, keywords);

        listOfObjects.add(place1);
        listOfObjects.add(place2);
        listOfObjects.add(place3);
        listOfObjects.add(item1);
        listOfObjects.add(item2);
        listOfObjects.add(item3);
    }

    @Test
    public void testConstructor() {
        ListOfObjects listOfObjects = new ListOfObjects();

        ArrayList<Item> lobj = new ArrayList<>();

        assertEquals(lobj, listOfObjects);
    }

    @Test
    void getEverythingTest() {

        String expectedResult = "A place\n2 place\n3 place\nConcert ticket\nConcert ticket\nOld ipad\n";

        String result1 = listOfObjects.getEverything();

        listOfObjects = new ListOfObjects();
        String result2  = listOfObjects.getEverything();

        assertEquals(expectedResult, result1);
        assertEquals("", result2);
    }

    @Test
    void getEveryTimelineTest() {
        String expectedResult = "A place 2022-07-17\nConcert ticket 2022-07-27\nConcert ticket 2022-09-19\n" +
                "Old ipad 2022-09-27\n2 place 2022-11-11\n3 place 2022-11-17\n";

        String result1 = listOfObjects.getEveryTimeline();

        listOfObjects = new ListOfObjects();
        String result2  = listOfObjects.getEveryTimeline();

        assertEquals(expectedResult, result1);
        assertEquals("", result2);
    }
}