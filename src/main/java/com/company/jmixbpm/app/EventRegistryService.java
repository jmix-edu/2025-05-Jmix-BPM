package com.company.jmixbpm.app;

import com.google.gson.Gson;
import org.flowable.eventregistry.api.EventDefinition;
import org.flowable.eventregistry.api.EventDefinitionQuery;
import org.flowable.eventregistry.api.EventRegistry;
import org.flowable.eventregistry.api.EventRepositoryService;
import org.flowable.eventregistry.api.runtime.EventInstance;
import org.flowable.eventregistry.impl.EventRegistryEngine;
import org.flowable.eventregistry.impl.EventRegistryEngineConfiguration;
import org.flowable.eventregistry.model.ChannelModel;
import org.flowable.eventregistry.model.OutboundChannelModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class EventRegistryService {
    private static final Logger log = LoggerFactory.getLogger(EventRegistryService.class);

    @Autowired
    private EventRegistryEngine eventRegistryEngine;

    @Autowired
    private EventRegistry eventRegistry;

    @Autowired
    private EventRepositoryService eventRepositoryService;

    protected static final String MY_EVENT = """
            {
              "key": "myEvent",
              "name": "My event",
              "correlationParameters": [
                  {
                    "name": "customerId",
                    "type": "string"
                  }
              ],
              "payload": [
                  {
                    "name": "customerName",
                    "type": "string"
                  },
                  {
                    "name": "amount",
                    "type": "integer"
                  }
              ]
            }""";


    public void useEventRegistry() {
        EventDefinitionQuery eventDefinitionQuery = eventRepositoryService.createEventDefinitionQuery();
        List<EventDefinition> eventDefinitions = eventDefinitionQuery.latestVersion().list();
        log.info("Event definitions lisl: {}", eventDefinitions.toString());
    }

    public EventRegistry getEventRegistry() {
        return eventRegistry;
    }

    public void eventRegistryGetConfig() {
        EventRegistryEngineConfiguration configuration = EventRegistryEngineConfiguration.
                createEventRegistryEngineConfigurationFromResourceDefault();

        String engineName = configuration.getEngineName();
        log.info("Engine name: {}", engineName);
    }

    public void sendTestMessage() {
        EventInstance eventInstance = createEventInstance(MY_EVENT); // Create an instance of EventInstance
        Collection<ChannelModel> channelModels = new ArrayList<>(); // Create a collection of ChannelModel

// Add ChannelModel instances to the collection
        OutboundChannelModel channelModel1 = new OutboundChannelModel();
        channelModel1.setName("Channel 1");
        channelModels.add(channelModel1);
//        ChannelModel channelModel2 = new ChannelModel();
//        channelModel2.setName("Channel 2");
//        channelModels.add(channelModel2);

// Call the sendEventOutbound method
        eventRegistry.sendEventOutbound(eventInstance, channelModels);
    }

    public CustomEventInstance createEventInstance(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, CustomEventInstance.class);
    }
}
