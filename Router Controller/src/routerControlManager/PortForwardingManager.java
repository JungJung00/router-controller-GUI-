package routerControlManager;

import common.DetailOperationCode;
import common.OperationCode;
import common.OperationSocketStructure;
import router.PortForwardingInfo;

public class PortForwardingManager {
    private static class Singleton{
        private static final PortForwardingManager instance = new PortForwardingManager();
    }

    public static PortForwardingManager getInstance(){
        return PortForwardingManager.Singleton.instance;
    }

    public OperationSocketStructure getPortForwardingOperation(){
        return new OperationSocketStructure(OperationCode.PORTFORWARDING_CONFIGURATION, DetailOperationCode.GET_PORTFORWARDING_CONFIGURATION);
    }

    public OperationSocketStructure setPortForwardingOperation(String name, String innerIP, String outerP, String innerP){
        OperationSocketStructure operation = new OperationSocketStructure(OperationCode.PORTFORWARDING_CONFIGURATION, DetailOperationCode.SET_PORTFORWARDING_CONFIGURATION);

        operation.setPortForwardingSetting(new PortForwardingInfo(name, innerIP, outerP, innerP));

        return operation;
    }
}
