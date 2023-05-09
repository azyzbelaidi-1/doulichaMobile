package codingbeasts.doulicha.services;
import com.codename1.io.ConnectionRequest;

import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class MapService {



public Double[] getCoordinatesFromAddress(String address) {
    String url = "https://nominatim.openstreetmap.org/search?q=" + address + "&format=json&addressdetails=1&limit=1";
    ConnectionRequest request = new ConnectionRequest();
    request.setUrl(url);
    request.setFailSilently(true);
    Double[] coords = new Double[2];
    request.addResponseListener((evt) -> {
        try {
            byte[] data = request.getResponseData();
            if (data != null) {
                com.codename1.io.JSONParser parser = new com.codename1.io.JSONParser();
                    Map<String, Object> coordonnees = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data)));
                    System.out.println(coordonnees);
                if (!coordonnees.isEmpty()) {
                    // Obtention de la liste de résultats à partir de la clé "root"
List<Map<String, Object>> results = (List<Map<String, Object>>) coordonnees.get("root");

// Obtention du premier résultat
if (!results.isEmpty()) {
    Map<String, Object> firstResult = results.get(0);
  



// Obtention des valeurs de latitude et de longitude
String lat = (String) firstResult.get("lat");
double latitude = Double.parseDouble(lat);
String lon = (String) firstResult.get("lon");
double longitude = Double.parseDouble(lon);

// Affichage des valeurs
System.out.println("Latitude : " + lat);
System.out.println("Longitude : " + lon);

                   
                    coords[0]=latitude;
                    coords[1]=longitude;
}} else {
                    System.out.println("erreur de generation de coordonnees");
}
                }
            }
         catch (IOException ex) {
            Dialog.show("Error", "An error occurred while retrieving the coordinates.", "OK", null);
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(request);
    
    return coords;
}

}
