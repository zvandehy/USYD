package au.edu.sydney.cpa.erp.feaa.reports;

import java.util.Arrays;

/**
 * Flyweight object to store intrinsic report data. Also is a Value Object. **DO NOT ALLOW MODIFICATION TO DATA**
 */
public class ReportData {

    //If desired, these double[] could be replaced with immutable lists.
    private final double[] legalData;
    private final double[] cashFlowData;
    private final double[] mergesData;
    private final double[] tallyingData;
    private final double[] deductionsData;

    //hashcode is saved so that it can be used in the equals() method without recomputing the hashCode
    private final int hashcode;

    /**
     * Create a ReportData Flyweight
     * @param legalData - legal data of report
     * @param cashFlowData - cash flow data of report
     * @param mergesData - merges data of report
     * @param tallyingData - tallying data of report
     * @param deductionsData - deductions data of report
     */
    public ReportData(double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {
        //Data is cloned in the constructor so that the arrays remain immutable
        /*if they are not cloned, then the entries of the array could be modified
            if the array is created, passed to the ReportData, and then modified from outside of ReportData
         */
        //the null checks are necessary to avoid null pointer exceptions when a report is created with null data (which occurs in the AllowedScopeTest)
        this.legalData = legalData == null ? null : legalData.clone();
        this.cashFlowData = cashFlowData == null ? null : cashFlowData.clone();
        this.mergesData = mergesData == null ? null : mergesData.clone();
        this.tallyingData = tallyingData == null ? null : tallyingData.clone();
        this.deductionsData = cashFlowData == null ? null :  deductionsData.clone();
        //hashcode is calculated in the constructor
        //this is a higher initial cost, but improves performance if ReportData is compared to other ReportData often
        hashcode = hashCode();
    }

    //the getters also return a clone of the array so that they, again, remain immutable
    //if this was not done, then the entries of the array could be modified after the array is accessed

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
        //if hashcode hasn't been initialized then calculate it
        if(this.hashcode == 0) {
            int result = Arrays.hashCode(legalData);
            result = 31 * result + Arrays.hashCode(cashFlowData);
            result = 31 * result + Arrays.hashCode(mergesData);
            result = 31 * result + Arrays.hashCode(tallyingData);
            result = 31 * result + Arrays.hashCode(deductionsData);
            return result;
        }
        else return hashcode; //otherwise just return
    }
}
