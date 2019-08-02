package com.findme.models;

import javax.persistence.*;

@Entity
@Table(name = "RELATIONSHIPS")
public class Relationship {
    private User userFromId;
    private User userToId;
    private RelationshipStatus status;

    public Relationship() {
    }

    public Relationship(User userFromId, User userToId, RelationshipStatus status) {
        this.userFromId = userFromId;
        this.userToId = userToId;
        this.status = status;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "USER_FROM_ID")
    public User getUserFromId() {
        return userFromId;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "USER_TO_ID")
    public User getUserToId() {
        return userToId;
    }

    @Column(name = "STATUS")
    public RelationshipStatus getStatus() {
        return status;
    }

    public void setUserFromId(User userFromId) {
        this.userFromId = userFromId;
    }

    public void setUserToId(User userToId) {
        this.userToId = userToId;
    }

    public void setStatus(RelationshipStatus status) {
        this.status = status;
    }
}
