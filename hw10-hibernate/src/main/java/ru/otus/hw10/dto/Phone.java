package ru.otus.hw10.dto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue
    private Long id;

    private String number;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
