package PrintEmployeesFromRnD;


import entities.Department;
import entities.Employee;
import interfaces.Executable;

import javax.persistence.EntityManager;
import java.util.List;

public class PrintEmployeesFromRnD implements Executable {
    private final String departmentName = "Research and Development";
    private EntityManager entityManager;

    public PrintEmployeesFromRnD(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void execute() {

        this.entityManager.getTransaction().begin();
        Department department = this.entityManager.createQuery("FROM Department WHERE name =:departmentName", Department.class)
                .setParameter("departmentName", this.departmentName).getSingleResult();
        this.entityManager.getTransaction().commit();
        this.entityManager.getTransaction().begin();
        List<Employee> employees = this.entityManager.createQuery("FROM Employee WHERE department.id = :id ORDER BY salary ASC , id ASC", Employee.class)
                .setParameter("id", department.getId()).getResultList();
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
        StringBuilder sb = new StringBuilder();
        employees.forEach(employee ->
                sb.append(employee.getFirstName()).append(" ")
                        .append(employee.getLastName()).append(" from ")
                        .append(employee.getDepartment().getName()).append(" - $")
                        .append(employee.getSalary()).append(System.lineSeparator())
        );
        System.out.print(sb.toString());
    }
}
