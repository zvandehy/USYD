package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Arrays;
import java.util.Objects;

//We are making a Report a Value Object
//It is immutable, so all of the data it holds needs to be in immutable lists
//We will use hashcode to check equality. If there is a collision, then we continue to check equality with the
//array and other field comparisons. This is more efficient in this context because reports are checked for equality so often.
//This would be less efficient if creating a report happened more often than checking equality (because we have to go through each list
//in order to create the hashcode).
//The getter methods return a clone of the double[]. This is necessary because for the Report to be fully immutable, it should not be possible
//for a client to change the data inside of the array. In this way, there is a performance cost of implementing the Report as a Value object. However,
//this is a good solution because the arrays are accessed infrequently enough that it should not have a large impact on performance.
//Rather, by implementing Report as a Value Object, we simplify the code by allowing reports to be easily compared.
//In addition, because of the use of hashcode in the equals method, the performance benefit from not needing to make so many comparisons
//to each double[] (and checking the hashcode first), outweighs the cost of cloning the data in the getters (in this domain) because reports
//are frequently compared with each other. It is worth noting that this benefit is more significant when more reports that are *not* equal are
//compared to each other. This is because all of the arrays must be compared if the hash codes are equal (small possibility to share hashcode but not be equal).

public class ReportImpl implements Report {

    private final String name;
    private final double commissionPerEmployee;
    private final ReportData data;

    private final int hashcode;

    public ReportImpl(String name,
                      double commissionPerEmployee,
                      double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {
        this.data = ReportDataFactory.getReportData(legalData, cashFlowData, mergesData, tallyingData, deductionsData);
        this.name = name;
        this.commissionPerEmployee = commissionPerEmployee;
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
        if(hashcode == 0) {
            return 31 * Objects.hash(name, commissionPerEmployee, data);
        } else return hashcode;
    }
}
