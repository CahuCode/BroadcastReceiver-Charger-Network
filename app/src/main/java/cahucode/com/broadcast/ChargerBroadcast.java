package cahucode.com.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Creado por Carlos_Code el 21/03/2022.
 * carlos.japon.code@gmail.com
 */

class ChargerBroadcast extends BroadcastReceiver {
    OnStateCharge onStateCharge;

    public ChargerBroadcast(OnStateCharge onStateCharge) {
        this.onStateCharge = onStateCharge;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentString = intent.getAction();
        switch (intentString){
            case Intent.ACTION_POWER_CONNECTED:
                Toast.makeText(context, "Charger Connected!", Toast.LENGTH_SHORT).show();
                onStateCharge.charge(true);
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Toast.makeText(context, "Charger Disconnected!", Toast.LENGTH_SHORT).show();
                onStateCharge.charge(false);
                break;
        }
    }
}


