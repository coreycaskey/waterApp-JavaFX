/*
 Created by Corey Caskey on 4/24/2017
 */

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class QualityReportList {

	private static Map<Integer, QualityReport> qualityReportList = new HashMap<>();
    private static QualityReport currentQReport;

    public static boolean addQualityReport(QualityReport qr) {
    	String[] locations = getLocationList();

		// for (String location : locations) {
		// 	if (location.equals(qr.getLocation())) {
		// 		return false;
		// 	}
		// }
		qualityReportList.put(qr.getReportNumber(), qr);
		return true;
    }

    public static Map<Integer, QualityReport> getQualityReportList() {
        return qualityReportList;
    }

    public static void changeQualityReportList(QualityReport qr, int reportNumber) {
        qualityReportList.replace(reportNumber, qr);
    }

    public static QualityReport getQualityReport(int reportNumber) {
        return qualityReportList.get(reportNumber);
    }

    public static ArrayList<QualityReport> getQualityReportListValues() {
    	ArrayList<QualityReport> qualityReports = new ArrayList<>();
        for (QualityReport qualityReport : qualityReportList.values()) {
        	qualityReports.add(qualityReport);
        }
        return qualityReports;
    }

    public static void setQualityReportList(Map<Integer, QualityReport> qualityReportMap) {
        qualityReportList = qualityReportMap;
    }

    public static ArrayList<Integer> getQualityReportListKeys() {
        ArrayList<Integer> integerArr = new ArrayList<>();
    	for (Integer number : qualityReportList.keySet()) {
    		integerArr.add(number);
    	}
		return integerArr;
    }

    public static String getPurityCondition(int key) {
        return qualityReportList.get(key).getPurityCondition().toString();
    }

    public static String[] getLocationList() {
        ArrayList<QualityReport> qualityReports = getQualityReportListValues();
        String[] locations = new String[qualityReports.size()];
        int index = 0;
        for (QualityReport qualityReport : qualityReports) {
            locations[index] = qualityReport.getLocation();
            index++;
        }
        return locations;
    }

    public static int getNumFromLocation(String location) {
        ArrayList<QualityReport> qualityReports = getQualityReportListValues();
    	int number = 0;

    	for (QualityReport qualityReport : qualityReports) {
    		if (location.equals(qualityReport.getLocation())) {
    			number = qualityReport.getReportNumber();
    			break;
    		}
    	}
        return number;
    }

    public static String[] getDateList() {
    	String[] dates = new String[qualityReportList.size()];
    	ArrayList<QualityReport> qualityReports = getQualityReportListValues();
    	int index = 0;

    	for (QualityReport qr : qualityReports) {
    		dates[index] = qr.getDate();
    		index++;
    	}
        return dates;
    }

    public static QualityReport getQReportFromLocation(String location) {
        ArrayList<QualityReport> qReports = getQualityReportListValues();
        QualityReport wantedQReport = null;

        for (QualityReport qReport : qReports) {
            if (location.equals(qReport.getLocation())) {
                wantedQReport = qReport;
                break;
            }
        }
        return wantedQReport;
    }

    public static QualityReport getCurrentQReport() {
        return currentQReport;
    }

    public static void setCurrentQReport(QualityReport qualityReport) {
        currentQReport = qualityReport;
    }

    public static String[] getUniqueLocationList() {
        ArrayList<String> locationSet = new ArrayList<>();

        for (String location : getLocationList()) {
            if (!locationSet.contains(location)) {
                locationSet.add(location);
            }
        }

        String[] locationArray = new String[locationSet.size()];
        int index = 0;

        for (String location: locationSet) {
            locationArray[index] = location;
            index++;
        }

        return locationArray;
    }

    public static ArrayList<String> getUniqueDateList(String location) {
        ArrayList<String> dateSet = new ArrayList<>();

        for (QualityReport qr: getQualityReportListValues()) {
            if (qr.getLocation().equals(location)) {
                dateSet.add(qr.getDate().substring(6,7));
            }
        }

        return dateSet;
    }
}
