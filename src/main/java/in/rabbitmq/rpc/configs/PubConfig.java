package in.rabbitmq.rpc.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile("pub")
public class PubConfig {

    @Value("${routingKey.request}")
    private String requestRoutingKey;

    @Value("${exchange.direct}")
    private String directExchange;

    @Value("${routingKey.reply}")
    private String replyRoutingKey;

    @Value("${queue.request}")
    private String requestQueue;

    @Value("${queue.reply}")
    private String replyQueue;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchange);
    }

    @Bean
    Queue requestQueue() {
        return QueueBuilder.durable(requestQueue).build();
    }

    @Bean
    Queue replyQueue() {
        return QueueBuilder.durable(replyQueue).build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(requestQueue()).to(exchange()).with(requestRoutingKey);
    }

    /*@Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }*/

    @Bean
    AsyncRabbitTemplate template() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(replyQueue);
        return new AsyncRabbitTemplate(rabbitTemplate, container);
    }

}
