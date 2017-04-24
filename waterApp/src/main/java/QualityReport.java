/*
 Created by Corey Caskey on 4/24/2017
 */

public class QualityReport {

	private int reportNumber = ReportList.getReportList().size();
    private String location;
    private PurityCondition purityCondition;
    private String address;
    private String date;
    private Double virus;
    private Double contaminant;

    public QualityReport(int reportNumber, String location, String address, String date, PurityCondition purityCondition, Double virus, Double contaminant) {
        this.reportNumber = reportNumber;
        this.location = location;
        this.address = address;
        this.date = date;
        this.purityCondition = purityCondition;
        this.virus = virus;
        this.contaminant = contaminant;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return this.location;
    }

    public Double getVirusCount() {
        return this.virus;
    }

    public Double getContaminantCount() {
        return this.contaminant;
    }

    public PurityCondition getPurityCondition() {
        return this.purityCondition;
    }

    public int getReportNumber() {
        return this.reportNumber;
    }

    public String getDate() {
        return this.date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setVirusCount(Double virusCount) {
        this.virus = virusCount;
    }

    public void setContaminantCount(Double contaminantCount) {
        this.contaminant = contaminantCount;
    }

    public void setPurityCondition(PurityCondition purityCondition) {
        this.purityCondition = purityCondition;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
