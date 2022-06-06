@Amazon
Feature: Amazon Tests to log the second highest television by brand

  Scenario: Log the second highly priced Samsung Television
    Given Launch the browser and hit the url
    Then the user should land on the amazon home page
    And click on the hamburger icon
    And click on TV, Appliances, Electronics section
    When the subsection gets displayed, click on Televisions
    When the user lands on the television section, scroll down to brands and select "Samsung"
    And sort the list by price in descending order
    Then capture the second item in the search result and print the description