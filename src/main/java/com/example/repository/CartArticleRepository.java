package com.example.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.model.CartArticle;

@RepositoryRestResource
public interface CartArticleRepository extends PagingAndSortingRepository<CartArticle, Long> {

	Iterable<CartArticle> findByCartId(@Param("cartId") long cartId);
}
