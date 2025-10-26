package seedu.address.ui;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";

    @FXML
    private ListView<Event> eventListView;
    @FXML
    private HBox studentHeader;
    @FXML
    private Label studentName;

    /**
     * Creates a {@code EventListPanel} with the given {@code ObservableList}.
     */
    public EventListPanel(ObservableList<Event> eventList, ObjectProperty<Event> selectedEvent,
                          ObjectProperty<Person> selectedPerson, ObjectProperty<Boolean> isZoomIn) {
        super(FXML);
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        eventListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        selectedEvent.addListener((obs, old, newEvent) -> {
            eventListView.getSelectionModel().select(newEvent);
        });
        eventListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, newEvent) -> {
                    selectedEvent.setValue(newEvent);
                });

        updateZoomInSelectedPerson(selectedPerson, isZoomIn);
        selectedPerson.addListener((obs, old, newPerson) -> {
            updateZoomInSelectedPerson(selectedPerson, isZoomIn);
        });
        isZoomIn.addListener((obs, old, newZoom) -> {
            updateZoomInSelectedPerson(selectedPerson, isZoomIn);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            }
        }
    }

    /** Updates UI to hide/show the student header which indicates that the events belong to a particular student. */
    private void updateZoomInSelectedPerson(ObjectProperty<Person> selectedPerson, ObjectProperty<Boolean> isZoom) {
        if (isZoom.get() && selectedPerson.get() != null) {
            studentHeader.setManaged(true);
            studentName.setText("Showing Events Of: " + selectedPerson.get().getName().toString());
        } else {
            studentHeader.setManaged(false);
        }
    }
}
