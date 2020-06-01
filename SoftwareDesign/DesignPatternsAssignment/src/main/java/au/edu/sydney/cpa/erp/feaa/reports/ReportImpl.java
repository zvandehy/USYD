package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Arrays;
import java.util.Objects;

/**
 * ReportImpl is the context object for the Flyweight Pattern. It implements the Report interface.
 */
public class ReportImpl implements Report {

    private final String name;
    private final double commissionPerEmployee;
    private final ReportData data;

    //hashcode is saved so that it can be used in the equals() method without recomputing the hashCode
    //it acts similarly to a primary key, but not exactly the same
    private final int hashcode;

    /**
     * Create a ReportImpl
     * @param name - name of report
     * @param commissionPerEmployee - commission per employee in report
     * @param legalData - legal data of report
     * @param cashFlowData - cash flow data of report
     * @param mergesData - merges data of report
     * @param tallyingData - tallying data of report
     * @param deductionsData - deductions data of report
     */
    public ReportImpl(String name,
                      double commissionPerEmployee,
                      double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {
        //use ReportDataFactory to create the ReportData flyweights
        this.data = ReportDataFactory.getReportData(legalData, cashFlowData, mergesData, tallyingData, deductionsData);
        this.name = name;
        this.commissionPerEmployee = commissionPerEmployee;
        //hashcode is calculated in the constructor
        //this is a higher initial cost, but improves performance if Report is compared to other Reports often
        //also acts similarly to primary key
        this.hashcode = hashCode();
    }

    @Override
    public String getReportName() {
        return name;
    }

    @Override
    public double getCommission() {
        return commissionPerEmployee;
    }

    //there is no getter for the ReportData
    //instead there are getters for all of the data in the report data (as specified in the Report interface)

    @Override
    public double[] getLegalData() { return data.getLegalData(); }

    @Override
    public double[] getCashFlowData() {
        return data.getCashFlowData();
    }

    @Override
    public double[] getMergesData() {
        return data.getMergesData();
    }

    @Override
    public double[] getTallyingData() {
        return data.getTallyingData();
    }

    @Override
    public double[] getDeductionsData() {
        return data.getDeductionsData();
    }

    @Override
    public String toString() {
        return String.format("%s", name);
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
        ReportImpl report = (ReportImpl) o;
        return Double.compare(report.commissionPerEmployee, commissionPerEmployee) == 0 &&
                name.equals(report.name) &&
                data.equals(report.data); //technically, this could be replaced with data == report.data because the factory ensures that if they are equal, then they would be the same object (and share their memory address)
    }

    @Override
    public int hashCode() {
        //if hashcode hasn't been initialized then calculate it
        if(hashcode == 0) {
            return 31 * Objects.hash(name, commissionPerEmployee, data);
        } else return hashcode; //otherwise just return
    }
}
