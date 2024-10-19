package blps.lab.transaction.config;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;


@Configuration
@EnableTransactionManagement
public class TransactionManagerConfig {

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driverClassName}")
    private String driverName;

    @Bean(name = "bitronixTransactionManager")
    public BitronixTransactionManager bitronixTransactionManager() throws Throwable {
        BitronixTransactionManager bitronixTransactionManager =
                TransactionManagerServices.getTransactionManager();
        bitronixTransactionManager.setTransactionTimeout(10000);
        return bitronixTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({"bitronixTransactionManager"})
    public PlatformTransactionManager transactionManager(TransactionManager bitronixTransactionManager) {
        return new JtaTransactionManager(bitronixTransactionManager);
    }

    @Bean(name = "primaryPostgresqlDataSource")
    @Primary
    public DataSource primaryMySqlDataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(driverName);
        driver.setUrl(url);
        driver.setUsername(userName);
        driver.setPassword(password);
        return driver;
    }
}

