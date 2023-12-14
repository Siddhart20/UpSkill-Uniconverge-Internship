import java.sql.*;
import java.util.Scanner;

public class MusicPlayerApp {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/music_player";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            initializeDatabase(connection);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1. View Music Library");
                System.out.println("2. Create Playlist");
                System.out.println("3. Add Song to Playlist");
                System.out.println("4. View Playlist");
                System.out.println("5. Play Playlist");
                System.out.println("6. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        viewMusicLibrary(connection);
                        break;
                    case 2:
                        createPlaylist(connection, scanner);
                        break;
                    case 3:
                        addSongToPlaylist(connection, scanner);
                        break;
                    case 4:
                        viewPlaylist(connection, scanner);
                        break;
                    case 5:
                        playPlaylist(connection, scanner);
                        break;
                    case 6:
                        System.out.println("Exiting Music Player. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeDatabase(Connection connection) throws SQLException {
        // Initialize database and tables if not already present
        // Note: In a real-world scenario, you would use a database migration tool for this purpose.
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS songs (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255)," +
                "artist VARCHAR(255)," +
                "duration INT" +
                ")");
        statement.execute("CREATE TABLE IF NOT EXISTS playlists (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)" +
                ")");
        statement.execute("CREATE TABLE IF NOT EXISTS playlist_songs (" +
                "playlist_id INT," +
                "song_id INT," +
                "PRIMARY KEY (playlist_id, song_id)," +
                "FOREIGN KEY (playlist_id) REFERENCES playlists(id)," +
                "FOREIGN KEY (song_id) REFERENCES songs(id)" +
                ")");
    }

    private static void viewMusicLibrary(Connection connection) throws SQLException {
        // Retrieve and display songs from the music library
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM songs");

        System.out.println("Music Library:");
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id") +
                    ", Title: " + resultSet.getString("title") +
                    ", Artist: " + resultSet.getString("artist") +
                    ", Duration: " + resultSet.getInt("duration"));
        }
        System.out.println();
    }

    private static void createPlaylist(Connection connection, Scanner scanner) throws SQLException {
        // Create a new playlist
        System.out.print("Enter the name of the new playlist: ");
        String playlistName = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO playlists (name) VALUES (?)");
        preparedStatement.setString(1, playlistName);
        preparedStatement.executeUpdate();

        System.out.println("Playlist '" + playlistName + "' created successfully.\n");
    }

    private static void addSongToPlaylist(Connection connection, Scanner scanner) throws SQLException {
        // Add a song to a playlist
        viewMusicLibrary(connection);
        System.out.print("Enter the ID of the song to add to the playlist: ");
        int songId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        viewPlaylists(connection);
        System.out.print("Enter the ID of the playlist: ");
        int playlistId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)");
        preparedStatement.setInt(1, playlistId);
        preparedStatement.setInt(2, songId);
        preparedStatement.executeUpdate();

        System.out.println("Song added to the playlist successfully.\n");
    }

    private static void viewPlaylists(Connection connection) throws SQLException {
        // Retrieve and display existing playlists
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM playlists");

        System.out.println("Playlists:");
        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id") +
                    ", Name: " + resultSet.getString("name"));
        }
        System.out.println();
    }

    private static void viewPlaylist(Connection connection, Scanner scanner) throws SQLException {
        // View songs in a selected playlist
        viewPlaylists(connection);
        System.out.print("Enter the ID of the playlist to view: ");
        int playlistId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT s.title, s.artist, s.duration FROM songs s " +
                        "JOIN playlist_songs ps ON s.id = ps.song_id " +
                        "WHERE ps.playlist_id = ?");
        preparedStatement.setInt(1, playlistId);
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println("Playlist Songs:");
        while (resultSet.next()) {
            System.out.println("Title: " + resultSet.getString("title") +
                    ", Artist: " + resultSet.getString("artist") +
                    ", Duration: " + resultSet.getInt("duration"));
        }
        System.out.println();
    }

    private static void playPlaylist(Connection connection, Scanner scanner) throws SQLException {
        // Simulate playing a playlist (print the playlist content)
        viewPlaylist(connection, scanner);
        System.out.println("Simulating playback of the selected playlist.\n");
    }
}
