package AddAddressAndUpdateEmployee;

import entities.Address;
import entities.Employee;
import entities.Town;
import interfaces.Executable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Scanner;

public class AddAddressAndUpdateEmployee implements Executable {
    private EntityManager entityManager;
    private Scanner console;

    public AddAddressAndUpdateEmployee(EntityManager entityManager,Scanner console) {
        this.entityManager = entityManager;
        this.console=console;
    }

    @Override
    public void execute() {
        try {
            String lastName = this.console.nextLine().trim();
            Address address = new Address();
            this.entityManager.getTransaction().begin();
            Town town = this.entityManager.createQuery("FROM Town WHERE id = 32", Town.class).getSingleResult();
            address.setText("Vitoshka 15");
            address.setTown(town);
            this.entityManager.persist(address);
            this.entityManager.getTransaction().commit();
            this.entityManager.getTransaction().begin();
            Employee employee = this.entityManager.createQuery("FROM Employee  WHERE lastName = :lastName", Employee.class).setParameter("lastName", lastName).getSingleResult();
            employee.setAddress(address);
            this.entityManager.flush();
        }catch (NoResultException e){
            System.out.println("Name not exist in database");
        }
        this.entityManager.getTransaction().commit();
    }
}
