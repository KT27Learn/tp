package seedu.address.model.client;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Client in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final ClientId clientId;
    private final Name name;
    private final Email email;

    // Data fields
    private final Phone phone;
    private final Address address;
    private final RiskAppetite riskAppetite;
    private final DisposableIncome disposableIncome;
    private final CurrentPlan currentPlan;
    private final LastMet lastMet;
    private final NextMeeting nextMeeting;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Client(ClientId clientId, Name name, Phone phone, Email email, Address address, RiskAppetite riskAppetite,
                  DisposableIncome disposableIncome, CurrentPlan currentPlan, LastMet lastMet, NextMeeting nextMeeting,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags, riskAppetite, disposableIncome, currentPlan, lastMet,
            nextMeeting
        );
        this.clientId = clientId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.riskAppetite = riskAppetite;
        this.disposableIncome = disposableIncome;
        this.currentPlan = currentPlan;
        this.lastMet = lastMet;
        this.nextMeeting = nextMeeting;
        addTags(tags);
    }

    /**
     * Assigns {@code tags} to the {@code Client}.
     *
     * @param tags Tags to be added.
     */
    public void addTags(Set<Tag> tags) {
        this.tags.addAll(tags);
        this.tags.forEach(tag -> tag.addClient(this));
    }

    public boolean isSameDayMeeting(LocalDate date) {
        return nextMeeting.isSameDay(date);
    }

    /**
     * Removes lingering references to this client.
     * Invokes this when the client is no longer referenced.
     */
    public void delete() {
        tags.forEach(tag -> {
            tag.removeClient(this);
        });
    }

    /**
     * Returns true if the client has next meeting.
     *
     * @return true if the client has next meeting.
     */
    public boolean hasNextMeeting() {
        return !nextMeeting.isNullMeeting();
    }

    /**
     * Returns true if both clients have the same name.
     * This defines a weaker notion of equality between two clients.
     */
    public boolean isSameClient(Client otherClient) {
        if (otherClient == null) {
            return false;

        }

        if (otherClient == this) {
            return true;
        }

        return otherClient.getName().equals(getName())
            && otherClient.getEmail().equals(getEmail());
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDate getNextMeetingDate() {
        return nextMeeting.getDate();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, lastMet, currentPlan, tags);
    }

    /**
     * Returns true if both clients have the same identity and data fields.
     * This defines a stronger notion of equality between two clients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Client)) {
            return false;
        }

        Client otherClient = (Client) other;
        return otherClient.getClientId().equals(getClientId())
            && otherClient.getName().equals(getName())
            && otherClient.getPhone().equals(getPhone())
            && otherClient.getEmail().equals(getEmail())
            && otherClient.getAddress().equals(getAddress())
            && otherClient.getRiskAppetite().equals(getRiskAppetite())
            && otherClient.getDisposableIncome().equals(getDisposableIncome())
            && otherClient.getCurrentPlan().equals(getCurrentPlan())
            && otherClient.getLastMet().equals(getLastMet())
            && otherClient.getTags().equals(getTags());

    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Client ID: ")
            .append(getClientId())
            .append("; Name: ")
            .append(getName())
            .append("; Email: ")
            .append(getEmail())
            .append("; Phone: ")
            .append(getPhone())
            .append("; Address: ")
            .append(getAddress())
            .append("; Risk Appetite: ")
            .append(getRiskAppetite())
            .append("; Disposable Income: ")
            .append(getDisposableIncome())
            .append("; current plans: ")
            .append(getCurrentPlan())
            .append("; Last Met: ")
            .append(getLastMet())
            .append("; Next Meeting: ")
            .append(getNextMeeting());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

    public ClientId getClientId() {
        return clientId;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public RiskAppetite getRiskAppetite() {
        return riskAppetite;
    }

    public DisposableIncome getDisposableIncome() {
        return disposableIncome;
    }

    public CurrentPlan getCurrentPlan() {
        return currentPlan;
    }

    public LastMet getLastMet() {
        return lastMet;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public NextMeeting getNextMeeting() {
        return nextMeeting;
    }
}
