import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;



public class ImportPicPokemonDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //URL probabilmente da cambiare
        String connectionString="jdbc:mysql://localhost:3306/prova_pokemon?user=root&password=federico";
        String dirPicture="/home/fededgs/Progetto-UNIPV-Java/Progetto-M18/database/DB_pokemon/card_pics";
        int numPic;

        File folder = new File(dirPicture);
        File[] listOfFiles= folder.listFiles();
        numPic=listOfFiles.length;

        Arrays.sort(listOfFiles, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return ((File) f1).getName().compareTo(((File) f2).getName());
            }
        });
        try {
            Connection conn= (Connection) DriverManager.getConnection(connectionString);
            for(int i=0;i<numPic;i++) {
                PreparedStatement pstm = conn.prepareStatement("UPDATE pokemon_card Set Picture=? WHERE Cards_ID=?");
                FileInputStream in = new FileInputStream(listOfFiles[i]);
                pstm.setBinaryStream(1, in, listOfFiles[i].length());
                pstm.setInt(2, i + 1);
                pstm.executeUpdate();
                pstm.close();
            }
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {

        }


    }


}

