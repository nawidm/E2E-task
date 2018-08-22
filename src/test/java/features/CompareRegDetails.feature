Feature: Using Gov vehicle enquiry service
As a user
I would like to check the details of a vehicle registration number
so that I can confirm the registration number belongs to the correct car

Scenario: Check details of specific vehicle registration number
Given I press start on the mainpage
When I enter car registration number
Then Result details of car match data in file
