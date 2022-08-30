package ui;

import model.Item;
import model.Place;
import model.ListOfObjects;
import persistence.JsonWriter;
import persistence.ListOfObjectsJsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

// SevenInventory application Console UI
public class SevenInventory {
    // Next phase:
    // TODO: finish search method: look up items, using LevenshteinDistance
    // TODO: finish getTimeline method.
    // TODO: finish add get all items: print out all items in all places
    private static final String JSON_STORE = "./data/sevenInventory.json";
    private JsonWriter jsonWriter;
    private ListOfObjectsJsonReader jsonReader;

    private ListOfObjects topLevel;
    private LinkedList<Place> pathOfPlaces;
    private Place currentPlace = null; // whenever we access a place, set the place as current
    private Place previousPlace = null; // when we access a place inside a place, set current place to previous
    private Item currentItem = null; // item we currently have access
    private Scanner scanner; // get user input
    private String name = null;
    private String importantDate = null;
    private int degreeOfImportance = 0;
    private ArrayList<String> keywords = null;

    // EFFECTS: runs the 7-Inventory application. Some code from TellerApp.java
    public SevenInventory() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        String input;

        // instantiate pathOfPlaces, topLevel, scanner, jsonWriter, jsonReader
        init();

        do { // Loop until user typed I like (the project) :)
            System.out.print("\nEnter \"I like the project\" to continue: \n>>>");
            input = scanner.nextLine().toLowerCase().replaceAll("\\s+", "");
            // TODO:  if time permits, normalize string: remove extra space in the middle
        } while (!input.matches("ilike.*"));

        while (true) { // Loop until user chose to quit
            if (topLevel.isEmpty()) { // when Top Level has no place to access
                displayEmptyMenu();
            } else if (currentPlace == null) { // meaning  we are at the Top Level
                displayTopLevelMenu();
            } else { // if currentPlace is assigned a place, meaning we are inside a place
                displayPlaceMenu();
            }

            // get user input
            System.out.print(">>> ");
            input = scanner.next().toLowerCase();

            // break when user entered q
            if (input.equals("q")) {
                saveSevenInventory(); // automatically save when quit
                System.out.println("Program quit.");
                break; // exit the while loop, program ends.
            }

            processInput(input);
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize listOfPlaces and scanner
    private void init() {
        pathOfPlaces = new LinkedList<>();
        topLevel = new ListOfObjects();
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new ListOfObjectsJsonReader(JSON_STORE);
    }

    // EFFECTS: display a menu when listOfPlaces is empty
    public void displayEmptyMenu() {
        System.out.println("We are at Top Level, no available places. Select from: ");
        System.out.println("1. Load file from last time (enter \"ld\")");
        System.out.println("2. Create a place (enter \"p\")");
        System.out.println("3. Quit (enter\"q\")");
    }

    // EFFECTS: display a menu when currentPlace is null meaning we are at the top level
    public void displayTopLevelMenu() {
        System.out.println("We are at Top Level. Available Places: ");
        System.out.println(topLevel.getCurrentAll());
        System.out.println("\nSelect from: ");
//        System.out.println("1. Find an item (enter \"f\")");
        System.out.println("1. Create a place (enter \"p\")");
        System.out.println("2. Access a place (enter \"acc\")");
        System.out.println("3. Get a timeline of all important dates (enter \"t\")");
        System.out.println("4. Save (enter\"s\")");
        System.out.println("5. Quit (enter\"q\")");
    }

    // EFFECTS: display a menu when currentPlace is a Place meaning we are inside a place
    public void displayPlaceMenu() {
        // Print: We are in "Place.name" (created on "date"):
        System.out.println("\nWe are in \"" + currentPlace.getName() + "\""
                + " (created on " + currentPlace.getCreatedDate() + ")" + ": ");
        String impDate = currentPlace.getImportantDate();

        // Check if user entered a date, since "7777-07-17" is by default
        if (!impDate.equals("7777-07-17")) {
            System.out.println("Important date: " + impDate);
        }
        int doi = currentPlace.getDegreeOfImportance();

        // Check if user entered a doi, since -777 is by default
        if (doi != -777) {
            System.out.println("Degree of importance: " + doi);
        }

        System.out.println("This place contains: ");
        System.out.println(currentPlace.getKeptItems().getCurrentAll().trim());
        System.out.println("Select from: ");
        System.out.println("1. Create an item (enter \"i\")");
        System.out.println("2. Create a place (enter \"p\")");
        System.out.println("3. Access a place/item (enter \"acc\")");
        System.out.println("3. Delete the current place (enter\"d\")");
        System.out.println("5. Go back to last place (enter \"la\")");
        System.out.println("6. Go back to Top Level menu (enter \"b\")");
        System.out.println("7. Quit (enter \"q\")");
    }

    // MODIFIES: this
    // EFFECT: process user input
    private void processInput(String input) {
//        if (input.matches("f.*")) {
//            findItem();
//        } else
        if (input.matches("p.*")) {
            createPlace();
        } else if (input.matches("acc.*")) {
            access();
        } else if (input.matches("b.*")) {
            backToTopLevel();
        } else if (input.matches("i.*")) {
            createItem();
        } else if (input.matches("d.*")) {
            removePlace();
        } else if (input.matches("ld.*")) {
            loadSevenInventory();
        } else if (input.matches("la.*")) {
            goBackToLastPlace();
        } else if (input.matches("t.*")) {
            getTimeline();
        } else if (input.matches("s.*")) {
            saveSevenInventory();
        }
    }

    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    // EFFECTS: saves the workroom to file
    private void saveSevenInventory() {
        try {
            jsonWriter.open();
            jsonWriter.writeTopLevel(topLevel);
            jsonWriter.close();
            System.out.println("Everything is saved to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadSevenInventory() {
        try {
            topLevel = jsonReader.read();
            System.out.println("Loaded everything from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    // Main methods below

    // MODIFIES: this
    // EFFECTS: create a place and add it to currentPlace
    private void createPlace() {
        System.out.println("\nName the place: ");
        System.out.println("\nName the place: ");
        System.out.print(">>> ");
        name = scanner.next(); // TODO: normalize string

        askItemInfo();
        Place place = new Place(name, importantDate, degreeOfImportance, keywords);
        // add place to the current place, and change the current place to the place just created
        if (currentPlace == null) {
            topLevel.add(place);
        } else {
            currentPlace.add(place);
        }
    }

    // MODIFIES: this
    // EFFECTS: access a place or item
    private void access() {
        System.out.println("Enter name of the place/item you want to access: ");
        listItems();
        String input;
        while (true) {
            System.out.print(">>> ");
            input = scanner.next();
            if (currentPlace == null) {
                for (Item i: topLevel) {
                    if (input.equals(i.getName())) {
                        currentPlace = (Place) i;
                        // pathOfPlaces.add((Place) i); // currentPlace = null, meaning Top at Level
                        // System.out.println("We now are inside " + i.getName() + ".");
                        return;
                    }
                }
            }
            if (setItem(input)) {
                return;
            } else {
                tryAgain();
            }
        }
    }

    private void getTimeline() {
        System.out.println("Important dates in all places: ");
        System.out.println(topLevel.getEveryTimeline() + "\n");
    }

    // MODIFIES: this
    // EFFECTS: create an item and add it to current listOfObjects
    private void createItem() {
        System.out.println("\nName the item: ");
        System.out.print(">>> ");
        name = scanner.next();    // TODO: normalize string if time permits

        askItemInfo();

        Item item = new Item(name, importantDate, degreeOfImportance, keywords);

        assert currentPlace != null;
        currentPlace.add(item);
    }

    // MODIFIES: this
    // EFFECTS: display Item info, and then delete or exit the item
    private void displayItemInfo() {
        assert currentItem != null;
        System.out.println("Item: " + currentItem.getName());
        System.out.println("Important date: " + currentItem.getImportantDate());
        System.out.println("Degree of importance: " + currentItem.getDegreeOfImportance());
        System.out.println("Created date: " + currentItem.getCreatedDate());
        // System.out.println("Key words: " + currentItem.getKeywords()); TODO: keywords
        String input;
        while (true) {
            System.out.println("\nDelete the item (enter \"d\")");
            System.out.println("Exit item, go to the current place (enter \"l\")");
            System.out.print(">>> ");
            input = scanner.next();

            if (input.matches("d.*")) {
                currentPlace.remove(currentItem);
                currentItem = null;
                displayPlaceMenu();
                break;
            } else if (input.matches("l.*")) {
                currentItem = null;
                displayPlaceMenu();
                break;
            }
        }
    }

    /// MODIFIES: this
    // EFFECTS: remove current place and go back to last place
    private void removePlace() {
        if (previousPlace == null) {
            // when we are at a place of Top Level
            topLevel.remove(currentPlace);
            goBackToLastPlace();
        } else {
            // when we are in a place inside some other place
            try {
                previousPlace.getKeptItems().remove(currentPlace);
                goBackToLastPlace();
                // make previousPlace to placePath, ArrayList<Place>, keeping track of Place accessed,
            } catch (NullPointerException e) {
                System.out.println("NullPointerException"); // stub
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: go back to last place
    private void goBackToLastPlace() {
        if (previousPlace == null) {
            currentPlace = null;
            pathOfPlaces.clear();
        } else {
            currentPlace = previousPlace; // go back to previousPlace from currentPlace
            previousPlace = pathOfPlaces.pollLast(); // get the previousPlace before the current previousPlace
        }
    }

    // MODIFIES: this
    // EFFECTS: reset fields to go  back to Top Level
    private void backToTopLevel() {
        pathOfPlaces.clear();
        previousPlace = null;
        currentPlace = null;
        currentItem = null;
        // while loop will display the Top Level Menu automatically
    }

    // Main methods above
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    // Helper methods below

    // EFFECTS: list items in a place if within a place, list all places if in Top Level
    private void listItems() {
        if (currentPlace == null) {
            System.out.println("\nWe are at Top Level. Select from available places:");
            System.out.println(topLevel.getCurrentAll().trim());
        } else {
            System.out.println("\nWe are in \"" + currentPlace.getName() + "\".");
            System.out.println("Available objects: \n" + currentPlace.getKeptItems().getCurrentAll().trim());
        }
    }

    // MODIFIES: this
    // EFFECTS: set currentPlace if input name is a place, set currentItem if specified an item name
    private boolean setItem(String input) {
        if (currentPlace == null) {
            return false;
        } else if (currentPlace.getKeptItems().isEmpty()) {
            System.out.println("Current place is empty, Nothing to access.");
            return true;
        }
        for (Item i: currentPlace.getKeptItems()) {
            if (input.equals(i.getName())) {
                if (i.getClass().equals(Place.class)) {
                    // if we are not in Top Level, we copy the currentPlace to previousPlace and set new currentPlace
                    if (previousPlace != null) {
                        pathOfPlaces.add(previousPlace); // add previous place to path
                    }
                    previousPlace = currentPlace;
                    currentPlace = (Place) i;
                } else {
                    // if the input item is type Item, simply set currentItem
                    currentItem = i;
                    displayItemInfo();
                    return true;
                }
            }
        }
        return false;
    }

    // EFFECTS: print try again if the name is not matched by any item
    private void tryAgain() {
        if (currentPlace == null) {
            System.out.println("Wrong name, try again: ");
            System.out.println("Places available: \n" + topLevel.getCurrentAll().trim());
            System.out.print(">>> ");
        } else {
            System.out.println("Wrong name, try again: ");
            System.out.println("Objects available: \n" + currentPlace.getKeptItems().getCurrentAll().trim());
            System.out.print(">>> ");
        }
    }

    // MODIFIES: this
    // EFFECTS: ask user the input information of an item
    private void askItemInfo() {
        String input;
        importantDate = "7777-07-17"; // set default
        degreeOfImportance = -777;
        keywords = new ArrayList<>();


        while (true) {
            System.out.println("\nAny extra information you want to add/change?");
            System.out.println("Important date (date)");
            System.out.println("Degree of importance (doi)");
            System.out.println("Keywords (key)");
            System.out.println("Enter \"ok\" if done");
            System.out.print(">>> ");
            input = scanner.next().toLowerCase();

            if (input.matches("o.*")) {
                break;
            }

            processItemInput(input);
        }
    }

    // MODIFIES: this
    // EFFECTS: process user input when creating an item
    private void processItemInput(String input) {
        if (input.matches("da.*")) {
            askImportantDate();
        } else if (input.matches("do.*")) {
            askDegreeOfImportance();
        } else if (input.matches("k.*")) {
            askKeywords();
        }
    }

    // MODIFIES: this
    // EFFECTS: ask ImportantDate until get a valid one
    private void askImportantDate() {
        String input;

        while (true) {
            System.out.println("Enter a date (yyyy-MM-dd): ");
            System.out.print(">>> ");
            input = formatInputDate(scanner.next());
            if (isDateValid(input)) {
                importantDate = input;
                break;
            }
        }
    }

    // EFFECT: format yyyyMMdd to yyyy-MM-dd, do nothing if input is not yyyyMMdd
    private String formatInputDate(String input) {
        if (input.matches("\\d{8}")) {
            return input.substring(0, 4) + "-" + input.substring(4, 6)
                    + "-" + input.substring(6);
        }
        return input;
    }

    // EFFECTS: return true if the input Sting is a valid date in the format of yyyy-MM-dd
    private boolean isDateValid(String input) {
        try {
            LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Date is not valid, enter again (Format: yyyy-MM-dd)");
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: ask user for degreeOfImportance
    private void askDegreeOfImportance() {
        String input;

        while (true) {
            System.out.println("Enter any whole number (whichever value makes sense to you): ");
            System.out.print(">>> ");
            input = scanner.next();
            if (input.matches("-?\\d+")) {
                try {
                    degreeOfImportance = Integer.parseInt(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Number too large LOL, change one.");
                }
            } else {
                System.out.println("Not an integer, enter again: ");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: ask user for keywords
    private void askKeywords() {
        String input;

        while (true) {
            System.out.println("Enter a keyword (enter \"ok\" if you are finished): ");
            System.out.print(">>> ");
            input = scanner.next();

            if (input.equals("ok")) {
                break;
            }

            keywords.add(input);
        }
    }

    // Helper methods above
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    // TODO: if time permits, finish below
//    // EFFECTS: remove extra blank space in the input String
//    private String normalizeInput(String input) {
//        return input.replaceAll("\\s+", " ").replaceAll("\\s.", "");
//    }

//    // TODO: find method, if time permits
//    private void findItem() {
//        String input;
//        System.out.println("Enter item name: ");
//        input = scanner.next();
//
//       String result = searchIn(listOfObjects, input);
//
//        System.out.println(result);
//    }
//
//    private String searchIn(ListOfObjects loo, String input) {
//        String itemLocation = "Item path: ";
//        for (Item item: loo) {
//            if (item.getName().equals(input)) {
//                itemLocation += item.getName();
//                return itemLocation;
//            }
//            if (item.getClass().equals(Place.class)) {
//                if (searchIn(((Place) item).getKeptItems(), input) != "Item not found") {
//                    itemLocation += item.getName();
//                    return itemLocation;
//                }
//            }
//        }
//        return "Item not found.";
//    }
}