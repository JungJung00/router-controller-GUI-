package routerAmulator;



import common.OperationSocketStructure;
import common.ResultSocketStructure;
import router.ConnectedDeviceInfo;

import java.util.ArrayList;

/**
 * Created by Jeong Taegyun on 2017-04-24.
 */
public class ConnectedDeviceHandler {

    public ResultSocketStructure handleConnectedDeviceOperation(){
        ArrayList<ConnectedDeviceInfo> connectedDevices = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            ConnectedDeviceInfo tempDevice = new ConnectedDeviceInfo(i + " Device");
            connectedDevices.add(tempDevice);
        }

        ResultSocketStructure result = new ResultSocketStructure("Connected Devices Information");
        result.setConnectedDevices(connectedDevices);

        return result;
    }
}
