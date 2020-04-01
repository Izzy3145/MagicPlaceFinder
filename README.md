# MagicPlaceFinder
An app that allows the user to search for nearby places, providing the essential details for each place.


# Setup:
This project uses the Google Places API and the Gooogle Maps API to retrieve data. In order to use this app, you need to create:
1) Go to the Google Cloud Console and create an account https://console.cloud.google.com/
2) Create a new project
3) Set up billing for that project
4) Enable three APIS; Places API, Maps SDK for Android, Maps Javascript API
4) Within the project, get a Google Places API Key https://developers.google.com/places/web-service/get-api-key
5) Restrict the key to the Google Places API only
6) Within the project, get a Google Maps API Key https://developers.google.com/maps/documentation/android-sdk/get-api-key
5) Restrict the key to the Google Maps API only
7) Clone the repository
8) Create a new properties file named "sercret.keys" in /**/MagicPlaceFinder/app/
9) Enter two lines:
        placesAPIKey="YOUR_PLACES_API_KEY"
        mapsAPIKey="YOUR_MAPS_API_KEY"
