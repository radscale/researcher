//package com.researcher.university.domain;
//
//import org.springframework.data.annotation.CreatedDate;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "Message")
//public class Message {
//
//    public Message() {
//    }
//
//    public Message(String text, Date createdAt, User sender) {
//        this.text = text;
//        this.createdAt = createdAt;
//        this.sender = sender;
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(name = "Text")
//    private String text;
//
//    @Column(name = "CreatedAt", nullable = false, updatable = false)
//    @CreatedDate
//    private Date createdAt;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumns({
//            @JoinColumn(name="SenderId", referencedColumnName="Id"),
//            @JoinColumn(name="ReceiverId", referencedColumnName="Id")
//    })
//    private User sender;
//
//
//    public User getSender() {
//        return sender;
//    }
//
//    public void setSender(User sender) {
//        this.sender = sender;
//    }A
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//
//    @Override
//    public int hashCode() {
//        int result = getId().hashCode();
//        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
//        result = 31 * result + getCreatedAt().hashCode();
//        result = 31 * result + getSender().hashCode();
//        return result;
//    }
//}
