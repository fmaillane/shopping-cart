package com.example.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.URISyntaxException;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.model.Article;
import com.example.model.Cart;
import com.example.model.CartAmount;
import com.example.model.CartArticle;
import com.example.model.User;
import com.example.repository.CartArticleRepository;
import com.example.repository.CartRepository;
import com.example.repository.UserRepository;

@Controller
//@RequestMapping(path = "/users")
public class CartController {
	
	private final static String ARTICLES_ENDPOINT = "http://challenge.getsandbox.com/articles/";
	
	@Autowired
	CartRepository cartRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartArticleRepository cartArticleRepository;

	/**
	 * Get shopping cart for a given userId
	 * @param userId
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/cart", method = GET)
    public ResponseEntity<Cart> getCart(@RequestParam(value="userId") Long userId) throws URISyntaxException {
        User user = userRepository.findOne(userId);
        Cart cart = cartRepository.findByUser(user);
        if(user == null || cart == null) {
			return new ResponseEntity<Cart>(cart, HttpStatus.NOT_FOUND);
		}
        return new ResponseEntity<Cart>(cart, HttpStatus.FOUND);
    }
	
	/**
	 * Create a new shopping cart for a given userId
	 * @param userId
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/addcart", method = POST)
    public ResponseEntity<Cart> createCart(@RequestParam(value="userId") Long userId) throws URISyntaxException {
		User user = userRepository.findOne(userId);
		Cart cart = null;
		try {
			cart = new Cart(user);
			cartRepository.save(cart);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<Cart>(cart, HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }
	
	/**
	 * Add an item to a given shopping cart, if the item exists in the cart just increment the quantity, otherwise creates a new record
	 * @param cartId
	 * @param articleId
	 * @param quantity
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/addcartitem", method = POST)
    public ResponseEntity<Cart> addCartItem(@RequestParam(value="cartId") Long cartId, @RequestParam(value="articleId") Long articleId, @RequestParam(value="quantity") Long quantity) throws URISyntaxException {
		CartArticle article = null;
		Iterable<CartArticle> articles = cartArticleRepository.findByCartId(cartId);
		for(CartArticle a: articles) {
			if(a.getArticleId() == articleId) {
				article = a;
				article.setQuantity(article.getQuantity() + 1);
				break;
			}
		} 
		if(article == null){
			String reference = ARTICLES_ENDPOINT + articleId;
			article = new CartArticle(articleId, quantity, reference, cartId);
		}
		cartArticleRepository.save(article);
        return new ResponseEntity<Cart>(cartRepository.findOne(cartId), HttpStatus.CREATED);
    }
	
	/**
	 * List items of given shopping cart
	 * @param cartId
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/cartarticles", method = GET)
    public ResponseEntity<Iterable<CartArticle>> getCartArticles(@RequestParam(value="cartId") Long cartId) throws URISyntaxException {
		Iterable<CartArticle> articles = cartArticleRepository.findByCartId(cartId);
        return new ResponseEntity<Iterable<CartArticle>>(articles, HttpStatus.FOUND);
    }
	
	/**
	 * Remove an item from a given shopping cart, if the quantity is greater than 1 just decrement by 1, otherwise the item is removed
	 * @param cartId
	 * @param id
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/deletecartitem", method = POST)
    public ResponseEntity<HttpStatus> deleteCartItem(@RequestParam(value="cartId") Long cartId, @RequestParam(value="id") Long id) throws URISyntaxException {
		CartArticle article = cartArticleRepository.findOne(id);
		if(article.getQuantity() > 1) {
			article.setQuantity(article.getQuantity() - 1);
			cartArticleRepository.save(article);
		} else {
			cartArticleRepository.delete(article);
		}
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
	
	/**
	 * Remove all items from a give shopping cart
	 * @param cartId
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/clearcart", method = DELETE)
    public ResponseEntity<HttpStatus> clearCart(@RequestParam(value="cartId") Long cartId) throws URISyntaxException {
		Iterable<CartArticle> articles = cartArticleRepository.findByCartId(cartId);
		cartArticleRepository.delete(articles);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
	
	/**
	 * Get the total amount for a given shopping cart
	 * @param cartId
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/amountcart", method = GET)
    public ResponseEntity<CartAmount> getAmountCart(@RequestParam(value="cartId") Long cartId) throws URISyntaxException {
		Iterable<CartArticle> articles = cartArticleRepository.findByCartId(cartId);
		CartAmount cartAmount = null;
		if(!articles.iterator().hasNext()) {
			return new ResponseEntity<CartAmount>(cartAmount, HttpStatus.NOT_FOUND);
		}
		float total = 0;
		try {
			for(CartArticle ca: articles) {
				RestTemplate restTemplate = new RestTemplate();
				Article a = restTemplate.getForObject(ca.getReference(), Article.class);
			    total = total + (a.getPrice() * ca.getQuantity());
			}
		} catch (RestClientException e) {
			return new ResponseEntity<CartAmount>(cartAmount, HttpStatus.BAD_REQUEST);
		} 
		
		cartAmount = new CartAmount(cartId, total);
        return new ResponseEntity<CartAmount>(cartAmount, HttpStatus.OK);
    }
	
}
