package com.findme.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MESSAGES")
public class Message {
    private Long id;
    private String text;
    private Date dateSent;
    private Date dateEdited;
    private Date dateDeleted;
    private Date dateRead;
    private User userFrom;
    private User userTo;

    @Id
    @SequenceGenerator(name = "MESS_SEQ", sequenceName = "SEQUENCE_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESS_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "TEXT")
    public String getText() {
        return text;
    }

    @Column(name = "DATE_SENT")
    public Date getDateSent() {
        return dateSent;
    }

    @Column(name = "DATE_EDITED")
    public Date getDateEdited() {
        return dateEdited;
    }

    @Column(name = "DATE_DELETED")
    public Date getDateDeleted() {
        return dateDeleted;
    }

    @Column(name = "DATE_READ")
    public Date getDateRead() {
        return dateRead;
    }

    @ManyToOne
    @JoinColumn(name = "USER_FROM_ID")
    public User getUserFrom() {
        return userFrom;
    }

    @ManyToOne
    @JoinColumn(name = "USER_TO_ID")
    public User getUserTo() {
        return userTo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }
}
