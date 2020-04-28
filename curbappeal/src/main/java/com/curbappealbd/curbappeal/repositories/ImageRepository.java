package com.curbappealbd.curbappeal.repositories;

import com.curbappealbd.curbappeal.domain.ImageModel;
import org.springframework.data.repository.CrudRepository;

/**
 * ImageRepository extends the CrudRepository class to allow the ImageModel to be stored in the database
 * @author Fizzor
 */
public interface ImageRepository extends CrudRepository<ImageModel, Long>{

}
