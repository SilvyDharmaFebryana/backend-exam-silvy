package com.cimb.backendexam.controller;

import java.util.List;
import java.util.Optional;

import com.cimb.backendexam.dao.CategoryRepo;
import com.cimb.backendexam.dao.MovieRepo;
import com.cimb.backendexam.entity.Category;
import com.cimb.backendexam.entity.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private MovieRepo movieRepo;

    @GetMapping
    public Iterable<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategory(@PathVariable int id) {
        return categoryRepo.findById(id);
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryRepo.save(category);
    }


    @GetMapping("/{categoryId}/movies")
    public List<Movie> getMoviesOnCategory(@PathVariable int categoryId){
        Category findCategory = categoryRepo.findById(categoryId).get();


        return findCategory.getMovie();
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable int categoryId) {
        Category findCategory =  categoryRepo.findById(categoryId).get();

        findCategory.getMovie().forEach(movies -> {
            List<Category> movieCategory = movies.getCategory();
            movieCategory.remove(findCategory);
            movieRepo.save(movies);
        });

        findCategory.setMovie(null);

        categoryRepo.deleteById(categoryId);
    }

    @PutMapping
    public Category updateCategory(@RequestBody Category category) {

        Optional<Category> findCategory = categoryRepo.findById(category.getId());
        
        if (findCategory.toString() == "Optional.empty") {
            throw new RuntimeException("not found");
        }

        // category.setMovie(null);
        return categoryRepo.save(category);

    }





    
}