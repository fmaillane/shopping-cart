/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldCreateCart() throws Exception {
		mockMvc.perform(post("/addcart?userId={userId}", 1).content("")).andExpect(status().isCreated());
	}

	@Test
	public void shouldCreateCartArticle() throws Exception {
		mockMvc.perform(post("/addcart?userId={userId}", 1).content(""));
		mockMvc.perform(post("/addcartitem?cartId={cartId}&articleId={articleId}&quantity={quantity}", 1,2,4).content("")).andExpect(status().isCreated());
	}
	
	@Test
	public void shouldRetrieveCartArticles() throws Exception {
		mockMvc.perform(post("/addcart?userId={userId}", 1).content(""));
		mockMvc.perform(post("/addcartitem?cartId={cartId}&articleId={articleId}&quantity={quantity}", 1,2,4).content(""));
		mockMvc.perform(get("/cartarticles?cartId={cartId}", 1)).andExpect(status().isFound()).andExpect(jsonPath("$[0].articleId").value(2)).
		andExpect(jsonPath("$[0].reference").value("http://challenge.getsandbox.com/articles/2"));
	}

	@Test
	public void shouldRetrieveCartAmount() throws Exception {
		mockMvc.perform(post("/addcart?userId={userId}", 1).content(""));
		mockMvc.perform(post("/addcart?userId={userId}", 2).content(""));
		mockMvc.perform(post("/addcartitem?cartId={cartId}&articleId={articleId}&quantity={quantity}", 2,1,3).content(""));
		mockMvc.perform(post("/addcartitem?cartId={cartId}&articleId={articleId}&quantity={quantity}", 2,2,1).content(""));
		mockMvc.perform(get("/amountcart?cartId={cartId}", 2)).andExpect(status().isOk()).andExpect(jsonPath("$.total").value(10.7));
	}

}