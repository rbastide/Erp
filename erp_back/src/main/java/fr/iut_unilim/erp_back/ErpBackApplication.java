package fr.iut_unilim.erp_back;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import fr.iut_unilim.erp_back.startup.EnvironmentVariablesManager;
import fr.iut_unilim.erp_back.tools.exceptions.UndefinedEnvironmentVariable;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.logging.Logger;

@SpringBootApplication
public class ErpBackApplication {

    private static final Logger LOGGER = Logger.getLogger(ErpBackApplication.class.getName());
    private static final String[] DATABASE_ENVIRONMENT_VARIABLES = {"DB_HOST", "DB_PORT", "DB_NAME", "DB_USERNAME", "DB_PASSWORD"};
    private static final String[] SSH_ENVIRONMENT_VARIABLES = {"SSL_USERNAME", "SSL_PASSWORD", "DB_IP", "DB_HOST"};

    public static void main(String[] args) {
        startApplication(args);
    }

    private static void connectSSH(Map<String, String> envVariables) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(envVariables.get("SSL_USERNAME"), envVariables.get("DB_IP"), 22);
        session.setPassword(envVariables.get("SSL_PASSWORD"));
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPortForwardingL(3307, envVariables.get("DB_HOST"), 3306);
        session.connect();
    }

    private static void connectToDatabase() throws JSchException, UndefinedEnvironmentVariable {
        Dotenv dotenv = Dotenv.load();

        EnvironmentVariablesManager.updateSystemEnvironmentVariables(dotenv, DATABASE_ENVIRONMENT_VARIABLES);

        Map<String, String> loginsSSH = EnvironmentVariablesManager.retrieveEnvironmentVariablesValues(dotenv, SSH_ENVIRONMENT_VARIABLES);
        connectSSH(loginsSSH);
    }

    private static void startApplication(String[] args) {
        boolean errorWhileConnectingDatabase = false;
        try {
            connectToDatabase();
        } catch (JSchException e) {
            LOGGER.severe("Error while connecting in SSH : " + e.getMessage());
            errorWhileConnectingDatabase = true;
        } catch (UndefinedEnvironmentVariable e) {
            LOGGER.severe("Error while retrieving environment variables : " + e.getMessage());
            errorWhileConnectingDatabase = true;
        }

        if (!errorWhileConnectingDatabase) {
            SpringApplication.run(ErpBackApplication.class, args);
        } else {
            LOGGER.info("Application did not start correctly. Please refer to the upper logs for more information.");
        }
    }

}
