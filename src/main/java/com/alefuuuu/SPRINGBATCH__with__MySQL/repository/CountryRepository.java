package com.alefuuuu.SPRINGBATCH__with__MySQL.repository;

import com.alefuuuu.SPRINGBATCH__with__MySQL.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CountryRepository extends JpaRepository<Country, Serializable> {
}
