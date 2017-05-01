package routerControlManager;

import common.OperationCode;
import common.OperationSocketStructure;

public class ConnectedDeviceManager {
    private static class Singleton{
        private static final ConnectedDeviceManager instance = new ConnectedDeviceManager();
    }

    public static ConnectedDeviceManager getInstance(){
        return ConnectedDeviceManager.Singleton.instance;
    }

    public OperationSocketStructure connectedDeviceOperation(){
        return new OperationSocketStructure(OperationCode.CONNECTED_DEVICES);
    }
}
