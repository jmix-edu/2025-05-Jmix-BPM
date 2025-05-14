package com.company.jmixbpm.security;

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

}