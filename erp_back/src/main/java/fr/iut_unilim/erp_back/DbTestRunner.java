package fr.iut_unilim.erp_back;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbTestRunner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DbTestRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        var results = jdbcTemplate.queryForList("SELECT * FROM Professeurs");
        System.out.println("Database connection successful: " + results);
    }
}
