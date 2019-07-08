package com.softuni.springadvancedqueringlab.repository;

import com.softuni.springadvancedqueringlab.domain.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    Label findOneById(Long id);

    List<Label> findAllBySubtitle(String subtitle);
}
