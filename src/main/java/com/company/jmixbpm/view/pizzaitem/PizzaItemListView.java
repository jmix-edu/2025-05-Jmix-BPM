package com.company.jmixbpm.view.pizzaitem;

import com.company.jmixbpm.entity.PizzaItem;
import com.company.jmixbpm.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.flowui.action.SecuredBaseAction;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.model.InstanceLoader;
import io.jmix.flowui.view.*;

@Route(value = "pizza-items", layout = MainView.class)
@ViewController(id = "PizzaItem.list")
@ViewDescriptor(path = "pizza-item-list-view.xml")
@LookupComponent("pizzaItemsDataGrid")
@DialogMode(width = "64em")
public class PizzaItemListView extends StandardListView<PizzaItem> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<PizzaItem> pizzaItemsDc;

    @ViewComponent
    private InstanceContainer<PizzaItem> pizzaItemDc;

    @ViewComponent
    private InstanceLoader<PizzaItem> pizzaItemDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<PizzaItem> pizzaItemsDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        pizzaItemsDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("pizzaItemsDataGrid.createAction")
    public void onPizzaItemsDataGridCreateAction(final ActionPerformedEvent event) {
        dataContext.clear();
        PizzaItem entity = dataContext.create(PizzaItem.class);
        pizzaItemDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("pizzaItemsDataGrid.editAction")
    public void onPizzaItemsDataGridEditAction(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        PizzaItem item = pizzaItemDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        pizzaItemsDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        pizzaItemDc.setItem(null);
        pizzaItemDl.load();
        updateControls(false);
    }

    @Subscribe(id = "pizzaItemsDc", target = Target.DATA_CONTAINER)
    public void onPizzaItemsDcItemChange(final InstanceContainer.ItemChangeEvent<PizzaItem> event) {
        PizzaItem entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            pizzaItemDl.setEntityId(entity.getId());
            pizzaItemDl.load();
        } else {
            pizzaItemDl.setEntityId(null);
            pizzaItemDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(PizzaItem entity) {
        ViewValidation viewValidation = getViewValidation();
        ValidationErrors validationErrors = viewValidation.validateUiComponents(form);
        if (!validationErrors.isEmpty()) {
            return validationErrors;
        }
        validationErrors.addAll(viewValidation.validateBeanGroup(UiCrossFieldChecks.class, entity));
        return validationErrors;
    }

    private void updateControls(boolean editing) {
        UiComponentUtils.getComponents(form).forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });

        detailActions.setVisible(editing);
        listLayout.setEnabled(!editing);
        pizzaItemsDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}