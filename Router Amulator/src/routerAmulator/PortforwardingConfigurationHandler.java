package routerAmulator;

import common.OperationSocketStructure;
import common.ResultSocketStructure;
import router.PortForwardingInfo;

import java.util.ArrayList;

import static common.Now.getTime;

/**
 * Created by Jeong Taegyun on 2017-04-25.
 */

public class PortforwardingConfigurationHandler {
    public ResultSocketStructure handlePortforwardingConfigurationOperation(OperationSocketStructure operation){
        ResultSocketStructure result = null;

        switch(operation.getDetailOperationCode()){
            case GET_PORTFORWARDING_CONFIGURATION:
                result = handleGetPortforwardingConfigurationOperation(operation);
                break;

            case SET_PORTFORWARDING_CONFIGURATION:
                result = handleSetPortforwardingConfigurationOperation(operation);
                break;
        }

        return result;
    }

    private ResultSocketStructure handleGetPortforwardingConfigurationOperation(OperationSocketStructure operation){
        ResultSocketStructure result = new ResultSocketStructure("Portforwarding Configuration Information - GET");
        ArrayList<PortForwardingInfo> portforwardingConfigurations = new ArrayList<>();

        for (int i = 0; i < 15; i++){
            portforwardingConfigurations.add(new PortForwardingInfo(i + " Config", "127.0.0.1", "3000", "3000"));
        }

        result.setPortForwardingConfigurations(portforwardingConfigurations);
        return result;
    }

    private ResultSocketStructure handleSetPortforwardingConfigurationOperation(OperationSocketStructure operation){
        PortForwardingInfo pfi = operation.getPortForwardingSetting();
        System.out.println(getTime() + " PortForwarding Operation : " + pfi.getRuleName() + " | Inner IP " + pfi.getInnerIPAddress() +
                        " | Inner Port " + pfi.getInnerPort() + " | Outer Port " + pfi.getOuterPort());

        return new ResultSocketStructure("Portforwarding Configuration Information - SET");
    }
}
