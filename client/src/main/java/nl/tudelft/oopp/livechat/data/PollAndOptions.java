package nl.tudelft.oopp.livechat.data;

import java.util.List;
import java.util.Objects;


public class PollAndOptions {
    /**
     * The poll entity.
     */
    private Poll poll;

    /**
     * The list of poll options.
     */
    private List<PollOption> options;

    /**
     * Current poll and its options.
     */
    private static PollAndOptions currentPollAndOptions;


    /**
     * Gets current poll and its options.
     * @return current poll and its options
     */
    public static PollAndOptions getCurrent() {
        return currentPollAndOptions;
    }

    /**
     * Sets current poll and its options.
     * @param currentPollAndOptions  current poll and its options
     */
    public static void setCurrent(PollAndOptions currentPollAndOptions) {
        PollAndOptions.currentPollAndOptions = currentPollAndOptions;
    }

    /**
     * Creates a PollAndQuestion object.
     */
    public PollAndOptions() {
    }

    /**
     * Creates a PollAndQuestion entity with the specified parameters.
     * @param poll the poll entity
     * @param options the list of poll answer options
     */
    public PollAndOptions(Poll poll, List<PollOption> options) {
        this.poll = poll;
        this.options = options;
    }

    /**
     * Gets the poll entity.
     * @return the poll entity
     */
    public Poll getPoll() {
        return poll;
    }

    /**
     * Sets the poll entity.
     * @param poll the poll entity
     */
    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    /**
     * Gets the poll answer options.
     * @return the poll answer options
     */
    public List<PollOption> getOptions() {
        return options;
    }

    /**
     * Sets the poll answer options.
     * @param options the poll answer options
     */
    public void setOptions(List<PollOption> options) {
        this.options = options;
    }

    /**
     * Compares the PollAndOptions object to another object.
     * @param o the other object to compare to
     * @return true iff the other object is also a PollAndOptions object and has the same
     *         poll entity and poll answer options. False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof PollAndOptions) {
            PollAndOptions that = (PollAndOptions) o;
            return this.poll.equals(that.poll) && this.options.equals(that.options);
        }
        return false;
    }

    /**
     * Generates the hash code for the PollAndOptions object.
     * @return the generated hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(poll, options);
    }
}