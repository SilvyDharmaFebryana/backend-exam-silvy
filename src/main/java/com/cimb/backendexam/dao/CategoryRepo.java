package com.cimb.backendexam.dao;

import com.cimb.backendexam.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    
}