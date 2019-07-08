package com.softuni.springadvancedqueringlab.controller;

import com.softuni.springadvancedqueringlab.domain.entities.Shampoo;
import com.softuni.springadvancedqueringlab.domain.entities.Size;
import com.softuni.springadvancedqueringlab.repository.LabelRepository;
import com.softuni.springadvancedqueringlab.repository.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class AppController implements CommandLineRunner {
    private final LabelRepository labelRepository;
    private final ShampooRepository shampooRepository;

    @Autowired
    public AppController(LabelRepository labelRepository, ShampooRepository shampooRepository) {
        this.labelRepository = labelRepository;
        this.shampooRepository = shampooRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String labelSubtitle = scanner.nextLine();
        this.labelRepository
                .findAllBySubtitle(labelSubtitle)
                .forEach(label ->
                        System.out.printf("%d. %s <-> %s \\r\n", label.getId(), label.getTitle(), label.getSubtitle()));
        System.out.println(this.labelRepository.findOneById(4L).getTitle());
        String shapmooSize = scanner.nextLine().toUpperCase();
        this.shampooRepository.findBySize(Size.valueOf(shapmooSize)).forEach(shampoo -> System.out.println(String.format("%s %s %.2flv",shampoo.getBrand(),shampoo.getSize(),shampoo.getPrice())));
        List<Shampoo> shampoosFromDb = this.shampooRepository.findBySize(Size.valueOf(shapmooSize));
        for (Shampoo shampoo : shampoosFromDb) {
            shampoo.setPrice(shampoo.getPrice().add(BigDecimal.valueOf(100L)));
        }
        this.shampooRepository.saveAll(shampoosFromDb);


    }
}
