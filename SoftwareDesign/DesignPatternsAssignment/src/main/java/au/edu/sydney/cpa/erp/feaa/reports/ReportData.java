package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Arrays;
import java.util.Objects;

public class ReportData {

    private final double[] legalData;
    private final double[] cashFlowData;
    private final double[] mergesData;
    private final double[] tallyingData;
    private final double[] deductionsData;

    private final int hashcode;

    public ReportData(double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {
        this.legalData = legalData.clone();
        this.cashFlowData = cashFlowData.clone();
        this.mergesData = mergesData.clone();
        this.tallyingData = tallyingData.clone();
        this.deductionsData = deductionsData.clone();
        hashcode = hashCode();
    }

    public double[] getLegalData() {
        return legalData.clone();
    }

    public double[] getCashFlowData() {
        return cashFlowData.clone();
    }

    public double[] getMergesData() {
        return mergesData.clone();
    }

    public double[] getTallyingData() {
        return tallyingData.clone();
    }

    public double[] getDeductionsData() {
        return deductionsData.clone();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        //by definition of hashcode, if two objects of the same type are equal
        //then they must share the same hashCode.
        if(hashcode != o.hashCode()) return false;

        //if two objects share the same hashCode, it is possible that they are not equal (collision)
        //thus, we still need to compare all the other values
        ReportData report = (ReportData) o;
        return Arrays.equals(legalData, report.getLegalData()) &&
                Arrays.equals(cashFlowData, report.getCashFlowData()) &&
                Arrays.equals(mergesData, report.getMergesData()) &&
                Arrays.equals(tallyingData, report.getTallyingData()) &&
                Arrays.equals(deductionsData, report.getDeductionsData());
    }

    @Override
    public int hashCode() {
        if(this.hashcode == 0) {
            int result = Arrays.hashCode(legalData);
            result = 31 * result + Arrays.hashCode(cashFlowData);
            result = 31 * result + Arrays.hashCode(mergesData);
            result = 31 * result + Arrays.hashCode(tallyingData);
            result = 31 * result + Arrays.hashCode(deductionsData);
            return result;
        }
        else return hashcode;
    }
}
