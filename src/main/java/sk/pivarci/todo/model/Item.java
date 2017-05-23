package sk.pivarci.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    @NotBlank
    private String content;
    @Column
    private boolean done;
    @ManyToOne
    @JoinColumn(name ="user_id")
    @JsonIgnore
    private User user;

    public Item() {}

    public Item(User user, String content, boolean done) {
        this.user = user;
        this.content = content;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isDone() {
        return done;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
