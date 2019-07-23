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
        List<Category> categories = this.categoryRepository.findAll();
    /*    categories.sort(new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                int val = Integer.compare(o1.getItems().size(),o2.getItems().size());
               // o1.getItems().forEach(item -> sum1.incrementAndGet(item.getPrice()));
                if(val==1) return
                return val;
            }
        });*/

        return "kura mi qnko";
    }
}
