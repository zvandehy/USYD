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

    private int hashcode;

    public ReportData(double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {
        this.legalData = legalData;
        this.cashFlowData = cashFlowData;
        this.mergesData = mergesData;
        this.tallyingData = tallyingData;
        this.deductionsData = deductionsData;
        hashcode = hashCode();
    }

    public double[] getLegalData() {
        return legalData;
    }

    public double[] getCashFlowData() {
        return cashFlowData;
    }

    public double[] getMergesData() {
        return mergesData;
    }

    public double[] getTallyingData() {
        return tallyingData;
    }

    public double[] getDeductionsData() {
        return deductionsData;
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
        int result = Arrays.hashCode(legalData);
        result = 31 * result + Arrays.hashCode(cashFlowData);
        result = 31 * result + Arrays.hashCode(mergesData);
        result = 31 * result + Arrays.hashCode(tallyingData);
        result = 31 * result + Arrays.hashCode(deductionsData);
        return result;
    }
}
