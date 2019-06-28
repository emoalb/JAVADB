import AddAddressAndUpdateEmployee.AddAddressAndUpdateEmployee;
import AddressWithCount.AddressWithCount;
import ContainsEmployee.ContainsEmployee;
import FindEmployeesByFirstName.FindEmployeesByFirstName;
import FindLatestTenProjects.FindLatestTenProjects;
import GetEmployeesWithProject.GetEmployeesWithProject;
import IncreaseSalaries.IncreaseSalaries;
import PrintEmployeesFromRnD.PrintEmployeesFromRnD;
import PrintEmployeesWithSalaryOver5000.PrintEmployeesWithSalaryOver5000;
import RemoveTowns.RemoveTowns;
import com.mysql.cj.xdevapi.Collection;
import entities.Address;
import entities.Department;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private Scanner console;

    Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.console = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println("Enter data for problem 3:");
        ContainsEmployee containsEmployee = new ContainsEmployee(this.entityManager, this.console);
        containsEmployee.execute();
        System.out.println();

        System.out.println("Executing problem 4:");
        PrintEmployeesWithSalaryOver5000 employeesWithSalaryOver5000 = new PrintEmployeesWithSalaryOver5000(this.entityManager);
        employeesWithSalaryOver5000.execute();
        System.out.println();

        System.out.println("Executing problem 5:");
        PrintEmployeesFromRnD printEmployeesFromRnD = new PrintEmployeesFromRnD(this.entityManager);
        printEmployeesFromRnD.execute();
        System.out.println();

        System.out.println("Enter data for problem 6:");
        AddAddressAndUpdateEmployee addAddressAndUpdateEmployee = new AddAddressAndUpdateEmployee(this.entityManager, this.console);
        addAddressAndUpdateEmployee.execute();
        System.out.println();

        System.out.println("Executing problem  7:");
        AddressWithCount addressWithCount = new AddressWithCount(this.entityManager);
        addressWithCount.execute();
        System.out.println();

        System.out.println("Enter data for problem 8:");
        GetEmployeesWithProject getEmployeesWithProject = new GetEmployeesWithProject(this.entityManager, this.console);
        getEmployeesWithProject.execute();
        System.out.println();

        System.out.println("Executing problem  9:");
        FindLatestTenProjects latestTenProjects = new FindLatestTenProjects(this.entityManager);
        latestTenProjects.execute();
        System.out.println();

        System.out.println("Executing problem  10:");
        IncreaseSalaries increaseSalaries = new IncreaseSalaries(this.entityManager);
        increaseSalaries.execute();
        System.out.println();

        System.out.println("Enter data for problem 11:");
        RemoveTowns removeTowns = new RemoveTowns(this.entityManager, this.console);
        removeTowns.execute();

        System.out.println("Enter data for problem 12:");
        FindEmployeesByFirstName findEmployeesByFirstName = new FindEmployeesByFirstName(this.entityManager, this.console);
        findEmployeesByFirstName.execute();
        System.out.println();

    }


}
