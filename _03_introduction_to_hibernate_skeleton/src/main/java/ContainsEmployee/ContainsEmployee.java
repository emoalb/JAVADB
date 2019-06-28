package ContainsEmployee;

import entities.Employee;
import interfaces.Executable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Scanner;

public class ContainsEmployee implements Executable {
    private EntityManager entityManager;
    private Scanner console;

    public ContainsEmployee(EntityManager entityManager, Scanner console) {
        this.entityManager = entityManager;
        this.console = console;
    }

    @Override
    public void execute() {
        String name = this.console.nextLine().trim();
        this.entityManager.getTransaction().begin();
        try {
            Employee employee = this.entityManager
                    .createQuery("FROM Employee  WHERE CONCAT(firstName,' ',lastName) = :name", Employee.class)
                    .setParameter("name", name)
                    .getSingleResult();
            System.out.println("Yes");
        } catch (NoResultException e) {
            System.out.println("No");
        }

        this.entityManager.getTransaction().commit();
    }
}