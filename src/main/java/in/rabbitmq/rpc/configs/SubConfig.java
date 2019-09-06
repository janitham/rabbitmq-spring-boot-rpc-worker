package in.rabbitmq.rpc.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile("sub")
public class SubConfig {

    @Value("${queue.request}")
    private String requestQueue;

    @Bean
    Queue requestQueue() {
        return QueueBuilder.durable(requestQueue ).build();
    }

    /*@Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }*/
}
