package domain;

import org.junit.Test;

import static domain.MyDate.isBefore;
import static domain.MyDate.nrOfDaysBetweenDates;
import static org.junit.Assert.*;

public class myDateTest {
    MyDate date = new MyDate(1, 1, 2019);
    MyDate nye = new MyDate(31, 12, 2019);
    MyDate date2 = new MyDate(31, 1, 2020);
    MyDate date3 = new MyDate(6, 6, 2019);
    MyDate date4 = new MyDate(6, 6, 2020);
    MyDate date5 = new MyDate(3, 1, 2024);
    MyDate date6 = new MyDate(30, 6, 2033);
    MyDate date7 = new MyDate(29,2,2020);
    MyDate date8 = new MyDate(2, 1, 2019);

    @Test
    public void getDay() {
    }

    @Test
    public void getMonth() {
    }

    @Test
    public void getYear() {
    }

    @Test
    public void isLeap() {
    }

    @Test
    public void testDaysUntilTheEndOfTheYear() {

        assertEquals (date.daysUntilTheEndOfTheYear(), 364);
        assertEquals (date2.daysUntilTheEndOfTheYear(), 335);
        assertEquals (date4.daysUntilTheEndOfTheYear(), 208);
        assertEquals (date3.daysUntilTheEndOfTheYear(), 208);
        assertEquals (date5.daysUntilTheEndOfTheYear(), 363);
        assertEquals (nye.daysUntilTheEndOfTheYear(), 0);


    }

    @Test
    public void testNrOfDaysBetweenDates() {


        assertEquals(nrOfDaysBetweenDates(date,date),0);
        assertEquals(nrOfDaysBetweenDates(date,date8),1);
        assertEquals (nrOfDaysBetweenDates(date,nye), 364);
        assertEquals (nrOfDaysBetweenDates(date,date5), 1828);
        assertEquals (nrOfDaysBetweenDates(date,date2), 395);
        assertEquals (nrOfDaysBetweenDates(date,date6), 5294);
        assertEquals (nrOfDaysBetweenDates(date7,date6), 4870);


    }

    @Test
    public void testIsBefore() {

        MyDate datea = new MyDate(1, 1, 2019);
        MyDate dateb = new MyDate(3, 2, 2045);

        assertTrue(isBefore(dateb,datea));
        assertTrue(isBefore(date,nye));
        assertTrue(isBefore(date,date));
        assertTrue(isBefore(date3,date6));
    }

    @Test
    public void testCompareTo() {
        MyDate date9 = new MyDate(2, 1, 2019);
        MyDate date10 = new MyDate(2, 1, 2024);

        assertEquals(date.compareTo(date), 0);
        assertEquals(date.compareTo(date8), -1);
        assertEquals(nye.compareTo(date2), -1);
        assertEquals(date6.compareTo(date5), 1);
        assertEquals(date9.compareTo(date10), -1);
    }
}