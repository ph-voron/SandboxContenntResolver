package com.app.sandboxcontenntresolver;

import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.sandboxcontenntresolver.providers.DataRequestListener;
import com.app.sandboxcontenntresolver.providers.content.parameters.GoogleGeocodeProviderParams;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Приложение демонстрирует работу механизма асинхронного разрешения запросов к пулу поставщиков данных из UI
 * Данный пример испольуется для демонстрации части системы (BasicRequestHandler, BasicContentProviderResolver и т.д)
 * обработки запросов к контент-провайдерам приложения. Частично реализует подход IoC.
 * Такая реализация была выбрана в виду того, что в целевом приложении (из которого взят код) на момент начала разработки
 * заказчиком часто менялись источники данных, их количество и типы данных и требования
 * (это было оговорено заранее). Для того, что бы можно было сделать систему максимально гибкой и
 * не критичной к постоянным изменениям требований была выбрана такая реализация.
 * Для демонстрации в примере использован только один content provider (GoogleGeocodeProvider)
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    EditText etRequest = null;
    Button btnSumbit = null;
    ListView lvResults = null;
    ArrayAdapter<String> arrayAdapter = null;
    ProgressBar pbProgress = null;
    //
    MapFragment mapFragment = null;
    GoogleMap googleMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        etRequest = (EditText)findViewById(R.id.etRequest);
        btnSumbit = (Button)findViewById(R.id.btnSubmit);
        lvResults = (ListView)findViewById(R.id.lvResults);
        pbProgress = (ProgressBar)findViewById(R.id.pbProgress);
        //
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //
        etRequest.setText("Москва Ле");
        //
        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
        //
        arrayAdapter = new ArrayAdapter<>(this,
               android.R.layout.simple_list_item_1, new ArrayList<String>());
        lvResults.setAdapter(arrayAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Остановка выполняемых асинхронных операций, если они выполняются
        DependencyResolver
                .getRequestHandler()
                .cancelTasks();

    }

    //Отправка асинхронного запроса к поставщику данных
    public void onSubmit(){
        arrayAdapter.clear();
        arrayAdapter.notifyDataSetChanged();
        //
        GoogleGeocodeProviderParams params = new GoogleGeocodeProviderParams(etRequest.getText().toString());
        etRequest.setText("");
        //
        setProgressState(true);
        //вызов обработчика
        DependencyResolver
                .getRequestHandler()
                .beginGoogleGeocode(params, new DataRequestListener<List<Address>>() {
                    @Override
                    public void onResult(List<Address> data) {
                        applyData(data);
                        setProgressState(false);
                    }

                    @Override
                    public void onException(Exception e) {
                        showToast(e.getMessage());
                        e.printStackTrace();
                        Log.e("beginGoogleGeocode",e.getMessage());
                        setProgressState(false);
                    }
                });
    }
    //Передача результатов компонентам UI
    public void applyData(final List<Address> results){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Address item : results){
                    String countryName = item.getCountryName();
                    String thoroughfare = item.getThoroughfare();
                    String subThoroughfare = item.getSubThoroughfare();
                    arrayAdapter.add(String.format("%s - %s, %s",
                            countryName != null ? countryName : "",
                            thoroughfare != null ? thoroughfare : "",
                            subThoroughfare != null ? subThoroughfare : ""));
                    arrayAdapter.notifyDataSetChanged();
                }
                if(results.size() > 0){
                    Address firstItem = results.get(0);
                    //
                    LatLng latLng = new LatLng(firstItem.getLatitude(), firstItem.getLongitude());
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                    googleMap.animateCamera(cameraUpdate);
                }
            }
        });
    }
    //
    public void showToast(String text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }
    //
    public void setProgressState(final boolean val){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pbProgress.setVisibility(val ? View.VISIBLE : View.GONE);
                btnSumbit.setEnabled(!val);
            }
        });
    }
    //
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
