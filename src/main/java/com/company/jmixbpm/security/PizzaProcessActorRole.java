package com.company.jmixbpm.security;

import com.company.jmixbpm.entity.OrderLine;
import com.company.jmixbpm.entity.PizzaItem;
import com.company.jmixbpm.entity.PizzaOrder;
import com.company.jmixbpm.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "PizzaProcessActor", code = PizzaProcessActorRole.CODE, scope = "UI")
public interface PizzaProcessActorRole {
    String CODE = "pizza-process-actor";

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @MenuPolicy(menuIds = "User.list")
    @ViewPolicy(viewIds = "User.list")
    void screens();

    @EntityAttributePolicy(entityClass = PizzaItem.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = PizzaItem.class, actions = EntityPolicyAction.READ)
    void pizzaItem();

    @EntityAttributePolicy(entityClass = PizzaOrder.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = PizzaOrder.class, actions = EntityPolicyAction.READ)
    void pizzaOrder();

    @EntityAttributePolicy(entityClass = OrderLine.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = OrderLine.class, actions = EntityPolicyAction.READ)
    void orderLine();
}