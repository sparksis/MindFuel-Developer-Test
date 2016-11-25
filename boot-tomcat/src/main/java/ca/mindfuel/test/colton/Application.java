/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.mindfuel.test.colton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ro.isdc.wro.http.ConfigurableWroFilter;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	private static Log logger = LogFactory.getLog(Application.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * WebResourceOptimizer handles combining of the many js files required for
	 * a modern webapp into a few bundles. These bundles are declaratively built
	 * in order to improve code re-use.
	 * 
	 * See Also WEB-INF/wro.properties
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean wroRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();

		registration.setName("WebResourceOptimizer");

		ConfigurableWroFilter filter = new ConfigurableWroFilter();

		try (InputStream in = Application.class.getResourceAsStream("/wro.properties")) {
			Properties props = new Properties();
			props.load(in);
			in.close();
			filter.setProperties(props);
		} catch (IOException io) {
			/**
			 * will cause the startup to crash (hard fail)
			 */
			throw new RuntimeException(io);
		}

		registration.setFilter(filter);
		registration.addUrlPatterns("/wro/*");
		return registration;
	}

	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {
			@Override
			public void contextInitialized(ServletContextEvent sce) {
				logger.info("ServletContext initialized");
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				logger.info("ServletContext destroyed");
			}
		};
	}

}
