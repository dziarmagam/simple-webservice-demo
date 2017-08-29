package demo;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@SpringBootApplication
@Configuration("classpath:application.properties")
public class MainApp {

    private static final String CREATE_SCHEMA_SQL_PATH = "/create_schema.sql";

    @Value("${database.url}")
    private String databaseUrl;
    @Value("@{database.username}")
    private String username;
    @Value("${database.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Bean
    public DataSource createDataSourceAndSchema() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(databaseUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        createSchema(basicDataSource);
        return basicDataSource;
    }

    private void createSchema(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String createSchemaSQL = IOUtils.toString(this.getClass().getResourceAsStream(CREATE_SCHEMA_SQL_PATH));
            statement.execute(createSchemaSQL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
