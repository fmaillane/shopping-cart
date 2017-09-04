/**
 * 
 */
package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CartAmount {
	
	private long cartId;
	private float total;
	
	public CartAmount(long cartId, float total) {
		this.cartId = cartId;
		this.total = total;
	}
	
	public CartAmount() {
		
	}

	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	


}
