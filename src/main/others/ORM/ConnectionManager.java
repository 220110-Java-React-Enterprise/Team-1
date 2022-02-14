package ORM;

import utils.FileLogger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
//singleton model

class ConnectionManager {
    private static FileLogger FileLog;

    private static Connection connection = null;

    private ConnectionManager() {FileLog = FileLogger.getFileLogger();}


    public  static Connection GetConnection(String Path) throws IOException, SQLException {
        if (connection == null)
            connection = connect(Path);
        return connection;
    }

    private  static Connection connect(String Path) throws IOException, SQLException {


        Properties props = new Properties();
        FileReader fr = new FileReader(Path);;

        ClassLoader cl = ConnectionManager.class.getClassLoader();
        try (InputStream stream = cl.getResourceAsStream("Database.properties")) {
            //BufferedInputStream bis = new BufferedInputStream(new FileInputStream("db.properties"));//worked before but later caused an exception
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(Path));
            props.load(bis);
        }
        catch(Exception exc)
        {
            System.out.println("Exception: " + exc.getMessage());
            FileLog.log(exc);
        }

        //props.load(fr); //doesn't work when exported as jar the properties file is not found

        String connectionString = "jdbc:mariadb://" + props.getProperty("hostname") + ":" +
                props.getProperty("port") + "/" +
                props.getProperty("dbname") + "?user=" +
                props.getProperty("username") + "&password=" +
                props.getProperty("password");

        System.out.println(connectionString);
        connection = DriverManager.getConnection(connectionString);

        return connection;
    }


}


// connStr = "jdbc:sqlite:C:\\Users\\ahmed\\AppData\\Roaming\\DBeaverData\\workspace6\\.metadata\\sample-database-sqlite-1\\Chinook.db";

