# Asteroid Radar
Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view all the detected asteroids in a period of time, their data (Size, velocity, distance to Earth) and if they are potentially hazardous.

The app is consists of three screens: A Main screen with a list of all the detected asteroids(the main screen will also show the NASA image.), a Details screen that is going to display the data of that asteroid once it´s selected in the Main screen list and which shows images of the day in full screen.

### Screenshots

![Screenshot 1](/Screenshots/Screenshot_1631291802.png)
![Screenshot 2](/Screenshots/Screenshot_1631291811.png)
![Screenshot 3](/Screenshots/Screenshot_1631291815.png)
![Screenshot 4](/Screenshots/Screenshot_1631291823.png)

### Installation

To get the project running on your local machine, you need to follow these steps:

**Step 1: Clone the repo**

Use this to clone it to your local machine:
```bash
git clone https://github.com/udacity/REPOSITORY_NAME.git
```

**Step 2: Check out the ‘master’ branch**

This branch is going to let you start working with it. The command to check out a branch would be:

```bash
git checkout master
```

**Step 3: Get an API Key from NASA**

You need to get an API key, which can be obtained from the link https://api.nasa.gov/, just fill in the fields in the form and click Register.

```bash
AsteroidRadar/app/src/main/java/com/cryoggen/asteroidradar/Constants.kt
```
In file Constants.kt Replace the word NASA_key with your own key

**Step 4: Run the project**

Open the project in Android Studio and click the Run ‘app’ button, check that it runs correctly and you can see the app in your device or emulator.

#### App use libraries and technologies:

- ViewModel
- Room
- LiveData
- Data Binding
- Navigation
- Retrofit2
- Picasso
- Moshi
- Recyclerview
- Repository
