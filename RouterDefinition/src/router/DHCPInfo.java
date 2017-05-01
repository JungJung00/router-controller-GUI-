package router;

import java.io.Serializable;

public class DHCPInfo implements Serializable{
    private String minDHCPAddressRange;
    private String maxDHCPAddressRange;

    public DHCPInfo(){}
    public DHCPInfo(String min, String max){
        minDHCPAddressRange = min;
        maxDHCPAddressRange = max;
    }

    /* Configuration getter and setter */
    public String getMinDHCPAddressRange() {
        return minDHCPAddressRange;
    }
    public void setMinDHCPAddressRange(String minDHCPAddressRange) {
        this.minDHCPAddressRange = minDHCPAddressRange;
    }
    public String getMaxDHCPAddressRange() {
        return maxDHCPAddressRange;
    }
    public void setMaxDHCPAddressRange(String maxDHCPAddressRange) {
        this.maxDHCPAddressRange = maxDHCPAddressRange;
    }
}
