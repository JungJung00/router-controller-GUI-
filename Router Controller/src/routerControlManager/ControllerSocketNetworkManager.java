package routerControlManager;

import common.*;

import java.io.*;
import java.net.Socket;

/**
 * Created by Jeong Taegyun on 2017-04-19.
 */

public class ControllerSocketNetworkManager {
    private String IP = "127.0.0.1";
    private short PORT = 3000;

    private InputStream inputStream;
    private ObjectInputStream objectInputStream;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;

    private static Socket serverSocket;

    private boolean connected = false;

    private ErrorManager errorManager;

    public ControllerSocketNetworkManager(){
        inputStream = null;
        objectInputStream = null;
        outputStream = null;
        objectOutputStream = null;
        serverSocket = null;

        errorManager = new ErrorManager();
    }

    private static class Singleton{
        private static final ControllerSocketNetworkManager instance = new ControllerSocketNetworkManager();
    }

    public static ControllerSocketNetworkManager getInstance(){
        return Singleton.instance;
    }

    public ResultSocketStructure operationExecute(OperationSocketStructure operation){
        ResultSocketStructure result = null;

        // Send operation
        try{
            /* First, turn on the router */
            if (serverSocket == null){
                System.out.println("Router is off");
                return errorManager.handleError(operation);
            }

            if (serverSocket.isClosed()){
                serverSocket = new Socket(IP, PORT);
            }

            /* Get objectStreams to send socket(OperationSocket) */
            outputStream = serverSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);

            /* Send operation socket to router emulator */
            sendOperation(operation);

            /* Get objectStreams to receive socket(ResultSocket) */
            inputStream = serverSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);

            /* Receive result socket from router emulator */
            result = receiveResult();

            /* Close streams and sockets*/
            inputStream.close();
            outputStream.close();
            objectInputStream.close();
            objectOutputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }

    // Send operation socket to router amulator
    public void sendOperation(OperationSocketStructure operation){
        try{
        objectOutputStream.reset();
        objectOutputStream.writeObject(operation);
        objectOutputStream.flush(); // Send object

    } catch (IOException e){
        e.printStackTrace();
    }
    }

    // Receive result socket from router emulator
    public ResultSocketStructure receiveResult(){
        ResultSocketStructure result = null;
        try{
            result = (ResultSocketStructure) objectInputStream.readObject();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return result;
    }

    public ResultSocketStructure connectRouter(){
        OperationSocketStructure operation = new OperationSocketStructure(OperationCode.TURN_POWER, DetailOperationCode.TURN_ON_POWER);
        try{
            /* Connect to amulator */
            serverSocket = new Socket(IP, PORT);
            connected = true;
        } catch (IOException e){
            e.printStackTrace();
        }

        return operationExecute(operation);
    }

    public ResultSocketStructure disconnectRouter(){
        OperationSocketStructure operation = new OperationSocketStructure(OperationCode.TURN_POWER, DetailOperationCode.TURN_OFF_POWER);
        ResultSocketStructure result = operationExecute(operation);

        try{
            if (!serverSocket.isClosed())
                serverSocket.close();
            connected = false;
        } catch (IOException e){
            e.printStackTrace();
        }
        serverSocket = null;

        return result;
    }
    public boolean isConnected(){
        return connected;
    }
}
