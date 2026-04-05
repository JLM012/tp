package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

        updateStatusLabel();
        setupAutoStatusUpdate();
    }

    /**
     * Updates the membership status label based on the expiry date.
     * Sets the label text to "Active" (green) if expiry date is more than 30 days away,
     * "Expiring in X days" (amber) if expiry is <= 30 days,
     * "Expires today!" (amber) if expiry date is today,
     * otherwise sets it to "Expired" (red).
     */
    private void updateStatusLabel() {
        LocalDate expiryDate = person.getMembershipExpiryDate().value;
        LocalDate today = LocalDate.now();

        if (expiryDate == null || expiryDate.isBefore(today)) {
            // Expired
            membershipStatus.setText("Expired");
            membershipStatus.getStyleClass().removeAll("membership-active", "membership-expiring-soon",
                    "membership-expired");
            membershipStatus.getStyleClass().add("membership-expired");
        } else {
            // Calculate days remaining
            long daysUntilExpiry = ChronoUnit.DAYS.between(today, expiryDate);

            if (daysUntilExpiry == 0) {
                // Expires today
                membershipStatus.setText("Expires today!");
                membershipStatus.getStyleClass().removeAll("membership-active", "membership-expiring-soon",
                        "membership-expired");
                membershipStatus.getStyleClass().add("membership-expiring-soon");

            } else if (daysUntilExpiry <= 30) {
                // Expiring soon (1–30 days)
                membershipStatus.setText("Expiring in (" + daysUntilExpiry + ") days");
                membershipStatus.getStyleClass().removeAll("membership-active", "membership-expiring-soon",
                        "membership-expired");
                membershipStatus.getStyleClass().add("membership-expiring-soon");

            } else {
                // Active (>30 days)
                membershipStatus.setText("Active");
                membershipStatus.getStyleClass().removeAll("membership-active", "membership-expiring-soon",
                        "membership-expired");
                membershipStatus.getStyleClass().add("membership-active");
            }
        }
    }

    /**
     * Sets up automatic daily updates for the membership status label.
     * Schedules the updateStatusLabel task to run at midnight and every subsequent day.
     */
    private void setupAutoStatusUpdate() {
        setupAutoDailyTask(this::updateStatusLabel);
    }

    /**
     * Schedules a given task to run once at the next midnight, then daily thereafter.
     * @param task The Runnable task to execute daily.
     */
    private void setupAutoDailyTask(Runnable task) {
        long initialDelay = millisUntilNextMidnight();

        Timeline firstRun = new Timeline(
            new KeyFrame(
                javafx.util.Duration.millis(initialDelay),
                e -> {
                    task.run();
                    startDailyLoop(task);
                }
            )
        );
        firstRun.setCycleCount(1);
        firstRun.play();
    }

    /**
     * Starts a daily loop that executes the given task every 24 hours.
     * @param task The Runnable task to execute daily.
     */
    private void startDailyLoop(Runnable task) {
        Timeline daily = new Timeline(
            new KeyFrame(
                javafx.util.Duration.hours(24),
                e -> task.run()
            )
        );
        daily.setCycleCount(Timeline.INDEFINITE);
        daily.play();
    }

    /**
     * Calculates the number of milliseconds until the next midnight.
     * @return Milliseconds until next midnight.
     */
    private long millisUntilNextMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMidnight = now.toLocalDate().plusDays(1).atStartOfDay();
        return java.time.Duration.between(now, nextMidnight).toMillis();
    }
}
