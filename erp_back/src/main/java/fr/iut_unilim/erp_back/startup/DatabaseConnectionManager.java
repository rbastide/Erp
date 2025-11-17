package fr.iut_unilim.erp_back.startup;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import fr.iut_unilim.erp_back.tools.exceptions.UndefinedEnvironmentVariable;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

import static fr.iut_unilim.erp_back.startup.EnvironmentVariablesManager.*;

public class DatabaseConnectionManager {

    private DatabaseConnectionManager() {}

    private static final String[] DATABASE_ENVIRONMENT_VARIABLES = {"DB_HOST", "DB_PORT", "DB_NAME", "DB_USERNAME", "DB_PASSWORD", "SGBD"};
    private static final String[] SSH_ENVIRONMENT_VARIABLES = {"SSL_USERNAME", "SSL_PASSWORD", "DB_IP", "DB_HOST", "DB_PORT"};

    private static void connectSSH(Map<String, String> envVariables) throws JSchException {
        JSch jsch = new JSch();
        int dbPort = Integer.parseInt(envVariables.get("DB_PORT"));
        Session session = jsch.getSession(envVariables.get("SSL_USERNAME"), envVariables.get("DB_IP"), 22);
        session.setPassword(envVariables.get("SSL_PASSWORD"));
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPortForwardingL(dbPort, envVariables.get("DB_HOST"), 3306);
        session.connect();
    }

    public static void connectToDatabase() throws JSchException, UndefinedEnvironmentVariable {
        Dotenv dotenv = Dotenv.load();

        updateSystemEnvironmentVariables(dotenv, DATABASE_ENVIRONMENT_VARIABLES);

        Map<String, String> loginsSSH = retrieveEnvironmentVariablesValues(dotenv, SSH_ENVIRONMENT_VARIABLES);

        boolean needToConnectSSH = "true".equals(retrieveEnvironmentVariable(dotenv, "IS_SSH"));

        if (needToConnectSSH) connectSSH(loginsSSH);
    }

}
