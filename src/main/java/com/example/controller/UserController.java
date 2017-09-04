package com.example.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.User;
import com.example.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	/**
	 * Get user for given id
	 * @param id
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/user", method = GET)
    public ResponseEntity<User> getUser(@RequestParam(value="id") Long id) throws URISyntaxException {
        User user = userRepository.findOne(id);
        return new ResponseEntity<User>(user, HttpStatus.FOUND);
    }
	
	/**
	 * Get all users
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/users", method = GET)
    public ResponseEntity<Iterable<User>> getAllUsers() throws URISyntaxException {
        return new ResponseEntity<Iterable<User>>(userRepository.findAll(), HttpStatus.CREATED);
    }
	
	/**
	 * Add a new user
	 * @param user
	 * @return
	 * @throws URISyntaxException
	 */
    @RequestMapping(value="/adduser", method = POST)
    public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
        userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
	
}
