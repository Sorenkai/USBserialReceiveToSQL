package nl.dehaagsehogeschool.thechallenge;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;

public class InsertIntoSQL {

    /**
     * Connect to the vb1.db database
     *
     * @return the Connection object
     */
    public boolean saveUserData = true;


    private Connection connect() {
        Connection conn = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        // MySQL connection string, pas zonodig het pad aan:
        String connection = "jdbc:mysql://64.225.69.25:3306/ubuntu";
        String user = "ubuntu";
        String password = "wachtwoord123";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(connection, user, password);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }

    public boolean canSaveUserData(){
        String query = "SELECT t.SensorID, t.naam, k.opslaan_data FROM tbl1 t JOIN klant_privacy k ON t.SensorID = k.SensorID ORDER BY t.tijdstip desc LIMIT 1";
        boolean saveData = true;
        try{
            Connection conn = connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                int userDataInt = rs.getInt("opslaan_data");
                if(userDataInt == 0){
                    saveData = false;
                }else{
                    saveData = true;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return saveData;
    }

    /**
     * Insert a new row into the tbl1 table
     *  @param tijdstip
     * @param luchtkwaliteit
     * @param SensorID
     * @param Naam
     * @param Adres
     * @param Woonplaats
     */
    public void insert(String tijdstip, Integer luchtkwaliteit, String SensorID, String Naam, String Adres, String Woonplaats) {
        String sql = "INSERT INTO tbl1(tijdstip, luchtkwaliteit, SensorID, Naam, Adres, Woonplaats) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connect().prepareStatement(sql);
            preparedStatement.setString(1, tijdstip);
            preparedStatement.setInt(2, luchtkwaliteit);
            preparedStatement.setString(3, SensorID);
            preparedStatement.setString(4, Naam);
            preparedStatement.setString(5, Adres);
            preparedStatement.setString(6, Woonplaats);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
