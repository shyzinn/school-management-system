package db;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import java.sql.Connection;

/*
 this class will CREATE TABLE for our database if they don't exits
 this is for one time only
 run this class functions first time only to CREATE TABLEs
 */
public class CreateDatabase {

    DbConnection dbConnection = new DbConnection();


    public void createDb() {
        try {
            Connection conn = dbConnection.connect();
            String create_table = "CREATE DATABASE IF NOT EXISTS school_management";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAdminCredential() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE IF NOT EXISTS admin_credentials (" +
                    "  id int(11) NOT NULL AUTO_INCREMENT," +
                    "  username varchar(100) DEFAULT NULL," +
                    "  password varchar(100) DEFAULT NULL," +
                    "  PRIMARY KEY (id)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAssignments() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE IF NOT EXISTS `assignments` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `topic` varchar(100) DEFAULT NULL," +
                    "  `content` text DEFAULT NULL," +
                    "  `deadline` varchar(100) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `assignments_fk` (`course_id`)," +
                    "  CONSTRAINT `assignments_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAssignmentsResults() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE `assignments_results` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `student_id` int(11) DEFAULT NULL," +
                    "  `assignment_id` int(11) DEFAULT NULL," +
                    "  `total_marks` double DEFAULT NULL," +
                    "  `obtained_marks` double DEFAULT NULL," +
                    "  `percentage` varchar(100) DEFAULT NULL," +
                    "  `comments` text DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `results_fk_1` (`course_id`)," +
                    "  KEY `results_fk_2` (`student_id`)," +
                    "  KEY `results_fk` (`assignment_id`)," +
                    "  CONSTRAINT `assignments_results_fk` FOREIGN KEY (`assignment_id`) REFERENCES `uploaded_assignments` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `assignments_results_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `assignments_results_fk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAttendance() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE IF NOT EXISTS `attendance` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `student_id` int(11) DEFAULT NULL," +
                    "  `att_date` varchar(100) DEFAULT NULL," +
                    "  `status` char(1) DEFAULT NULL," +
                    "  `att_day` int(11) DEFAULT NULL," +
                    "  `att_month` int(11) DEFAULT NULL," +
                    "  `att_year` int(11) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `attendance_fk` (`course_id`)," +
                    "  KEY `attendance_fk_1` (`student_id`)," +
                    "  CONSTRAINT `attendance_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `attendance_fk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAttendancePivotCourses() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE IF NOT EXISTS `attendance_pivot_courses` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `date` varchar(100) DEFAULT NULL," +
                    "  `day` int(11) DEFAULT NULL," +
                    "  `month` int(11) DEFAULT NULL," +
                    "  `year` int(11) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `attendance_entries_fk_1` (`course_id`)," +
                    "  CONSTRAINT `attendance_entries_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void setCourses() {
        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE IF NOT EXISTS `courses` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_name` varchar(100) DEFAULT NULL," +
                    "  `course_code` varchar(100) DEFAULT NULL," +
                    "  `department` varchar(100) DEFAULT NULL," +
                    "  `faculty_name` varchar(100) DEFAULT NULL," +
                    "  `start_date` varchar(100) DEFAULT NULL," +
                    "  `end_date` varchar(100) DEFAULT NULL," +
                    "  `fee` varchar(100) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setExams() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE `exams` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `code` varchar(100) DEFAULT NULL," +
                    "  `details` text DEFAULT NULL," +
                    "  `start_date` varchar(100) DEFAULT NULL," +
                    "  `start_time` time DEFAULT NULL," +
                    "  `time_allotted` int(11) DEFAULT NULL," +
                    "  `content` varchar(100) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `exams_fk` (`course_id`)," +
                    "  CONSTRAINT `exams_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setExamsResults() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE `exams_results` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `student_id` int(11) DEFAULT NULL," +
                    "  `uploaded_exam_id` int(11) DEFAULT NULL," +
                    "  `exam_id` int(11) DEFAULT NULL," +
                    "  `obtained_marks` double DEFAULT NULL," +
                    "  `total_marks` double DEFAULT NULL," +
                    "  `percentage` varchar(100) DEFAULT NULL," +
                    "  `comments` varchar(100) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `results_fk` (`uploaded_exam_id`) USING BTREE," +
                    "  KEY `results_fk_1` (`course_id`) USING BTREE," +
                    "  KEY `results_fk_2` (`student_id`) USING BTREE," +
                    "  KEY `exams_results_fk_1` (`exam_id`)," +
                    "  CONSTRAINT `exams_results_fk` FOREIGN KEY (`uploaded_exam_id`) REFERENCES `uploaded_exams` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `exams_results_fk_1` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `results_fk_1_copy` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `results_fk_2_copy` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFaculty() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE `faculty` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `name` varchar(100) DEFAULT NULL," +
                    "  `department` varchar(100) DEFAULT NULL," +
                    "  `office_no` varchar(100) DEFAULT NULL," +
                    "  `salary` double DEFAULT NULL," +
                    "  `date_joined` varchar(100) DEFAULT NULL," +
                    "  `username` varchar(100) DEFAULT NULL," +
                    "  `password` varchar(100) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFacultyPivotCourses() {
        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE IF NOT EXISTS `faculty_pivot_courses` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `faculty_id` int(11) DEFAULT NULL," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `faculty_pivot_courses_fk` (`faculty_id`)," +
                    "  KEY `faculty_pivot_courses_fk_1` (`course_id`)," +
                    "  CONSTRAINT `faculty_pivot_courses_fk` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `faculty_pivot_courses_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setResults() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE IF NOT EXISTS `results` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `student_id` int(11) DEFAULT NULL," +
                    "  `assignment_id` int(11) DEFAULT NULL," +
                    "  `total_marks` double DEFAULT NULL," +
                    "  `obtained_marks` double DEFAULT NULL," +
                    "  `percentage` varchar(100) DEFAULT NULL," +
                    "  `comments` text DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `results_fk_1` (`course_id`)," +
                    "  KEY `results_fk_2` (`student_id`)," +
                    "  KEY `results_fk` (`assignment_id`)," +
                    "  CONSTRAINT `results_fk` FOREIGN KEY (`assignment_id`) REFERENCES `uploaded_assignments` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `results_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `results_fk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStudentPivotCourses() {
        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE IF NOT EXISTS `student_pivot_courses` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `student_id` int(11) DEFAULT NULL," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `student_pivot_courses_fk_1` (`course_id`)," +
                    "  KEY `student_pivot_courses_fk` (`student_id`)," +
                    "  CONSTRAINT `student_pivot_courses_fk` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `student_pivot_courses_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStudents() {
        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE `students` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `name` varchar(100) DEFAULT NULL," +
                    "  `reg_no` varchar(100) DEFAULT NULL," +
                    "  `department` varchar(100) DEFAULT NULL," +
                    "  `phone` varchar(100) DEFAULT NULL," +
                    "  `email` varchar(100) DEFAULT NULL," +
                    "  `fee` double DEFAULT NULL," +
                    "  `date_joined` varchar(100) DEFAULT NULL," +
                    "  `username` varchar(100) DEFAULT NULL," +
                    "  `password` varchar(100) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTimeTable() {
        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE `time_table` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `day` varchar(100) DEFAULT NULL," +
                    "  `time` varchar(100) DEFAULT '---'," +
                    "  `class_venue` varchar(100) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `time_table_fk` (`course_id`)," +
                    "  CONSTRAINT `time_table_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=325 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setUploadedAssignments() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE `uploaded_assignments` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `student_id` int(11) DEFAULT NULL," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `assignment_id` int(11) DEFAULT NULL," +
                    "  `uploaded_date` varchar(100) DEFAULT NULL," +
                    "  `content` text DEFAULT NULL," +
                    "  `status` varchar(100) DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `uploaded_assignments_fk` (`assignment_id`)," +
                    "  KEY `uploaded_assignments_fk_1` (`course_id`)," +
                    "  KEY `uploaded_assignments_fk_2` (`student_id`)," +
                    "  CONSTRAINT `uploaded_assignments_fk` FOREIGN KEY (`assignment_id`) REFERENCES `assignments` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `uploaded_assignments_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE," +
                    "  CONSTRAINT `uploaded_assignments_fk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUploadedExams() {

        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE `uploaded_exams` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `exam_id` int(11) DEFAULT NULL," +
                    "  `student_id` int(11) DEFAULT NULL," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `time_taken` int(11) DEFAULT NULL," +
                    "  `completed_on` varchar(100) DEFAULT NULL," +
                    "  `content` text DEFAULT NULL," +
                    "   `status` varchar(100) DEFAULT NULL," +
                    "    PRIMARY KEY (`id`)," +
                    "    KEY `uploaded_exams_fk` (`exam_id`)," +
                    "    KEY `uploaded_exams_fk_1` (`student_id`)," +
                    "    KEY `uploaded_exams_fk_2` (`course_id`)," +
                    "    CONSTRAINT `uploaded_exams_fk` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`) ON DELETE CASCADE," +
                    "    CONSTRAINT `uploaded_exams_fk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE," +
                    "    CONSTRAINT `uploaded_exams_fk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCoursePlan() {
        try {
            Connection conn = dbConnection.connectToDb();
            String create_table = "CREATE TABLE `course_plan` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `course_id` int(11) DEFAULT NULL," +
                    "  `date` varchar(100) DEFAULT NULL," +
                    "  `plan` text DEFAULT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `course_plan_fk` (`course_id`)," +
                    "  CONSTRAINT `course_plan_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;";

            conn.createStatement().executeUpdate(create_table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
