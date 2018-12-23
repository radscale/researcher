package com.researcher.university.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Message")
public class Message {

    public Message() {
    }

    public Message(String text, Date date, User sender, User receiver) {
        this.text = text;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Text")
    private String text;

    @Column(name = "Date", nullable = false, updatable = false)
    @CreatedDate
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User sender;

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User receiver;


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getSender().hashCode();
        return result;
    }
}
