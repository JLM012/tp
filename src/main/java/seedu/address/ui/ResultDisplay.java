package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    /**
     * Constructs a ResultDisplay and sets up the TextArea for displaying feedback.
     */
    public ResultDisplay() {
        super(FXML);
        resultDisplay.setWrapText(true);
    }

    /**
     * Sets the feedback message to the user in the result display box.
     *
     * @param feedbackToUser The feedback message to display.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

}
