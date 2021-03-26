package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
public class Controller implements Initializable {

    @FXML
    public TableView<student> table;
    @FXML
    public TableColumn<student, Integer> colID;
    @FXML
    public TableColumn<student, String> colFirstName;
    @FXML
    public TableColumn<student, String> colLastName;
    @FXML
    public TableColumn<student, String> colNumber;
    @FXML
    public TableColumn<student, String> colGender;
    @FXML
    public TableColumn<student, String> colEmail;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnAdd;
    @FXML
    public TextField tfID;
    @FXML
    public TextField tfGender;
    @FXML
    public TextField tfNumber;
    @FXML
    public TextField tfEmail;
    @FXML
    public TextField tfLastName;
    @FXML
    public TextField tfFirstName;

    public void handleButtonAction(ActionEvent actionEvent) {
        if(actionEvent.getSource() == btnAdd){
            insertRecord();
        }
        else if(actionEvent.getSource() == btnUpdate) {
            updateRecord();
        }
        else if(actionEvent.getSource() == btnDelete){
            deleteRecord();
        }
    }

    public void handleMouseAction(MouseEvent mouseEvent) {
        student diagnosesFromtv = table.getSelectionModel().getSelectedItem();

        tfID.setText(""+ diagnosesFromtv.getID());
        tfLastName.setText(diagnosesFromtv.getLastName());
        tfFirstName.setText(diagnosesFromtv.getFirstName());
        tfNumber.setText(diagnosesFromtv.getNumber());
        tfGender.setText(diagnosesFromtv.getGender());
        tfEmail.setText(diagnosesFromtv.getEmail());
    }

    public Connection getConnection(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentAccomodation", "root","Password123");
            return conn;
        }catch (Exception ex){
            System.out.println("Error " + ex.getMessage());
            return null;
        }
    }

    public ObservableList<student> getStudentList(){
        ObservableList<student> studentObservableList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM student";
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            student students;
            while(rs.next()){
                students = new student(rs.getInt("ID"), rs.getString("LastName"), rs.getString("FirstName"),
                        rs.getString("Number"), rs.getString("Gender"), rs.getString("Email"));

                studentObservableList.add(students);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return studentObservableList;
    }

    public void showStudents(){
        ObservableList<student> list = getStudentList();

        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        table.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showStudents();
    }


    public void insertRecord(){
        String query = "INSERT INTO student VALUES (" + tfID.getText() + ", "+ " ' "+ tfLastName.getText()+ "', '" + tfFirstName.getText() + "', '" +
                tfNumber.getText() + "', '" +tfGender.getText() + "', '" +tfEmail.getText() +  "')";

        System.out.println(query);
        executeQuery(query);
        showStudents();
    }

    public void updateRecord(){
        String query = "UPDATE student SET LastName = ' " + tfLastName.getText()+ "', FirstName =  '" + tfFirstName.getText()+
                "', Number =  '" + tfNumber.getText()+ "', Gender =  '" + tfGender.getText()+ "', Email =  '" + tfEmail.getText()
         + "'  WHERE ID = " + tfID.getText() + ";";
        System.out.println(query);
        executeQuery(query);
        showStudents();
    }

    public void deleteRecord(){
        String query = "DELETE FROM student WHERE ID = " + tfID.getText() + ";";
        System.out.println(query);
        executeQuery(query);
        showStudents();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
