package com.findme.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "POSTS")
public class Post {
    @Id
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "SEQUENCE_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_POSTED")
    private Date datePosted;

    @Column(name = "LOCATION")
    private String location;

    @ManyToMany(mappedBy = "tagsInPosts")
    private List<User> usersTagged;

    @JoinColumn(name = "USER_POSTED_ID")
    @OneToOne(cascade = CascadeType.ALL)
    private User userPosted;

    @JoinColumn(name = "USER_PAGE_ID")
    @OneToOne(cascade = CascadeType.ALL)
    private User userPagePosted;
}
