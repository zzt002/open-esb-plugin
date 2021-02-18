package com.dongdl.springboot1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/13 15:12 UTC+8
 * @description
 **/
@Configuration
@Slf4j
public class RabbitMqConfig {

    public static final String APPLICATION_NAME = "esb-plugin-test";

    @Value("${spring.application.name:esb-plugin-null}")
    String applicationName;

    /**
     * 集群项目
     * rabbitMq 需要队列名称区分
     * 不同项目前缀务必不同，以免RabbitMq监听混乱
     */
    @PostConstruct
    public void valid() {
        if (!APPLICATION_NAME.equals(applicationName)) {
            log.error("RabbitMq配置前缀必须相同, {}, {}", APPLICATION_NAME, applicationName);
            System.exit(0);
        }
    }

    @Bean
    public Queue queueReload() {
        return new Queue(APPLICATION_NAME +".queueReload");
    }

    @Bean
    public Queue queueSaveLog() {
        return new Queue(APPLICATION_NAME +".queueSaveLog");
    }

    @Bean
    public Queue queueSynSeq() {
        return new Queue(APPLICATION_NAME +".queueSynSeq");
    }

    @Bean
    public FanoutExchange exchangeReload() {
        return new FanoutExchange(APPLICATION_NAME +".reload");
    }

    @Bean
    public FanoutExchange exchangeSaveLog() {
        return new FanoutExchange(APPLICATION_NAME +".saveLog");
    }

    @Bean
    public FanoutExchange exchangeSynSeq() {
        return new FanoutExchange(APPLICATION_NAME +".synSeq");
    }

//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange("topic.reload");
//    }

    @Bean
    public Binding bindingReload() {
        return BindingBuilder.bind(queueReload()).to(exchangeReload());
    }

    @Bean
    public Binding bindingOpenIpLog() {
        return BindingBuilder.bind(queueSaveLog()).to(exchangeSaveLog());
    }

    @Bean
    public Binding bindingSynSeq() {
        return BindingBuilder.bind(queueSynSeq()).to(exchangeSynSeq());
    }
}
