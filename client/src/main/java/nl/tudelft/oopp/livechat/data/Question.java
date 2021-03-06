package nl.tudelft.oopp.livechat.data;

import com.google.gson.annotations.Expose;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class Question implements Comparable<Question> {

    private static List<Question> currentQuestions = new ArrayList<>();
    private static Question currentQuestion;

    @Expose(serialize = false, deserialize = true)
    private long id;

    @Expose(serialize = true, deserialize = true)
    private UUID lectureId;

    @Expose(serialize = true, deserialize = true)
    private Timestamp time;

    @Expose(serialize = false, deserialize = true)
    private int votes;

    @Expose(serialize = true, deserialize = true)
    private String text;

    @Expose(serialize = false, deserialize = true)
    private boolean answered;

    @Expose(serialize = false, deserialize = true)
    private String answerText;

    @Expose(serialize = false, deserialize = true)
    private Timestamp answerTime;

    @Expose(serialize = false, deserialize = true)
    private boolean edited;

    @Expose(serialize = true, deserialize = true)
    private long ownerId;

    @Expose(serialize = true, deserialize = true)
    private String ownerName;

    @Expose(serialize = false, deserialize = true)
    private String status = "new";

    /**
     * Empty constructor to create a question entity.
     */
    public Question() {
    }

    /**
     * Creates a new question entity.
     * @param lectureId the lecture id
     * @param text the text of the question
     * @param ownerId the id of the owner of the question
     */
    public Question(UUID lectureId, String text, long ownerId) {
        this.lectureId = lectureId;
        this.text = text;
        this.ownerId = ownerId;
    }

    /**
     * Gets the id of the question.
     * @return the id of the question
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the id of the question.
     * @param id the id of the question
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the id of the lecture.
     * @return the id of the lecture
     */
    public UUID getLectureId() {
        return this.lectureId;
    }

    /**
     * Sets the time the question was asked/changed.
     * @param time the time the question was asked/changed
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * Gets the time the question was asked.
     * @return the time the question was asked
     */
    public Timestamp getTime() {
        return this.time;
    }

    /**
     * Sets the number of votes for the question.
     * @param votes the number of votes for the question
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * Gets the number of votes for the question.
     * @return the number of votes for the question
     */
    public int getVotes() {
        return this.votes;
    }

    /**
     * Gets the text of the question.
     * @return the text of the question
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the text of the question (e.g after a moderator has edited).
     * @param newText the new text of the question
     */
    public void setText(String newText) {
        this.text = newText;
    }

    /**
     * Sets the question as answered/unanswered.
     * @param answered boolean indicating if question is answered
     */
    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    /**
     * Checks whether the question is (un)answered.
     * @return true if the question is answered, false otherwise
     */
    public boolean isAnswered() {
        return this.answered;
    }

    /**
     * Sets the question as (not) edited.
     * @param edited boolean indicating if question has been edited
     */
    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    /**
     * Checks whether the question is (not) edited.
     * @return true if the question has been edited, false otherwise
     */
    public boolean isEdited() {
        return this.edited;
    }

    /**
     * Gets the text of the answer.
     * @return the text of the answer
     */
    public String getAnswerText() {
        return this.answerText;
    }

    /**
     * Sets the text of the answer.
     * @param answerText the text of the answer
     */
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    /**
     * Gets the answer time of the question.
     * @return the answer time of the question if it is answered, null otherwise
     */
    public Timestamp getAnswerTime() {
        return this.answerTime;
    }

    /**
     * Sets the answer time of the question.
     * @param answerTime the answer time of the question
     */
    public void setAnswerTime(Timestamp answerTime) {
        this.answerTime = answerTime;
    }

    /**
     * Gets the id of the owner of the question.
     * @return the id of the owner of the question.
     */
    public long getOwnerId() {
        return this.ownerId;
    }

    /**
     * Sets the id of the new owner of the question.
     * @param newOwnerId the id of the new owner of the question.
     */
    public void setOwnerId(long newOwnerId) {
        this.ownerId = newOwnerId;
    }

    /**
     * Gets the name of the owner of the question.
     * @return the name of the owner of the question.
     */
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     * Sets the name of the new owner of the question.
     * @param newName the name of the new owner of the question.
     */
    public void setOwnerName(String newName) {
        this.ownerName = newName;
    }

    /**
     * Gets the status of the question.
     * @return the status of the question
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status of the question.
     * @param status the status of the question
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Compares the question to another object.
     * @param o object to compare to
     * @return true iff the other object is also a Question and has the same id. False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Question) {
            Question q = (Question) o;
            return this.id == q.id;
        }
        return false;
    }

    /**
     * The hash code of the Question object.
     * @return the hash code of the Question object
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    /**
     * Converts the question object to String format.
     * @return the question object in String format
     */
    @Override
    public String toString() {
        return "QuestionEntity{"
                + "id=" + id
                + ", lectureId=" + lectureId
                + ", time=" + time
                + ", votes=" + votes
                + ", text='" + text + '\''
                + ", answered=" + answered
                + ", answerText='" + answerText + '\''
                + ", answerTime=" + answerTime
                + ", ownerId=" + ownerId
                + '}';
    }

    /**
     * Compares the current question to another question.
     * @param other the question to compare to
     * @return -1 if current question was asked later
     *          0 if both questions were asked at the same time
     *          1 if current question was asked earlier
     */
    @Override
    public int compareTo(Question other) {
        return other.time.compareTo(this.time);
    }


    /**
     * Gets the list of current questions.
     * @return the current questions
     */
    public static List<Question> getCurrentList() {
        return currentQuestions;
    }

    /**
     * Sets the list of current questions.
     * @param questions the questions
     */
    public static void setCurrentList(List<Question> questions) {
        currentQuestions = questions;
    }

    /**
     * Gets the current question.
     * @return the current question
     */
    public static Question getCurrent() {
        return currentQuestion;
    }

    /**
     * Sets the current question.
     * @param currentQuestion the current question
     */
    public static void setCurrent(Question currentQuestion) {
        Question.currentQuestion = currentQuestion;
    }
}
