package fr.iut_unilim.erp_back;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErpBackApplication {

    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();

        updateEnvironmentVariables(dotenv);

        connectSSH(dotenv);

        SpringApplication.run(ErpBackApplication.class, args);
    }

    private static void updateEnvironmentVariables(Dotenv dotenv) {
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    private static void connectSSH(Dotenv dotenv) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(dotenv.get("SSL_USERNAME"), dotenv.get("DB_IP"), 22);
        session.setPassword(dotenv.get("SSL_PASSWORD"));
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPortForwardingL(3307, dotenv.get("DB_HOST"), 3306);
        session.connect();
    }

}
