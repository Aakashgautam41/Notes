package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

@Service
public class ServiceBusSender {

    private final ServiceBusSenderClient senderClient;

    public ServiceBusSender(
            @Value("${spring.cloud.azure.servicebus.connection-string}") String connectionString,
            @Value("${servicebus.queue-name}") String queueName) {
        senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .queueName(queueName)
                .buildClient();
    }

    public void send(String messageBody) {
        ServiceBusMessage message = new ServiceBusMessage(messageBody);
        message.setContentType("application/json");
        senderClient.sendMessage(message);
        System.out.println("Message sent: " + messageBody);
    }

}
