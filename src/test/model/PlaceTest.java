package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        item2 = new Item("Concert ticket","", 5, keywords);
        item3 = new Item("Old ipad", "2022-09-27", 3, keywords);
    }



    @Test
    public void testConstructor4() {
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
    void findTest() {
        place1.add(place2);
        place1.add(item1);
        place1.add(item2);
        place1.add(item3);

        int result1= place1.find("2 place");
        int result2 = place1.find("abc");
        int result3 = place1.find("Concert ticket");

        assertEquals(0, result1);
        assertEquals(-1, result2);
        assertEquals(999, result3);
    }



    @Test
    void tryFindTest() {
        place1.add(place2);
        place1.add(item1);
        place1.add(item2);
        place1.add(item3);

        int result1= place1.find("place");
        int result2 = place1.find("abc");
        int result3 = place1.find("Concert tic");

        assertEquals(0, result1);
        assertEquals(-1, result2);
        assertEquals(999, result3);
    }



    @Test
    void getAllTest() {
        place1.add(place2);
        place1.add(item1);
        place1.add(item2);
        place1.add(item3);

        String result = place1.getAll();

        String expectedResult = "All items kept in \"A place\": 2 place, " +
                "Concert ticket, Concert ticket, Old ipad.";
        assertEquals(expectedResult, result);
    }



    @Test
    void getTimelineTest() {
        place1.add(place2);
        place1.add(item1);
        place1.add(item2);
        place1.add(item3);

        String result = place1.getTimeline();

        String expectedResult = "Important dates: Concert ticket 2022-09-19, " +
                "Old ipad 2022-09-27, 2 place 2022-11-11.";
        assertEquals(expectedResult, result);
    }
}