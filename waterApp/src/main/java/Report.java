public class Report {
	private String address;
	private int reportNumber;
	private String location;
	private WaterType waterType;
	private WaterCondition waterCondition;

	public Report(int reportNumber, String location, String address, WaterType waterType, WaterCondition waterCondition) {
		this.reportNumber = reportNumber;
		this.location = location;
		this.address = address;
		this.waterType = waterType;
		this.waterCondition = waterCondition;
	}

	public void setWaterType(WaterType waterType) {
		this.waterType = waterType;
	}

	public void setWaterCondition(WaterCondition waterCondition) {
		this.waterCondition = waterCondition;
	}

	public void setReportNumber(int reportNumber) {
		this.reportNumber = reportNumber;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public WaterType getWaterType() {
		return this.waterType;
	}

	public WaterCondition getWaterCondition() {
		return this.waterCondition;
	}

	public int getReportNumber() {
		return this.reportNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public String getLocation() {
		return this.location;
	}

}