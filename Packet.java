package code;

public class Packet implements Comparable<Packet> {
	private String packetValue;
	private String packetNumber;
	
	public Packet(int packetNumber, String packetValue) {
		this.packetNumber = String.valueOf(packetNumber);
		this.packetValue = packetValue;
	}
	
	public String getPacketValue() {
		return packetValue;
	}
	
	public String getPacketNumber() {
		return packetNumber;
	}

	@Override
	public int compareTo(Packet o) {
		return Integer.compare(Integer.parseInt(this.packetNumber), Integer.parseInt(o.packetNumber));
	}
}

