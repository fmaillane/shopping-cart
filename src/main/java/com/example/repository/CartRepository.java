package com.example.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.model.Cart;
import com.example.model.User;

@RepositoryRestResource
public interface CartRepository extends PagingAndSortingRepository<Cart, Long> {

	Cart findByUser(@Param("user") User user);

}
