package uni.finalproject;

import javafx.scene.control.*;
import uni.finalproject.controller.RegistrationSystem;
import javafx.fxml.FXML;
import uni.finalproject.model.Student;
import uni.finalproject.model.Teacher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class GUIController {
    private final RegistrationSystem registrationSystem;


    @FXML
    private TextField studentID;
    @FXML
    private TextField studentFirstName;
    @FXML
    private TextField studentLastName;
    @FXML
    private Label totalCredits;
    @FXML
    private TextField teacherID;
    @FXML
    private TextField teacherFirstName;
    @FXML
    private TextField teacherLastName;
    @FXML
    private TextField courseID;
    @FXML
    private Label teacherNotFound;
    @FXML
    private Label studentNotFound;
    @FXML
    private TextField StudentLogInID;
    @FXML
    private TextField TeacherLogInID;
    @FXML
    private Label registrationChecker;
    @FXML
    private ListView listView;

    public void setStudentLogInID(TextField studentLogInID) {
        StudentLogInID = studentLogInID;
    }

    public GUIController() throws SQLException {
        this.registrationSystem = new RegistrationSystem();
    }

    @FXML
    protected void onStudentMenuButtonClick() throws IOException {
        GUIApplication.showMenu("StudentLogIn.fxml", "Log in - Student");
    }

    @FXML
    protected void onTeacherMenuButtonClick() throws IOException {
        GUIApplication.showMenu("TeacherLogIn.fxml", "Log in - Teacher");
    }

    /**
     * Button for the log in of a student
     */
    @FXML
    protected void onStudentLogInButtonClick() throws IOException, SQLException {
        int tempStudentID;
        boolean ok = false;
        if (!StudentLogInID.getText().isEmpty())
            tempStudentID = parseInt(StudentLogInID.getText());
        else
            tempStudentID = -1;
        for (Student student : registrationSystem.getStudentRepository().findAll()) {
            if (student.getStudentID() == tempStudentID) {
                studentNotFound.setText("");
                setStudentLogInID(StudentLogInID);
                ok = true;
                GUIApplication.showMenu("StudentOptions.fxml", "Student Options");
            }

        }
        if (StudentLogInID.getText().isEmpty() || ok == false)
            studentNotFound.setText("Student not found");

    }

    @FXML
    protected void onTeacherLogInButtonClick() throws IOException, SQLException {
        int tempTeacherID;
        boolean ok = false;
        if (!TeacherLogInID.getText().isEmpty())
            tempTeacherID = parseInt(TeacherLogInID.getText());
        else
            tempTeacherID = -1;
        for (Teacher teacher : registrationSystem.getTeacherRepository().findAll()) {
            if (teacher.getTeacherID() == tempTeacherID) {
                teacherNotFound.setText("");
                ok = true;
                GUIApplication.showMenu("TeacherOptions.fxml", "Teacher Options");
            }

        }
        if (TeacherLogInID.getText().isEmpty() || ok == false)
            teacherNotFound.setText("Teacher not found");

    }

    @FXML
    protected void registerStudent() throws SQLException {
        if(registrationSystem.register(parseInt(studentID.getText()),parseInt(courseID.getText())))
            registrationChecker.setText("Successfully registered");
        else
            registrationChecker.setText("Not registered");

    }

    @FXML
    protected void showCredits() throws SQLException {
        if(!studentID.getText().isEmpty()){
            totalCredits.setText(String.valueOf(registrationSystem.getStudentRepository().findOne(parseInt(studentID.getText())).getTotalCredits()));
        }
        else
            totalCredits.setText("Yoy have to enter your ID first");

    }

    @FXML
    protected void showStudents() throws SQLException {
        if(teacherID.getText() != null) {
            listView.getItems().clear();
            List<Student> students = registrationSystem.retrieveStudentsFromCourse(Integer.parseInt(courseID.getText()));
            List<String> strings = students.stream().map(Student::toString).collect(Collectors.toList());
            listView.getItems().addAll(strings);
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }}



}
