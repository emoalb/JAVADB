package AddressWithCount;

import entities.Address;
import interfaces.Executable;

import javax.persistence.EntityManager;
import java.util.List;


public class AddressWithCount implements Executable {
    private final String addressQuery = "FROM Address ORDER BY size(employees) DESC, town.id";

    private EntityManager entityManager;


    public AddressWithCount(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private void addressWithCount() {
        this.entityManager.getTransaction().begin();
        List<Address> addresses = this.entityManager.createQuery(this.addressQuery, Address.class).setMaxResults(10).getResultList();
        addresses.forEach(a -> System.out.printf("%s %s - %d employees", a.getText(), a.getTown().getName(), a.getEmployees().size()).println());
        this.entityManager.getTransaction().commit();
    }

    private void addressWithCountNativeQueryVariant() {
        this.entityManager.getTransaction().begin();
        List<Object[]> addresses = this.entityManager.createNativeQuery("SELECT a.`address_text`, t.`name`, COUNT(e.`employee_id`) AS 'count'\n" +
                "FROM `addresses` AS a\n" +
                "JOIN `employees` AS e ON a.`address_id` = e.`address_id`\n" +
                "JOIN `towns` AS t ON a.`town_id`=t.`town_id`\n" +
                "GROUP BY a.`address_id`\n" +
                "ORDER BY `count` DESC , t.`town_id` LIMIT 10;").getResultList();
        for (Object[] address : addresses) {
            System.out.printf("%s, %s - %s", address[0], address[1], address[2]).println();
        }
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void execute() {
        this.addressWithCount();

    }
}
