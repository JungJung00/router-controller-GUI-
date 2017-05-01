package routerControlManager;

import common.DetailOperationCode;
import common.OperationCode;
import common.OperationSocketStructure;
import router.DHCPInfo;

public class DHCPManager {
    private static class Singleton{
        private static final DHCPManager instance = new DHCPManager();
    }

    public static DHCPManager getInstance(){
        return DHCPManager.Singleton.instance;
    }

    public OperationSocketStructure getDHCPOperation(){
        return new OperationSocketStructure(OperationCode.DHCP_CONFIGURATION, DetailOperationCode.GET_DHCP_CONFIGURATION);
    }

    public OperationSocketStructure setDHCPOperation(String min, String max){
        OperationSocketStructure operation = new OperationSocketStructure(OperationCode.DHCP_CONFIGURATION, DetailOperationCode.SET_DHCP_CONFIGURATION);

        operation.setDhcpSetting(new DHCPInfo(min, max));

        return operation;
    }
}
