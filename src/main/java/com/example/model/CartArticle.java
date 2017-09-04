/**
 * 
 */
package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CartArticle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long articleId;
	private long quantity;
	private String reference;
	private long cartId;
	
	public CartArticle() {
		
	}
	
	public CartArticle(long articleId, long quantity, String reference, long cartId) {
		this.articleId = articleId;
		this.quantity = quantity;
		this.reference = reference;
		this.cartId = cartId;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

}
