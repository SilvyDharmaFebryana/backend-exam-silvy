package com.cimb.backendexam.dao;

import com.cimb.backendexam.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, Integer>{
    
}