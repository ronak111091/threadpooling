package sw.architecture.threadpooling;

public class TaskRequest {
	
	private int id;
	private String lattitude;
	private String longitude;
	
	public TaskRequest(int id, String lattitude, String longitude) {
		super();
		this.id = id;
		this.lattitude = lattitude;
		this.longitude = longitude;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLattitude() {
		return lattitude;
	}
	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
