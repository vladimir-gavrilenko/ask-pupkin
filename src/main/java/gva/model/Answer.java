package gva.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    @SequenceGenerator(name = "answerSeq", sequenceName = "answers_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answerSeq")
    private Long id;

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
    private LocalDateTime timeStamp = LocalDateTime.now(); // FIXME migrate hibernate session api to jpa

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
                Objects.equals(question.getId(), answer.question.getId()) &&
                Objects.equals(author.getId(), answer.author.getId()) &&
                Objects.equals(timeStamp, answer.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question.getId(), author.getId(), timeStamp);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean isCorrect() {
        return isCorrect != null && isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct == Boolean.TRUE;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @PrePersist
    public void prePersist() {
        timeStamp = LocalDateTime.now();
    }
}
