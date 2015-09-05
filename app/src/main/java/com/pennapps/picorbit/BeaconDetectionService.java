package com.pennapps.picorbit;

/**
 * Created by championswimmer on 5/9/15.
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class BeaconDetectionService extends Service implements BeaconConsumer {

    protected static final String TAG = "BeaconService";
    private BeaconManager beaconManager;
    private boolean temp = false;

    public BeaconDetectionService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {


        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.d(TAG, "" + beacons + beacons.iterator().next().getDistance());
                    for (Beacon beac : beacons) {
                        Log.d(TAG, "Going to start notification " + beac.getDistance());
                        //We are in a beacon's range
                        Log.d(TAG, " beacon s" + beaconManager.getRangedRegions().size());
                        Log.d(TAG, " beacon s m" + beaconManager.getMonitoredRegions().size());


                    }
                }
            }
        });

        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.d(TAG, "didEnterRegion ");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.d(TAG, "didExitRegion ");

            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", Identifier.parse("50454E4E-4150-5053-4841-434B32303135"),
                    null, null));
        } catch (RemoteException e) {
        }

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myRegion", Identifier.parse("50454E4E-4150-5053-4841-434B32303135"),
                    null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}

