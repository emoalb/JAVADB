package RemoveTowns;

import entities.Town;
import interfaces.Executable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Scanner;

public class RemoveTowns implements Executable {
    private final String getTownQuery = "FROM Town WHERE name = :name";
    private final String deleteAddressesQuery = "DELETE Address WHERE town.id= :id";
    private final String deleteTownsQuery = "DELETE Town WHERE id= :id";
    private EntityManager entityManager;
    private Scanner console;

    public RemoveTowns(EntityManager entityManager, Scanner console) {
        this.entityManager = entityManager;
        this.console = console;
    }

    @Override
    public void execute() {
        String name = this.console.nextLine();

        this.entityManager.getTransaction().begin();
        try {
            Town town = entityManager.createQuery(this.getTownQuery, Town.class).setParameter("name", name).getSingleResult();
            this.entityManager.getTransaction().commit();
            this.entityManager.getTransaction().begin();
            int deleteAddressCount = this.entityManager.createQuery(this.deleteAddressesQuery).setParameter("id", town.getId()).executeUpdate();
            this.entityManager.getTransaction().commit();
            this.entityManager.getTransaction().begin();
            int deleteTownCount = this.entityManager.createQuery(this.deleteTownsQuery).setParameter("id", town.getId()).executeUpdate();
            this.entityManager.getTransaction().commit();
            String address = deleteAddressCount == 1 ? "address" : "addresses";
            System.out.printf("%d %s in %s deleted", deleteAddressCount, address, town.getName()).println();
        } catch (NoResultException e) {
            System.out.println("No such town was found!");
            this.entityManager.getTransaction().commit();
        }

    }
}
