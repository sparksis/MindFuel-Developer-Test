package ca.mindfuel.test.colton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String VALIDATE_SCHEMA = "SELECT COUNT(*) AS count FROM information_schema.tables WHERE table_name = 'AUTHORITIES'";

	@Autowired
	private DataSource ds;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll();
		http.formLogin().loginPage("/").loginProcessingUrl("/login");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// FIXME Security is in memory only for JUNIT
		if(System.getProperty("java.class.path").contains("junit")){
			auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN","USER")
			.and().withUser("user").password("user").roles("USER");
			return;
		}
		
		JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer = auth.jdbcAuthentication().dataSource(ds); 
		
		try(Connection c = ds.getConnection();PreparedStatement statement = c.prepareStatement(VALIDATE_SCHEMA)){
			ResultSet rs = statement.executeQuery();
			if(rs.first()&& rs.getInt(1)==0){
				configurer
					.withDefaultSchema()
					.passwordEncoder(passwordEncoder())
					.withUser("admin").password("admin").roles("USER","ADMIN");
			}
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
