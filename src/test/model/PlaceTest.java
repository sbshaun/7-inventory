package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlaceTest {
    private Place place1;
    private Place place2;
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

        place1 = new Place("A place", "2022-07-17",
                7, keywords);
        place2 = new Place("2 place", "2022-11-11",
                5, keywords);

        item1 = new Item("Concert ticket", "2022-09-19", 7, keywords);
        item2 = new Item("Concert ticket2","2023-11-11", 5, keywords);
        item3 = new Item("Old ipad", "2022-09-27", 3, keywords);
    }

    @Test
    public void testConstructor() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("yes");
        keywords.add("abc");
        keywords.add("def");

        Place place = new Place("FFF", "2023-03-17",
                5, keywords);

        String name = place.getName();
        String impDate= place.getImportantDate();
        int degOfImp = place.getDegreeOfImportance();
        ArrayList<String> itemKeywords = place.getKeywords();

        assertEquals("FFF", name);
        assertEquals("2023-03-17", impDate);
        assertEquals(5, degOfImp);
        assertEquals(keywords, itemKeywords);
    }

    @Test
    void addTest() {
        ListOfObjects keptItems = place1.getKeptItems();
        ArrayList<Item> copy = new ArrayList<>();

        place1.add(place2);
        copy.add(place2);

        assertEquals(copy, keptItems);

        place1.add(item1);
        copy.add(item1);

        place1.add(item2);
        copy.add(item2);

        place1.add(item3);
        copy.add(item3);

        assertEquals(copy,keptItems);
    }

    @Test
    void removeTest() {
        ListOfObjects keptItems = place1.getKeptItems();
        place1.add(place2);
        place1.add(item1);
        place1.add(item2);
        place1.add(item3);
        ArrayList<Item> copy = new ArrayList<>(
                Arrays.asList(place2, item1, item2, item3)
        );

        place1.remove(item1);
        copy.remove(item1);
        assertEquals(copy, keptItems);

        place1.remove(item2);
        place1.remove(item3);
        place1.remove(place2);

        ArrayList<Item> emptyList = new ArrayList<>();
        assertEquals(emptyList, keptItems);
    }

    @Test
    public void getAllTest() {
        place1.add(place2);
        place2.add(item1);
        place1.add(item2);
        place2.add(item3);

        String result = place1.getAll();
        String expectedResult = "2 place\n" + "Concert ticket\n" + "Old ipad\n" + "Concert ticket2\n";
        assertEquals(expectedResult, result);
    }

    @Test
    public void getTimelineTest() {
        String result1 = place1.getTimeline();

        place1.add(place2);
        String result2 = place1.getTimeline();

        place1.add(item1);
        place1.add(item2);
        place1.add(item3);
        String result3 = place1.getTimeline();

        String expectedResult = "Concert ticket 2022-09-19\n" +
                                "Old ipad 2022-09-27\n" +
                                "2 place 2022-11-11\n" +
                                "Concert ticket2 2023-11-11\n";
        assertEquals("", result1);
        assertEquals("\"A place\" 2022-11-11\n", result2);
        assertEquals(expectedResult, result3);
    }
}