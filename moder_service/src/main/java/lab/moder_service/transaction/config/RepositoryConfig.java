package lab.moder_service.transaction.config;

//@Configuration
//@EnableJpaRepositories(
//        basePackages = {
//                "blps.lab.article.repository",
//                "blps.lab.moderation.repository",
//                "blps.lab.security.repository"
//        },
//        entityManagerFactoryRef = "myEntityManager"
//)
public class RepositoryConfig {
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Bean(initMethod = "init", destroyMethod = "close")
//    public AtomikosDataSourceBean dataSource() {
//        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
//        dataSource.setUniqueResourceName("resourceName");
//        dataSource.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
//
//        String connectionString = url.substring("jdbc:postgresql://".length());
//        Properties xaProperties = getProperties(connectionString);
//        dataSource.setXaProperties(xaProperties);
//        dataSource.setPoolSize(10);
//        return dataSource;
//    }
//
//    private Properties getProperties(String connectionString) {
//        String[] parts = connectionString.split("/");
//        String serverAndPort = parts[0];
//        String[] serverPortParts = serverAndPort.split(":");
//        String serverName = serverPortParts[0];
//        String portNumber = serverPortParts.length > 1 ? serverPortParts[1] : "5432";
//        String databaseName = parts[1];
//        Properties xaProperties = new Properties();
//        xaProperties.put("user", username);
//        xaProperties.put("password", password);
//        xaProperties.put("serverName", serverName);
//        xaProperties.put("portNumber", portNumber);
//        xaProperties.put("databaseName", databaseName);
//        return xaProperties;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean myEntityManager() {
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//
//        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
//        entityManagerFactory.setPackagesToScan("blps.lab.*");
//        entityManagerFactory.setDataSource(dataSource());
//        Properties jpaProperties = new Properties();
//        jpaProperties.setProperty("jakarta.persistence.transactionType", "JTA");
//        entityManagerFactory.setJpaProperties(jpaProperties);
//
//        return entityManagerFactory;
//    }
}
