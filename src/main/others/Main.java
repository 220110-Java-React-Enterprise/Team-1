import ORM.MyJDBC;

public class Main {
public static void main(String[] args)
{
    try {
        //MyJDBC.CreateTable();
        MyJDBC.AddUser("John","John","John","John@gmail.com");
    } catch (Exception e) {
        e.printStackTrace();
    }

  }
}
