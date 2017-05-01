package routerAmulator;

import common.OperationSocketStructure;
import common.ResultSocketStructure;
import router.SecurityConfigurationInfo;

import static common.Now.getTime;

/**
 * Created by Jeong Taegyun on 2017-04-25.
 */

public class SecurityConfigurationHandler {
    public ResultSocketStructure handleSecurityConfigurationOperation(OperationSocketStructure operation){
        ResultSocketStructure result = null;

        switch(operation.getDetailOperationCode()){
            case GET_SECURITY_CONFIGURATION:
                result = handleGetSecurityConfigurationOperation(operation);
                break;

            case SET_SECURITY_CONFIGURATION:
                result = handleSetSecurityConfigurationOperation(operation);
                break;
        }

        return result;
    }

    private ResultSocketStructure handleGetSecurityConfigurationOperation(OperationSocketStructure operation){
        ResultSocketStructure result = new ResultSocketStructure("Security Configuration Information - GET");

        result.setSecurityConfigurationInfo(new SecurityConfigurationInfo("TEST", "TESINGG"));

        return result;
    }

    private ResultSocketStructure handleSetSecurityConfigurationOperation(OperationSocketStructure operation){
        SecurityConfigurationInfo sci = operation.getSecuritySetting();
        System.out.println(getTime() + " Security Operation : SSID " + sci.getSSID() + " | PW " + sci.getPassword());
        return new ResultSocketStructure("Security Configuration Information - SET");
    }
}
