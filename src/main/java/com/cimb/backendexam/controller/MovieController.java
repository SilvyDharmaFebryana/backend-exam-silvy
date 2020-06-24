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
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @PostMapping
    public Movie addMovies(@RequestBody Movie movie) {
        return movieRepo.save(movie);
    }

    @GetMapping
    public Iterable<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Movie> getMovie(@PathVariable int id) {
        return movieRepo.findById(id);
    }
    

    @PutMapping("/{movieId}/category/{categoryId}") // add category di movie
    public Movie addCategoryToMovie(@PathVariable int movieId, @PathVariable int categoryId) {
        Movie findMovie = movieRepo.findById(movieId).get();

        Category findCategory = categoryRepo.findById(categoryId).get();

        findMovie.getCategory().add(findCategory);

        return movieRepo.save(findMovie);
    }

    @PutMapping
    public Movie updateMovie(@RequestBody Movie movie) {

        Optional<Movie> findMovie = movieRepo.findById(movie.getId());
        
        if (findMovie.toString() == "Optional.empty") {
            throw new RuntimeException("not found");
        }
        // findMovie.setId(0);   
        return movieRepo.save(movie);
    }



    // putusin hubungannya si category dan movie di mov_cat
    @DeleteMapping("/{id}/category/{categoryId}") 
    public void deleteMovieCategory(@PathVariable int id, @PathVariable int categoryId) {

        Movie findMovie = movieRepo.findById(id).get();

        Category findCategory = categoryRepo.findById(categoryId).get();

        findMovie.getCategory().forEach(category -> {
            List<Movie> movieCategory = category.getMovie();
            movieCategory.remove(findMovie);
            categoryRepo.save(category);
        });

        findCategory.getMovie().forEach(movie -> {
            List<Category> moviCategories = movie.getCategory();
            moviCategories.remove(findCategory);
            movieRepo.save(movie);
        });

        // findMovie.setCategory(null);
        findCategory.setMovie(null);
        // movieRepo.deleteById(id);
        // return "has been deleted";

    }


    @DeleteMapping("/{id}") 
    public void deleteMovie(@PathVariable int id) {

        Movie findMovie = movieRepo.findById(id).get();

        findMovie.getCategory().forEach(category -> {
            List<Movie> movieCategory = category.getMovie();
            movieCategory.remove(findMovie);
            categoryRepo.save(category);
        });

        movieRepo.deleteById(id);

    }

    









}