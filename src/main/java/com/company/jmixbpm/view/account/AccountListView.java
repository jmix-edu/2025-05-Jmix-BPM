package com.company.jmixbpm.view.account;

import com.company.jmixbpm.entity.Account;
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

@Route(value = "accounts", layout = MainView.class)
@ViewController(id = "Account.list")
@ViewDescriptor(path = "account-list-view.xml")
@LookupComponent("accountsDataGrid")
@DialogMode(width = "64em")
public class AccountListView extends StandardListView<Account> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Account> accountsDc;

    @ViewComponent
    private InstanceContainer<Account> accountDc;

    @ViewComponent
    private InstanceLoader<Account> accountDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Account> accountsDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        accountsDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("accountsDataGrid.createAction")
    public void onAccountsDataGridCreateAction(final ActionPerformedEvent event) {
        dataContext.clear();
        Account entity = dataContext.create(Account.class);
        accountDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("accountsDataGrid.editAction")
    public void onAccountsDataGridEditAction(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Account item = accountDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        accountsDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        accountDc.setItem(null);
        accountDl.load();
        updateControls(false);
    }

    @Subscribe(id = "accountsDc", target = Target.DATA_CONTAINER)
    public void onAccountsDcItemChange(final InstanceContainer.ItemChangeEvent<Account> event) {
        Account entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            accountDl.setEntityId(entity.getId());
            accountDl.load();
        } else {
            accountDl.setEntityId(null);
            accountDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Account entity) {
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
        accountsDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}