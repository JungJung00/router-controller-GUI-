package routerControllerGUI;

import common.ResultSocketStructure;
import router.ConnectedDeviceInfo;
import router.DHCPInfo;
import router.PortForwardingInfo;
import router.SecurityConfigurationInfo;
import routerController.RouterController;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Jeong Taegyun on 2017-04-19.
 */

public class RouterControllerGUI {
    private JPanel panelMain;
    private JList connectedDeviceList;
    private JScrollPane connectedDeviceListPane;
    private JPanel configurationButtonPane;
    private JButton power;
    private JButton SSIDPasswordButton;
    private JButton DHCPButton;
    private JTextArea responseMessageArea;
    private JButton refreshButton;
    private JPanel portforwardingField;
    private JPanel DHCPField;
    private JPanel SSIDPasswordField;
    private JTextField ruleNameText;
    private JFormattedTextField innerIPText;
    private JTextField innerPortText;
    private JTextField outerPortText;
    private JFormattedTextField minDHCPAddressRangeTextField;
    private JFormattedTextField maxDHCPAddressRangeTextField;
    private JTextField passwordTextField;
    private JTextField SSIDTextField;
    private JButton DHCPSetButton;
    private JButton SSIDPasswordSetButton;
    private JButton portForwardingButton;
    private JButton portForwardingSetButton;
    private JTextField IPAllocationTime;

    private static RouterController routerController;

    public RouterControllerGUI(){
        DefaultListModel dlm = (DefaultListModel) connectedDeviceList.getModel();

        connectedDeviceListPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });

        // ON/OFF router power
        power.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!routerController.isConnected()){
                    responseMessageArea.append(routerController.turnOnPower().getMessage() + "\n");
                }
                else{
                    responseMessageArea.append(routerController.turnOffPower().getMessage() + "\n");
                }
            }
        });

        // Refresh connected devices list
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSocketStructure result = routerController.getConnectedDevices();
                ArrayList<ConnectedDeviceInfo> connectedDevices = result.getConnectedDevices();

                responseMessageArea.append(result.getMessage() + "\n");

                dlm.clear();

                for(int i = 0; i < connectedDevices.size(); i++){
                    dlm.addElement(connectedDevices.get(i).getDeviceName());
                }
            }
        });

        // Get/Set DHCP configurations
        DHCPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSocketStructure result = routerController.getDHCPConfiguration();
                DHCPInfo dhcpConfiguration = result.getDhcpConfigurations();

                responseMessageArea.append(result.getMessage() + "\n");

                dlm.clear();
                dlm.addElement("DHCP IP Range : " + dhcpConfiguration.getMinDHCPAddressRange() + " ~ " + dhcpConfiguration.getMaxDHCPAddressRange() + " | Allocation Time : " + dhcpConfiguration.getIpAllocationTime());
            }
        });
        DHCPSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSocketStructure result = routerController.setDHCPConfiguration(minDHCPAddressRangeTextField.getText(), maxDHCPAddressRangeTextField.getText(), IPAllocationTime.getText());

                responseMessageArea.append(result.getMessage() + "\n");
            }
        });

        // Get/Set portforwarding configuration
        portForwardingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSocketStructure result = routerController.getPortForwardingConfiguration();
                ArrayList<PortForwardingInfo> portForwardingConfigurations = result.getPortForwardingConfigurations();

                responseMessageArea.append(result.getMessage() + "\n");

                dlm.clear();

                for(int i = 0; i < portForwardingConfigurations.size(); i++){
                    PortForwardingInfo pfi = portForwardingConfigurations.get(i);
                    dlm.addElement(pfi.getRuleName() + " : " + "Inner IP " + pfi.getInnerIPAddress() + " | Outer Port "
                                + pfi.getOuterPort() + " | Inner Port " + pfi.getInnerPort());
                }
            }
        });
        portForwardingSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSocketStructure result = routerController.setPortForwardingConfiguration(
                        ruleNameText.getText(),
                        innerIPText.getText(),
                        outerPortText.getText(),
                        innerPortText.getText()
                );

                responseMessageArea.append(result.getMessage() + "\n");
            }
        });

        /* Get/Set Security configuration */
        SSIDPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSocketStructure result = routerController.getSecurityConfiguration();
                SecurityConfigurationInfo securityConfiguration = result.getSecurityConfigurationInfo();

                responseMessageArea.append(result.getMessage() + "\n");

                dlm.clear();
                dlm.addElement("SSID : " + securityConfiguration.getSSID() + " | Password : " + securityConfiguration.getPassword());
            }
        });
        SSIDPasswordSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSocketStructure result = routerController.setSecurityConfiguration(
                        SSIDTextField.getText(),
                        passwordTextField.getText()
                );

                responseMessageArea.append(result.getMessage() + "\n");
            }
        });

        routerController = new RouterController();
    }

    public static void main(String args[]){
        JFrame frame = new JFrame("RouterControllerGUI");
        RouterControllerGUI routerControllerGUI = new RouterControllerGUI();
        frame.setContentPane(routerControllerGUI.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try{
            MaskFormatter mf = new MaskFormatter("###.###.###.###");
            DefaultFormatterFactory dff = new DefaultFormatterFactory(mf);
            routerControllerGUI.minDHCPAddressRangeTextField.setFormatterFactory(dff);
            routerControllerGUI.maxDHCPAddressRangeTextField.setFormatterFactory(dff);
            routerControllerGUI.innerIPText.setFormatterFactory(dff);
        } catch (ParseException e){
            e.printStackTrace();
        }

        frame.pack();
        frame.setVisible(true);

    }
}
