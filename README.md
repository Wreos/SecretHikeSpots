# job-challenge-auto-tester-android

This repository hosts the challenge for the people applying for the Automated Tester - Android role.  

## App Description
SecreteHikeSpot allows users to manage a list of their favourite secret hiking spots (a Spot)

Each Spot has:
 - a name
 - a location
 - a preview image

The user should be able to see their previously saved Spots in a list with the newest first.  
Users should be able to create new Spots by selecting a point on a map and giving it a name.  
Users should be able to edit the name after creating a Spot.  
Users should be able to use other apps to navigate to their Spots.  
Users should be able to remove a Spot from their list.  

## App Functionality

### A list of your Spots
`SpotListActivity`. Written with compose

Click on the name to edit the Spot (done in a dialog fragment: `SpotFragment`)  
Click on the preview image to open a navigation intent to the location  
Swipe left to delete a Spot  
Click on the + floating action button to add a new Spot  

### Adding a new Spot
`AddSpotActivity`. ViewModels & Fragment that uses MapLibre to show a map with user's current location  

The map should center on your current location when opened  
The map can be panned, zoomed, rotated etc using standard gestures  
Clicking on the map opens the `SpotFragment` to add a name and create a new Spot  

### Delays
Disk and network operations are slow, the `SecretSpotRepo` `.add`, `.update` and `.delete` methods add an artificial delay to represent this.
