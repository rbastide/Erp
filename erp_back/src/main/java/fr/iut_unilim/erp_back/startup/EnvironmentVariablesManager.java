package fr.iut_unilim.erp_back.startup;

import fr.iut_unilim.erp_back.tools.exceptions.UndefinedEnvironmentVariable;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentVariablesManager {

    private EnvironmentVariablesManager() {}

    public static Map<String, String> retrieveEnvironmentVariablesValues(Dotenv dotenv, String[] args) throws UndefinedEnvironmentVariable {
        Map<String, String> envVariables = new HashMap<>();
        for (String arg : args) {
            String envVariableValue = dotenv.get(arg);
            if (envVariableValue == null) {
                throw new UndefinedEnvironmentVariable("The environment variable " + arg + " is not defined !");
            }
            envVariables.put(arg, envVariableValue);
        }

        return envVariables;
    }

    public static void updateSystemEnvironmentVariables(Dotenv dotenv, String[] args) throws UndefinedEnvironmentVariable {
        Map<String, String> envVariables = retrieveEnvironmentVariablesValues(dotenv, args);
        for (String arg : args) {
            System.setProperty(arg, envVariables.get(arg));
        }
    }

}
