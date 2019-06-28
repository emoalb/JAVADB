package FindLatestTenProjects;

import entities.Project;
import interfaces.Executable;

import javax.persistence.EntityManager;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FindLatestTenProjects implements Executable {
    private EntityManager entityManager;

    public FindLatestTenProjects(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void execute() {
        this.entityManager.getTransaction().begin();
        List<Project> projects = this.entityManager.createQuery("FROM Project WHERE endDate IS NULL ORDER BY startDate DESC",Project.class)
                .setMaxResults(10).getResultList();
        projects.sort(Comparator.comparing(Project::getName));
        StringBuilder sb  = new StringBuilder();
        projects.forEach(project -> {
            sb.append("Project name: ").append(project.getName()).append(System.lineSeparator());
            sb.append("\t").append("Project Description: ").append(project.getDescription()).append(System.lineSeparator());
            sb.append("\t").append("Project Start Date: ").append(project.getStartDate()).append(System.lineSeparator());
            sb.append("\t").append("Project End Date: ").append(project.getEndDate()).append(System.lineSeparator());
        });
        this.entityManager.getTransaction().commit();
        System.out.println(sb.toString());
    }
}
