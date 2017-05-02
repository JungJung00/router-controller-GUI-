package routerAmulator;

import common.OperationSocketStructure;
import common.ResultSocketStructure;
import router.DHCPInfo;

import static common.Now.getTime;

/**
 * Created by Jeong Taegyun on 2017-04-25.
 */

public class DHCPConfigurationHandler {
    public ResultSocketStructure handleDHCPConfigurationOperation(OperationSocketStructure operation){
        ResultSocketStructure result = null;

        switch(operation.getDetailOperationCode()){
            case GET_DHCP_CONFIGURATION:
                result = handleGetDHCPConfigurationOperation(operation);
                break;

            case SET_DHCP_CONFIGURATION:
                result = handleSetDHCPConfigurationOperation(operation);
                break;
        }

        return result;
    }

    private ResultSocketStructure handleGetDHCPConfigurationOperation(OperationSocketStructure operation){
        ResultSocketStructure result = new ResultSocketStructure("DHCP Configuration Information - GET");

        result.setDhcpConfigurations(new DHCPInfo("127.0.0.1", "127.0.0.50", "60"));

        return result;
    }

    private ResultSocketStructure handleSetDHCPConfigurationOperation(OperationSocketStructure operation){
        System.out.println(getTime() + " DHCP operation : " + operation.getDhcpSetting().getMinDHCPAddressRange() +
                " ~ " + operation.getDhcpSetting().getMaxDHCPAddressRange() + " ..." + operation.getDhcpSetting().getIpAllocationTime() + " minutes");

        return new ResultSocketStructure("DHCP Configuration Information - SET");
    }
}
