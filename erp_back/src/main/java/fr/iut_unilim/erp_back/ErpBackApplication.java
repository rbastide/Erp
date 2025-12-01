package fr.iut_unilim.erp_back;

import com.jcraft.jsch.JSchException;
import fr.iut_unilim.erp_back.tools.exceptions.UndefinedEnvironmentVariable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

import static fr.iut_unilim.erp_back.startup.DatabaseConnectionManager.connectToDatabase;

@SpringBootApplication
public class ErpBackApplication {

    private static final Logger LOGGER = Logger.getLogger(ErpBackApplication.class.getName());

    public static void main(String[] args) {
        startApplication(args);
    }

    private static void startApplication(String[] args) {
        boolean errorWhileConnectingDatabase = connectingDatabase();

        if (!errorWhileConnectingDatabase) {
            SpringApplication.run(ErpBackApplication.class, args);
        } else {
            LOGGER.info("Application did not start correctly. Please refer to the upper logs for more information.");
        }
    }

    private static boolean connectingDatabase() {
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
        return errorWhileConnectingDatabase;
    }

}
