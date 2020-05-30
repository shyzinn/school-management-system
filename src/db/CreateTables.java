package db;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

public class CreateTables {

    CreateDatabase table;

    public CreateTables() {

        table = new CreateDatabase();

        table.createDb();
        table.setAdminCredential();
        table.setCourses();
        table.setTimeTable();
        table.setFaculty();
        table.setStudents();
        table.setAssignments();
        table.setUploadedAssignments();
        table.setResults();
        table.setAttendance();
        table.setFacultyPivotCourses();
        table.setAttendancePivotCourses();
        table.setStudentPivotCourses();
        table.setExams();
        table.setAssignmentsResults();
        table.setUploadedExams();
        table.setExamsResults();
        table.setCoursePlan();
    }
}
