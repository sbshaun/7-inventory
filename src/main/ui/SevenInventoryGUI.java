package ui;

import model.*;
import model.Event;
import persistence.JsonWriter;
import persistence.ListOfObjectsJsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;

public class SevenInventoryGUI extends JFrame {
    private static final String JSON_STORE = "./data/sevenInventory.json";
    private JsonWriter jsonWriter;
    private ListOfObjectsJsonReader jsonReader;

    private ListOfObjects topLevel;
    private Place place;
    private Item item;
    private LinkedList<Place> pathOfPlaces;
    private Place currentPlace = null; // whenever we access a place, set the place as current
    private Place previousPlace = null; // when we access a place inside a place, set current place to previous
    private String name = null;
    private String importantDate = null;
    private int degreeOfImportance = 0;
    private ArrayList<String> keywords = null;

    // above copied from SevenInventory
    ////////////////////////////////
    private JFrame frame;


    private JPanel mainPanel;

    private JButton createNewPlaceButton;

    private JLabel currentPlaceLabel;
    private JButton createNewItemButton;
    private JTextField itemNameTextField;
    private JButton accessButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton goBackButton;
    private JList availableItemsList;
    private JLabel availableItemsLabel;
    private JButton timeLineButton;
    private JButton loadButton;
    private JButton resetButton;
    private JButton backToTopLevelButton;

    // TODO: Display a picture
    // TODO: Display a picture
    // TODO: Display a picture
    public SevenInventoryGUI() throws IOException {
        startApp();
        showImage();
        setupFrame();
        init();
        loadSevenInventory();
        initMainPanel();
        EventLog.getInstance().clear();
    }

    // EFFECTS: validate it to start the application
    private void startApp() {
        String input;
        do {
            input = JOptionPane.showInputDialog("Enter \"I like the project to start\"");
        } while (!input.toLowerCase().replaceAll("\\s+", "").matches("ilike.*"));

    }

    // MODIFIES: this
    // EFFECTS:
    private void showImage() throws IOException {
        BufferedImage image = ImageIO.read(new File("data/icons8-sheep-100.png"));
        JLabel picLabel = new JLabel(new ImageIcon(image));
        JOptionPane.showMessageDialog(null, picLabel, "display a picture", JOptionPane.PLAIN_MESSAGE, null);
    }

    // MODIFIES: this
    // EFFECTS: basic frame configuration
    private void setupFrame() {
        frame = new JFrame("7-Inventory");
        createNewItemButton.setVisible(false);
        frame.setPreferredSize(new Dimension(707, 357));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Do operation when exit
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                printLog();
                saveSevenInventory();
                System.exit(0);
            }
        });

        setPanel(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // display the frame to center position of a screen
        frame.setVisible(true);
    }

    // EFFECTS: print out logged events on console
    private void printLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize fields
    private void init() {
        previousPlace = null;
        currentPlace = null;
        pathOfPlaces = new LinkedList<>();
        topLevel = new ListOfObjects();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new ListOfObjectsJsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initialize mainPanel
    private void initMainPanel() {
        setupButtons();
        displayAvailablePlaces(); // display places in topLevel
        setupItemNameTextField();
        configureAvailableItemsList();
    }

    // MODIFIES: this
    // EFFECTS: setup buttons
    private void setupButtons() {
        setCreateNewPlaceButton();
        setCreateNewItemButton();
        setTimeLineButton();
        setAccessButton();
        setGoBackButton();
        setDeleteButton();
        setSaveButton();
        setLoadButton();
        setResetButton();
        setBackToTopLevelButton();
    }

    // MODIFIES: this
    // EFFECTS: create a new Place
    private void setCreateNewPlaceButton() {
        createNewPlaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = JOptionPane.showInputDialog("Enter name of the place: ");
                while (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid name", "Invalid name", JOptionPane.ERROR_MESSAGE);
                    name = JOptionPane.showInputDialog("Enter name of the place: ");
                }
                askImportantDate();
                askDegreeOfImportance();
                askKeywords();
                place = new Place(name, importantDate, degreeOfImportance, keywords);

                if (currentPlace == null) {
                    topLevel.add(place);
                    displayAvailablePlaces();
                } else {
                    currentPlace.add(place);
                    updatePlacePanel();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: create a new Place
    private void setCreateNewItemButton() {
        createNewItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = JOptionPane.showInputDialog("Enter name of the item: ");
                while (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid name", "Invalid name", JOptionPane.ERROR_MESSAGE);
                    name = JOptionPane.showInputDialog("Enter name of the item: ");
                }
                askImportantDate();
                askDegreeOfImportance();
                askKeywords();
                item = new Item(name, importantDate, degreeOfImportance, keywords);
                assert currentPlace != null;
                currentPlace.add(item);
                updatePlacePanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: setup timelineButton to pop up a window to show imp dates
    private void setTimeLineButton() {
        timeLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, topLevel.getEveryTimeline());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: access a place or item when  button is clicked
    private void setAccessButton() {
        accessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = itemNameTextField.getText();
                access(name);
                itemNameTextField.setText("   Enter the place/item name");
                frame.requestFocusInWindow();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: access a place/item when double click
    private void configureAvailableItemsList() {
        availableItemsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if ((evt.getClickCount() == 2) || (evt.getClickCount() == 3)) {
                    // Double-click detected
                    name = (String) availableItemsList.getSelectedValue();
                    access(name);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: go back to last place
    private void setGoBackButton() {
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assert currentPlace != null;
                goBackToLastPlace();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: delete currentPlace
    private void setDeleteButton() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePlace();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: setup saveButton to save file when clicked
    private void setSaveButton() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSevenInventory();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: setup loadButton to load file when clicked
    private void setLoadButton() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog.setDefaultLookAndFeelDecorated(true);

                String confirmString = "Confirm: Load the file from last time, "
                        + "\n       you may lost all unsaved changes.";

                int response = JOptionPane.showConfirmDialog(null, confirmString, "Load File Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "Load File cancelled.");
                } else if (response == JOptionPane.YES_OPTION) {
                    loadSevenInventory();
                    JOptionPane.showMessageDialog(null, "File loaded from last time");
                    displayTopLevelPanel();
                } else if (response == JOptionPane.CLOSED_OPTION) {
                    JOptionPane.showMessageDialog(null, "Load File cancelled.");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: setup resetButton to reset file when clicked
    private void setResetButton() {
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog.setDefaultLookAndFeelDecorated(true);

                String confirmString = "Confirm: reset the file to empty, \n       you'll lost all unsaved data.";

                int response = JOptionPane.showConfirmDialog(null, confirmString, "Reset File Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "File is not reset.");
                } else if (response == JOptionPane.YES_OPTION) {
                    topLevel.clear();
                    JOptionPane.showMessageDialog(null, "File is reset, you can still restore "
                            + "the data from last time by clicking \"Load File\"");
                    displayTopLevelPanel();
                } else if (response == JOptionPane.CLOSED_OPTION) {
                    JOptionPane.showMessageDialog(null, "File is not reset.");
                }
            }
        });

    }

    // MODIFIES: this
    // EFFECTS: go back to topLevel panel
    private void setBackToTopLevelButton() {
        backToTopLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTopLevelPanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: show hint text on itemNameTextField
    private void setupItemNameTextField() {
        itemNameTextField.setText("   Enter the place/item name");
        itemNameTextField.setFont(new Font("Tahoma", Font.ITALIC, 11));

        itemNameTextField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (itemNameTextField.getText().equals("   Enter the place/item name")) {
                    itemNameTextField.setText("");
                } else {
                    itemNameTextField.setText(itemNameTextField.getText());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (itemNameTextField.getText().equals("   Enter the place/item name")
                        || itemNameTextField.getText().length() == 0) {
                    itemNameTextField.setText("   Enter the place/item name");
                    itemNameTextField.setForeground(Color.GRAY);
                } else {
                    itemNameTextField.setText(itemNameTextField.getText());
                    itemNameTextField.setForeground(Color.BLACK);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: display Panel on frame
    private void setPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.validate();
    }

    // MODIFIES: this
    // EFFECTS: access a place or item
    private void access(String name) {
        if (currentPlace == null) {
            for (Item i: topLevel) {
                if (name.equals(i.getName())) {
                    currentPlace = (Place) i;
                    updatePlacePanel();
                    displayPlaceInfoWindow(currentPlace);
                    // JOptionPane.showMessageDialog(null, "You accessed place: \"" + name + "\"");
                    return;
                }
            }
        }
        if (!setItem(name)) {
            JOptionPane.showMessageDialog(null, "Item not found", "Invalid input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: set currentPlace if input name is a place, set currentItem if specified an item name
    private boolean setItem(String name) {
        if (currentPlace == null) {
            return false;
        } else if (currentPlace.getKeptItems().isEmpty()) {
            return false;
        }
        for (Item i: currentPlace.getKeptItems()) {
            if (name.equals(i.getName())) {
                if (i.getClass().equals(Place.class)) {
                    // if we are not in Top Level, we copy the currentPlace to previousPlace and set new currentPlace
                    if (previousPlace != null) {
                        pathOfPlaces.add(previousPlace); // add previous place to path
                    }
                    previousPlace = currentPlace;
                    currentPlace = (Place) i;
                    updatePlacePanel();
                    displayPlaceInfoWindow(currentPlace);
                    //
                    // JOptionPane.showMessageDialog(null, "You accessed: \"" + name + "\"");
                } else {
                    // if the input item is type Item, simply set currentItem
                    displayItemInfoWindow(i);
                }
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: pop up a window display item info and option to delete
    private void displayPlaceInfoWindow(Place place) {
        String info = getItemInfoString(place);
        JOptionPane.showMessageDialog(null, info);
    }

    // MODIFIES: this
    // EFFECTS: pop up a window display item info and option to delete
    private void displayItemInfoWindow(Item i) {
        JDialog.setDefaultLookAndFeelDecorated(true);


        String info = getItemInfoString(i) + "\n\nDo you want to delete the item?";

        int response = JOptionPane.showConfirmDialog(null, info, i.getName() + " Information",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//        if (response == JOptionPane.NO_OPTION) {
//            // Do nothing
//            // JOptionPane.showMessageDialog(null, "We are back to: \"" + currentPlace.getName() + "\"");
//        } else
        if (response == JOptionPane.YES_OPTION) {
            String deleted = i.getName();
            currentPlace.remove(i);
            updatePlacePanel();
            JOptionPane.showMessageDialog(null, "You deleted: \"" + deleted + "\"");
        }
//        else if (response == JOptionPane.CLOSED_OPTION) {
//            // Do nothing
//            // JOptionPane.showMessageDialog(null, "We are back to: \"" + currentPlace.getName() + "\"");
//        }
    }

    // EFFECTS: return info of an item as a String
    private String getItemInfoString(Item item) {
        String name = item.getName();
        String impDate = item.getImportantDate();
        int doi = item.getDegreeOfImportance();
        String createdDate = item.getCreatedDate().toString();
        ArrayList<String> keywords = item.getKeywords();
        String itemInfo = "Item: " + name;
        itemInfo += "\nCreated on: " + createdDate;

        if (!impDate.equals("7777-07-17")) {
            itemInfo += "\nImportant date: " + impDate;
        }
        if (doi != -777) {
            itemInfo += "\nDegree of importance: " + doi;
        }
        if (!keywords.isEmpty()) {
            itemInfo += "\nKeywords: " + concatKeywords(keywords);
        }

        return itemInfo;
    }

    // EFFECTS: concatenate all keywords and return a String
    private String concatKeywords(ArrayList<String> keywords) {
        String keys = "";
        for  (String keyword: keywords) {
            keys += keyword + ", ";
        }
        return keys.substring(0, keys.length() - 2) + ".";
    }

    // MODIFIES: this
    // EFFECTS: display place panel according to the given place
    private void updatePlacePanel() {
        String name = currentPlace.getName();
        createNewItemButton.setVisible(true);
        timeLineButton.setVisible(false);
        currentPlaceLabel.setText("We are at Place: \"" + name + "\"");
        displayAvailableItems();
    }

    // REQUIRES: currentPlace != null
    // MODIFIES: this
    // EFFECTS: display an array of available Items in the current place
    private void displayAvailableItems() {
        ListOfObjects availableItems = currentPlace.getKeptItems();
        setAvailableItemsList(availableItems);
    }

    // MODIFIES: this
    // EFFECTS: display an array of available Items in topLevel
    private void displayAvailablePlaces() {
        setAvailableItemsList(topLevel);
    }

    // MODIFIES: this
    // EFFECTS: set availableItemsList to display the given ListOfObjects
    private void setAvailableItemsList(ListOfObjects listOfObjects) {
        DefaultListModel model = new DefaultListModel();

        if (listOfObjects.isEmpty()) {
            availableItemsList.setModel(model);
            return;
        } else {
            Item item;
            for (int i = 0; i < listOfObjects.size(); i++) {
                item = listOfObjects.get(i);
                model.add(i, item.getName());
            }
        }
        availableItemsList.setModel(model);
    }

    /// MODIFIES: this
    // EFFECTS: delete current place and go back to last place
    private void deletePlace() {
        if (currentPlace == null) {
            JOptionPane.showMessageDialog(null, "We are at \"Top Level\", to delete a place, first access it.");
            return;
        }
        String deleted = currentPlace.getName();
        if (previousPlace == null) {
            // when we are at a place of Top Level
            topLevel.remove(currentPlace);
            JOptionPane.showMessageDialog(null, "You deleted: \"" + deleted + "\"");
            goBackToLastPlace();
        } else {
            // when we are in a place inside some other place
            try {
                previousPlace.remove(currentPlace);
                JOptionPane.showMessageDialog(null, "You deleted: \"" + deleted + "\"");
                goBackToLastPlace();
                // make previousPlace to placePath, ArrayList<Place>, keeping track of Place accessed,
            } catch (NullPointerException e) {
                System.out.println("NullPointerException"); // stub
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: display top level panel
    private void displayTopLevelPanel() {
        pathOfPlaces.clear();
        previousPlace = null;
        currentPlace = null;
        setAvailableItemsList(topLevel);
        createNewItemButton.setVisible(false);
        timeLineButton.setVisible(true);
        currentPlaceLabel.setText("We are at \"Top Level\"");
        // JOptionPane.showMessageDialog(null, "We are back to: \"Top Level\"");
    }

    // MODIFIES: this
    // EFFECTS: go back to last place
    private void goBackToLastPlace() {
        if (previousPlace == null) {
            displayTopLevelPanel();
        } else {
            currentPlace = previousPlace; // go back to previousPlace from currentPlace
            previousPlace = pathOfPlaces.pollLast(); // get the previousPlace before the current previousPlace
            updatePlacePanel();
            // JOptionPane.showMessageDialog(null, "We are back to: \"" + currentPlace.getName() + "\"");
        }
    }

    // MODIFIES: this
    // EFFECTS: get a valid degreeOfImportance input
    private void askDegreeOfImportance() {
        degreeOfImportance = -777;

        while (true) {
            String input = null;
            try {
                input = JOptionPane.showInputDialog("(Optional) Enter degree of importance: ");
                degreeOfImportance = Integer.parseInt(input);
                return;
            } catch (NumberFormatException nfe) {
                assert input != null;
                if (input.matches("^$")) {
                    return;
                }
                JOptionPane.showMessageDialog(null, "Invalid Number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: ask ImportantDate until get a valid one
    private void askImportantDate() {
        importantDate = "7777-07-17";

        while (true) {
            String input = formatInputDate(JOptionPane.showInputDialog("(Optional) Enter a important date "
                    + "(yyyy-MM-dd): "));

            if (isDateValid(input)) {
                importantDate = input;
                return;
            }

            if (input.matches("^$")) {
                return;
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Invalid Date",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    // EFFECT: format yyyyMMdd to yyyy-MMd-dd, do nothing if input is not yyyyMMdd
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
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: ask user for keywords
    private void askKeywords() {
        keywords = new ArrayList<>();

        String keyword = JOptionPane.showInputDialog("(Optional) Enter a keyword:");
        if (keyword.equals("")) {
            return;
        } else {
            keywords.add(keyword);
        }

        while (true) {
            keyword = JOptionPane.showInputDialog("(Optional) Continue to enter more keywords: \n"
                    + "(Enter \"ok\" to finish)");

            if (keyword.matches("(^$|ok)")) {
                break;
            }

            keywords.add(keyword);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the workroom to file
    private void saveSevenInventory() {
        try {
            jsonWriter.open();
            jsonWriter.writeTopLevel(topLevel);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Everything is saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadSevenInventory() {
        try {
            topLevel = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded everything from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}