package com.wgeneric.microservices;

import com.wgeneric.microservices.models.entidades.Caracteristicas;
import com.wgeneric.microservices.repositorios.CaracteristicasRepo;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan({"com.wgeneric.microservices", "com.wgeneric.microservices.services.imp.operaciones"})
public class MicroservicesApplication implements CommandLineRunner {
	@Autowired
	public CaracteristicasRepo caracteristicasRepo;
	public static void main(String[] args) {
		SpringApplication.run(MicroservicesApplication.class, args);
	}
	@Bean
	public GroupedOpenApi myApi() {
		return GroupedOpenApi.builder()
			.group("Multi Servicio")
			.pathsToMatch("/api/**" )
			.build();
	}

	@Override
	public void run(String... args) throws Exception {
		List<Caracteristicas> lsita =  caracteristicasRepo.findAll() ;
		if (lsita.isEmpty() || lsita.size() < 3){
			caracteristicasRepo.save(new Caracteristicas("HEADER"));
			caracteristicasRepo.save(new Caracteristicas("URL"));
			caracteristicasRepo.save(new Caracteristicas("SECURITY"));


		}

	}
}
