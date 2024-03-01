package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Category;
import pl.coderslab.repository.CategoryRepository;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setName(name);
            return categoryRepository.save(newCategory);
        });
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }
}
