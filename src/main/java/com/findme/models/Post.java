package com.findme.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "POSTS")
public class Post {
    private Long id;
    private String message;
    private Date datePosted;
    private String location;
    private List<User> usersTagged;
    private User userPosted;
    private User userPagePosted;
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

    @Column(name = "LOCATION")
    public String getLocation() {
        return location;
    }

    @ManyToMany(mappedBy = "tagsInPosts")
    public List<User> getUsersTagged() {
        return usersTagged;
    }

    @JoinColumn(name = "USER_POSTED_ID")
    @OneToOne(cascade = CascadeType.ALL)
    public User getUserPosted() {
        return userPosted;
    }

    @JoinColumn(name = "USER_PAGE_ID")
    @OneToOne(cascade = CascadeType.ALL)
    public User getUserPagePosted() {
        return userPagePosted;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUsersTagged(List<User> usersTagged) {
        this.usersTagged = usersTagged;
    }

    public void setUserPosted(User userPosted) {
        this.userPosted = userPosted;
    }

    public void setUserPagePosted(User userPagePosted) {
        this.userPagePosted = userPagePosted;
    }
}
