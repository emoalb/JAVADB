package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder sb = new StringBuilder();
        List<Category> categories = this.categoryRepository.findAllOrderByItemsCountAndPrice();
        categories.forEach(category -> {
            sb.append("Category: ").append(category.getName()).append(System.lineSeparator());
            category.getItems().forEach(item -> {
                sb.append("--Item Name: ").append(item.getName()).append(System.lineSeparator());
                sb.append("--Item Price").append(item.getPrice()).append(System.lineSeparator());
            });
        });

        return sb.toString();
    }
}
