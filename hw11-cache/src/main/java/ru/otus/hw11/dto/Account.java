package ru.otus.hw11.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue
    private Long no;
    private String type;
    private BigDecimal rest;

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getRest() {
        return rest;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
