package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @Test
    public void testConstructor() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("yes");
        keywords.add("abc");
        keywords.add("def");

        Item item = new Item("FFF", "2023-03-17",
                5, keywords);

        String name = item.getName();
        String impDate= item.getImportantDate();
        int degOfImp = item.getDegreeOfImportance();
        LocalDate addedDate = item.getCreatedDate(); // How to test added date?
        ArrayList<String> itemKeywords = item.getKeywords();

        assertEquals("FFF", name);
        assertEquals(LocalDate.now(), addedDate);
        assertEquals("2023-03-17", impDate);
        assertEquals(5, degOfImp);
        assertEquals(keywords, itemKeywords);
    }

    @Test
    public void hashCodeTest() {
        Item item1 = new Item("a", "2022-10-01", 7, new ArrayList<>());
        Item item2 = new Item("a", "2022-10-01", 7, new ArrayList<>());

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }
}