package events;

public class ConnectToGameEvent extends Event {
	private static final long serialVersionUID = 1L;
	private String ip;
	public ConnectToGameEvent(String ip) {
		this.ip = ip;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
