package de.nwoehler.unknown.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().registerModule(new MoneyModule());
	}

	@Bean
	public RestOperations restOperations(ObjectMapper objectMapper) {
		return new RestTemplateBuilder()
				.additionalMessageConverters(createMappingJacksonHttpMessageConverter(objectMapper))
				.build();
	}

	private MappingJackson2HttpMessageConverter createMappingJacksonHttpMessageConverter(ObjectMapper objectMapper) {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);
		return converter;
	}

}
