import org.json.JSONArray;

import java.util.Scanner;


public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning)
        {
            System.out.print("Enter a GitHub username (or 'exit' to quit): ");

            String username = scanner.nextLine();

            if (username.equalsIgnoreCase("exit"))
            {
                keepRunning = false;
            }
            else
            {
                try
                {
                    JSONArray activityData = GitHubService.fetchGitHubActivity(username, 5);
                    ActivityDisplayer.displayActivity(activityData);
                }
                catch (GitHubUserNotFoundException e)
                {
                    System.err.println("Error: User " + username + " not found");
                }
                catch (Exception e)
                {
                    System.err.println("Error fetching activity: " + e.getMessage());
                }
            }
        }

        scanner.close();
    }
}