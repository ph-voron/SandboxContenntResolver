package com.app.sandboxcontenntresolver.providers.content.parameters;


import com.app.sandboxcontenntresolver.providers.content.ContentProviderParams;

/**
 * Параметры провайдера GoogleGeocodeProvider
 */
public class GoogleGeocodeProviderParams implements ContentProviderParams {
    public double latitude = 0;
    public double longitude = 0;
    public String placeName = "";
    public GoogleGeocodeProviderParams(String placeName){
        this.placeName = placeName;
    }

    public GoogleGeocodeProviderParams(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
