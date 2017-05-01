package routerAmulator;

import common.OperationSocketStructure;
import common.ResultSocketStructure;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static common.Now.getTime;


/**
 * Created by Jeong Taegyun on 2017-04-19.
 */

public class RouterAmulatorHandler extends Thread{
    private String IP = "127.0.0.1";
    private short PORT = 3000;

    private InputStream inputStream;
    private ObjectInputStream objectInputStream;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private static ErrorHandler errorHandler;
    private static ConnectedDeviceHandler connectedDeviceHandler;
    private static SecurityConfigurationHandler securityConfigurationHandler;
    private static IPAllocateConfigurationHandler ipAllocateConfigurationHandler;
    private static PortforwardingConfigurationHandler portforwardingConfigurationHandler;
    private static DHCPConfigurationHandler dhcpConfigurationHandler;



    public RouterAmulatorHandler(){
        inputStream = null;
        objectInputStream = null;
        outputStream = null;
        objectOutputStream = null;
        serverSocket = null;

        errorHandler = new ErrorHandler();
        connectedDeviceHandler = new ConnectedDeviceHandler();
        securityConfigurationHandler = new SecurityConfigurationHandler();
        ipAllocateConfigurationHandler = new IPAllocateConfigurationHandler();
        portforwardingConfigurationHandler = new PortforwardingConfigurationHandler();
        dhcpConfigurationHandler = new DHCPConfigurationHandler();
    }

    @Override
    public void run(){
        // server sockect binding
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println(getTime() + "Server is listening on " + PORT);

        } catch(IOException e){
            e.printStackTrace();
        }

        Socket clientSocket;

        // handle router controller's operation
        while (true){
            try{
                System.out.println(getTime() + "Waiting for connecting...");

                clientSocket = serverSocket.accept();
                System.out.println(getTime() + " " + clientSocket.getInetAddress() + " is connected.");

                OperationSocketStructure operation;

                /* Get objectInputStream to receive object(OperationSocketStructure) from router controller */
                inputStream = clientSocket.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);

                operation = (OperationSocketStructure) objectInputStream.readObject();   // Receive operation socket

                ResultSocketStructure result;

                switch (operation.getOperationCode()){
                    case CONNECTED_DEVICES:
                        result = connectedDeviceHandler.handleConnectedDeviceOperation();
                        break;

                    case SECURITY_CONFIGURATION:
                        result = securityConfigurationHandler.handleSecurityConfigurationOperation(operation);
                        break;

                    case IPALLOCATE_CONFIGURATION:
                        result = ipAllocateConfigurationHandler.handleIPAllocateConfigurationOperation(operation);
                        break;

                    case PORTFORWARDING_CONFIGURATION:
                        result = portforwardingConfigurationHandler.handlePortforwardingConfigurationOperation(operation);
                        break;

                    case DHCP_CONFIGURATION:
                        result = dhcpConfigurationHandler.handleDHCPConfigurationOperation(operation);
                        break;

                    case TURN_POWER:
                        result = handlePowerOperation(operation);
                        break;

                    default:
                        result = errorHandler.handleError(operation);
                }

                /* TODO : Save router configurations to txt file */

                /* Get objectOutputStream to send object(ResultSocketStructure) to router controller */
                outputStream = clientSocket.getOutputStream();
                objectOutputStream = new ObjectOutputStream(outputStream);

                /* Send result socket to router controller */
                objectOutputStream.reset();
                objectOutputStream.writeObject(result);
                objectOutputStream.flush();

                /* Close streams and sockets*/
                try{
                    inputStream.close();
                    outputStream.close();
                    objectInputStream.close();
                    objectOutputStream.close();

                    clientSocket.close();
                } catch (IOException e){
                    e.printStackTrace();
                }

            } catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    public ResultSocketStructure handlePowerOperation(OperationSocketStructure operation){
        ResultSocketStructure result = null;

        switch (operation.getDetailOperationCode()){
            case TURN_ON_POWER:
                result = handleTurnOnPowerOperation(operation);
                break;

            case TURN_OFF_POWER:
                result = handleTurnOffPowerOperation(operation);
                break;
        }

        return result;
    }

    private ResultSocketStructure handleTurnOnPowerOperation(OperationSocketStructure operation){
        return new ResultSocketStructure("Turn on power");
    }

    private ResultSocketStructure handleTurnOffPowerOperation(OperationSocketStructure operation){
        return new ResultSocketStructure("Turn off power");
    }
}
