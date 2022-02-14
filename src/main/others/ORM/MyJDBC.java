package ORM;

import utils.FileLogger;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//persistence and jdbc layer

public class MyJDBC {

    private static Connection conn;
    private static String PropertiesPath = "src/main/resources/Database.properties";
    private boolean initialized = false;
    private static FileLogger FileLog;
    private MyJDBC() {
        FileLog = FileLogger.getFileLogger();
    }


    public static boolean CreateTable() throws SQLException, Exception
    {
        InitializeConnect();
        String output ="";
        try {

            PreparedStatement stmt = null;
            String SQL =
                            "CREATE TABLE Users.Users (" +
                                "ID INT auto_increment NOT NULL, " +
                                "FirstName varchar(20) NOT NULL, " +
                                "LastName varchar(20) NOT NULL, " +
                                "Password varchar(20) NOT NULL, " +
                                "Email varchar(20) NOT NULL, " +
                                "CONSTRAINT Users_PK PRIMARY KEY (ID) " +
                                ") " +
                                "ENGINE=InnoDB " +
                                "DEFAULT CHARSET=latin1 " +
                                "COLLATE=latin1_swedish_ci; ";

            stmt = conn.prepareStatement(SQL);
            int result  = stmt.executeUpdate();
            if (result >= 1)
            {
                System.out.println("Table has been created successfully");
                return true;
            }
            else
            {
                System.out.println("Table has not been created");
                return false;
              }
            }
        catch (SQLException exc) {
            FileLog.log(exc);
            throw new SQLException(exc);
        }
        catch(Exception exc)
        {
            FileLog.log(exc);
            throw new Exception(exc);
        }
    }

    public static String GetOneUser(String id) throws SQLException, Exception {
        InitializeConnect();
        String output ="";
        try {
            if (isNumber(id)) {
                {
                    try {
                        PreparedStatement stmt = null;
                        String SQL = "SELECT * FROM Users WHERE Id = ?";
                        stmt = conn.prepareStatement(SQL);
                        stmt.setInt(1, Integer.parseInt(id));
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            output += "{\n";
                            output += "\t\"id\":" + rs.getInt("ID") + ",\n" +
                                      "\t\"first name\":\"" + rs.getString("FirstName") + "\",\n" +
                                    "\t\"last name\":\"" + rs.getString("LastName") + "\",\n" +
                                      "\t\"password\":\"" + rs.getString("Password") + "\",\n" +
                                      "\t\"email\":\"" + rs.getString("Email") + "\"\n";
                            output += "  },\n";
                        }
                        else
                            System.out.println("no more rows to display");
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                        throw new SQLException(exc.getMessage());
                    }
                }
            } else
                throw new Exception("exception: invalid input type. you must enter a number for Album Id");
        }
        catch(SQLException exc)
            {
                FileLog.log(exc);
                throw new SQLException(exc.getMessage());
            }
         catch (Exception exc2)
          {
              FileLog.log(exc2);
            throw new SQLException(exc2.getMessage());
          }
        System.out.println("User info is\n: " + output);
        return output;
        }

    private static boolean isNumber(String num)
    {
        return num.chars().anyMatch(Character::isDigit);
    }

   public static boolean AddUser(String FirstName, String LastName, String Password, String Email) throws SQLException, Exception
   {
       InitializeConnect();

       try
       {
           String SQL = "INSERT INTO Users(FirstName, LastName, Password, Email) VALUES (?, ?, ?, ?)";
           PreparedStatement stmt = conn.prepareStatement(SQL);
           stmt.setString(1, FirstName);
           stmt.setString(2, LastName);
           stmt.setString(3, Password);
           stmt.setString(4, Email);
           int result = stmt.executeUpdate();
           if (result >= 1)
           {
               System.out.println("record have added successfully to Users Table");
           }
           else
           {
               System.out.println("record haven't been added");
               return false;
           }
       }
       catch(Exception exc)
       {
           System.out.println("Exception: " + exc.getMessage());
           FileLog.log(exc);
           return false;
       }
       return true;
   }

   public static String GetAllUsers() throws SQLException, Exception
   {
       InitializeConnect();
       String output = "";
       try
       {
           String SQL = "SELECT * FROM Users";
           PreparedStatement stmt = conn.prepareStatement(SQL);
           ResultSet rs  = stmt.executeQuery();
           output = "[\n";
           while (rs.next())
           {
               output += "  {\n";
               output += "\t\"id\":" + rs.getInt( "ID") + ",\n" +
                         "\t\"first Name\":\"" + rs.getString("FirstName") + "\",\n" +
                       "\t\"last name\":\"" + rs.getString("LastName") + "\",\n" +
                       "\t\"password\":\"" + rs.getString("Password") + "\",\n" +
                         "\t\"email\":\"" + rs.getString("Email") + "\"\n";
               output += "  },\n";
           }
           output += "]";
           System.out.println("All Users' info is\n: " + output);
       }
       catch(Exception exc)
       {
           System.out.println("Exception: " + exc.getMessage());
           FileLog.log(exc);
           return "Exception: " + exc.getMessage() + " no rows has been returned or another exception occurred" ;
       }
       return output;
   }

    public static boolean UpdateUser(int id, String FirstName, String LastName, String Password, String Email) throws SQLException, Exception
    {
        InitializeConnect();

        try
        {
            String SQL = "UPDATE Users SET FirstName = ?, LastName = ?, Password = ?, Email = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setString(1, FirstName);
            stmt.setString(2, LastName);
            stmt.setString(3,Password);
            stmt.setString(4, Email);
            stmt.setInt(5, id);
            int rs  = stmt.executeUpdate();
            if (rs >= 1)
            {
                System.out.println("record have been updated successfully to Users Table");
                return true;
            }
            else
            {
                System.out.println("record haven't been updated");
                return false;
            }
        }
        catch(Exception exc)
        {
            System.out.println("Exception: " + exc.getMessage());
            FileLog.log(exc);
            return false;
        }
    }

    public static boolean DeleteUser(int id) throws SQLException, Exception
    {
        InitializeConnect();

        boolean returned = false;
        try
        {
            String SQL = "DELETE FROM Users WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(SQL);
            stmt.setInt(1, id);
            int rs  = stmt.executeUpdate();
            if (rs >= 1)
            {
                System.out.println("record have been deleted successfully to Users Table");
                return  true;
            }
            else
            {
                System.out.println("record haven't been deleted");
                return false;
            }
        }
        catch(Exception exc)
        {
            System.out.println("Exception: " + exc.getMessage());
            FileLog.log(exc);
            return false;
        }
    }


    private  static void InitializeConnect() throws SQLException, IOException {
        try
        {
            File F = new File(PropertiesPath);
            F = F.getAbsoluteFile(); //get absolute path of the properties file
            PropertiesPath = F.getAbsolutePath(); //assign it as the new properties file path
            conn = ConnectionManager.GetConnection(PropertiesPath);

        }
        catch (Exception Exc)
        {
            System.out.println("Exception: " + Exc.getMessage());
            FileLog.log(Exc);
        }
    }




}
