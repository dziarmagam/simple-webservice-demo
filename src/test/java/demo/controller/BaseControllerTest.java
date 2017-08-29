package demo.controller;


import demo.MainApp;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainApp.class)
@TestPropertySource("classpath:test.properties")
public class BaseControllerTest {

    @Value("${database.url}")
    protected String databaseUrl;
    @Value("@{database.username}")
    protected String username;
    @Value("${database.password}")
    protected String password;

    @Before
    public void createSchema() throws IOException, SQLException {

        String createSchemaQuery = IOUtils.toString(BaseControllerTest.class
                .getResourceAsStream("/create_schema.sql"));
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute(createSchemaQuery);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
