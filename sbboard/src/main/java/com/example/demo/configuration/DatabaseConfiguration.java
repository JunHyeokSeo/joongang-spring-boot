package com.example.demo.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")          
public class DatabaseConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;

	//DB 연결 풀 관련 설정 객체를 반환
	//@ConfigurationProperties(prefix = "spring.datasource.hikari"): application.properties 파일에 정의된 속성들 중 spring.datasource.hikari 프리픽스로 시작하는 속성들을 자동으로 해당 HikariConfig 객체에 매핑한다는 의미
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	//myBatis를 설정하는 Bean 반환
	//@ConfigurationProperties 어노테이션을 사용하여 mybatis.configuration 프리픽스로 시작하는 설정값들을 바인딩한다
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {
		return new org.apache.ibatis.session.Configuration();
	}

	//데이터베이스 연결 풀을 제공하는 DataSource 빈을 생성한다
	//hikariCP를 이용하 데이터소스를 설정하고 반환한다
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		return dataSource;
	}

	//MyBatis의 SqlSessionFactory 빈을 생성한다
	//데이터 소스와 MyBatis를 설정하고, 매퍼 파일 위치 및 DTO 클래스의 패키지 등을 설정한다
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(
			applicationContext.getResources("classpath:/mapper/*.xml"));
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		sqlSessionFactoryBean.setTypeAliasesPackage("com.example.demo.model");
		return sqlSessionFactoryBean.getObject();
	}
	//SqlSessionTemplate 빈을 생성한다.
	//SqlSessionTemplate은 MyBatis의 SqlSession을 감싼 Wrapper로, 데이터베이스와의 상호작용을 단순화하는 데 사용된다.
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
