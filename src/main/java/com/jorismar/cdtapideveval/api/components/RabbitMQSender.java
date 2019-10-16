package com.jorismar.cdtapideveval.api.components;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.jorismar.cdtapideveval.api.components.RabbitMQReceiver;
import com.jorismar.cdtapideveval.api.config.RabbitMQConfig;
import com.jorismar.cdtapideveval.api.entities.Lancamento;
import com.jorismar.cdtapideveval.api.form.Proposta;

@Component
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQReceiver receiver;

    public RabbitMQSender(RabbitMQReceiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishMessage(Proposta proposta) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME,
                        RabbitMQConfig.ROUTINE_KEY, proposta);
    }

    public void publishMessage(Lancamento lancamento) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME,
                        RabbitMQConfig.ROUTINE_KEY, lancamento);
    }
}