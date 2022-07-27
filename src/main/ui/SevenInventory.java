package ui;

import model.Item;
import model.Place;
import model.ListOfObjects;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

// SevenInventory application
public class SevenInventory {
    // Next phase:
    // TODO: finish search method: look up items, using LevenshteinDistance
    // TODO: finish getTimelinee method.
    // TODO: finish add get all items: print out all items in all places
    private ListOfObjects listOfObjects;
    private LinkedList<Place> pathOfPlaces;
    private Place currentPlace = null;
    private Place previousPlace = null;
    private Item currentItem = null;
    private Scanner scanner;
    private String currentName = null;
    private String currentImportantDate = null; //if time sensitive. e.g. effective date, expiry date...
    private int currentDegreeOfImportance = 0; // if important, how important it is
    private ArrayList<String> currentKeywords = null; // related to the item for easy lookup

    // EFFECTS: runs the 7-Inventory application. Some code from TellerApp.java
    public SevenInventory() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        String input;

        init();

        do {
            System.out.println("\nEnter \"I like the project\" to continue: ");
            input = scanner.nextLine().toLowerCase().replaceAll("\\s+", "");
            // TODO:  if time permits, normalize string: remove extra space in the middle
        } while (!input.equals("iliketheproject"));

        while (true) {
            if (listOfObjects.isEmpty()) {
                // when Top Level has no place
                displayEmptyMenu();
            } else if (currentPlace == null) {
                // meaning  we are at the Top Level
                displayTopLevelMenu();
            } else {
                displayPlaceMenu();
            }

            input = scanner.next().toLowerCase();

            if (input.equals("q")) {
                System.out.println("Program quit.");
                break;
            }

            processInput(input);
        }
    }


    // EFFECTS: display a menu when listOfPlaces is empty
    public void displayEmptyMenu() {
        System.out.println("We are at Top Level, no available places. Select from: ");
        System.out.println("1. Create a place (enter \"p\")");
        System.out.println("2. Quit (enter\"q\")");
    }

    // EFFECTS: display a menu when currentPlace is null meaning we are at the top level
    public void displayTopLevelMenu() {
        System.out.println("We are at Top Level. Available Places: ");
        System.out.println(listOfObjects.getCurrentAll());
        System.out.println("\nSelect from: ");
//        System.out.println("1. Find an item (enter \"f\")");
        System.out.println("1. Create a place (enter \"p\")");
        System.out.println("2. Access a place (enter \"acc\")");
        System.out.println("3. Get a timeline of all important dates (enter \"t\")");
        System.out.println("4. Quit (enter\"q\")");
    }

    // EFFECTS: display a menu when currentPlace is a Place meaning we are inside a place
    public void displayPlaceMenu() {
        System.out.println("\nWe are in \"" + currentPlace.getName() + "\": ");
        System.out.print("(" + currentPlace.getCreatedDate() + ")");
        String impDate = currentPlace.getImportantDate();
        if (!impDate.equals("7777-07-17")) {
            System.out.println("Important date: " + impDate);
        }
        int doi = currentPlace.getDegreeOfImportance();
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
        System.out.println("5. Go back to last place (enter \"l\")");
        System.out.println("6. Go back to Top Level menu (enter \"b\")");
        System.out.println("7. Quit (enter\"q\")");
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
        } else if (input.matches("l.*")) {
            goBackToLastPlace();
        } else if (input.matches("t.*")) {
            getTimeline();
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

    // MODIFIES: this
    // EFFECTS: access a place or item
    private void access() {
        System.out.println("Enter name of the place/item you want to access: ");
        listItems();
        String input;
        while (true) {
            input = scanner.next();
            if (currentPlace == null) {
                for (Item i: listOfObjects) {
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

    // EFFECTS: list items in a place if within a place, list all places if in Top Level
    private void listItems() {
        if (currentPlace == null) {
            System.out.println("\nWe are at Top Level. Available places:");
            System.out.println(listOfObjects.getCurrentAll().trim());
            // TODO: not get everything, simply print out what's in listOfObjects
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
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: delete or exit the item
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

    // EFFECTS: print try again if the name is not matched by any item
    private void tryAgain() {
        if (currentPlace == null) {
            System.out.println("Wrong name, try again: ");
            System.out.println("Places available: \n" + listOfObjects.getCurrentAll().trim());
        } else {
            System.out.println("Wrong name, try again: ");
            System.out.println("Objects available: \n" + currentPlace.getKeptItems().getCurrentAll().trim());
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

    /// MODIFIES: this
    // EFFECTS: remove current place and go back to last place
    private void removePlace() {
        if (previousPlace == null) {
            // when we are at a place of Top Level
            listOfObjects.remove(currentPlace);
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
    // EFFECTS: initialize listOfPlaces and scanner
    private void init() {
        pathOfPlaces = new LinkedList<>();
        listOfObjects = new ListOfObjects();
        scanner = new Scanner(System.in);
    }

//    // EFFECTS: remove extra blank space in the input String
//    private String normalizeInput(String input) {
//        return input.replaceAll("\\s+", " ").replaceAll("\\s.", "");
//    }


//    TODO: timeline
    private void getTimeline() {
        System.out.println("Important dates in all places: ");
        System.out.println(listOfObjects.getEveryTimeline() + "\n");
    }

    // TODO: find method, if time permits
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

    // MODIFIES: this
    // EFFECTS: create a place and add it to currentPlace
    private void createPlace() {
        System.out.println("\nName the place: ");
        currentName = scanner.next(); // TODO: normalize string

        askItemInfo();
        Place place = new Place(currentName, currentImportantDate, currentDegreeOfImportance, currentKeywords);
        // add place to the current place, and change the current place to the place just created
        if (currentPlace == null) {
            listOfObjects.add(place);
        } else {
            currentPlace.add(place);
        }
    }

    // MODIFIES: this
    // EFFECTS: create an item and add it to current listOfObjects
    private void createItem() {
        System.out.println("\nName the item: ");
        currentName = scanner.next();    // TODO: normalize string if time permits

        askItemInfo();

        Item item = new Item(currentName, currentImportantDate, currentDegreeOfImportance, currentKeywords);

        assert currentPlace != null;
        currentPlace.add(item);
    }

    // MODIFIES: this
    // EFFECTS: ask user the input information of an item
    private void askItemInfo() {
        String input;
        currentImportantDate = "7777-07-17"; // set default
        currentDegreeOfImportance = -777;
        currentKeywords = new ArrayList<>();


        while (true) {
            System.out.println("\nAny extra information you want to add/change?");
            System.out.println("Important date (date)");
            System.out.println("Degree of importance (doi)");
            System.out.println("Keywords (key)");
            System.out.println("Enter \"ok\" if done");

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
            input = formatInputDate(scanner.next());
            if (isDateValid(input)) {
                currentImportantDate = input;
                break;
            }
        }
    }

    // EFFECT: format yyyyMMdd to yyyy-MMd-dd, don't format if input is not yyyyMMdd
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
            input = scanner.next();
            if (input.matches("-?\\d+")) {
                try {
                    currentDegreeOfImportance = Integer.parseInt(input);
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
            System.out.println("Enter a keyword (enter \"q\" is you are finished): ");
            input = scanner.next();

            if (input.equals("q")) {
                break;
            }

            currentKeywords.add(input);
        }
    }
}