package com.pagos.autorizacion.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String COLA_AUTORIZACIONES = "pagos.autorizaciones";

	@Bean
	public Queue colaAutorizaciones() {
		return new Queue(COLA_AUTORIZACIONES, true);
	}
}
