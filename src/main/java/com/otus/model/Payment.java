package com.otus.model;


import javax.persistence.*;
import java.time.LocalDateTime;
@Entity(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "local_date_time", insertable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
