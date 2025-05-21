package com.company.jmixbpm.view.choosepizzaform;


import com.company.jmixbpm.entity.PizzaItem;
import com.company.jmixbpm.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.OutputVariable;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ProcessForm(outcomes = {
        @Outcome(id = "selected"),
        @Outcome(id = "not_hungry")
}, outputVariables = {
        @OutputVariable(name = "message", type = String.class),
        @OutputVariable(name = "pizzaItem", type = PizzaItem.class),
        @OutputVariable(name = "quantity", type = Long.class),
        @OutputVariable(name = "specialRequirements", type = String.class)
})
@Route(value = "choose-pizza-form", layout = MainView.class)
@ViewController(id = "ChoosePizzaForm")
@ViewDescriptor(path = "choose-pizza-form.xml")
@DialogMode(width = "40em")
public class ChoosePizzaForm extends StandardView {

    @Autowired
    private ProcessFormContext processFormContext;

    @ProcessVariable(name = "message")
    @ViewComponent
    private TypedTextField<String> messageField;

    @ProcessVariable(name = "pizzaItem")
    @ViewComponent
    private EntityPicker<PizzaItem> pizzaItemField;

    @ProcessVariable(name = "quantity")
    @ViewComponent
    private TypedTextField<Long> quantityField;

    @ProcessVariable(name = "specialRequirements")
    @ViewComponent
    private TypedTextField<String> specialRequirementsField;
    @Autowired
    private ViewValidation viewValidation;

    @Subscribe(id = "selectedBtn", subject = "clickListener")
    protected void onSelectedBtnClick(ClickEvent<JmixButton> event) {

        ValidationErrors errors = viewValidation.validateUiComponents(getContent());

        if (!errors.isEmpty()) {
            viewValidation.showValidationErrors(errors);
            viewValidation.focusProblemComponent(errors);
            return;
        }

        processFormContext.taskCompletion()
                .withOutcome("selected")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe(id = "not_hungryBtn", subject = "clickListener")
    protected void onNot_hungryBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("not_hungry")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }
}