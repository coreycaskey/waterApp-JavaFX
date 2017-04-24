import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class ReportList {

	private static Map<Integer, Report> reportList = new HashMap<>();
	private static Report currentReport;

	public static void addReport(Report report) {
		String[] locations = getLocationList();

		// for (String location : locations) {
		// 	if (location.equals(report.getLocation())) {
		// 		return false;
		// 	}
		// }

		//can have multiple reports at the same location
		reportList.put(report.getReportNumber(), report);
	}

	public static Map<Integer, Report> getReportList() {
        return reportList;
    }

    public static void changeReportList(Report report, int reportNumber) {
        reportList.replace(reportNumber, report);
    }

    public static Report getReport(int reportNumber) {
        return reportList.get(reportNumber);
    }

    public static ArrayList<Report> getReportListValues() {
        ArrayList<Report> reports = new ArrayList<>();
        for (Report report : reportList.values()) {
        	reports.add(report);
        }
        return reports;
    }

    public static void setReportList(Map<Integer, Report> reportMap) {
        reportList = reportMap;
    }

    public static ArrayList<Integer> getReportListKeys() {
    	ArrayList<Integer> integerArr = new ArrayList<>();
    	for (Integer number : reportList.keySet()) {
    		integerArr.add(number);
    	}
		return integerArr;
    }

    public static String getWaterType(int key) {
        return reportList.get(key).getWaterType().toString();
    }

    public static String getWaterCondition(int key) {
        return reportList.get(key).getWaterCondition().toString();
    }

    public static String[] getLocationList() {
    	ArrayList<Report> reports = getReportListValues();
        String[] locations = new String[reportList.size()];
        int index = 0;
        for (Report report : reports) {
            locations[index] = report.getLocation();
            index++;
        }
        return locations;
    }

    public static int getNumFromLocation(String location) {
    	ArrayList<Report> reports = getReportListValues();
    	int number = 0;

    	for (Report report : reports) {
    		if (location.equals(report.getLocation())) {
    			number = report.getReportNumber();
    			break;
    		}
    	}
        return number;
    }

    public static Report getReportFromLocation(String location) {
    	ArrayList<Report> reports = getReportListValues();
    	Report wantedReport = null;

    	for (Report report : reports) {
    		if (location.equals(report.getLocation())) {
    			wantedReport = report;
    			break;
    		}
    	}
    	return wantedReport;
    }

    public static Report getCurrentReport() {
    	return currentReport;
    }

    public static void setCurrentReport(Report report) {
    	currentReport = report;
    }

}