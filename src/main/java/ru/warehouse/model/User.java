package ru.warehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String login;

    @JsonIgnore
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    String name;

    String phone;

    String position;

    String address;

    @AllArgsConstructor
    @Getter
    public enum Role {
        ADMIN("Администратор"),
        EMPLOYEE("Сотрудник"),
        GUEST("Гость");

        @JsonValue
        String name;
    }
}
