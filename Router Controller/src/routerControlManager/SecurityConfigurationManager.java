package routerControlManager;

import common.DetailOperationCode;
import common.OperationCode;
import common.OperationSocketStructure;
import router.SecurityConfigurationInfo;

public class SecurityConfigurationManager {
    private static class Singleton{
        private static final SecurityConfigurationManager instance = new SecurityConfigurationManager();
    }

    public static SecurityConfigurationManager getInstance(){
        return SecurityConfigurationManager.Singleton.instance;
    }

    public OperationSocketStructure getSecurityConfiguration(){
        return new OperationSocketStructure(OperationCode.SECURITY_CONFIGURATION, DetailOperationCode.GET_SECURITY_CONFIGURATION);
    }

    public OperationSocketStructure setSecurityConfiguration(String SSID, String password){
        OperationSocketStructure operation = new OperationSocketStructure(OperationCode.SECURITY_CONFIGURATION, DetailOperationCode.SET_SECURITY_CONFIGURATION);

        operation.setSecuritySetting(new SecurityConfigurationInfo(SSID, password));

        return operation;
    }
}
