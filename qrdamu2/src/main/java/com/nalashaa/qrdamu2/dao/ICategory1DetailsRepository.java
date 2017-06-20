package com.nalashaa.qrdamu2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nalashaa.qrdamu2.entity.Category1Entity;

@Repository
public interface ICategory1DetailsRepository extends JpaRepository<Category1Entity, Long>{

}
