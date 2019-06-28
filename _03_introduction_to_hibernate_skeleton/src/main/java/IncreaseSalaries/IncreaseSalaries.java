package IncreaseSalaries;

import entities.Employee;
import interfaces.Executable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class IncreaseSalaries implements Executable {
    private EntityManager entityManager;

    public IncreaseSalaries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void execute() {
        try {
            this.entityManager.getTransaction().begin();
            int count = this.entityManager.createQuery("UPDATE Employee SET salary=salary+salary*0.12 WHERE department.id=1 OR department.id=2 OR department.id=4 OR department.id=11 ").executeUpdate();
            this.entityManager.getTransaction().commit();
            if (count == 0) {
                throw new NoResultException("No record were updated");
            }
            this.entityManager.getTransaction().begin();
            List<Employee> employees = this.entityManager.createQuery("FROM Employee WHERE department.id=1 OR department.id=2 OR department.id=4 OR department.id=11", Employee.class).getResultList();
            this.entityManager.getTransaction().commit();
            employees.forEach(employee -> {
                System.out.printf("%s %s ($%s)",employee.getFirstName(),employee.getLastName(),employee.getSalary().toString()).println();
            });

        }catch (Exception e){
           System.out.println(e.getMessage());
        }

    }
}
