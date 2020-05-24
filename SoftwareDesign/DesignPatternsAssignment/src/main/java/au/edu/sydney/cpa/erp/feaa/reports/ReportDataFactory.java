package au.edu.sydney.cpa.erp.feaa.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReportDataFactory {
    public static List<ReportData> cachedData = new ArrayList<>();
    public static ReportData getReportData(double[] legalData,
                                           double[] cashFlowData,
                                           double[] mergesData,
                                           double[] tallyingData,
                                           double[] deductionsData) {
        ReportData reportData = new ReportData(legalData, cashFlowData, mergesData, tallyingData, deductionsData);
        int index = cachedData.indexOf(reportData);
        if(index == -1) {
           cachedData.add(reportData);
        } else {
            reportData =  cachedData.get(index);
        }
        return reportData;
    }
}
