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
    private static final String[] SSH_ENVIRONMENT_VARIABLES = {"SSH_USERNAME", "SSH_PASSWORD", "DB_IP", "DB_HOST", "DB_PORT"};

    private static final int SSH_PORT = 22;
    private static final int DB_PORT_RECEIVER = 3306;

    private static void connectSSH(Map<String, String> envVariables) throws JSchException {
        JSch jsch = new JSch();
        int dbPort = Integer.parseInt(envVariables.get("DB_PORT"));
        Session session = jsch.getSession(envVariables.get("SSH_USERNAME"), envVariables.get("DB_IP"), SSH_PORT);
        session.setPassword(envVariables.get("SSH_PASSWORD"));
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPortForwardingL(dbPort, envVariables.get("DB_HOST"), DB_PORT_RECEIVER);
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
