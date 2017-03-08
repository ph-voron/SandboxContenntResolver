package com.app.sandboxcontenntresolver.providers.content;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;


import com.app.sandboxcontenntresolver.providers.content.parameters.GoogleGeocodeProviderParams;

import java.util.List;
import java.util.Locale;

/**
 * Провайдер данных геокодера Google. Позволяет получить координаты из названия географического места или получить список мест
 * расположенных по указанным координатам
 */
public class GoogleGeocodeProvider implements ContentProvider<List<Address>> {
    public final int MAX_RESULTS = 100;
    //
    Geocoder geocoder = null;
    //
    public GoogleGeocodeProvider(Context context){
        this.geocoder = new Geocoder(context, Locale.getDefault());
    }
    //
    @Override
    public List<Address> getResult(ContentProviderParams prams) {
        if(prams == null || !(prams instanceof GoogleGeocodeProviderParams)) return null;
        //
        GoogleGeocodeProviderParams providerParams = (GoogleGeocodeProviderParams)prams;
        try {
            if (providerParams.placeName != null && providerParams.placeName.length() > 0) {
                return geocoder.getFromLocationName(providerParams.placeName, MAX_RESULTS);
            } else {
                return geocoder.getFromLocation(providerParams.latitude, providerParams.longitude,
                        MAX_RESULTS);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
