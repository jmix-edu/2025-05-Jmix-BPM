package com.company.jmixbpm.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "PIZZA_ORDER", indexes = {
        @Index(name = "IDX_PIZZA_ORDER_INITIATOR", columnList = "INITIATOR_ID"),
        @Index(name = "IDX_PIZZA_ORDER_APPROVER", columnList = "APPROVER_ID")
})
@Entity
public class PizzaOrder {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Composition
    @OneToMany(mappedBy = "pizzaOrder")
    private List<OrderLine> orderLines;

    @JoinColumn(name = "INITIATOR_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User initiator;

    @JoinColumn(name = "APPROVER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User approver;

    @Column(name = "DELIVERY_NUMBER")
    private String deliveryNumber;

    @Column(name = "ORDER_AMOUNT")
    private Long orderAmount;

    @Column(name = "APPROVED", nullable = false)
    @NotNull
    private Boolean approved = false;

    @Column(name = "REJECTED", nullable = false)
    @NotNull
    private Boolean rejected = false;

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public User getApprover() {
        return approver;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}