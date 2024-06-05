package pe.bn.com.sate.transversal.configuration.init;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "pe.bn.com.sate.transversal.configuration",
		"pe.bn.com.sate.transversal.util.anotaciones" })
public class ApplicationContextInitializer  {

}
