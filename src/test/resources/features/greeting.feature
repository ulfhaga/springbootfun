Feature: Greeting API
  The API should return a greeting and store the generated message.

  Scenario: Generate and store the default greeting
    Given there are no stored greeting messages
    When I request the default greeting
    Then the greeting response message should be "Hello, World!"
    And the greeting should be stored with message "Hello, World!"
    And the stored greeting should have a creation timestamp

  Scenario: Generate and store a greeting from a query parameter
    Given there are no stored greeting messages
    When I request a greeting for "Spring" using the query parameter
    Then the greeting response message should be "Hello, Spring!"
    And the greeting should be stored with message "Hello, Spring!"
    And the stored greeting should have a creation timestamp

  Scenario: Generate and store a greeting from a path variable
    Given there are no stored greeting messages
    When I request a greeting for "Cursor" using the path variable
    Then the greeting response message should be "Hello, Cursor!"
    And the greeting should be stored with message "Hello, Cursor!"
    And the stored greeting should have a creation timestamp
