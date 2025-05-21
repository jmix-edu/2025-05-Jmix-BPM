package com.company.jmixbpm.view.startpizzaprocessform;


import com.company.jmixbpm.entity.User;
import com.company.jmixbpm.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.OutputVariable;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ProcessForm(outputVariables = {
        @OutputVariable(name = "approver", type = User.class),
        @OutputVariable(name = "message", type = String.class),
        @OutputVariable(name = "pizzaEater", type = User.class)
})
@Route(value = "start-pizza-process-form", layout = MainView.class)
@ViewController(id = "StartPizzaProcessForm")
@ViewDescriptor(path = "start-pizza-process-form.xml")
public class StartPizzaProcessForm extends StandardView {

    @Autowired
    private ProcessFormContext processFormContext;

    @ProcessVariable(name = "message")
    @ViewComponent
    private TypedTextField<String> messageField;

    @ProcessVariable(name = "pizzaEater")
    @ViewComponent
    private EntityComboBox<User> pizzaEaterField;

    @ProcessVariable(name = "approver")
    @ViewComponent
    private EntityComboBox<User> approverField;

    @Autowired
    private ViewValidation viewValidation;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private CollectionContainer<User> eatersDc;

    @Subscribe(id = "startProcessBtn", subject = "clickListener")
    protected void onStartProcessBtnClick(ClickEvent<JmixButton> event) {
        ValidationErrors errors = viewValidation.validateUiComponents(getContent());

        if (!errors.isEmpty()) {
            viewValidation.showValidationErrors(errors);
            viewValidation.focusProblemComponent(errors);
            return;
        }

        processFormContext.processStarting()
                .saveInjectedProcessVariables()
                .start();
        closeWithDefaultAction();
    }

}