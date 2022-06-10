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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Obtaining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    OperationType operationType;

    @CreatedDate
    LocalDate documentDate;

    @ManyToOne
    Warehouse warehouse;

    @ManyToOne
    Supplier supplier;

    @ManyToOne
    User employee;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "obtaining", fetch = FetchType.EAGER)
    List<ObtainingItem> obtainingItems;

    @AllArgsConstructor
    @Getter
    public enum OperationType {
        RETURN("Возврат"),
        ARRIVAL("Приход");

        @JsonValue
        String name;
    }

    @AllArgsConstructor
    @Getter
    public enum PaymentStatus {
        PAYED("Оплачено"),
        WAITING_PAYED("Ожидает оплаты");

        @JsonValue
        String name;
    }
}
