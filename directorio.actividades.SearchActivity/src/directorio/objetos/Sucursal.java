package directorio.objetos;

public class Sucursal {
	private int id;
	private int AdvertiserID;
	private String name;
	private String address;
	private String CityName;
	private double pointX;
	private double pointY;

	public Sucursal(int id, int advertiserID, String name, String address,
			String cityName, double pointX, double pointY) {
		super();
		this.id = id;
		AdvertiserID = advertiserID;
		this.name = name;
		this.address = address;
		CityName = cityName;
		this.pointX = pointX;
		this.pointY = pointY;
	}

	public Sucursal() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdvertiserID() {
		return AdvertiserID;
	}

	public void setAdvertiserID(int advertiserID) {
		AdvertiserID = advertiserID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public double getPointX() {
		return pointX;
	}

	public void setPointX(double pointX) {
		this.pointX = pointX;
	}

	public double getPointY() {
		return pointY;
	}

	public void setPointY(double pointY) {
		this.pointY = pointY;
	}

}
