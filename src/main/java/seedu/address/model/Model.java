package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Tag> PREDICATE_SHOW_ALL_TAGS = unused -> true;


    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same identity as {@code clientId} exists in the address book.
     */
    boolean hasClientId(ClientId clientId);

    /**
     * Deletes the person with the matching Client ID and Email and returns the deleted person
     */
    List<Person> deletePersonByClientIds(List<ClientId> clientIds);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    List<Person> setPersonByClientIds(List<ClientId> clientIds, EditPersonDescriptor editedPersonDescriptor);

    /**
     * Returns person with corresponding clientId.
     *
     * @param clientId clientId of client
     * @return client with given clientId
     */
    Person getPerson(ClientId clientId);

    /**
     * Returns true if a tag with the same identity as {@code tagName} exists in the address book.
     */
    boolean hasTagName(String tagName);

    /**
     * Adds the given Tag.
     * {@code Tag} must not already exist in the address book.
     */
    void addTag(Tag tag);

    /**
     * Returns Tag with corresponding tagName.
     *
     * @param tagName tagName of client
     * @return client with given tagName
     */
    Tag getTag(String tagName);

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);


    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the meetings for current user.
     */
    ObservableList<Person> getFilteredNextMeetingList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered person list to filter by current predicate and the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void filterFilteredPersonList(Predicate<Person> predicate);

    /**
     * Sorts the filtered person list to sort by the given {@code sorter}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void sortFilteredPersonList(Comparator<Person> sorter);

    /**
     * Returns an unmodifiable view of the person to view
     */
    ObservableList<Person> getPersonToView();

    /**
     * Checks and returns if there is person to view
     */
    boolean isPersonExistToView();

    /**
     * Returns the name of the person to view
     */
    String getNameOfPersonToView();

    /**
     * Updates the filter of the filtered person to view list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updatePersonToView(Predicate<Person> predicate);

}
