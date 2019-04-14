package domain.validators;

import domain.Employee;
import domain.MyDate;

import java.util.ArrayList;
import java.util.TreeMap;

import static domain.MyDate.buildDateFromString;
import static domain.MyDate.isBefore;

public class EmployeeValidator implements IValidator <Employee>{

    @Override
    public void validate(Employee e) throws ValidatorException {

        if(e.getStartDate().compareTo(e.getEndDate()) > 0) throw new ValidatorException("employment end date cannot be before start date!");

        try{
            e.getSuspensionDates().forEach(
                    (k, v) ->{ buildDateFromString(k); buildDateFromString(v);});

        }catch(ValidatorException ex){
            throw new ValidatorException("Invalid dates in suspension period list");

        }

        //Here I check whether all the suspension date entries are in an ascending order such that
        //the entries don't overlap and suspension date pairs are also in an ascending order
        ArrayList<MyDate> dateS = new ArrayList<>();
        TreeMap<MyDate, MyDate> oSuspension = Employee.orderSuspension(e.getSuspensionDates()) ;
        oSuspension.forEach(
                (k, v) ->{
                    dateS.add(k);
                    dateS.add(v);
                }
                    );

        for (int i = 0; i < dateS.size() - 1; i++) {
            if(dateS.get(i).compareTo(dateS.get(i+1)) >= 0)
            {
                throw new ValidatorException("Invalid suspension list!");
            }
        }



    }
}
