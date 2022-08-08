package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.ListOfObjectsJsonReader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ListOfObjectsTest {
    private ListOfObjects listOfObjects;
    private Place place1;
    private Place place2;
    private Place place3;
    private Item item1;
    private Item item2;
    private Item item3;

    @BeforeEach
    public void setup() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("Charger");
        keywords.add("Iphone");
        keywords.add("White");
        keywords.add("Square");
        keywords.add("Thin");

        listOfObjects = new ListOfObjects();

        place1 = new Place("A place", "2022-07-17",
                7, keywords);
        place2 = new Place("2 place", "2022-11-11",
                5, keywords);
        place3 = new Place("3 place", "2022-11-17",
                11, keywords);

        item1 = new Item("Concert ticket", "2022-09-19", 7, keywords);
        item2 = new Item("Concert ticket", "2022-07-27", 5, keywords);
        item3 = new Item("Old ipad", "2022-09-27", 3, keywords);

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
    public void getAllPlacesTest() {
        listOfObjects = new ListOfObjects();
        listOfObjects.add(place1);
        listOfObjects.add(place2);
        place1.add(place1);
        place1.add(place2);
        place2.add(place2);
        place2.add(place3);
        place3.add(item1);
        place3.add(item2);
        place3.add(item3);

        String expectedResult = "\nA place\n2 place";
        String result = listOfObjects.getCurrentAll();

        listOfObjects = new ListOfObjects();
        String result2  = listOfObjects.getCurrentAll();

        assertEquals(expectedResult, result);
        assertEquals("", result2);
    }


    @Test
    public void getEverythingTest() {

        String expectedResult = "A place\n2 place\n3 place\nConcert ticket\nConcert ticket\nOld ipad\n";

        String result1 = listOfObjects.getEverything();

        listOfObjects = new ListOfObjects();
        String result2  = listOfObjects.getEverything();

        assertEquals(expectedResult, result1);
        assertEquals("", result2);
    }

    @Test
    public void getEveryTimelineTest() {
        String expectedResult1 = "\nA place: 2022-07-17\nConcert ticket: 2022-07-27\nConcert ticket: 2022-09-19\n" +
                "Old ipad: 2022-09-27\n2 place: 2022-11-11\n3 place: 2022-11-17";

        String result1 = listOfObjects.getEveryTimeline();

        listOfObjects = new ListOfObjects();
        String result2  = listOfObjects.getEveryTimeline();

        place3.add(item3);
        place2.add(place3);
        place2.add(item2);
        place2.add(item1);
        place1.add(place2);

        listOfObjects.add(place1);
        String result3 = listOfObjects.getEveryTimeline();

        assertEquals(expectedResult1, result1);
        assertEquals("", result2);
        assertEquals(expectedResult1, result3);
    }
}