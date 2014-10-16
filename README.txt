########
New bug : when navigating from mainActivity to the ListActivity , List persists its state(WTF)

todo:
-- grab user location, update and reuse the lat/long(splash activity is a good place for this)
-- store fetched list from the api in shared prefs, based on the userInput generate a hash and store the hash and the api result as a key value pair in the sharedPrefs, if hash changes new api call happens
-- apply settingsActivity
-- map
-- phone intent
-- we might want to display the menu in all activities ???