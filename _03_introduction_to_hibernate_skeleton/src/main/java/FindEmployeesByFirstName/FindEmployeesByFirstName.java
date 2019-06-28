package FindEmployeesByFirstName;

import entities.Employee;
import interfaces.Executable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class FindEmployeesByFirstName implements Executable {
    private EntityManager entityManager;
    private Scanner console;

    public FindEmployeesByFirstName(EntityManager entityManager, Scanner console) {
        this.entityManager = entityManager;
        this.console = console;

    }

    @Override
    public void execute() {
        String sequence = this.console.nextLine();

        this.entityManager.getTransaction().begin();
        List<Employee> employees = this.entityManager.createQuery("FROM Employee WHERE SUBSTRING(firstName,1,:length)= :sequence", Employee.class)
                .setParameter("length", sequence.length()).setParameter("sequence", sequence).getResultList();

        employees.forEach(employee -> {
            System.out.printf("%s %s - %s - ($%s)", employee.getFirstName(), employee.getLastName()
                    , employee.getJobTitle(), employee.getSalary().toString()).println();
        });

        this.entityManager.getTransaction().commit();
    }
}
