package com.findme.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POSTS")
public class Post {
    private Long id;
    private String message;
    private Date datePosted;
    private User userPosted;
    //TODO
    //permissions
    //comments

    @Id
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "SEQUENCE_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    @Column(name = "DATE_POSTED")
    public Date getDatePosted() {
        return datePosted;
    }

    @JoinColumn(name = "USER_ID")
    @OneToOne(cascade = CascadeType.ALL)
    public User getUserPosted() {
        return userPosted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setUserPosted(User userPosted) {
        this.userPosted = userPosted;
    }
}
