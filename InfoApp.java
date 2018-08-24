package yourpackage.utils;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Classe responsavel por conter funcoes que sao uteis para o aplicativo em si
 * Nessa classe e possivel encontrar funcoes uteis de interface grafica, controle de versao, dentre outras funcionalidades totalmente uteis ao aplicativo em si
 */
public class InfoApp {

    /**
     * Declaracao de constantes
     */
    public static final long READ_TIMEOUT = 30000;
    public static final long WRITE_TIMEOUT = 30000;
    public static final String FIREBASE_TOKEN_UPDATE    = "FIREBASE_TOKEN_UPDATE";
    public static final String FONTAWESOME_BRANDS_DIR = "fonts/fa-brands-400.ttf";
    public static final String FONTAWESOME_REGULAR_DIR = "fonts/fa-regular-400.ttf";
    public static final String FONTAWESOME_SOLID_DIR = "fonts/fa-solid-900.ttf";
    /**
     * Funcao responsavel por gerar uma notificacao com som, vibracao e textos
     * @param context
     * @param destiny
     * @param ticker
     * @param title
     * @param text
     * @param icon
     */
    public static void showNotification(Context context, Class destiny, String ticker, String title, String text, int icon){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, destiny);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(title);
        builder.setContentText(text);
        //builder.setStyle(new NotificationCompat.BigTextStyle().bigText(text));
        builder.setSmallIcon(icon);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon));
        builder.setContentIntent(pendingIntent);

        /*
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        String[] descs = new String[]{text, text, text, text};
        for (int x = 0; x < descs.length; x++){
            style.addLine(descs[x]);
        }
        builder.setStyle(style);
        */

        Notification notification = builder.build();
        notification.vibrate = new long[]{150, 300, 150, 600};
        notificationManager.notify(icon, notification);

        try {
            Uri sound  = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone tone = RingtoneManager.getRingtone(context, sound);
            tone.play();
        } catch(Exception e) {
        }
    }

    /**
     * Funcao responsavel por atualizar o token do firebase no servidor, utilizando o webservice
     * @param url
     * @param params
     */
    public static String postToUrl(String url, RequestBody params){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(InfoApp.READ_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(InfoApp.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);

        OkHttpClient http = builder.build();

        Request request = new Request.Builder().url(url).post(params).build();

        Response response = null;

        String json = null;

        try {
            response = http.newCall(request).execute();
            json = response.body().string();
        } catch(IOException e) {
            Log.e(FIREBASE_TOKEN_UPDATE, e.getMessage());
        }
        return (json != null) ? ((json != "[]") ? json : "") : "";
    }

    /**
     * Funcao responsavel por obter o codigo da versao do app
     * @return
     */
    public static int getVersionCodeOfApp(Context context){
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(getPackageName(context), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Funcao responsavel por obter o nome da versao do app
     * @return
     */
    public static String getVersionNameOfApp(Context context){
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(getPackageName(context), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Funcao responsavel por obter o nome do pacote do app
     * @param context
     * @return
     */
    public static String getPackageName(Context context){
        return context.getApplicationContext().getPackageName();
    }

    /**
     * Funcao responsavel por abrir a play store para que o usuario baixe a nova versao do app
     */
    public static void abrirPlayStore(Context context) {
        final String appPackageName = context.getApplicationContext().getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    /**
     * Funcao responsavel por mostrar um progressDialog para o usuario enquanto alguma tarefa em segundo plano e exibida
     * @param progressDialog
     * @param context
     */
    public static void showProgressDialog(Context context, ProgressDialog progressDialog, String title, String message){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    /**
     * Funcao responsavel por esconder o progressDialog
     * @param progressDialog
     */
    public static void hideProgressDialog(ProgressDialog progressDialog){
        progressDialog.dismiss();
    }

    /**
     * Funcao responsavel por obter a data de hoje e converte-la para string
     */
    public static String convertDataTodayToString(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /**
     * Funcao responsavel por obter a hora atual e converte-la para string
     * @return
     */
    public static String convertTimeNowToString(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * Funcao responsavel por mostrar um dialog de aviso
     */
    public static void showDialogWarning(Context context, int icon, String title, String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setNeutralButton("Ok", null);
        dialog.setIcon(icon);
        dialog.show();
    }

    /**
     * Funcao responsavel por inicializar a fontawesome no projeto android
     * @param context
     * @param font
     * @return
     */
    private static Typeface getTypeface(Context context, String font){
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    /**
     * Funcao que permite usar os icones da fontawesome como se fossem icones
     * @param context
     * @param v
     * @param font
     */
    private static void markAsIconContainer(Context context, View v, String font) {
        Typeface typeface = InfoApp.getTypeface(context, font);
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                InfoApp.markAsIconContainer(context, child, font);
            }
        } else if (v instanceof TextView) {
            ((TextView) v).setTypeface(typeface);
        }
    }

    public static void enableFontAwesome(Context c, View v){
        InfoApp.markAsIconContainer(c, v, InfoApp.FONTAWESOME_BRANDS_DIR);
        InfoApp.markAsIconContainer(c, v, InfoApp.FONTAWESOME_REGULAR_DIR);
        InfoApp.markAsIconContainer(c, v, InfoApp.FONTAWESOME_SOLID_DIR);
    }
}
