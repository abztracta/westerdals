package no.woact.external.restful;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Espen Rønning
 * @version 2016-05-28
 * @since 1.8
 */
@Stateless
public class RestcountriesEuService implements Serializable {

    private static final String API_URL = "http://restcountries.eu";
    private static final Logger LOG = Logger.getLogger(RestcountriesEuService.class.getName());
    private static final String BASE_URL = System.getProperty("restcountrieseu", API_URL);

    private List<String> countries;

    public List<String> getCountries() {
        if (countries != null) {
            return countries;
        }
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(BASE_URL).path("rest").path("v1").path("all");
        LOG.info("Launching API call to " + target.getUri().toString());
        Response response = target.request().get();
        String json = response.readEntity(String.class);

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);
        response.close();
        countries = buildList(jsonArray);
        return countries;
    }

    private List<String> buildList(JsonArray array) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            String name = array.get(i).getAsJsonObject().get("name").getAsString();
            names.add(name);
        }
        return names;
    }
}
