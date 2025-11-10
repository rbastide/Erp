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
            String envVariableValue = retrieveEnvironmentVariable(dotenv, arg);
            envVariables.put(arg, envVariableValue);
        }

        return envVariables;
    }

    public static String retrieveEnvironmentVariable(Dotenv dotenv, String key) throws UndefinedEnvironmentVariable {
        String envVariableValue = dotenv.get(key);
        if (envVariableValue == null) {
            throw new UndefinedEnvironmentVariable("The environment variable " + key + " is not defined !");
        }
        return envVariableValue;
    }

    public static void updateSystemEnvironmentVariables(Dotenv dotenv, String[] args) throws UndefinedEnvironmentVariable {
        Map<String, String> envVariables = retrieveEnvironmentVariablesValues(dotenv, args);
        for (String arg : args) {
            System.setProperty(arg, envVariables.get(arg));
        }
    }

}
