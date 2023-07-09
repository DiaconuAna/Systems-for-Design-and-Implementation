package ro.ubb.socket.domain.database;

public class DatabaseConnectionDetails {
    public String databaseName;
    public String username;
    public String password;

    public DatabaseConnectionDetails() {
        databaseName = "moviedatabase";
        username = "root";
        password = "doareu123";
    }

//    public DatabaseConnectionDetails(String databaseName, String username, String password) {
//        this.databaseName = databaseName;
//        this.username = username;
//        this.password = password;
//    }

    public String getConnectionString() {
        return String.format("jdbc:mysql://localhost:3306/%s", databaseName);
    }
}
