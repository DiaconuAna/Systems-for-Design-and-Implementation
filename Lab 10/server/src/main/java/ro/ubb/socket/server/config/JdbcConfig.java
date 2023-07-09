package ro.ubb.socket.server.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import com.mysql.cj.jdbc.Driver;

import java.sql.SQLException;

@Configuration
public class JdbcConfig {

    @Bean
    JdbcOperations jdbcOperations() {
        System.out.println("HEY FUCKER");
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        try {
            System.out.println(jdbcTemplate.getDataSource().getConnection().getMetaData().getURL());
            System.out.println("My jdbc template: "+ jdbcTemplate.getDataSource().getConnection().getMetaData().getURL());

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return jdbcTemplate;
    }

    private DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUsername("root");
        dataSource.setPassword("doareu123");
        dataSource.setUrl("jdbc:mysql://localhost:3306/moviedatabase");
        dataSource.setInitialSize(2);
        return dataSource;
    }
}
