package com.company.jmixbpm.view.pizzaorder;

import com.company.jmixbpm.entity.PizzaOrder;
import com.company.jmixbpm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.view.*;

@Route(value = "pizza-orders/:id", layout = MainView.class)
@ViewController(id = "PizzaOrder.detail")
@ViewDescriptor(path = "pizza-order-detail-view.xml")
@EditedEntityContainer("pizzaOrderDc")
public class PizzaOrderDetailView extends StandardDetailView<PizzaOrder> {
    @ViewComponent
    private JmixCheckbox approvedField;
    @ViewComponent
    private JmixCheckbox rejectedField;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        approvedField.setRequiredIndicatorVisible(false);
        rejectedField.setRequiredIndicatorVisible(false);
    }
    
    
}