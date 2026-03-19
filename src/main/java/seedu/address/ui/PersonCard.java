package seedu.address.ui;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label membershipId;
    @FXML
    private Label membershipExpiryDate;
    @FXML
    private Label membershipStatus;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        membershipId.setText("Membership ID: " + person.getMembershipId().value);
        membershipExpiryDate.setText("Membership Expiry: " + person.getMembershipExpiryDate().toString());

        LocalDate expiryDate = person.getMembershipExpiryDate().value;
        LocalDate today = LocalDate.now();
        boolean isActive = expiryDate != null && !expiryDate.isBefore(today);
        membershipStatus.setText(isActive ? "Active" : "Expired");
        String color = isActive ? "#4CAF50" : "#F44336";
        membershipStatus.setStyle(
                "-fx-background-color: " + color + ";"
                + "-fx-text-fill: white;"
                + "-fx-padding: 2 12 2 12;"
                + "-fx-background-radius: 8;"
                + "-fx-font-weight: bold;"
        );
    }
}
