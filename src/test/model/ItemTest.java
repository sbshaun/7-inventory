package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        // String addedDate = item.getAddedDate(); // How to test added date?
        ArrayList<String> itemKeywords = item.getKeywords();

        assertEquals("FFF", name);
        assertEquals("2023-03-17", impDate);
        assertEquals(5, degOfImp);
        assertEquals(keywords, itemKeywords);
    }
}