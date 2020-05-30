

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `school_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_credentials`
--

CREATE TABLE `admin_credentials` (
  `id` int(11) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `assignments`
--

CREATE TABLE `assignments` (
  `id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `topic` varchar(100) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `deadline` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `assignments_results`
--

CREATE TABLE `assignments_results` (
  `id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `assignment_id` int(11) DEFAULT NULL,
  `total_marks` double DEFAULT NULL,
  `obtained_marks` double DEFAULT NULL,
  `percentage` varchar(100) DEFAULT NULL,
  `comments` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `att_date` varchar(100) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `att_day` int(11) DEFAULT NULL,
  `att_month` int(11) DEFAULT NULL,
  `att_year` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `attendance_pivot_courses`
--

CREATE TABLE `attendance_pivot_courses` (
  `id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `course_name` varchar(100) DEFAULT NULL,
  `course_code` varchar(100) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `faculty_name` varchar(100) DEFAULT NULL,
  `start_date` varchar(100) DEFAULT NULL,
  `end_date` varchar(100) DEFAULT NULL,
  `fee` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `course_plan`
--

CREATE TABLE `course_plan` (
  `id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `plan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `exams`
--

CREATE TABLE `exams` (
  `id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `details` text DEFAULT NULL,
  `start_date` varchar(100) DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `time_allotted` int(11) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `exams_results`
--

CREATE TABLE `exams_results` (
  `id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `uploaded_exam_id` int(11) DEFAULT NULL,
  `exam_id` int(11) DEFAULT NULL,
  `obtained_marks` double DEFAULT NULL,
  `total_marks` double DEFAULT NULL,
  `percentage` varchar(100) DEFAULT NULL,
  `comments` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `faculty`
--

CREATE TABLE `faculty` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `office_no` varchar(100) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `date_joined` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `faculty_pivot_courses`
--

CREATE TABLE `faculty_pivot_courses` (
  `id` int(11) NOT NULL,
  `faculty_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE `results` (
  `id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `assignment_id` int(11) DEFAULT NULL,
  `total_marks` double DEFAULT NULL,
  `obtained_marks` double DEFAULT NULL,
  `percentage` varchar(100) DEFAULT NULL,
  `comments` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `reg_no` varchar(100) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `date_joined` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `student_pivot_courses`
--

CREATE TABLE `student_pivot_courses` (
  `id` int(11) NOT NULL,
  `student_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `time_table`
--

CREATE TABLE `time_table` (
  `id` int(11) NOT NULL,
  `course_id` int(11) DEFAULT NULL,
  `day` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT '---',
  `class_venue` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `uploaded_assignments`
--

CREATE TABLE `uploaded_assignments` (
  `id` int(11) NOT NULL,
  `student_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `assignment_id` int(11) DEFAULT NULL,
  `uploaded_date` varchar(100) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `uploaded_exams`
--

CREATE TABLE `uploaded_exams` (
  `id` int(11) NOT NULL,
  `exam_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `time_taken` int(11) DEFAULT NULL,
  `completed_on` varchar(100) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_credentials`
--
ALTER TABLE `admin_credentials`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `assignments`
--
ALTER TABLE `assignments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `assignments_fk` (`course_id`);

--
-- Indexes for table `assignments_results`
--
ALTER TABLE `assignments_results`
  ADD PRIMARY KEY (`id`),
  ADD KEY `results_fk_1` (`course_id`),
  ADD KEY `results_fk_2` (`student_id`),
  ADD KEY `results_fk` (`assignment_id`);

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`id`),
  ADD KEY `attendance_fk` (`course_id`),
  ADD KEY `attendance_fk_1` (`student_id`);

--
-- Indexes for table `attendance_pivot_courses`
--
ALTER TABLE `attendance_pivot_courses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `attendance_entries_fk_1` (`course_id`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `course_plan`
--
ALTER TABLE `course_plan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `course_plan_fk` (`course_id`);

--
-- Indexes for table `exams`
--
ALTER TABLE `exams`
  ADD PRIMARY KEY (`id`),
  ADD KEY `exams_fk` (`course_id`);

--
-- Indexes for table `exams_results`
--
ALTER TABLE `exams_results`
  ADD PRIMARY KEY (`id`),
  ADD KEY `results_fk` (`uploaded_exam_id`) USING BTREE,
  ADD KEY `results_fk_1` (`course_id`) USING BTREE,
  ADD KEY `results_fk_2` (`student_id`) USING BTREE,
  ADD KEY `exams_results_fk_1` (`exam_id`);

--
-- Indexes for table `faculty`
--
ALTER TABLE `faculty`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `faculty_pivot_courses`
--
ALTER TABLE `faculty_pivot_courses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `faculty_pivot_courses_fk` (`faculty_id`),
  ADD KEY `faculty_pivot_courses_fk_1` (`course_id`);

--
-- Indexes for table `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`id`),
  ADD KEY `results_fk_1` (`course_id`),
  ADD KEY `results_fk_2` (`student_id`),
  ADD KEY `results_fk` (`assignment_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student_pivot_courses`
--
ALTER TABLE `student_pivot_courses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student_pivot_courses_fk_1` (`course_id`),
  ADD KEY `student_pivot_courses_fk` (`student_id`);

--
-- Indexes for table `time_table`
--
ALTER TABLE `time_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `time_table_fk` (`course_id`);

--
-- Indexes for table `uploaded_assignments`
--
ALTER TABLE `uploaded_assignments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `uploaded_assignments_fk` (`assignment_id`),
  ADD KEY `uploaded_assignments_fk_1` (`course_id`),
  ADD KEY `uploaded_assignments_fk_2` (`student_id`);

--
-- Indexes for table `uploaded_exams`
--
ALTER TABLE `uploaded_exams`
  ADD PRIMARY KEY (`id`),
  ADD KEY `uploaded_exams_fk` (`exam_id`),
  ADD KEY `uploaded_exams_fk_1` (`student_id`),
  ADD KEY `uploaded_exams_fk_2` (`course_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_credentials`
--
ALTER TABLE `admin_credentials`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `assignments`
--
ALTER TABLE `assignments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `assignments_results`
--
ALTER TABLE `assignments_results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `attendance_pivot_courses`
--
ALTER TABLE `attendance_pivot_courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `course_plan`
--
ALTER TABLE `course_plan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `exams`
--
ALTER TABLE `exams`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `exams_results`
--
ALTER TABLE `exams_results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `faculty`
--
ALTER TABLE `faculty`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `faculty_pivot_courses`
--
ALTER TABLE `faculty_pivot_courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `student_pivot_courses`
--
ALTER TABLE `student_pivot_courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `time_table`
--
ALTER TABLE `time_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=337;

--
-- AUTO_INCREMENT for table `uploaded_assignments`
--
ALTER TABLE `uploaded_assignments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT for table `uploaded_exams`
--
ALTER TABLE `uploaded_exams`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assignments`
--
ALTER TABLE `assignments`
  ADD CONSTRAINT `assignments_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `assignments_results`
--
ALTER TABLE `assignments_results`
  ADD CONSTRAINT `assignments_results_fk` FOREIGN KEY (`assignment_id`) REFERENCES `uploaded_assignments` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `assignments_results_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `assignments_results_fk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `attendance`
--
ALTER TABLE `attendance`
  ADD CONSTRAINT `attendance_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `attendance_fk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `attendance_pivot_courses`
--
ALTER TABLE `attendance_pivot_courses`
  ADD CONSTRAINT `attendance_entries_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `course_plan`
--
ALTER TABLE `course_plan`
  ADD CONSTRAINT `course_plan_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `exams`
--
ALTER TABLE `exams`
  ADD CONSTRAINT `exams_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `exams_results`
--
ALTER TABLE `exams_results`
  ADD CONSTRAINT `exams_results_fk` FOREIGN KEY (`uploaded_exam_id`) REFERENCES `uploaded_exams` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `exams_results_fk_1` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `results_fk_1_copy` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `results_fk_2_copy` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `faculty_pivot_courses`
--
ALTER TABLE `faculty_pivot_courses`
  ADD CONSTRAINT `faculty_pivot_courses_fk` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `faculty_pivot_courses_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `results`
--
ALTER TABLE `results`
  ADD CONSTRAINT `results_fk` FOREIGN KEY (`assignment_id`) REFERENCES `uploaded_assignments` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `results_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `results_fk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `student_pivot_courses`
--
ALTER TABLE `student_pivot_courses`
  ADD CONSTRAINT `student_pivot_courses_fk` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `student_pivot_courses_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `time_table`
--
ALTER TABLE `time_table`
  ADD CONSTRAINT `time_table_fk` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `uploaded_assignments`
--
ALTER TABLE `uploaded_assignments`
  ADD CONSTRAINT `uploaded_assignments_fk` FOREIGN KEY (`assignment_id`) REFERENCES `assignments` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `uploaded_assignments_fk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `uploaded_assignments_fk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `uploaded_exams`
--
ALTER TABLE `uploaded_exams`
  ADD CONSTRAINT `uploaded_exams_fk` FOREIGN KEY (`exam_id`) REFERENCES `exams` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `uploaded_exams_fk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `uploaded_exams_fk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
