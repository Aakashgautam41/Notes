package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;

@Service
public class ServiceBusReceiver {

    private final ServiceBusProcessorClient processorClient;

    public ServiceBusReceiver(
            @Value("${spring.cloud.azure.servicebus.connection-string}") String connectionString,
            @Value("${servicebus.queue-name}") String queueName) {
        processorClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .processor()
                .queueName(queueName)
                .processMessage(context -> {
                    System.out.println("Message Received: " + context.getMessage().getBody());
                    context.complete();
                })
                .processError(context -> System.err.println("Message Processing Error: " + context.getException()))
                .buildProcessorClient();

        processorClient.start();
    }
}
