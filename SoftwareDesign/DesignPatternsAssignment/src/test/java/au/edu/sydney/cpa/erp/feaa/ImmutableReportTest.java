package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.feaa.reports.ReportImpl;
import au.edu.sydney.cpa.erp.ordering.Report;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ImmutableReportTest {

    private Report report;
    @Before
    public void setup() {
        String name = "name";
        double commissionPerEmployee = 2.0;
        double[] legalData = {1.0, 1.0};
        double[] cashFlowData = {1.0, 1.0};
        double[] mergesData = {1.0, 1.0};
        double[] tallyingData = {1.0, 1.0};
        double[] deductionsData = {1.0, 1.0};
        report = new ReportImpl(name, commissionPerEmployee, legalData, cashFlowData, mergesData, tallyingData, deductionsData);
    }

    @Test
    public void legalDataNotModifiableTest() {
        System.out.println("Legal Data Before: " + Arrays.toString(report.getLegalData()));
        double[] changedData = report.getLegalData();
        changedData[0] = 2.0;
        System.out.println("Legal Data After : " + Arrays.toString(report.getLegalData()));

        System.out.println("Changed   Legal Data: " + Arrays.toString(changedData));
        System.out.println("Unchanged Legal Data: " + Arrays.toString(report.getLegalData()));
        assertNotEquals(changedData, report.getLegalData());
    }

    @Test
    public void nameNotModifiable() {
        String name = report.getReportName();

        System.out.println("Name Before: " + report.getReportName());
        name += " change";
        System.out.println("Name After: " + report.getReportName());

        assertEquals("name", report.getReportName());
        assertEquals("name change", name);

    }

    @Test
    public void commissionNotModifiable() {
        double commission = report.getCommission();

        System.out.println("Commission Before: " + report.getCommission());
        commission += 3.0;
        System.out.println("Commission After: " + report.getCommission());

        assertEquals(2.0, report.getCommission(), .01);
        assertEquals(5.0, commission, .01);

    }

    @Test
    public void compareReports() {
        //equal
        Report report2 = report;

        //not equal
        String name = "name2";
        double commissionPerEmployee = 4.0;
        double[] legalData = {2.0, 2.0};
        double[] cashFlowData = {2.0, 2.0};
        double[] mergesData = {2.0, 2.0};
        double[] tallyingData = {2.0, 2.0};
        double[] deductionsData = {2.0, 2.0};
        Report report3 = new ReportImpl(name, commissionPerEmployee, legalData, cashFlowData, mergesData, tallyingData, deductionsData);

        //equal
        name = "name";
        commissionPerEmployee = 2.0;
        legalData = new double[]{1.0, 1.0};
        cashFlowData = new double[]{1.0, 1.0};
        mergesData = new double[]{1.0, 1.0};
        tallyingData = new double[]{1.0, 1.0};
        deductionsData = new double[]{1.0, 1.0};
        Report report4 = new ReportImpl(name, commissionPerEmployee, legalData, cashFlowData, mergesData, tallyingData, deductionsData);

        //not equal
        name = "othername";
        Report report5 = new ReportImpl(name, commissionPerEmployee, legalData, cashFlowData, mergesData, tallyingData, deductionsData);

        name = "name"; //same name but...
        //not equal
        deductionsData = new double[]{1.0, 2.0};
        Report report6 = new ReportImpl(name, commissionPerEmployee, legalData, cashFlowData, mergesData, tallyingData, deductionsData);

        //1 = 2 = 4
        //3,5,6 are unique

        assertEquals(report, report2);
        assertEquals(report, report4);
        assertEquals(report2, report4);

        assertNotEquals(report, report3);
        assertNotEquals(report, report5);
        assertNotEquals(report, report6);

        assertNotEquals(report2, report3);
        assertNotEquals(report2, report5);
        assertNotEquals(report2, report6);

        assertNotEquals(report3, report5);
        assertNotEquals(report3, report6);

        assertNotEquals(report4, report5);
        assertNotEquals(report4, report6);

        assertNotEquals(report6, report5);



    }
}
