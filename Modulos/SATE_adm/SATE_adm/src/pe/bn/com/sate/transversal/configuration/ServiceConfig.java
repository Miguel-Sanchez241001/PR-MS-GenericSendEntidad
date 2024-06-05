package pe.bn.com.sate.transversal.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
		"pe.bn.com.sate.infrastructure.service.external.impl",
		"pe.bn.com.sate.infrastructure.service.internal.impl",
		"pe.bn.com.sate.infrastructure.thread",
		"pe.bn.com.sate.infrastructure.facade" })
public class ServiceConfig {

}