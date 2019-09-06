package in.rabbitmq.rpc.handlers;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;


@Configuration
@Profile("pub")
public class Publisher {

    @Value("${routingKey.request}")
    private String requestRoutingKey;

    @Value("${exchange.direct}")
    private String directExchange;

    @Autowired
    private AsyncRabbitTemplate asyncRabbitTemplate;

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    @Scheduled(fixedDelay = 100 * 10)
    public void publishToDirectExchangeRPCStyle() {
        Integer integer = atomicInteger.getAndIncrement();
        String sampleRequestMessage = String.valueOf(integer);
        System.out.println("Sending out message on direct directExchange:" + integer);

        AsyncRabbitTemplate.RabbitConverterFuture<String> sampleResponseMessageRabbitConverterFuture = asyncRabbitTemplate
                .convertSendAndReceive(directExchange, requestRoutingKey, String.valueOf(integer));

        sampleResponseMessageRabbitConverterFuture.addCallback(
                sampleResponseMessage ->
                        System.out.println("Response for request message:" + sampleRequestMessage + " is:" + sampleResponseMessage)
                , failure ->
                        System.out.println(failure.getMessage())
        );
    }
}
