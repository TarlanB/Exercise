Feature: Adding items to the cart

  Scenario: Add items to the cart
    Given user on the dashboard page of amazon
    When user enters the text "shoes for men" in the search bar
    And  user chooses the last auto-complete option from the dropdown menu suggestions
    And  user adds the first shoe to the Cart with a quantity of "3"
    Then user goes to the cart and the total price and quantity of shoes are correct
    And  user deletes the items from the cart
    Then cart should be empty
