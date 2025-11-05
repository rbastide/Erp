package fr.iut_unilim.erp_back;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErpBackApplication {

    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();
        JSch jsch = new JSch();
        Session session = jsch.getSession(dotenv.get("SSL_USERNAME"), dotenv.get("DB_IP"), 22);
        session.setPassword(dotenv.get("SSL_PASSWORD"));
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPortForwardingL(3307, dotenv.get("DB_HOST"), 3306);
        session.connect();

        SpringApplication.run(ErpBackApplication.class, args);
    }

}
