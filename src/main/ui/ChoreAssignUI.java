package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import model.*;
import model.exceptions.ChoreException;
import model.exceptions.DuplicatePersonException;
import model.exceptions.PersonNotFoundException;
import persistence.JsonReader;
import persistence.JsonWriter;

//Represents the ChoreAssignUI graphical user interface
//code for persistence implementation based on CPSC210 JsonSerializationDemo
public class ChoreAssignUI extends JFrame {
    private static final String JSON_STORE = "./data/choreassign.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JButton addChoreButton;
    private JButton removeChoreButton;
    private JButton editButton;
    private JTable personChores;
    private JButton addPersonButton;
    private JButton removePersonButton;
    private JButton removeChorePersonButton;
    private JPanel mainPanel;
    private JTable choreTable;
    private JButton loadButton;
    private JButton saveButton;
    private JTable personList;
    private JScrollPane personListPane;
    private JScrollPane personChoresPane;
    private JScrollPane choresPane;
    private JButton assignChoreButton;
    private ChoreAssign ca;

    public ChoreAssignUI() {
        setContentPane(mainPanel);
        setTitle("ChoreAssign");
        setSize(1000, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog el = EventLog.getInstance();
                for (Event ev : el) {
                    System.out.println(ev.toString());
                }
            }
        });
        setVisible(true);

        ca = new ChoreAssign("My Chores");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializePanes();
        initializeButtons();
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons with listeners
    private void initializeButtons() {
        addChoreButton.addActionListener(e -> addChorePopUp());

        removeChoreButton.addActionListener(e -> removeChore());

        removePersonButton.addActionListener(e -> removePerson());

        removeChorePersonButton.addActionListener(e -> {
            Person person = getSelectedPerson();
            if (!person.getChores().isEmpty()) {
                int choreIndex = personChores.getSelectedRow();
                Chore chore = person.getChores().get(choreIndex);
                person.deleteChore(chore);
                chore.unassign();
                updateTables();
            }
        });

        addPersonButton.addActionListener(e -> addPersonPopUp());

        editButton.addActionListener(e -> editChorePopUp());

        assignChoreButton.addActionListener(e -> assignChorePopUp());

        loadButton.addActionListener(e -> loadChoreAssign());

        saveButton.addActionListener(e -> saveChoreAssign());

        removeChorePersonButton.addActionListener(e -> removeChorePerson());
    }

    // MODIFIES: this
    // EFFECTS: initializes table components of the GUI
    private void initializePanes() {
        initChoreTable();
        initPersonList();
        initPersonChores();
    }

    // MODIFIES: this
    // EFFECTS: Initializes chore table
    public void initChoreTable() {
        choresPane.setBorder(BorderFactory.createTitledBorder("Chores"));
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Description");
        model.addColumn("Time Required (minutes)");
        model.addColumn("Interval");
        model.addColumn("Assigned?");
        choreTable.setModel(model);
        choreTable.getSelectionModel().addListSelectionListener(new RowListener());
        choreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // MODIFIES: this
    // EFFECTS: initializes PersonList
    public void initPersonList() {
        personListPane.setBorder(BorderFactory.createTitledBorder("People"));
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Total Time (Weekly)");
        personList.setModel(model);
        personList.getSelectionModel().addListSelectionListener(e -> updatePersonChores());
        personList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // MODIFIES: this
    // EFFECTS: initializes personChores
    public void initPersonChores() {
        personChoresPane.setBorder(BorderFactory.createTitledBorder("Person's Chores"));
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Description");
        model.addColumn("Time Required (minutes)");
        model.addColumn("Interval");
        model.addColumn("Assigned?");
        personChores.setModel(model);
        personChores.getSelectionModel().addListSelectionListener(new RowListener());
        personChores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    // MODIFIES: this, ca
    // EFFECTS: generates dialog window for creating a new chore
    private void addChorePopUp() {
        JTextField name = new JTextField();
        JTextField desc = new JTextField();
        SpinnerModel model = new SpinnerNumberModel(0, 0, 1000, 1);
        JSpinner time = new JSpinner(model);
        JComboBox<Interval> interval = new JComboBox<>(Interval.values());

        Object[] message = {"Name:", name, "Description:", desc, "Time Required (minutes)", time, "Interval", interval
        };

        int option = JOptionPane.showConfirmDialog(
                mainPanel,
                message,
                "Create Chore",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            int timeValue = (Integer) time.getValue();
            Interval intervalValue = (Interval) interval.getSelectedItem();
            Chore chore = new Chore(name.getText(), desc.getText(), timeValue, intervalValue);
            ca.addChore(chore);
            updateTables();
        } else {
            System.out.println("chore creation cancelled");
        }
    }

    // MODIFIES: this, ca
    // EFFECTS: deletes the currently selected chore in choreTable
    private void removeChore() {
        int index = choreTable.getSelectedRow();
        int id = ca.getChores().get(index).getId();
        try {
            ca.deleteChore(id);
            updateTables();
        } catch (ChoreException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this, ca
    // EFFECTS: generates a dialog window to create a new person
    private void addPersonPopUp() {
        JTextField name = new JTextField();

        Object[] message = {
                "Name:", name
        };
        int option = JOptionPane.showConfirmDialog(
                mainPanel,
                message,
                "Create Person",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                ca.addPerson(name.getText());
                updateTables();
            } catch (DuplicatePersonException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("person creation cancelled");
        }
    }

    // MODIFIES: this, ca
    // EFFECTS: deletes currently selected person in personList
    private void removePerson() {
        int index = personList.getSelectedRow();
        Person selected = ca.getPeople().get(index);
        try {
            ca.deletePerson(selected.getName());
            updateTables();
        } catch (PersonNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this, ca, selectedChore
    // EFFECTS: generates dialog window to allow user to edit fields of currently selected chore in choreTable
    private void editChorePopUp() {
        Chore selectedChore = getSelectedChore();
        JTextField name = new JTextField(selectedChore.getName());
        JTextField desc = new JTextField(selectedChore.getDescription());
        SpinnerModel model = new SpinnerNumberModel(selectedChore.getTime(), 0, 1000, 1);
        JSpinner time = new JSpinner(model);
        JComboBox<Interval> interval = new JComboBox<>(Interval.values());

        Object[] message = {"Name:", name, "Description:", desc, "Time Required (minutes)", time, "Interval", interval
        };

        int option = JOptionPane.showConfirmDialog(mainPanel, message, "Edit Chore", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            int timeValue = (Integer) time.getValue();
            Interval intervalValue = (Interval) interval.getSelectedItem();
            selectedChore.setName(name.getText());
            selectedChore.setDescription(desc.getText());
            selectedChore.setTime(timeValue);
            selectedChore.setInterval(intervalValue);
            updateTables();
        } else {
            System.out.println("chore editing cancelled");
        }
    }

    // MODIFIES: this, ca
    // EFFECTS: generates dialog window to allow user to assign chore to person
    private void assignChorePopUp() {
        Chore selectedChore = getSelectedChore();
        JComboBox<String> names = new JComboBox<>(getPeopleNames());
        Object[] message = {"Assigning chore: ", selectedChore.getName(),
                "Select name of person to assign this chore to:", names
        };
        int option = JOptionPane.showConfirmDialog(mainPanel, message, "Assign Chore",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = (String) names.getSelectedItem();
            Person p;
            try {
                p = ca.getPerson(name);
                ca.assignChore(selectedChore.getId(), p.getName());
                updateTables();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("chore assignment cancelled");
        }
    }

    // EFFECTS: returns the Chore corresponding to the selected row in choreTable
    private Chore getSelectedChore() {
        int selectedRow = choreTable.getSelectedRow();
        if (selectedRow >= 0) {
            return ca.getChores().get(selectedRow);
        } else {
            return null;
        }
    }

    // MODIFIES: this, selectedPerson, selectedChore
    // EFFECTS: removes currently selected chore in personChores from person's chores
    private void removeChorePerson() {
        int personIndex = personList.getSelectedRow();
        int choreIndex = personChores.getSelectedRow();
        Person selectedPerson = ca.getPeople().get(personIndex);
        Chore selectedChore = selectedPerson.getChores().get(choreIndex);
        if (personIndex >= 0 || choreIndex >= 0) {
            selectedPerson.deleteChore(selectedChore);
            selectedChore.unassign();
            updateTables();
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes choreTable
    private void updateChoreTable() {
        List<Chore> chores = ca.getChores();
        DefaultTableModel model = (DefaultTableModel) choreTable.getModel();
        model.setRowCount(0);
        for (Chore c : chores) {
            model.addRow(new Object[]{c.getId(),
                    c.getName(),
                    c.getDescription(),
                    c.getTime(),
                    c.getInterval(),
                    c.getIsAssigned()});
        }
        choreTable.setModel(model);
    }

    // MODIFIES: this
    // EFFECTS: refreshes personList
    private void updatePersonList() {
        List<Person> people = ca.getPeople();
        DefaultTableModel model = (DefaultTableModel) personList.getModel();
        model.setRowCount(0);
        for (Person p : people) {
            model.addRow(new Object[]{p.getName(), p.getTotalTimeWeekly()});
        }
        personList.setModel(model);
    }

    // MODIFIES: this
    // EFFECTS: refreshes personChores
    private void updatePersonChores() {
        DefaultTableModel model = (DefaultTableModel) personChores.getModel();
        Person selectedPerson = getSelectedPerson();
        if (selectedPerson != null) {
            model.setRowCount(0);
            List<Chore> chores = selectedPerson.getChores();
            for (Chore c : chores) {
                model.addRow(new Object[]{c.getId(),
                        c.getName(),
                        c.getDescription(),
                        c.getTime(),
                        c.getInterval(),
                        c.getIsAssigned()});
                personChores.setModel(model);
            }
        }
    }

    // EFFECTS: returns the Person corresponding to the selected row in personList
    private Person getSelectedPerson() {
        int selectedRow = personList.getSelectedRow();
        if (selectedRow >= 0) {
            return ca.getPeople().get(selectedRow);
        } else {
            return null;
        }
    }

    // EFFECTS: updates all tables and prints chores to console
    private void updateTables() {
        updateChoreTable();
        updatePersonList();
        updatePersonChores();
    }

    // MODIFIES: this
    // EFFECTS: loads ChoreAssign from a file
    private void loadChoreAssign() {
        try {
            ca = jsonReader.read();
            System.out.println("Loaded " + ca.getName() + " from " + JSON_STORE);
            updateTables();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves ChoreAssign to a file
    private void saveChoreAssign() {
        try {
            jsonWriter.open();
            jsonWriter.write(ca);
            jsonWriter.close();
            System.out.println("Saved " + ca.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to write to file: " + JSON_STORE);
            JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: returns a vector of the names of all people in ca
    private String[] getPeopleNames() {
        List<Person> people = ca.getPeople();
        String[] names = new String[people.size()];
        for (int i = 0; i < names.length; i++) {
            String name = people.get(i).getName();
            names[i] = name;
        }
        return names;
    }

    // EFFECTS: runs the ChoreAssignUI
    public static void main(String[] args) {
        new SplashScreen();
        new ChoreAssignUI();
    }

    // Represents a listener for row selections in JList or JTable
    private class RowListener implements ListSelectionListener {

        // EFFECTS: listens for changes to selected row
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
        }
    }
}


