package com.example.lookattheanimals;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lookattheanimals.databinding.ActivityMqttconectBinding;

public class MQTTConect extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMqttconectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMqttconectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_mqttconect);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_mqttconect);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void conexionBroker() {

        String clientId = lookattheanimals;//MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);
        //para agregar los parametros
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(Yabran);
        options.setPassword(1234.qwer.toCharArray());
        try {


            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {

                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    Toast.makeText(getBaseContext(), "Conectado ", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    Toast.makeText(getBaseContext(), "NO Conectado ", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //si esta conectado no agas nada//else pero si no conectate
        if(client.isConnected()){
        }else {Toast.makeText(getBaseContext(),"connection_perdida: ",Toast.LENGTH_SHORT).show();
            conexionBroker();}
    }
    public void checkadorBroker(){

        client.setCallback(new MqttCallback() {
            @Override ///mensaje desconetado
            public void connectionLost(Throwable cause) {
                //toast de perdida de conexion internet
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                //resivir mensaje
                if (acceso_mensaje==true){
                    if (topic.equals("LED")){//Toast.makeText(getBaseContext(), "tema led", Toast.LENGTH_SHORT).show();
                        String msnS=new String(message.getPayload());
                        if(msnS.equals("D1")){ Toast.makeText(getBaseContext(), "dato"+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                tvNombreDispositivo.setText("e resivido un"+message+" del tema led"+ contUno);
                acceso_mensaje=true;
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    public void subcricionesTemas(){
        boolean acceso_mensaje=false;
        String tema= "LED";
        int qos =0;

        try {   client.subscribe(tema,qos);
            Toast.makeText(getBaseContext(),"subcribir a LED",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(),"no subcribir a LED",Toast.LENGTH_SHORT).show();
        }
    }
    public void desSubcribcion(View v){
        String tema ="LED";
        try {   client.unsubscribe(tema);
            Toast.makeText(getBaseContext(),"DESsusccipto",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(),"sigues subcrito a LED",Toast.LENGTH_SHORT).show();
        }
    }
    public  void  publicarD1 (View v) {
        String tema="LED";
        String  menssage="D1";
        try {
            int qos=0;

            boolean msn_retenido=false;
            client.publish(tema, menssage.getBytes(),qos, false);

        }catch (Exception e){e.printStackTrace();}
    }
    public  void  Desconectar (View v) {
        try {
            client.disconnect();

        }catch (Exception e){e.printStackTrace();}
    }

