package PrintEmployeesWithSalaryOver5000;

import entities.Employee;
import interfaces.Executable;

import javax.persistence.EntityManager;
import java.util.List;


public class PrintEmployeesWithSalaryOver5000 implements Executable {
    private EntityManager entityManager;

  public  PrintEmployeesWithSalaryOver5000(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    @Override
    public void execute() {
        this.entityManager.getTransaction().begin();
        List<Employee> employeesWithSalariesOver50000 = this.entityManager.createQuery("FROM Employee WHERE salary>50000", Employee.class).getResultList();
        employeesWithSalariesOver50000.forEach(employee ->
                System.out.println(employee.getFirstName()));
        this.entityManager.getTransaction().commit();
    }
}
