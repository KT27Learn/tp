package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.NextMeeting;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final SortedList<Person> sortedPersons;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> filteredNextMeetings;
    private final FilteredList<Person> personToView;
    private final FilteredList<Tag> filteredTags;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        sortedPersons = new SortedList<>(this.addressBook.getPersonList());
        filteredPersons = new FilteredList<>(sortedPersons);
        filteredNextMeetings = new SortedList<>(checkAllNextMeetings(this.addressBook.getPersonList()));
        filteredTags = new FilteredList<>(this.addressBook.getTagList());
        personToView = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasClientId(ClientId clientId) {
        requireNonNull(clientId);
        return addressBook.hasClientId(clientId);
    }

    @Override
    public List<Person> deletePersonByClientIds(List<ClientId> clientIds) {
        return addressBook.deletePersonByClientIds(clientIds);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public List<Person> setPersonByClientIds(List<ClientId> clientIds, EditPersonDescriptor editedPersonDescriptor) {
        requireAllNonNull(clientIds, editedPersonDescriptor);

        return addressBook.setPersonByClientIds(clientIds, editedPersonDescriptor);
    }

    @Override
    public Person getPerson(ClientId clientId) {
        requireNonNull(clientId);
        return addressBook.getPerson(clientId);
    }

    @Override
    public boolean hasTagName(String tagName) {
        requireNonNull(tagName);
        return addressBook.hasTagName(tagName);
    }

    @Override
    public void addTag(Tag tag) {
        addressBook.addTag(tag);
        updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
    }

    @Override
    public Tag getTag(String tagName) {
        requireNonNull(tagName);
        return addressBook.getTag(tagName);
    }

    // TODO: divider here
    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    public ObservableList<Person> getFilteredNextMeetingList() {
        return filteredNextMeetings;
    }

    private SortedList<Person> checkAllNextMeetings(ObservableList<Person> filteredPersons) {

        SortedList<Person> sortedMeetings = new SortedList<>(filteredPersons);
        //Create comparator for next meeting

        sortedMeetings.setComparator((currentPerson, nextPerson) -> {
            NextMeeting currentMeeting = currentPerson.getNextMeeting();
            NextMeeting nextMeeting = nextPerson.getNextMeeting();
            return currentMeeting.date.compareTo(nextMeeting.date) != 0
                ? currentMeeting.date.compareTo(nextMeeting.date)
                    : (currentMeeting.startTime.compareTo(nextMeeting.startTime) != 0
                        ? currentMeeting.startTime.compareTo(nextMeeting.startTime)
                            : (currentMeeting.endTime.compareTo(nextMeeting.endTime)));
        });
        return sortedMeetings;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void filterFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        Predicate<? super Person> currentPredicate = filteredPersons.getPredicate();
        if (currentPredicate == null) {
            currentPredicate = PREDICATE_SHOW_ALL_PERSONS;
        }
        filteredPersons.setPredicate(predicate.and(currentPredicate));
    }

    @Override
    public void sortFilteredPersonList(Comparator<Person> sorter) {
        sortedPersons.setComparator(sorter);
    }

    //=========== Person To View List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getPersonToView() {
        return personToView;
    }

    @Override
    public boolean isPersonExistToView() {
        return personToView.size() == 1;
    }

    @Override
    public String getNameOfPersonToView() {
        return personToView.get(0).getName().toString();
    }

    @Override
    public void updatePersonToView(Predicate<Person> predicate) {
        requireNonNull(predicate);
        personToView.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredPersons.equals(other.filteredPersons)
            && filteredTags.equals(other.filteredTags)
            && personToView.equals(other.personToView);
    }
}
