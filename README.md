# job-challenge-auto-tester-android

This is test assigment for Aleksandr Lozhkovoi - Android Test Automation Engineer 

## Tests at different levels

    Create a hiking spot - can be automated as this feature has a clear user journey and can be performed repeatedly. 
    Delete a hiking spot - this can be automated too as it can be included in the UI tests for hiking spot creation. I would suggest this is also manually tested occasionally too (as new O/S's are released) as it is a very specific swipe action animation. 
    Scroll through the list of hiking spots - was implemented on component test level without using real network
    Integration with other navigation apps to navigate to the spot. - I would suggest manually testing this feature to validate how the external tool is working in conjunction with the rest of the application workflow, writing tests that integrate with third parties may not be stable or give much return on investment. What should be tested is the payload sent over to the third party tool. 


## UI tests

### I have written tests for the user flows in the BDD format as this tends to be a format and framework which lends itself to easy reading, simple documentation, more uptake from other team members and easier handover to other QA colleagues if required.


## Bugs / improvements

* Several different accessibility issues but the one which I had to fix to save my eyesight was the word SAVE on the location name dialog box. I resolved this by changing the word SAVE to blue and the word CANCEL to red as it is a destructive action.

* There was a crash that occured when a location was not selected and the SAVE button was clicked, this crashed the app due to line 183 in the LocationPickerViewController fatalerror.

* Scroll didn't work by default. I fixed it by setting userScrollEnabled = true.

* There is no splash screen. It is better to add splash to give user better UX.

* There is no validation for empty spot name. 

## 5 tests which I consider should be automated as UI tests and run before every release. Justifing my decision.

### 1 @ios @android @smoke
      Scenario: Login as Premium user

      When User logs in to the app with valid Premium credentials
      Then He should get an access to the app
      
      I feel this could be quite a good, repetative test that could be expanded on for premium customers and give confidence to those who pay and deserve good service. 

### 2 @ios @android @smoke
      Scenario: Like the tour

      When User taps on the *Like* button on the other user tour
      Then Should see an indication of the tour being like
      And The counter should should reflect number of likes
      
      There is a lot to cover here, interaction with the app that lends itself to a good stable UI test. It also has a value that can be increased and reset several times. 

### 3 @ios @android @smoke
      Scenario: Plan 3+ waypoints oneway route

      When User adds POI as a waypoint
      And User adds Highlight as a waypoint
      And User adds map point as waypoint
      Then The route is planned and details are displayed
      
      Lots of interaction here to create a test that can be automated and give good return on investment of time and energy. 

### 4 @ios @android @smoke
      Scenario: Save planned route

      When User saves the route
      Then User can edit the route's name
      And The route is planned and saved
      
      This I would think is basic functionality and something that is often manually tested, it would be good to automate so that every release gives confidence that basic functionality is not regressed in any way. 

### 5 @ios @android @smoke
      Scenario: User cannot download a tour for offline use as Regular user

      Given User is not premium user 
      And User didn't buy region for specific tour         
      When He stores tour for the offline use
      Then Should see the Region barrier screen
      
      We need to ensure that only premium members have access to benefits and this test being automated could have the potential to save the company money by spotting if paid functionality had accdentially found its way in the regualar user space. High value test that should be automated. 

