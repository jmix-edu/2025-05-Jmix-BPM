package com.company.jmixbpm.view.pizzaorder;

import com.company.jmixbpm.entity.PizzaOrder;
import com.company.jmixbpm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "pizza-orders", layout = MainView.class)
@ViewController(id = "PizzaOrder.list")
@ViewDescriptor(path = "pizza-order-list-view.xml")
@LookupComponent("pizzaOrdersDataGrid")
@DialogMode(width = "64em")
public class PizzaOrderListView extends StandardListView<PizzaOrder> {
}