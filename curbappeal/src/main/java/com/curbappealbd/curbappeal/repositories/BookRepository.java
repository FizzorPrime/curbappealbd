package com.curbappealbd.curbappeal.repositories;

import com.curbappealbd.curbappeal.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
