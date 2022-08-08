package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static model.Item.stringSimilarity;
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

    @Test
    public void nameSimilarityTest() {
        String name1 = "";
        String name2 = "a";

        assertEquals(0, stringSimilarity(name1, name2));

        String name3 = "elephant";
        String name4 = "elepnamt";

        assertEquals(0.75, stringSimilarity(name3, name4));

        String name5 = "elephant";
        String name6 = "hippo";

        assertEquals(0.125, stringSimilarity(name5, name6));
    }

    @Test
    public void keywordsSimilarityTest() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("");
        keywords.add("a");
        keywords.add("elephant");
        keywords.add("hippo");

        Item item = new Item("abc", "2023-07-07", 7, keywords);

        float similarity = item.keywordsSimilarity("elepnamt");

        assertEquals(0.75, similarity);

        keywords.clear();
        float similarity2 =item.keywordsSimilarity("elepnamt");

        assertEquals(0, similarity2);
    }
}