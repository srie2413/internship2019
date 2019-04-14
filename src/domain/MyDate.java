package domain;

import java.util.Objects;

import static utils.Utils.buildIntFromString;

public class MyDate {

    private int day;
    private int month;
    private int year;

    public MyDate() {}

    public MyDate(int day, int month, int year)
    {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return this.day;
    }


    public int getMonth() {
        return this.month;
    }


    public int getYear() {
        return this.year;
    }

    public static boolean isLeap(int year)
    {

        if(year % 400 == 0)
        {
            return true;
        }
        else if (year % 100 == 0)
        {
            return false;
        }
        else if(year % 4 == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static int  nrOfDaysPerMonth(int month, boolean isLeap){
        switch (month) {
            case 1: return 31;
            case 2: return (isLeap) ? 29:28;
            case 3: return 31;
            case 4: return 30;
            case 5: return 31;
            case 6: return 30;
            case 7: return 31;
            case 8: return 31;
            case 9: return 30;
            case 10: return 31;
            case 11: return 30;
            case 12: return 31;

            default: return 0;
        }
    }

    public int daysUntilTheEndOfTheYear()
    {
        int nrOfDays = 0;

        if(this.day == 31 && this.month == 12)
            return 0;

        if ( isLeap(this.year) )
        {
            nrOfDays = 366;
            nrOfDays = nrOfDays - this.day;

            for(int i = this.getMonth() - 1; i>0 ;i--)
                nrOfDays = nrOfDays - nrOfDaysPerMonth(i, isLeap(this.year));

        }
        else {
            nrOfDays = 365;
            nrOfDays = nrOfDays - this.day;

            for(int i = this.getMonth() - 1; i>0 ;i--) {
                    nrOfDays = nrOfDays - nrOfDaysPerMonth(i, isLeap(this.year));
            }
        }

        return nrOfDays;
    }

    public static int nrOfDaysBetweenDates(MyDate date1, MyDate date2)
    {


        if(date1.getYear()==date2.getYear())
           return date1.daysUntilTheEndOfTheYear()-date2.daysUntilTheEndOfTheYear();

        int nrOfDays = date1.daysUntilTheEndOfTheYear();

        for(int i = date1.getYear()+1;i<date2.getYear();i++)
        {
            if (isLeap(i))
                nrOfDays=nrOfDays+366;
            else
                nrOfDays=nrOfDays+365;

        }

        if(isLeap(date2.getYear()))
            nrOfDays = nrOfDays + 366-date2.daysUntilTheEndOfTheYear() ;

        else
            nrOfDays=nrOfDays + 365-date2.daysUntilTheEndOfTheYear() ;

        return nrOfDays;
    }

    public static MyDate buildDateFromString(String date){


        String[] buildDate = date.split("-");

        int Year = buildIntFromString(buildDate[0]);
        int Month = buildIntFromString(buildDate[1]);
        int Day = buildIntFromString(buildDate[2]);

        return new MyDate(Day,Month,Year);

    }

    //Checks if date a is before date b or equal

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyDate myDate = (MyDate) o;
        return day == myDate.day &&
                month == myDate.month &&
                year == myDate.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    public static boolean isBefore(MyDate a, MyDate b) {
        if(a.compareTo(b) < 0)
            return false;
        return true;
    }

    //String representation of form yyyy/MM/dd

    @Override
    public String toString(){
        String strDate = "";

        strDate = strDate + String.valueOf(this.year) + "-";

        if(this.month<10){
            strDate = strDate + "0";
        }
        strDate += this.month + "-";

        if(this.day<10){
            strDate = strDate + "0";
        }
        strDate += this.day;

        return strDate;

    }

    public int compareTo(MyDate d) {
        int day1 = d.getDay();
        int month1 = d.getMonth();
        int year1 = d.getYear();


        if (this.year != year1)
            return this.year - year1;
        else if (this.month != month1)
            return this.month - month1;
        else
            return this.day - (day1);
    }

}
