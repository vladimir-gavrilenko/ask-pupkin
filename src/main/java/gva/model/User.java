package gva.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
@Entity
@Table(name = User.TABLE_NAME)
public class User implements Serializable {
    private static final long serialVersionUID = 8874492553125255655L;

    public static final String TABLE_NAME = "users";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String PASSWORD_HASH = "password_hash";
    public static final String AVATAR_PATH = "avatar_path";

    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private Long id;

    @Column(name = EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = NAME, nullable = false, unique = true)
    private String name;

    @Column(name = PASSWORD_HASH, nullable = false)
    private String passwordHash;

    @Column(name = AVATAR_PATH, nullable = false)
    private String avatarPath;

    @ManyToMany
    @JoinTable(name = "likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> likedQuestions = new HashSet<>();

    public User() {
    }

    public User(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(passwordHash, user.passwordHash) &&
                Objects.equals(avatarPath, user.avatarPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, passwordHash, avatarPath);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Set<Question> getLikedQuestions() {
        return likedQuestions;
    }
}
