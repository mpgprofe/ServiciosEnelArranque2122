package com.example.serviciosenelarranque2122;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String MENSAJE = "Mensaje";
    Button btArrancar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String mensaje = extras.getString(MENSAJE);
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
            extras.clear();
        }

        crearCanalNotificiones();
        btArrancar = findViewById(R.id.button);

        btArrancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiServicioIntenso.encolarTrabajo(getApplicationContext(), new Intent());
            }
        });
    }

    private void crearCanalNotificiones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String nombreCanal = "Mi canal favorito";
            NotificationChannel notificationChannel = new NotificationChannel(MiServicioIntenso.ID_CANAL, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }
}