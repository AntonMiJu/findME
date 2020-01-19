package com.findme.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "SEQUENCE_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    //TODO from existed data
    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "TOWN")
    private String town;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "DATE_REGISTERED")
    private Date dateRegistered;

    @Column(name = "DATE_LAST_ACTIVE")
    private Date dateLastActive;

    //Todo enum
    @Column(name = "RELATIONSHIP_STATUS")
    private String relationshipStatus;

    @Column(name = "RELIGION")
    private String religion;

    //TODO from existed data
    @Column(name = "SCHOOL")
    private String school;

    @Column(name = "UNIVERSITY")
    private String university;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "TAGS",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "POST_ID")})
    private List<Post> tagsInPosts;

    @OneToMany(mappedBy = "userFrom")
    private List<Message> messagesSent;

    @OneToMany(mappedBy = "userTo")
    private List<Message> messagesReceived;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(country, user.country) &&
                Objects.equals(town, user.town) &&
                Objects.equals(age, user.age) &&
                Objects.equals(dateRegistered, user.dateRegistered) &&
                Objects.equals(dateLastActive, user.dateLastActive) &&
                Objects.equals(relationshipStatus, user.relationshipStatus) &&
                Objects.equals(religion, user.religion) &&
                Objects.equals(school, user.school) &&
                Objects.equals(university, user.university);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phone, email, password, country, town, age, dateRegistered, dateLastActive, relationshipStatus, religion, school, university);
    }
}
