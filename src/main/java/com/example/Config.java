package com.example;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
@ComponentScan(basePackages = {"/"})
public class Config {

    @Bean
    public DataSource dataSource() {
		SimpleDriverDataSource sdds = new SimpleDriverDataSource();
		sdds.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		sdds.setUrl("jdbc:mysql://localhost:3306/crud");
		sdds.setUsername("sellpiad");
		sdds.setPassword("k10241024!");
		
		return sdds;
	}
    
}
