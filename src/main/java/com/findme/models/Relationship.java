package com.findme.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RELATIONSHIPS")
public class Relationship implements Serializable {
    private User userFromId;
    private User userToId;
    private RelationshipStatus status;
    private Date startOfRelationships;

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

    @Column(name = "START_OF_RLT")
    public Date getStartOfRelationships() {
        return startOfRelationships;
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

    public void setStartOfRelationships(Date startOfRelationships) {
        this.startOfRelationships = startOfRelationships;
    }
}
