import java.sql.*;
import java.util.Scanner;

public class Main{

    public static void main(String[] args){
        createNotesTable();
        Scanner input = new Scanner(System.in);

        System.out.println("-------NOTES APP------");
        System.out.println("1. Add new note");
        System.out.println("2. view notes");
        System.out.println("3. edit note"); 
        System.out.println("4. delete note");
        System.out.println("5. exit");

        int choice = input.nextInt();

        switch( choice ){
            case 1 -> addNote(input);
            case 2 -> viewNotes();
            case 3 -> editNote(input);
            case 4 -> deleteNote(input);
            case 5 -> System.exit(0);
            default -> System.out.println("Invalid Input");
        
        }


        input.close();

    }

    public static Connection connect(){
        String url = "jdbc:sqlite:notes.db";
        try {
            return DriverManager.getConnection(url);
        }
        catch(SQLException e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public static void createNotesTable(){

        String sql = "CREATE TABLE IF NOT EXISTS notes( "
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "title TEXT, "
                   + "content TEXT)";
        
       try{
            Connection connect = connect();
            Statement stmt = connect.createStatement();
            stmt.execute(sql);
        }
        catch(SQLException e){
            System.err.println(e.toString());
       }


    }

    public static void addNote(Scanner input){
        System.out.println("Title:  ");

        String title = input.nextLine();

        System.out.println("Content:  ");
        String content = input.nextLine();

        String sql = "INSERT INTO notes (title, content) VALUES(" + title +", "+ content+")";

        try{
            Connection connect = connect();
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(1, content);
            pstmt.executeUpdate();
            System.out.println("Note Added");
        }
        catch(SQLException e){
            System.err.println(e.toString());
       }
    }

    public static void viewNotes(){
        String sql = "SELECT * FROM notes";

        try{
            Connection connect = connect();
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                System.out.println(rs.getInt("id")+ "|"+ rs.getString("title")+ ": "+rs.getString("content"));
            }

        }catch(SQLException e){
            System.err.println(e.toString());
        }


    }

    public static void editNote(Scanner input){ 
        System.out.println("Type in the ID of the note: ");
        int id = input.nextInt();
        input.nextLine();
        
        System.out.println("Type in the new title: ");
        String title = input.nextLine();

        System.out.println("Type in the new content: ");
        String content = input.nextLine();

        String sql = "UPDATE notes"
                   + "SET content = "+ content+" "
                   + "SET title = "+ title+" "
                   + "WHERE id = "+ id;

        try{
            Connection connect = connect();
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(1, content);
            pstmt.setInt(3, id);
            
            int rowsUpdated = pstmt.executeUpdate();
            if(rowsUpdated > 0){
                System.out.println("Note Updated!");
            }
            else{
                System.out.println("No note found with that ID :(");
            }
        }
        catch(SQLException e){
            System.err.println(e.toString());
       }
        
    }

    public static void deleteNote(Scanner input){
        System.out.println("Enter the id to delete: ");
        int id = input.nextInt();

        String sql = "DELETE FROM notes WHERE id = "+ id;

        try{
            Connection connect = connect();
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Note deleted");
        }
        catch(SQLException e){
            System.err.println(e.toString());
       }


    }


    
}