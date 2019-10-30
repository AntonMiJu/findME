package com.findme.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "RELATIONSHIPS")
public class Relationship implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "USER_FROM_ID")
    private User userFromId;

    @Id
    @OneToOne
    @JoinColumn(name = "USER_TO_ID")
    private User userToId;

    @Column(name = "STATUS")
    private RelationshipStatus status;

    @Column(name = "START_OF_RLT")
    private Date startOfRelationships;
}
