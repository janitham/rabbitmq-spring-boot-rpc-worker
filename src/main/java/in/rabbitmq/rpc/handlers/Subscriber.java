package in.rabbitmq.rpc.handlers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Random;

@Configuration
@Profile("sub")
public class Subscriber {

    @RabbitListener(queues = "${queue.request}")
    public String subscribeToRequestQueue(@Payload String sampleRequestMessage, Message message) {

        System.out.println("Received message :" + message);

        try {
            Thread.sleep(generateRandomInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed processing and sending the message :" + message);

        return sampleRequestMessage;
    }

    private static int generateRandomInt(int upperRange) {
        Random random = new Random();
        return random.nextInt(upperRange);
    }
}
