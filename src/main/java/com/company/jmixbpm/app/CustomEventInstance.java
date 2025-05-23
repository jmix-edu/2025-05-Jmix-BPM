package com.company.jmixbpm.app;

import org.flowable.eventregistry.api.runtime.EventInstance;
import org.flowable.eventregistry.api.runtime.EventPayloadInstance;

import java.util.Collection;

public class CustomEventInstance implements EventInstance {
    private String key;
    private String name;
    private Collection<EventPayloadInstance> payloadInstances;
    private Collection<EventPayloadInstance> headerInstances;
    private Collection<EventPayloadInstance> correlationParameterInstances;

    @Override
    public String getEventKey() {
        return key;
    }

    @Override
    public Collection<EventPayloadInstance> getPayloadInstances() {
        return payloadInstances;
    }

    @Override
    public Collection<EventPayloadInstance> getHeaderInstances() {
        return headerInstances;
    }

    @Override
    public Collection<EventPayloadInstance> getCorrelationParameterInstances() {
        return correlationParameterInstances;
    }

    @Override
    public String getTenantId() {
        return null;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPayloadInstances(Collection<EventPayloadInstance> payloadInstances) {
        this.payloadInstances = payloadInstances;
    }

    public void setHeaderInstances(Collection<EventPayloadInstance> headerInstances) {
        this.headerInstances = headerInstances;
    }

    public void setCorrelationParameterInstances(Collection<EventPayloadInstance> correlationParameterInstances) {
        this.correlationParameterInstances = correlationParameterInstances;
    }
}
