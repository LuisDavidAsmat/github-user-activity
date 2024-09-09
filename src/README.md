# [Github User Activity](https://roadmap.sh/projects/github-user-activity)

##### Solution for the [github-user-activity](https://roadmap.sh/projects/github-user-activity) backend project from [roadmap](https://roadmap.sh/).
[![](https://avatars.githubusercontent.com/u/120650344?s=40&v=4)](https://roadmap.sh/)

A simple command line interface (CLI) to **fetch** the recent **activity** of a GitHub user and display it in the terminal. This project will help you:

- Practice your programming skills
- Working with APIs
- Handling JSON data
- Building a simple CLI application

## Features

- **Fetch User Events**: Retrieve a variety of events from a user's GitHub activity feed.
- **Event Types**:
    - **PushEvent**: Display details about push events, including commit authors and messages.
    - **PullRequestEvent**: Show details of pull requests, including the author, title, state, and merge status.
    - **ForkEvent**: Indicate when a repository has been forked, including the new forked repository's name.
    - **IssuesEvent**: Display information about issues created or commented on, including the issue title and state.
    - **CreateEvent**: Show details about repository creation or branch/tag creation.
    - **DeleteEvent**: Indicate when a repository or branch/tag has been deleted.
    - **WatchEvent**: Display when a user stars or unstarrs a repository.
- **Caching**:
    - Implement in-memory caching to reduce the number of API calls and improve performance.
    - Cache fetched data with a configurable expiration time to ensure up-to-date information.
- **Error Handling**:
    - Handle various errors gracefully, such as user not found or failed API requests.
    - Provide meaningful error messages to the user.
- **Modular Code Structure**:
    - Well-organized code with separate classes for fetching data, displaying events, and caching.
    - Easy to extend with additional event types or features.
- **User-Friendly Output**:
    - Clear and readable console output for displaying user activity.
    - Consistent formatting for different event types.

## Usage

1. Clone the repository:
   ```sh
   git clone https://github.com/LuisDavidAsmat/github-user-activity.git
   ```
2. Navigate to the project directory and enter *src* folder
    ```sh
   cd github-user-activity/src
   ```
3. Compile the project
    ```sh
    javac Main.java ActivityDisplayer.java Cache.java GitHubService.java GitHubUserNotFoundException.java
    ```
4. Run the application by using the following command
     ```sh
    java Main
    ```
5. Prompt to enter a GitHub username:
    ```sh
    Enter a GitHub username (or 'exit' to quit): 
    ```
6. Enter a Github username
    ```sh
    Enter a GitHub username (or 'exit' to quit): LuisDavidAsmat
    ```
7. Expected output on the first run
    ```sh
    Cache miss: Making the connection to fetch data for user LuisDavidAsmat
    Cache put: LuisDavidAsmat
    Fetched data from API and stored in cache for user LuisDavidAsmat
    ---------------------
    Create Event:
    Author of create event: LuisDavidAsmat
    Source repo: LuisDavidAsmat/github-user-activity
    Type of git ref object: repository
    ...
    ```
8. Enter the GitHub username again to fetch data from the cache.
    ```sh
    Enter a GitHub username (or 'exit' to quit): LuisDavidAsmat
    Cache hit: LuisDavidAsmat
    Cache hit: Returning cached data for user LuisDavidAsmat
    ---------------------
    Create Event:
    Author of create event: LuisDavidAsmat
    Source repo: LuisDavidAsmat/github-user-activity
    ...
    ```
9. Exit the program:
    ```sh
    Enter a GitHub username (or 'exit' to quit): exit
    Process finished with exit code 0
    ```

## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any enhancements or bug fixes.
