import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityDisplayer
{
    public static void displayActivity(JSONArray activityData)
    {
        for (int i = 0; i < activityData.length(); i++)
        {
            JSONObject event = activityData.getJSONObject(i);
            String eventType = event.getString("type");
            JSONObject eventPayload = event.getJSONObject("payload");
            String repoName = event.getJSONObject("repo").getString("name");
            String eventOwner = event.getJSONObject("actor").getString("login");

            switch (eventType)
            {
                case "PushEvent":
                {
                    displayPushEvent(eventPayload, repoName, eventOwner);
                    break;
                }
                case "PullRequestEvent":
                {
                    displayPullRequestEvent (eventPayload, repoName, eventOwner);
                    break;
                }
                case "ForkEvent":
                {
                    displayForkEvent (eventPayload, repoName, eventOwner);
                    break;
                }
                case "CreateEvent":
                {
                    displayCreateOrDeleteEvent(eventPayload, repoName, eventOwner, "Create");
                    break;
                }
                case "DeleteEvent":
                {
                    displayCreateOrDeleteEvent(eventPayload, repoName, eventOwner, "Delete");
                    break;
                }
                case "IssuesEvent":
                {
                    displayIssuesEvent (eventPayload, repoName, eventOwner);
                    break;
                }
                case "WatchEvent":
                {
                    displayWatchEvent (eventPayload, repoName, eventOwner);
                    break;
                }
                default:
                    System.err.println("Unknown event type: " + eventType);
                    break;
            }
        }
    }

    private static void displayWatchEvent(JSONObject eventPayload,
                                          String repoName, String eventOwner)
    {
        String action = eventPayload.getString("action");

        System.out.println("---------------------");
        System.out.println("Watch Event :");
        if ("started".equals(action)) {
            System.out.println("User " + eventOwner + " starred repository " + repoName);
        } else if ("stopped".equals(action)) {
            System.out.println("User " + eventOwner + " un-starred repository " + repoName);
        }
        System.out.println("---------------------");
    }

    private static void displayIssuesEvent(JSONObject eventPayload,
                                           String repoName, String eventOwner)
    {
        try
        {
            String actionOfEventOwner = eventPayload.getString("action");
            JSONObject issueJSONObj = eventPayload.getJSONObject("issue");
            String issueTitle = issueJSONObj.getString("title");
            String issueState = issueJSONObj.getString("state");
            String issueCreator = issueJSONObj.getJSONObject("user").getString("login");

            System.out.println("---------------------");
            System.out.println("Issues Event :");
            System.out.println("Author of event: " + eventOwner);
            System.out.println("Action of event owner: " + actionOfEventOwner);
            System.out.println("Source Repo: " + repoName);
            System.out.println("Issue creator " + issueCreator);
            System.out.println("Issue title: " + issueTitle);
            System.out.println("Issue state: " + issueState);
            System.out.println("---------------------\n");
        }
        catch (Exception e)
        {
            System.err.println("Error parsing JSON or accessing data: " + e.getMessage());
        }
    }

    private static void displayCreateOrDeleteEvent(JSONObject eventPayload, String repoName,
                                                   String eventOwner, String eventType)
    {
        try
        {
            String gitRefObject = eventPayload.getString("ref_type");

            System.out.println("---------------------");
            System.out.println(eventType + " Event:");
            System.out.println("Author of " + eventType.toLowerCase() + " event: " + eventOwner);
            System.out.println("Source repo: " + repoName);
            System.out.println("Type of git ref object: " + gitRefObject);
            System.out.println("---------------------");
        }
        catch (Exception e)
        {
            System.err.println("Error parsing JSON or accessing data: " + e.getMessage());
        }
    }

    private static void displayForkEvent(JSONObject eventPayload, String repoName, String eventOwner)
    {
        try
        {
            String forkeeRepoName =
                    eventPayload.getJSONObject("forkee").getString("name");

            System.out.println("---------------------");
            System.out.println("Fork Event:");
            System.out.println("Author of fork request: " + eventOwner);
            System.out.println("Source repo: " + repoName);
            System.out.println("Forked repo: " + forkeeRepoName);
            System.out.println("---------------------");
        }
        catch (Exception e )
        {
            System.err.println("Error parsing JSON or accessing data: " + e.getMessage());
        }
    }

    private static void displayPullRequestEvent(JSONObject eventPayload,
                                                String repoName, String eventOwner)
    {
        try
        {
            JSONObject pullRequest = eventPayload.getJSONObject("pull_request");
            String repoOwner = pullRequest.getJSONObject("user").getString("login");
            String title = pullRequest.getString("title");
            String state = pullRequest.getString("state");
            String createdAt = pullRequest.getString("created_at");
            String mergedAt = pullRequest.optString("merged_at", "Not merged");

            System.out.println("---------------------");
            System.out.println("Pull Request Event:");
            System.out.println("Author of pull request: " + eventOwner);
            System.out.println("Repo: " + repoName);
            System.out.println("Repo owner: " + repoOwner);
            System.out.println("Title: " + title);
            System.out.println("State: " + state);
            System.out.println("Created at: " + createdAt);
            System.out.println("Merged at: " + mergedAt);
            System.out.println("---------------------");
        }
        catch (Exception e )
        {
            System.err.println("Error parsing JSON or accessing data: " + e.getMessage());
        }
    }

    private static void displayPushEvent(JSONObject eventPayload,
                                         String repoName, String repoOwner)
    {
        try
        {
            JSONArray commitsArray = eventPayload.getJSONArray("commits");
            int commitCount = commitsArray.length();

            System.out.println("---------------------");
            System.out.println("Push Event:");
            System.out.println("Author of push: " + repoOwner);
            System.out.println("Repo: " + repoName);
            System.out.println("No of commits: " + commitCount);

            for (int j = 0; j < commitCount; j++)
            {
                JSONObject commit = commitsArray.getJSONObject(j);
                String authorName = commit.getJSONObject("author").getString("name");
                String commitMessage = commit.getString("message");

                System.out.println("Author of commit " + (j + 1) + ": " + authorName);
                System.out.println("Message of commit: " + commitMessage);
            }

            System.out.println("---------------------\n");
        }
        catch (Exception e)
        {
            System.err.println("Error parsing JSON or accessing data: " + e.getMessage());
        }
    }
}
