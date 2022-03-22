package cahucode.com.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements OnStateCharge {
    Snackbar snackbar;
    TextView txtcharge;
    RelativeLayout relativeLayout_main;
    ChargerBroadcast chargerBroadcast;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        // Broadcast charge
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(chargerBroadcast, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkStateReceiver);
        unregisterReceiver(chargerBroadcast);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout_main = findViewById(R.id.rl_main);
        txtcharge = findViewById(R.id.txtcharge);
        // Broadcast charge
        chargerBroadcast = new ChargerBroadcast(this);
    }

    // ----------------------  BROADCAST INTERNET  ---------------------
    private final BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = manager.getActiveNetworkInfo();
            onNetworkChange(ni);
        }
    };

    private void onNetworkChange(NetworkInfo networkInfo) {
        if (networkInfo != null && networkInfo.isConnected()) {
            mostrarSnackBar("CONECTADO A INTERNET", true);
        } else {
            mostrarSnackBar("VERIFIQUE SU CONEXION A INTERNET", false);
        }
    }

    private void mostrarSnackBar(String mensaje, boolean conectado) {
        snackbar = Snackbar.make(relativeLayout_main, mensaje, (conectado ? Snackbar.LENGTH_SHORT : Snackbar.LENGTH_INDEFINITE))
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.BLACK);
        snackbar.show();
    }

    @Override
    public void charge(boolean charge) {
        if (charge) txtcharge.setText("Cargando dispositivo...!!!");
        else txtcharge.setText("Dispositivo desconectado...!");
    }
}