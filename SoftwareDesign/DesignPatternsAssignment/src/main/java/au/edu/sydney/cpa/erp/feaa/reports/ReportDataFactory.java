package au.edu.sydney.cpa.erp.feaa.reports;

import java.util.ArrayList;
import java.util.List;

/**
 * Flyweight Factory manages the pool/cache of existing ReportData
 */
public class ReportDataFactory {
    //List of unique ReportData
    //note: should consider using a hashmap to utilize the hashCode that is initialized in the constructor of ReportData
    private static List<ReportData> cachedData = new ArrayList<>();

    /**
     * If an equivalent ReportData already exists in the cache, then return the cached ReportData. Otherwise, create a new ReportData object, add it
     * to the cache, and return it.
     * @param legalData - legal data of report
     * @param cashFlowData - cash flow data of report
     * @param mergesData - merges data of report
     * @param tallyingData - tallying data of report
     * @param deductionsData - deductions data of report
     * @return a ReportData object
     */
    public static ReportData getReportData(double[] legalData,
                                           double[] cashFlowData,
                                           double[] mergesData,
                                           double[] tallyingData,
                                           double[] deductionsData) {
        //need to create ReportData to compare it
        ReportData reportData = new ReportData(legalData, cashFlowData, mergesData, tallyingData, deductionsData);
        //index = -1 if reportData is unique, otherwise it returns the index in the cached list
        int index = cachedData.indexOf(reportData);
        if(index == -1) {
            //if the reportData is unique then add it to the cache
           cachedData.add(reportData);
        } else {
            //otherwise set reportData to the cached version (the other one will be garbage collected)
            reportData =  cachedData.get(index);
        }
        //return reportData
        return reportData;
    }
}
