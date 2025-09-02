package MainGUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainGUI extends Application {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // 1. Create UI Components
        TableView<Booking> bookingTable = createBookingTable();
        GridPane bookingForm = createBookingForm(bookingTable);

        // 2. Setup Main Layout
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(20));
        rootLayout.setCenter(createTableSection(bookingTable));
        rootLayout.setRight(createFormSection(bookingForm));

        // 3. Configure Scene
        Scene mainScene = new Scene(rootLayout, 1100, 650);
        loadStylesheet(mainScene);

        // 4. Show Window
        primaryStage.setTitle("Therapy Booking System");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // ====================== UI Component Builders ======================
    private TableView<Booking> createBookingTable() {
        TableView<Booking> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Create Columns
        String[] headers = {"ID", "Child", "Therapy Type", "Session Date", "Caregiver", "Contact"};
        String[] properties = {"id", "child", "therapy", "formattedDate", "caregiver", "contact"};

        for (int i = 0; i < headers.length; i++) {
            TableColumn<Booking, String> col = new TableColumn<>(headers[i]);
            col.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            table.getColumns().add(col);
        }

        // Load Data
        table.setItems(loadInitialBookings());
        return table;
    }

    private VBox createTableSection(TableView<Booking> table) {
        VBox tableSection = new VBox(10);
        tableSection.getChildren().addAll(
            new Label("Existing Bookings"),
            table
        );
        return tableSection;
    }

    private VBox createFormSection(GridPane form) {
        VBox formSection = new VBox(10);
        formSection.getChildren().addAll(
            new Label("New Therapy Booking"),
            form
        );
        return formSection;
    }

    private GridPane createBookingForm(TableView<Booking> table) {
        GridPane form = new GridPane();
        form.getStyleClass().add("form-panel");
        form.setVgap(12);
        form.setHgap(12);
        form.setPadding(new Insets(20));

        // Form Components
        TextField childField = new TextField();
        ComboBox<String> therapyCombo = createTherapyComboBox();
        DatePicker datePicker = new DatePicker();
        TextField caregiverField = new TextField();
        TextField contactField = new TextField();

        // Add Components to Form
        int row = 0;
        form.add(createFormLabel("Child's Full Name:"), 0, row);
        form.add(childField, 1, row++);
        
        form.add(createFormLabel("Therapy Type:"), 0, row);
        form.add(therapyCombo, 1, row++);
        
        form.add(createFormLabel("Session Date:"), 0, row);
        form.add(datePicker, 1, row++);
        
        form.add(createFormLabel("Caregiver's Name:"), 0, row);
        form.add(caregiverField, 1, row++);
        
        form.add(createFormLabel("Contact Number:"), 0, row);
        form.add(contactField, 1, row++);

        // Submit Button
        Button submitButton = createSubmitButton();
        submitButton.setOnAction(e -> handleFormSubmission(
            table, childField, therapyCombo, datePicker, caregiverField, contactField));
        
        form.add(submitButton, 0, row, 2, 1);
        return form;
    }

    // ====================== Helper Methods ======================
    private ComboBox<String> createTherapyComboBox() {
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(
            "Speech Therapy",
            "Occupational Therapy",
            "Physical Therapy",
            "Music Therapy",
            "ABA Therapy",
            "Play Therapy",
            "Sensory Integration",
            "Art Therapy",
            "Hydrotherapy",
            "Cognitive Therapy",
            "Dance Therapy",
            "Social Skills Training"
        );
        combo.setPromptText("Select Therapy Type");
        return combo;
    }

    private Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private Button createSubmitButton() {
        Button button = new Button("Book Session");
        button.getStyleClass().add("submit-button");
        button.setPrefWidth(200);
        return button;
    }

    private ObservableList<Booking> loadInitialBookings() {
        return FXCollections.observableArrayList(
            new Booking(1, "Emma Johnson", "Speech Therapy", LocalDate.parse("2025-07-01"), "Sarah Johnson", "555-0101"),
            new Booking(2, "Liam Smith", "Occupational Therapy", LocalDate.parse("2025-07-02"), "Michael Smith", "555-0102"),
            new Booking(3, "Noah Williams", "Physical Therapy", LocalDate.parse("2025-07-03"), "James Williams", "555-0103"),
            new Booking(4, "Olivia Brown", "Music Therapy", LocalDate.parse("2025-07-04"), "Patricia Brown", "555-0104"),
            new Booking(5, "Mason Jones", "ABA Therapy", LocalDate.parse("2025-07-05"), "Robert Jones", "555-0105"),
            new Booking(6, "Ava Garcia", "Play Therapy", LocalDate.parse("2025-07-06"), "Maria Garcia", "555-0106"),
            new Booking(7, "Ethan Miller", "Sensory Integration", LocalDate.parse("2025-07-07"), "David Miller", "555-0107"),
            new Booking(8, "Isabella Davis", "Art Therapy", LocalDate.parse("2025-07-08"), "Linda Davis", "555-0108"),
            new Booking(9, "Logan Rodriguez", "Hydrotherapy", LocalDate.parse("2025-07-09"), "Christopher Rodriguez", "555-0109"),
            new Booking(10, "Sophia Wilson", "Cognitive Therapy", LocalDate.parse("2025-07-10"), "Jessica Wilson", "555-0110"),
            new Booking(11, "Jacob Martinez", "Dance Therapy", LocalDate.parse("2025-07-11"), "Thomas Martinez", "555-0111"),
            new Booking(12, "Mia Anderson", "Social Skills Training", LocalDate.parse("2025-07-12"), "Nancy Anderson", "555-0112")
        );
    }

    private void loadStylesheet(Scene scene) {
        try {
            String cssPath = getClass().getResource("styles.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.err.println("Warning: Could not load stylesheet - " + e.getMessage());
        }
    }

    // ====================== Event Handlers ======================
    private void handleFormSubmission(TableView<Booking> table, TextField childField,
                                   ComboBox<String> therapyCombo, DatePicker datePicker,
                                   TextField caregiverField, TextField contactField) {
        try {
            Booking newBooking = validateAndCreateBooking(
                childField.getText(),
                therapyCombo.getValue(),
                datePicker.getValue(),
                caregiverField.getText(),
                contactField.getText()
            );

            table.getItems().add(newBooking);
            clearForm(childField, therapyCombo, datePicker, caregiverField, contactField);
            showAlert("Success", "Booking added for " + newBooking.getChild());

        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private Booking validateAndCreateBooking(String child, String therapy, LocalDate date,
                               String caregiver, String contact) {
        if (child == null || child.trim().isEmpty()) {
            throw new IllegalArgumentException("Child's name is required");
        }
        if (therapy == null || therapy.trim().isEmpty()) {
            throw new IllegalArgumentException("Therapy type must be selected");
        }
        if (date == null) {
            throw new IllegalArgumentException("Session date is required");
        }
        if (caregiver == null || caregiver.trim().isEmpty()) {
            throw new IllegalArgumentException("Caregiver's name is required");
        }
        if (contact == null || contact.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number is required");
        }

        int newId = getNextBookingId();
        return new Booking(newId, child, therapy, date, caregiver, contact);
    }

    private int getNextBookingId() {
        // In a real app, you'd get this from your database
        return 13; // Next ID after the initial 12
    }

    private void clearForm(TextField childField, ComboBox<String> therapyCombo,
                         DatePicker datePicker, TextField caregiverField,
                         TextField contactField) {
        childField.clear();
        therapyCombo.getSelectionModel().clearSelection();
        datePicker.setValue(null);
        caregiverField.clear();
        contactField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ====================== Data Model ======================
    public static class Booking {
        private final int id;
        private final String child;
        private final String therapy;
        private final LocalDate date;
        private final String caregiver;
        private final String contact;

        public Booking(int id, String child, String therapy, LocalDate date, String caregiver, String contact) {
            this.id = id;
            this.child = child;
            this.therapy = therapy;
            this.date = date;
            this.caregiver = caregiver;
            this.contact = contact;
        }

        // Getters
        public int getId() { return id; }
        public String getChild() { return child; }
        public String getTherapy() { return therapy; }
        public LocalDate getDate() { return date; }
        public String getFormattedDate() { return date.format(DATE_FORMATTER); }
        public String getCaregiver() { return caregiver; }
        public String getContact() { return contact; }
    }
}