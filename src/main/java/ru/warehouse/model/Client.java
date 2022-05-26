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
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    String fullName;

    @Enumerated(EnumType.STRING)
    Type type;

    String industry;

    String address;

    String postAddress;

    String phone;

    String director;

    LocalDate addDate;

    @AllArgsConstructor
    @Getter
    public enum Type {
        UL("Юрлицо"),
        FL("Физлицо");

        @JsonValue
        String name;
    }
}
