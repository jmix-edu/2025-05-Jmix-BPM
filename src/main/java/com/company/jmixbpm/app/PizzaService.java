package com.company.jmixbpm.app;

import com.company.jmixbpm.entity.OrderLine;
import org.springframework.stereotype.Component;

@Component
public class PizzaService {

    public long calculateOrder(OrderLine orderLine) {
        long price = orderLine.getPizzaItem().getPrice();
        long quantity = orderLine.getQuantity();

        return price * quantity;
    }
}