package com.example.serviciosenelarranque2122;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class MiServicioIntenso extends JobIntentService {
    private static final int ID = 1 ;
    public static final String ID_CANAL = "mi canal favorito" ;
    private static final int ID_TRABAJO = 666;
    String ETIQUETA = "SERVICIOINTENSO";
    int contador = 1;
    CuandoPasanCosas cuandoPasanCosas = new CuandoPasanCosas();


    public MiServicioIntenso() {

    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(ETIQUETA, "Comenzamos a trabajar");

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        getBaseContext().registerReceiver(cuandoPasanCosas, intentFilter);


        while(true){
            mandarNotificaciones(getApplicationContext());
            Log.d(ETIQUETA, "Manda notificación");

            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void mandarNotificaciones(Context applicationContext) {
        NotificationManager notificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        Intent intent = new Intent(applicationContext, MainActivity.class);
        Log.d(ETIQUETA, "Aleatorio: "+random.nextInt(1000000));
        intent.putExtra(MainActivity.MENSAJE, "El número es: "+random.nextInt(1000000));

        PendingIntent pendingIntent  = PendingIntent.getActivity(applicationContext, ID, intent,0);

        Notification notification = new NotificationCompat.Builder(applicationContext, ID_CANAL).setContentTitle("Mi notificación")
                .setContentText("Bonito día hoy").setSmallIcon(R.drawable.ic_launcher_background).setContentIntent(pendingIntent).build();

        notificationManager.notify(contador++,notification);
    }

    static void encolarTrabajo(Context context, Intent trabajo){
        enqueueWork(context, MiServicioIntenso.class, ID_TRABAJO,  trabajo);
    }

    class CuandoPasanCosas extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
                Log.d(ETIQUETA, "Se ha conectado el cable");
                Toast.makeText(context, "Has conecado el cable", Toast.LENGTH_SHORT).show();
            }else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
                Log.d(ETIQUETA, "Se ha desconectado el cable");
                Toast.makeText(context, "Has desconecado el cable", Toast.LENGTH_SHORT).show();
            }
        }
    }

}