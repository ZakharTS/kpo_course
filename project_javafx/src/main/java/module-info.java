module com.course.project_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.course.project_javafx to javafx.fxml;
    exports com.course.project_javafx;
}