Feature: Add book and list available books
  Background:
    * def saveBookResponse = { "title": "The Komgo book", "id": "#notnull" }

  Scenario: Add a new book to the catalog
    Given url baseUrl
    And path 'api/v1/books'
    And request { "title": "The Komgo book", "reference": "12345" }
    When method post
    Then status 201
    And match response == saveBookResponse

  Scenario: Get list of books
    Given url baseUrl
    And path 'api/v1/books'
    When method get
    Then status 200
    And match response contains saveBookResponse