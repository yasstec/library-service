Feature: Borrow and return a book

  Scenario: Create a user, borrow a book, return the book and list borrowed books of the user

     # Create a user
     Given url baseUrl
     And path 'api/v1/users'
     And request { "name": "test user" }
     When method post
     Then status 201
     And match response == { "name": "test user", "id": "#notnull" }
     * def userId = response.id

     # Create a book
     Given path 'api/v1/books'
     And request { "title": "The Komgo book"}
     When method post
     Then status 201
     * def bookId = response.id

     # Borrow the book
     Given path 'api/v1/borrowings'
     And request { "bookId": '#(bookId + "")', "userId": '#(userId + "")' }
     When method post
     Then status 201
     And match response == { "id": "#notnull", "bookId": '#(bookId + "")', "userId": '#(userId + "")', "borrowDate": "#notnull", "returnDate": null, "title": "#notnull" }
     * def borrowingId = response.id

     # Return the book
     Given path 'api/v1/borrowings/' + borrowingId + '/return'
     And request { "bookId": '#(bookId + "")', "userId": '#(userId + "")' }
     When method put
     Then status 200
     And match response == { "id": "#notnull", "bookId": '#(bookId + "")', "userId": '#(userId + "")', "borrowDate": "#notnull", "returnDate": "#notnull" , "title": "#notnull"}

     # List borrowed books of the user
     Given path 'api/v1/borrowings'
     And param userId = userId
     When method get
     Then status 200
     And match response contains { "id": "#notnull", "bookId": '#(bookId + "")', "userId": '#(userId + "")', "borrowDate": "#notnull", "returnDate": "#notnull", , "title": "#notnull" }