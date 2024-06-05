package pe.bn.com.sate.transversal.configuration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class PersistenceConfig {

	public static final String DATA_SOURCE_SATE = "jdbc/bn_sate";
	public static final String DATA_SOURCE_SARASIGN = "jdbc/bn_sate_sarasign";
	public static final String DATA_SOURCE_SATE_TABLAS = "jdbc/bn_sate_tablas";

	public static final String SQL_SESSION_FACTORY_SATE = "sqlSessionFactory1";
	public static final String SQL_SESSION_FACTORY_SARASIGN = "sqlSessionFactory2";
	public static final String SQL_SESSION_FACTORY_SATE_TABLAS = "sqlSessionFactory3";

	public static final String MAPPERS_PACKAGE_SATE = "pe.bn.com.sate.persistence.mapper.internal";
	public static final String MAPPERS_PACKAGE_SARASIGN = "pe.bn.com.sate.persistence.mapper.external.sarasign";
	public static final String MAPPERS_PACKAGE_SATE_TABLAS = "pe.bn.com.sate.persistence.mapper.external.bntablas";

	@Value("classpath:pe/bn/com/sate/persistence/mapper/internal/*.xml")
	private Resource[] mapperLocationSate;

	@Value("classpath:pe/bn/com/sate/persistence/mapper/external/sarasign/*.xml")
	private Resource[] mapperLocationSarasign;

	@Value("classpath:pe/bn/com/sate/persistence/mapper/external/bntablas/*.xml")
	private Resource[] mapperLocationTablas;

	@Bean
	public DataSource dataSourceSarasign() throws NamingException {
		JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
		dataSource.setExpectedType(DataSource.class);
		dataSource.setJndiName(DATA_SOURCE_SARASIGN);
		dataSource.setLookupOnStartup(true);
		dataSource.setCache(true);
//		dataSource.setProxyInterface(DataSource.class);

		try {
			dataSource.afterPropertiesSet();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return (DataSource) dataSource.getObject();
	}

	@Bean
	public DataSource dataSourceSate() throws NamingException {
		JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
		dataSource.setExpectedType(DataSource.class);
		dataSource.setJndiName(DATA_SOURCE_SATE);
		dataSource.setLookupOnStartup(true);
		dataSource.setCache(true);
//		dataSource.setProxyInterface(DataSource.class);

		try {
			dataSource.afterPropertiesSet();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return (DataSource) dataSource.getObject();
	}

	@Bean
	public DataSource dataSourceSateTablas() throws NamingException {
		JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
		dataSource.setExpectedType(DataSource.class);
		dataSource.setJndiName(DATA_SOURCE_SATE_TABLAS);
		dataSource.setLookupOnStartup(true);
		dataSource.setCache(true);
//		dataSource.setProxyInterface(DataSource.class);

		try {
			dataSource.afterPropertiesSet();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return (DataSource) dataSource.getObject();
	}

	// @Bean(name = "transactionManagerSarasign")
	// @Qualifier(value = "sarasign")
	// public DataSourceTransactionManager transactionManagerSarasign()
	// throws NamingException {
	// return new DataSourceTransactionManager(dataSourceSarasign());
	// // return new DataSourceTransactionManager();
	// }

	@Bean
	// @Qualifier(value = "sate")
	public DataSourceTransactionManager transactionManagerSate()
			throws NamingException {
		return new DataSourceTransactionManager(dataSourceSate());
		// return new DataSourceTransactionManager();
	}

	// @Bean(name = "transactionManagerTablas")
	// @Qualifier(value = "tablas")
	// public DataSourceTransactionManager transactionManagerTablas()
	// throws NamingException {
	// return new DataSourceTransactionManager(dataSourceSateTablas());
	// // return new DataSourceTransactionManager();
	// }

	@Bean(name = SQL_SESSION_FACTORY_SARASIGN)
	public SqlSessionFactory sqlSessionFactorySarasign() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSourceSarasign());
		sessionFactory.setMapperLocations(mapperLocationSarasign);
		sessionFactory
				.setTypeAliasesPackage("pe.bn.com.sate.transversal.dto.external.sarasign");
		SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
		sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
		return sqlSessionFactory;
	}

	@Bean(name = SQL_SESSION_FACTORY_SATE)
	public SqlSessionFactory sqlSessionFactorySate() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSourceSate());
		sessionFactory.setMapperLocations(mapperLocationSate);
		sessionFactory
				.setTypeAliasesPackage("pe.bn.com.sate.transversal.dto.sate");
		SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
		sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
		return sqlSessionFactory;
	}

	@Bean(name = SQL_SESSION_FACTORY_SATE_TABLAS)
	public SqlSessionFactory sqlSessionFactoryTablas() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSourceSateTablas());
		sessionFactory.setMapperLocations(mapperLocationTablas);
		sessionFactory
				.setTypeAliasesPackage("pe.bn.com.sate.transversal.dto.external.bntablas");
		SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
		sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
		return sqlSessionFactory;
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurerSarasign() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage(MAPPERS_PACKAGE_SARASIGN);
		configurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY_SARASIGN);
		return configurer;
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurerSate() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage(MAPPERS_PACKAGE_SATE);
		configurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY_SATE);
		return configurer;
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurerTablas() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage(MAPPERS_PACKAGE_SATE_TABLAS);
		configurer
				.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY_SATE_TABLAS);
		return configurer;
	}

}
