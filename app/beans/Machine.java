package beans;

import com.sdl.odata.api.edm.annotations.EdmEntity;
import com.sdl.odata.api.edm.annotations.EdmEntitySet;
import com.sdl.odata.api.edm.annotations.EdmProperty;

@EdmEntity(namespace = "SDL.OData.Example", key = "id", containerName = "SDLExample")
@EdmEntitySet
public class Machine {

	@EdmProperty(name = "id", nullable = false)
	private int machineId;

	@EdmProperty(name = "DnsName", nullable = false)
	private String dnsName;

	@EdmProperty(name = "CurrentRegistrationState", nullable = false)
	private int currentRegistrationState;

	@EdmProperty(name = "CurrentPowerState", nullable = false)
	private int currentPowerState;

	@EdmProperty(name = "CurrentSessionCount", nullable = false)
	private int currentSessionCount;


	public Machine(int machineId, String dnsName, int currentRegistrationState, int currentPowerState, int currentSessionCount) {
		this.machineId = machineId;
		this.dnsName = dnsName;
		this.currentRegistrationState = currentRegistrationState;
		this.currentPowerState = currentPowerState;
		this.currentSessionCount = currentSessionCount;
	}


	public Machine() {
	}


	public int getMachineId() {
		return machineId;
	}


	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}


	public String getDnsName() {
		return dnsName;
	}


	public void setDnsName(String dnsName) {
		this.dnsName = dnsName;
	}


	public int getCurrentRegistrationState() {
		return currentRegistrationState;
	}


	public void setCurrentRegistrationState(int currentRegistrationState) {
		this.currentRegistrationState = currentRegistrationState;
	}


	public int getCurrentPowerState() {
		return currentPowerState;
	}


	public void setCurrentPowerState(int currentPowerState) {
		this.currentPowerState = currentPowerState;
	}


	public int getCurrentSessionCount() { return currentSessionCount; }


	public void setCurrentSessionCount(final int currentSessionCount) { this.currentSessionCount = currentSessionCount; }
}
