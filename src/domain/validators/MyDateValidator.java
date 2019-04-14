package domain.validators;

import domain.MyDate;

import static domain.MyDate.isLeap;

public class MyDateValidator implements IValidator <MyDate> {

    @Override
    public void validate(MyDate entity) throws ValidatorException {
            if (entity.getYear() < 0) throw new ValidatorException("Year cannot be negative!");
            if ((entity.getMonth() < 1) || (entity.getMonth() > 12)) throw new ValidatorException("Invalid month!(must be between 1 and 12)");
            if ((entity.getDay() < 1) || (entity.getDay() > 31)) throw new ValidatorException("Invalid day(must be between 1 and 31)");

            switch (entity.getMonth()) {
                case 2: {
                    if (isLeap(entity.getYear()) && entity.getDay() > 29)
                        throw new ValidatorException("Leap year february date cannot be higher than 29");

                    else if(!isLeap(entity.getYear()) && entity.getDay() > 28)
                        throw new ValidatorException("Common year february date cannot be higher than 28");
                }

                case 4: if(entity.getDay()>30) throw new ValidatorException("April date cannot be higher than 30");
                case 6: if(entity.getDay()>30) throw new ValidatorException("June date cannot be higher than 30");
                case 9: if(entity.getDay()>30) throw new ValidatorException("September date cannot be higher than 30");
                case 11: if(entity.getDay()>30) throw new ValidatorException("November date cannot be higher than 30");
                default: ;
            }
    }
}

