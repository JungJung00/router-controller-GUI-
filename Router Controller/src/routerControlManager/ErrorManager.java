package routerControlManager;

import common.OperationSocketStructure;
import common.ResultSocketStructure;

/**
 * Created by qwert on 2017-04-25.
 */
public class ErrorManager {
    public ResultSocketStructure handleError(OperationSocketStructure operation){
        return new ResultSocketStructure("ERROR HAPPENED WHEN OPERATING " + operation.getOperationCode()
                + "(" + operation.getDetailOperationCode() + ")");
    }
}
