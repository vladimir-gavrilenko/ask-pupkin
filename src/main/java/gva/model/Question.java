package gva.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = Question.TABLE_NAME)
public class Question implements Serializable {
    private static final long serialVersionUID = 6789816587828570037L;

    public static final String TABLE_NAME = "questions";
    public static final String HEADER = "header";
    public static final String CONTENT = "content";
    public static final String USER_ID = "user_id";
    public static final String RATING = "rating";
    public static final String TIMESTAMP = "ts";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = HEADER, nullable = false)
    private String header;

    @Column(name = CONTENT)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USER_ID, nullable = false)
    private User author;

    @Column(name = RATING, nullable = false)
    private int rating;

    @Column(name = TIMESTAMP, nullable = false)
    private LocalDateTime timeStamp;

    @ManyToMany(mappedBy = "likedQuestions")
    private Set<User> likedBy = new HashSet<>();

    public Question() {
    }

    public Question(String header, String content, User author) {
        this.header = header;
        this.content = content;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id &&
                Objects.equals(header, question.header) &&
                Objects.equals(timeStamp, question.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header, timeStamp);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", author_name=" + (author != null ? author.getName() : null) +
                ", rating=" + rating +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public void addLikeBy(User user) {
        likedBy.add(user);
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    @PrePersist
    public void prePersist() {
        timeStamp = LocalDateTime.now();
    }
}
