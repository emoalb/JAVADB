package GetEmployeesWithProject;


import entities.Employee;
import interfaces.Executable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class GetEmployeesWithProject implements Executable {
    private EntityManager entityManager;
    private Scanner console;

    public GetEmployeesWithProject(EntityManager entityManager, Scanner console) {
        this.entityManager = entityManager;
        this.console = console;
    }

    @Override
    public void execute() {
        int id = Integer.parseInt(this.console.nextLine());
        this.entityManager.getTransaction().begin();
        Employee employee = this.entityManager.createQuery("FROM Employee WHERE id=:id", Employee.class)
                .setParameter("id", id).getSingleResult();
        this.entityManager.getTransaction().commit();
        this.entityManager.getTransaction().begin();
        List<Object> projects = this.entityManager.createNativeQuery("SELECT p.name\n" +
                "FROM `employees_projects` AS emp_prj\n" +
                "INNER JOIN `employees` AS e ON e.`employee_id` = emp_prj.`employee_id`\n" +
                "INNER JOIN `projects` AS p ON p.`project_id` = emp_prj.`project_id`\n" +
                "WHERE e.`employee_id`=(?1)\n" +
                "ORDER BY p.`name`;").setParameter(1, employee.getId()).getResultList();
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
        System.out.printf("%s %s - %s", employee.getFirstName(), employee.getLastName(), employee.getJobTitle()).println();
        projects.forEach(System.out::println);
    }
}
