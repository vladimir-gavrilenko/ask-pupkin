package gva.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = Answer.TABLE_NAME)
public class Answer implements Serializable {
    private static final long serialVersionUID = -1413389493803293698L;

    public static final String TABLE_NAME = "answers";
    public static final String CONTENT = "content";
    public static final String QUESTION_ID = "question_id";
    public static final String USER_ID = "user_id";
    public static final String IS_CORRECT = "is_correct";
    public static final String TIMESTAMP = "ts";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = CONTENT, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = QUESTION_ID, nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USER_ID, nullable = false)
    private User author;

    @Column(name = IS_CORRECT)
    private Boolean isCorrect = null;

    @Column(name = TIMESTAMP, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp = Calendar.getInstance().getTime();

    public Answer() {
    }

    public Answer(String content, User author, Question question) {
        this.content = content;
        this.author = author;
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.id &&
                Objects.equals(question, answer.question) &&
                Objects.equals(author, answer.author) &&
                Objects.equals(timeStamp, answer.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, author, timeStamp);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", isCorrect=" + isCorrect +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
