import Service.Service;
import domain.validators.EmployeeValidator;
import domain.validators.IValidator;
import domain.validators.MyDateValidator;

public class Main {

    public static void main(String[] args) {

        String inputFile = "C:\\Users\\Rares\\Documents\\ISS\\INT03\\src\\data\\input.json";
        String outPutFile = "C:\\Users\\Rares\\Documents\\ISS\\INT03\\src\\data\\output.json";
        MyDateValidator dateValidator = new MyDateValidator();
        EmployeeValidator employeeValidator = new EmployeeValidator();

        Service service = new Service(inputFile,outPutFile, dateValidator, employeeValidator);
        service.run();

    }
}
