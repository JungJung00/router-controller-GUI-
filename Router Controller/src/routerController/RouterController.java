package routerController;

import common.OperationSocketStructure;
import common.ResultSocketStructure;
import routerControlManager.*;

import static common.Now.getTime;


public class RouterController extends Equipment{
    /* Managers */
    private static ControllerSocketNetworkManager controllerSocketNetworkManager;
    private static ConnectedDeviceManager connectedDeviceManager;
    private static SecurityConfigurationManager securityConfigurationManager;
    private static DHCPManager dhcpManager;
    private static PortForwardingManager portForwardingManager;
    private static IPAllocationManager ipAllocationManager;

    public RouterController(){
        controllerSocketNetworkManager = ControllerSocketNetworkManager.getInstance();
        connectedDeviceManager = ConnectedDeviceManager.getInstance();
        dhcpManager = DHCPManager.getInstance();
        portForwardingManager = PortForwardingManager.getInstance();
        securityConfigurationManager = SecurityConfigurationManager.getInstance();
    }

    /* Power operation */
    @Override
    public ResultSocketStructure turnOnPower() {
        // Allocate new Socket to serverSocket variable
        System.out.println("Turn on power");
        return controllerSocketNetworkManager.connectRouter();
    }
    @Override
    public ResultSocketStructure turnOffPower() {
        // Close socket and allocate null to serverSocket variable
        System.out.println("Turn off power");
        return controllerSocketNetworkManager.disconnectRouter();
    }
    public boolean isConnected(){
        return controllerSocketNetworkManager.isConnected();
    }

    /* Connected device operation */
    public ResultSocketStructure getConnectedDevices(){
        OperationSocketStructure operation = connectedDeviceManager.connectedDeviceOperation();
        ResultSocketStructure result = controllerSocketNetworkManager.operationExecute(operation);

        System.out.println(getTime() + " " + result.getMessage());

        return result;
    }

    /* DHCP operation */
    public ResultSocketStructure getDHCPConfiguration(){
        OperationSocketStructure operation = dhcpManager.getDHCPOperation();
        ResultSocketStructure result = controllerSocketNetworkManager.operationExecute(operation);

        System.out.println(getTime() + " " + result.getMessage());

        return result;
    }
    public ResultSocketStructure setDHCPConfiguration(String min, String max, String time){
        OperationSocketStructure operation = dhcpManager.setDHCPOperation(min, max, time);
        ResultSocketStructure result = controllerSocketNetworkManager.operationExecute(operation);

        System.out.println(getTime() + " " + result.getMessage());

        return result;
    }

    /* Port Forwarding operation */
    public ResultSocketStructure getPortForwardingConfiguration(){
        OperationSocketStructure operation = portForwardingManager.getPortForwardingOperation();
        ResultSocketStructure result = controllerSocketNetworkManager.operationExecute(operation);

        System.out.println(getTime() + " " + result.getMessage());

        return result;
    }
    public ResultSocketStructure setPortForwardingConfiguration(String name, String innerIP, String outerP, String innerP){
        OperationSocketStructure operation = portForwardingManager.setPortForwardingOperation(name, innerIP, outerP, innerP);
        ResultSocketStructure result = controllerSocketNetworkManager.operationExecute(operation);

        System.out.println(getTime() + " " + result.getMessage());

        return result;
    }

    /* Security operation */
    public ResultSocketStructure getSecurityConfiguration(){
        OperationSocketStructure operation = securityConfigurationManager.getSecurityConfiguration();
        ResultSocketStructure result = controllerSocketNetworkManager.operationExecute(operation);

        System.out.println(getTime() + " " + result.getMessage());

        return result;
    }
    public ResultSocketStructure setSecurityConfiguration(String SSID, String password){
        OperationSocketStructure operation = securityConfigurationManager.setSecurityConfiguration(SSID, password);
        ResultSocketStructure result = controllerSocketNetworkManager.operationExecute(operation);

        System.out.println(getTime() + " " + result.getMessage());

        return result;
    }
}
