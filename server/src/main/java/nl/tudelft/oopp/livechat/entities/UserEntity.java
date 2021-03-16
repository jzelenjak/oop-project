package nl.tudelft.oopp.livechat.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The type User entity.
 */
@Entity
@Table(name = "user")
public class UserEntity {

    /**
     * The Uid.
     */
    @Id
    @Column(name = "id")
    long uid;

    /**
     * The Username.
     */
    @Column(name = "userName")
    String userName;

    /**
     * The Last question timestamp.
     */
    @Column(name = "last_question")
    Timestamp lastQuestion;

    /**
     * Allowed to ask questions.
     */
    @Column(name = "allowed")
    boolean allowed = true;

    /**
     * Instantiates a new User entity.
     */
    public UserEntity() {
    }

    /**
     * Instantiates a new User entity.
     *
     * @param uid          the uid
     * @param username     the username
     * @param lastQuestion the last question
     * @param allowed      the allowed
     */
    public UserEntity(long uid, String username, Timestamp lastQuestion, boolean allowed) {
        this.uid = uid;
        this.userName = username;
        this.lastQuestion = lastQuestion;
        this.allowed = allowed;
    }

    /**
     * Gets uid.
     *
     * @return the uid
     */
    public long getUid() {
        return uid;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets last question.
     *
     * @return the last question
     */
    public Timestamp getLastQuestion() {
        return lastQuestion;
    }

    /**
     * Is allowed boolean.
     *
     * @return the boolean
     */
    public boolean isAllowed() {
        return allowed;
    }

    /**
     * Sets uid.
     *
     * @param uid the uid
     */
    public void setUid(long uid) {
        this.uid = uid;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUserName(String username) {
        this.userName = username;
    }

    /**
     * Sets last question.
     *
     * @param lastQuestion the last question
     */
    public void setLastQuestion(Timestamp lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    /**
     * Sets allowed.
     *
     * @param allowed the allowed
     */
    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Override
    public int hashCode() {
        return (int) (uid >> 32) + (int) uid;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UserEntity) {
            UserEntity u = (UserEntity) obj;
            return this.uid == u.uid;
        }
        return false;
    }

}
