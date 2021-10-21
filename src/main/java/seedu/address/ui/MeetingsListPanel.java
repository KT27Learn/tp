package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;


/**
 * Panel containing the list of upcoming meetings.
 */
public class MeetingsListPanel extends UiPart<Region> {
    private static final String FXML = "MeetingsListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MeetingsListPanel.class);

    @FXML
    private ListView<Person> meetingsListView;

    /**
     * Creates a {@code MeetingsListPanel} with the given {@code ObservableList}.
     */
    public MeetingsListPanel(ObservableList<Person> personList) {
        super(FXML);
        this.meetingsListView.setItems(personList);
        this.meetingsListView.setCellFactory(listView -> new MeetingsListPanel.MeetingsListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code NextMeeting} using a {@code NextMeetingCard}.
     */
    class MeetingsListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new NextMeetingCard(person.getNextMeeting()).getRoot());
            }
        }
    }

}
