package io.mateo.mapstest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.content.pm.PackageManager.GET_META_DATA;
import static android.content.pm.PackageManager.NameNotFoundException;


public class Main extends Activity {

    private final String googleMaps = "com.google.android.apps.maps";
    private final String mapsUri = "market://details?id=" + googleMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inflate the buttons
        Button checkedButton = (Button) findViewById(R.id.checked_button);
        Button uncheckedButton = (Button) findViewById(R.id.unchecked_button);

        // When checked button is pressed...
        checkedButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if Maps is installed
                if (!isMapsInstalled()) {
                    // Prompt to install Maps from Google Play
                    promptInstall();
                } else {
                    // Maps is already installed
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Google Maps is installed.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        // When unchecked button is pressed...
        uncheckedButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set up to launch Maps
                Intent mapsIntent = new Intent();
                mapsIntent.setAction(Intent.ACTION_VIEW);
                mapsIntent.setClassName(googleMaps, googleMaps+".MapsActivity");
                try {
                    // Attempt to launch Maps
                    startActivity(mapsIntent);
                } catch (ActivityNotFoundException e) {
                    // Failed to launch, display exception to user
                    raiseException(e.getMessage());
                }
            }
        });

    }

    /**
     * Check to see if Google Maps is installed on device
     *
     * @return boolean
     */
    private boolean isMapsInstalled() {

        boolean isInstalled = true;

        try {
            getPackageManager().getPackageInfo(googleMaps, GET_META_DATA);
        } catch (NameNotFoundException e) {
            isInstalled = false;
        }

        return isInstalled;
    }

    /**
     * If Google Maps is not installed on device, then this method is called to prompt
     * the user to install Google Maps
     */
    private void promptInstall() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.maps_dialog)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapsUri));
                        startActivity(mapsIntent);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Google Maps is required for this app.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * When an exception has occurred, this method is called to display an {@link android.app.AlertDialog}
     * to show the user the exception message.
     *
     * @param message - The exception message
     */
    private void raiseException(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message).setPositiveButton("Dismiss", null);

        AlertDialog alert = builder.create();
        alert.show();
    }
}
