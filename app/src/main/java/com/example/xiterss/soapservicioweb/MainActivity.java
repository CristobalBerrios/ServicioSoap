package com.example.xiterss.soapservicioweb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    Button llamada;
    TextView txt;
    String respuesta;

    //VARIABLES PARA EL SERVICIO SOAP
    String metodo = "GetBookTitles";

    String namespace = "http://www.webserviceX.NET";

    String accionSoap = "http://www.webserviceX.NET/GetBookTitles";

    String url = "http://www.webservicex.net/BibleWebservice.asmx?WSDL";

    Boolean res=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llamada = (Button) findViewById(R.id.btnLlamada);
        llamada.setOnClickListener(this);
        txt=(TextView) findViewById(R.id.txt);
    }

    @Override
    public void onClick(View v) {

        new consumirAsyc().execute();

    }

    public boolean getRespuesta(){

        Boolean res = false;
        try {
            SoapObject soapObject = new SoapObject(namespace,metodo);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;
            sobre.setOutputSoapObject(soapObject);

            HttpTransportSE transporte = new HttpTransportSE(url);
            transporte.call(accionSoap,sobre);
            SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
            respuesta= resultado.toString();
            res = true;
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage());
        } catch (XmlPullParserException e) {
            Log.e("ERROR", e.getMessage());
        }

        return res;
    }

    private class consumirAsyc extends  android.os.AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            res=getRespuesta();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(res){
                txt.setText(respuesta);
            }

            Toast.makeText(getApplicationContext(), "Finalizada..", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Descargando..", Toast.LENGTH_SHORT).show();
        }

    }


}
