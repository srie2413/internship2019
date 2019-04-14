package domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static domain.MyDate.buildDateFromString;
import static domain.MyDate.isLeap;
import static domain.MyDate.nrOfDaysBetweenDates;

public class Employee {

    private MyDate startDate;
    private MyDate endDate;
    private TreeMap<Integer,Integer> holidayRightsPerYear;
    private HashMap<String,String>  suspensionDates;

    public HashMap<String, String> getSuspensionDates() {
        return suspensionDates;
    }

    public Employee(){}

    public Employee(MyDate startDate, MyDate endDate, HashMap<String,String> suspensionDates)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.suspensionDates = suspensionDates;
        this.holidayRightsPerYear = buildHolidayRightsPerYear(this.suspensionDates);
    }

    // Constructor Declaration of Class
    public Employee(MyDate startDate, MyDate endDate, TreeMap<Integer,Integer> holidayRightsPerYear)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.holidayRightsPerYear = holidayRightsPerYear;
    }


    public MyDate getStartDate()
    {
        return startDate;
    }


    public MyDate getEndDate()
    {
        return endDate;
    }


    public MyDate setStartDate(MyDate newStartDate)
    {
        this.startDate = newStartDate;

        return this.startDate;
    }

    public MyDate setEndDate(MyDate newEndDate)
    {
        this.endDate = newEndDate;

        return this.endDate;
    }

    //Function that maps the suspension days per each year
    //in: TreeMap<MyDate, MyDate> suspension (key: start date of the suspension ; value: end date of it)
    //out: TreeMap<Integer,Integer> suspensionPerYear key: year; value: nr of suspension days in that year
    private static TreeMap<Integer, Integer> getSuspensionDaysPerYear(TreeMap<MyDate, MyDate> suspension) {

        TreeMap<Integer, Integer> suspensionPerYear = new TreeMap<>();

        for (Map.Entry<MyDate, MyDate> entry : suspension.entrySet()) {

            MyDate dateStart = entry.getKey();
            MyDate dateEnd = entry.getValue();

            if (dateStart.getYear() == dateEnd.getYear()) {

                if(!suspensionPerYear.containsKey(dateStart.getYear()))
                    suspensionPerYear.put(dateEnd.getYear(), nrOfDaysBetweenDates(dateStart, dateEnd));
                else{
                    suspensionPerYear.put(dateStart.getYear(), suspensionPerYear.get(dateStart.getYear())
                            + nrOfDaysBetweenDates(dateStart, dateEnd));
                }

            } else {
                if(!suspensionPerYear.containsKey(dateStart.getYear()))

                    suspensionPerYear.put(dateStart.getYear(), dateStart.daysUntilTheEndOfTheYear());
                else
                    suspensionPerYear.put(dateStart.getYear(), suspensionPerYear.get(dateStart.getYear())
                            + dateStart.daysUntilTheEndOfTheYear());

                for (int i = dateStart.getYear() + 1; i < dateEnd.getYear(); i++) {
                    if (isLeap(i))
                        suspensionPerYear.put(i, 365);
                    else
                        suspensionPerYear.put(i, 364);

                }

                if (isLeap(dateEnd.getYear()))

                    suspensionPerYear.put(dateEnd.getYear(), 365 - dateEnd.daysUntilTheEndOfTheYear());
                else
                    suspensionPerYear.put(dateEnd.getYear(), 364 - dateEnd.daysUntilTheEndOfTheYear());

            }
        }
        return suspensionPerYear;
    }

    //Function that orders the elements from the suspensionDates by the start date(key)
    //and also stores them as TreeMap<MyDate, MyDate>
    public static TreeMap<MyDate, MyDate> orderSuspension(HashMap<String, String> suspension) {

        TreeMap<MyDate, MyDate> oSuspension = new TreeMap<>(new Comparator<MyDate>() {
            @Override
            public int compare(MyDate d1, MyDate d2) {
                return d1.compareTo(d2);
            }
        });
        for (Map.Entry<String, String> entry : suspension.entrySet()) {

            MyDate dateStart = buildDateFromString(entry.getKey());
            MyDate dateEnd = buildDateFromString(entry.getValue());
            oSuspension.put(dateStart,dateEnd);

        }
        return oSuspension;

    }

    public TreeMap<Integer, Integer> getHolidayRightsPerYear() {
        return holidayRightsPerYear;
    }

    //Function that returns a TreeMap<Integer, Integer> that has year as key and nr of vacation days as value
    private TreeMap<Integer, Integer> buildHolidayRightsPerYear(HashMap<String,String> suspension) {

        TreeMap<MyDate, MyDate> oSuspension = orderSuspension(suspension);
        TreeMap<Integer, Integer> suspensionPerYear = getSuspensionDaysPerYear(oSuspension);
        TreeMap<Integer,Integer> vacationDaysPerYear = new TreeMap<>();

        int holidayRights = 20;
        int newVacationDays;

        for(int i = this.startDate.getYear(); i<= this.endDate.getYear();i++) {


            if(suspensionPerYear.containsKey(i)){
                int noOfDays = suspensionPerYear.get(i);
                float vacationDays = (float) holidayRights*noOfDays/364;
                newVacationDays = Math.round(vacationDays);
            }

            else
                newVacationDays = holidayRights;

            if(holidayRights <24)
                holidayRights++;

            vacationDaysPerYear.put(i, newVacationDays);

        }

        return vacationDaysPerYear;

    }
}
