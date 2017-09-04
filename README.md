# shopping-cart

- The followsing users are saved into database at app start

  id   firstName   lastName
  1    "John"      "Doe"
  1    "Fernando"  "Maillane"
  
  
- Create a shopping cart
  
  You have to send a valid userId in the POST request
  Example:
  http://localhost:8080/addcart?userId=1
  
  
- Add item to a cart
  
  You need to send cartId, articleId and quantity in the POST request
  Example:
  http://localhost:8080/addcartitem?cartId=1&articleId=2&quantity=1
  
  
- Get cart articles
 
  You need to send cartId in the GET request
  Example:
  http://localhost:8080/cartarticles?cartId=1
  
  
- Get cart
 
  You need to send userId in the GET request (asume an user can have only one cart at time)
  Example:
  http://localhost:8080/cart?userId=1
  
  
- Delete cart item
 
  You need to send cartId and item id in the POST request, if the quantity is greater than 1 just decrement by 1, otherwise the item is     removed
  Example:
  http://localhost:8080/deletecartitem?cartId=1&id=2
  
  
- Clear cart
 
  You need to send cartId in the DELETE request
  Example:
  http://localhost:8080/clearcart?cartId=1
  
  
- Get total cart amount
 
  You need to send cartId in the GET request
  Example:
  http://localhost:8080/amountcart?cartId=2
  
  
You must execute this examples in the same order in order to works

There are endpoints to add and get users also:

  GET:
  http://localhost:8080/user?id={userId}
  
  POST:
  http://localhost:8080/adduser
   Body:
   {
     firstName:{firstName},
     lastName:{lastName}
   }
