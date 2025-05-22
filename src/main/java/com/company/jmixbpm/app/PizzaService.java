package com.company.jmixbpm.app;

import com.company.jmixbpm.entity.OrderLine;
import com.company.jmixbpm.entity.PizzaItem;
import io.jmix.core.UnconstrainedDataManager;
import org.springframework.stereotype.Component;

@Component
public class PizzaService {

    private final UnconstrainedDataManager unconstrainedDataManager;

    public PizzaService(UnconstrainedDataManager unconstrainedDataManager) {
        this.unconstrainedDataManager = unconstrainedDataManager;
    }

    public long calculateOrder(OrderLine orderLine) {
        long price = orderLine.getPizzaItem().getPrice();
        long quantity = orderLine.getQuantity();

        return price * quantity;
    }

    public long changePrice(PizzaItem pizzaItem, long newPrice) {
        long oldPrice = pizzaItem.getPrice();
        pizzaItem.setPrice(newPrice);
        unconstrainedDataManager.save(pizzaItem);
        return oldPrice;
    }
}