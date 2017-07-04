package gva.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
    private static final long serialVersionUID = 6789816587828570037L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "header", nullable = false)
    private String header;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "ts", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp = Calendar.getInstance().getTime();

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
                Objects.equals(author, question.author) &&
                Objects.equals(timeStamp, question.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header, author, timeStamp);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", author_name=" + author.getName() +
                ", rating=" + rating +
                ", timeStamp=" + timeStamp +
                '}';
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }
}
