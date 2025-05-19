package com.company.jmixbpm.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "ORDER_LINE", indexes = {
        @Index(name = "IDX_ORDER_LINE_PIZZA_ITEM", columnList = "PIZZA_ITEM_ID"),
        @Index(name = "IDX_ORDER_LINE_PIZZA_EATER", columnList = "PIZZA_EATER_ID"),
        @Index(name = "IDX_ORDER_LINE_PIZZA_ORDER", columnList = "PIZZA_ORDER_ID")
})
@Entity
public class OrderLine {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "PIZZA_ITEM_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private PizzaItem pizzaItem;

    @Column(name = "SPECICIAL_REQUIREMENTS")
    private String specicialRequirements;

    @JoinColumn(name = "PIZZA_EATER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User pizzaEater;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "PIZZA_ORDER_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private PizzaOrder pizzaOrder;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private OffsetDateTime createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private OffsetDateTime lastModifiedDate;

    public OffsetDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public PizzaOrder getPizzaOrder() {
        return pizzaOrder;
    }

    public void setPizzaOrder(PizzaOrder pizzaOrder) {
        this.pizzaOrder = pizzaOrder;
    }

    public User getPizzaEater() {
        return pizzaEater;
    }

    public void setPizzaEater(User pizzaEater) {
        this.pizzaEater = pizzaEater;
    }

    public String getSpecicialRequirements() {
        return specicialRequirements;
    }

    public void setSpecicialRequirements(String specicialRequirements) {
        this.specicialRequirements = specicialRequirements;
    }

    public PizzaItem getPizzaItem() {
        return pizzaItem;
    }

    public void setPizzaItem(PizzaItem pizzaItem) {
        this.pizzaItem = pizzaItem;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}