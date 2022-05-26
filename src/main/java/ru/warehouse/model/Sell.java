package ru.warehouse.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    SellType sellType;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    String documentNumber;

    LocalDate date;

    @ManyToOne
    Warehouse warehouse;

    @ManyToOne
    Client client;

    @ManyToOne
    User employee;

    @Enumerated(EnumType.STRING)
    Status status;

    String address;

    @OneToMany(mappedBy = "sell")
    List<SellItem> sellItems;

    @AllArgsConstructor
    @Getter
    public enum SellType {
        RETAIL("Розница"),
        WHOLESALE("Опт");

        @JsonValue
        String name;
    }

    @AllArgsConstructor
    @Getter
    public enum PaymentType {
        CASH("Наличные"),
        TRANSFER("Эл. платеж");

        @JsonValue
        String name;
    }

    @AllArgsConstructor
    @Getter
    public enum Status {
        SALES("Продано"),
        BOOKING("Бронь");

        @JsonValue
        String name;
    }
}
