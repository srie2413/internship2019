package Service;

import domain.Employee;
import domain.MyDate;
import domain.validators.EmployeeValidator;
import domain.validators.MyDateValidator;
import domain.validators.ValidatorException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javax.xml.validation.Validator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static domain.MyDate.buildDateFromString;
import static domain.MyDate.isLeap;
import static domain.MyDate.nrOfDaysBetweenDates;

public class Service {
    private String inputFileName;
    private String outputFileName;
    private String errors = "";
    private MyDateValidator dateValidator;
    private EmployeeValidator employeeValidator;

    public Service(String i, String o, MyDateValidator dateValidator,EmployeeValidator employeeValidator) {
        this.inputFileName = i;
        this.outputFileName = o;
        this.dateValidator = dateValidator;
        this.employeeValidator = employeeValidator;

    }

    //Read data from the .json file
    public Employee readEmployeeFromJson(){

        JSONParser parser = new JSONParser();
        Employee emp = new Employee();

        try{
            Object obj =parser.parse(new FileReader(this.inputFileName));
            JSONObject object = (JSONObject) obj;

            JSONObject employeeData = (JSONObject) object.get("employeeData");

            String startDate = (String) employeeData.get("employmentStartDate");

            String endDate = (String) employeeData.get("employmentEndDate");

            JSONArray suspensionArray = (JSONArray) employeeData.get("suspensionPeriodList");

            HashMap<String,String>suspension = new HashMap<String, String>();

            for (Object aSuspensionArray : suspensionArray) {
                JSONObject o = (JSONObject) aSuspensionArray;
                suspension.put((String) o.get("startDate"), (String) o.get("endDate"));
            }
            MyDate newStartDate=buildDateFromString(startDate);
            MyDate newEndDate=buildDateFromString(endDate);

            this.dateValidator.validate(newStartDate);
            this.dateValidator.validate(newEndDate);

            return new Employee(newStartDate,newEndDate,suspension);

        }
        catch (FileNotFoundException e) { this.errors += "File not found "; }
        catch (IOException | ValidatorException | ParseException e) { this.errors += e.getMessage(); }
        catch (NumberFormatException nfe){
            this.errors = " " + "Invalid date";
        }
        catch (Exception e2) {
            this.errors = this.errors + e2.getMessage(); }

            return emp;
    }

    @SuppressWarnings("unchecked")
    //Write the employee to output.json
    public void writeEmployeeToJson(Employee emp){

        JSONObject output = new JSONObject();
        JSONObject outputObj = new JSONObject();

        JSONObject holidayRightsPerYearList = new JSONObject();
        JSONArray holidayRightsPerYearEntry = new JSONArray();

        if(this.errors.equals("")){

            for (Map.Entry<Integer, Integer> entry : emp.getHolidayRightsPerYear().entrySet()){

            JSONObject listEntry = new JSONObject();

            listEntry.put("year", entry.getKey().toString());
            listEntry.put("holidayDays", entry.getValue().toString());

            holidayRightsPerYearEntry.add(listEntry);
        }
        }

        outputObj.put("errorMessage", this.errors);
        outputObj.put("holidayRightsPerYearList", holidayRightsPerYearEntry);
        output.put("output",outputObj);


        try (FileWriter file = new FileWriter(this.outputFileName))
        {
            file.write(output.toString());
            file.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    //This function reads and writes an employee from a json file
    public void run(){
        Employee employee = readEmployeeFromJson();
        if(this.errors.equals(""))
            try {
                employeeValidator.validate(employee);
            }
            catch (ValidatorException  ve){
                errors += " " + ve.getMessage();
            }
        writeEmployeeToJson(employee);


    }




}
