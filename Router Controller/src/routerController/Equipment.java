package routerController;

import common.ResultSocketStructure;

abstract class Equipment {
	abstract public ResultSocketStructure turnOnPower();
	
	abstract public ResultSocketStructure turnOffPower();
}
