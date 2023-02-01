# job-challenge-auto-tester-android

This is test assigment for Aleksandr Lozhkovoi - Android Test Automation Engineer 

## Tests at different levels

    Create a hiking spot - was automated on e2e level, as it is almost main e2e flow for user, and it is very important to check that it works like a real user
    Delete a hiking spot - was automated on integration test level, test spot was prepared on db level to speed up test execution time
    Scroll through the list of hiking spots - was implemented on component test level without using real network, as it is not required to check swipe
    Integration with other navigation apps to navigate to the spot. - was automated on integration test level, test spot was prepared on db level to speed up test execution time. But also it is important to check manually from time to time as the user may have multiple map apps installed, which is different from a flow test

## PS

* I didn't use the page object, since the application is quite simple and the use of the pattern looks like an overhead
* In several cases, I used the preparation of test data through the database to speed up tests

## Bugs / improvements

* App crashes when tap on map, which makes AddSpotTest flacky, stackrace
```
  Fatal signal 11 (SIGSEGV), code 1 (SEGV_MAPERR), fault addr 0x53e5896d in tid 15684 (secrethikespots), pid 15684 (secrethikespots)
  2023-02-01 09:45:40.376 15765-15765 DEBUG                   pid-15765                            A  pid: 15684, tid: 15684, name: secrethikespots  >>> de.komoot.android.secrethikespots <<<
  2023-02-01 09:45:40.589 15765-15765 DEBUG                   pid-15765                            A        #00 pc 00058cd7  /data/app/~~eexgm1DIeEWXcmEKrF5fAQ==/de.komoot.android.secrethikespots-JNFg152dIAt2p2lJms4k-w==/base.apk!libmapbox-gl.so (offset 0xcd000) (BuildId: 857c356e06e451715ed7329d48d37731b81c5bec)
  2023-02-01 09:45:40.589 15765-15765 DEBUG                   pid-15765                            A        #01 pc 002493e2  /data/app/~~eexgm1DIeEWXcmEKrF5fAQ==/de.komoot.android.secrethikespots-JNFg152dIAt2p2lJms4k-w==/base.apk!libmapbox-gl.so (offset 0xcd000) (BuildId: 857c356e06e451715ed7329d48d37731b81c5bec)                    A        #12 pc 001ee9df  /data/app/~~eexgm1DIeEWXcmEKrF5fAQ==/de.komoot.android.secrethikespots-JNFg152dIAt2p2lJms4k-w==/base.apk!libmapbox-gl.so (offset 0xcd000) (BuildId: 857c356e06e451715ed7329d48d37731b81c5bec)
  2023-02-01 09:45:40.590 15765-15765 DEBUG                   pid-15765                            A        #13 pc 001ed227  /data/app/~~eexgm1DIeEWXcmEKrF5fAQ==/de.komoot.android.secrethikespots-JNFg152dIAt2p2lJms4k-w==/base.apk!libmapbox-gl.so (offset 0xcd000) (BuildId: 857c356e06e451715ed7329d48d37731b81c5bec)
  2023-02-01 09:45:41.090   569-683   InputDispatcher         system_process                       E  channel '7c4835d de.komoot.android.secrethikespots/de.komoot.android.secrethikespots.ui.AddSpotActivity (server)' ~ Channel is unrecoverably broken and will be disposed!
  ```

* It is necessary to to add a mark of the selected place on the map, now the process of choosing a point is absolutely opaque for the user

* User is not able to open a spot when it has broken image

* Scroll didn't work by default. I fixed it by setting userScrollEnabled = true.

* There is no splash screen. It is better to add splash to give user better UX.

* There is no validation for empty spot name. 


## Environment
Pixel 5 emulator, API 30

## 5 tests which I consider should be automated as UI tests and run before every release. Justifing my decision.

Based on  scenarios, I selected the 5 tests that should be automated as UI tests and run before every release are:

Scenario: Login as Premium user.
This test is important to ensure that the basic functionality of the app, i.e. logging in, works as expected and the user can access the app with valid premium credentials.

Scenario: Discover feed is populated with the content.
This test is important to check that the discover feed, a key feature of the app, is working as expected and populated with the required content.

Scenario: Plan 3+ waypoints oneway route.
This test is important to ensure that the route planning feature, another key aspect of the app, works as expected and allows the user to plan a route with multiple waypoints.

Scenario: Start navigation on planned tour in unlocked region.
This test is important to ensure that the navigation feature of the app works as expected, even when the user has not unlocked all the regions required for the tour.

Scenario: Create highlight.
This test is important to check that the highlight creation feature works as expected, allowing the user to create a highlight and contribute to it with photos.