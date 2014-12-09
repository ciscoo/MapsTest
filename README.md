MapsTest
===================

This app simply tests to see if Google Maps is installed on an Android device. If Google Maps is not installed, then the user has the option to install it or not.

----------

MapsTest also (hopefully) fixes a bug in UW-Parksides KatApp where the app crashes if Google Maps is not installed. Google Maps comes installed on **stock** ROMs of Android, but not always on **custom** ROMs.

    FATAL EXCEPTION: main
    Process: org.kenosha.katapp, PID: 8668
    android.content.ActivityNotFoundException: Unable to find explicit activity class {com.google.android.apps.maps/com.google.android.maps.MapsActivity}; have you declared this activity in your AndroidManifest.xml?

The above exception is raised when the `Route` button on the KatApp is pushed. The button attempts to launch Google Maps, but crashes when Google Maps' main activity is not found.