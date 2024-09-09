import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class GitHubService
{
    private final static String GITHUB_API_USER_EVENTS_URL = "https://api.github.com/users/%s/events";
    private static final Cache<String, JSONArray> cache = new Cache<>(10, TimeUnit.MINUTES);

    public static JSONArray fetchGitHubActivity(String username, int limit) throws Exception
    {
        JSONArray cachedData = cache.get(username);

        if (cachedData != null)
        {
            System.out.println("Cache hit: Returning cached data for user " + username);
            return cachedData;
        }

        System.out.println("Cache miss: Making the connection to fetch data for user " + username);

        HttpURLConnection connection = getHttpURLConnection(username, limit);

        int httpStatusCode = connection.getResponseCode();

        if (httpStatusCode == 200)
        {
            // Reading from connection
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            // Write from connection
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }

            in.close();

            JSONArray activityData = new JSONArray(response.toString());
            cache.put(username, activityData);

            //return new JSONArray(response.toString());
            System.out.println("Fetched data from API and stored in cache for user " + username);

            return activityData;
        }
        else if (httpStatusCode == 404)
        {
            throw new GitHubUserNotFoundException("User '" + username + "' not found.");
        }
        else
        {
            throw new Exception("Failed to fetch user activity.");
        }
    }

    private static HttpURLConnection getHttpURLConnection(String username, int limit)
            throws IOException, URISyntaxException
    {
        // user events url
        String userEventsApiUrl = String.format(GITHUB_API_USER_EVENTS_URL, username);

        // What if there is 500 events ? limit the number of events per page
        String perPageQuery = "?per_page=";

        String finalUserEventsApiUrl = userEventsApiUrl +perPageQuery + limit;

        // making the connection and get method
        HttpURLConnection connection =
                (HttpURLConnection) new URI(finalUserEventsApiUrl).toURL().openConnection();

        connection.setRequestMethod("GET");

        return connection;
    }
}
