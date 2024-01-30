package Base;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;
import SQL.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author pooqw
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    Login login = new Login();
    Randomizer randomizer = new Randomizer();
    TablesOperation tablesOpertaion = new TablesOperation();
    LessonOperation lessonOperation = new LessonOperation();
    TablesOperation tablesReader = new TablesOperation();
    ArrayList<Student> studentArray = new ArrayList<>();
    ArrayList<Teacher> teacherArray = new ArrayList<>();

    Student loggedStudent;
    Teacher loggedTeacher;

    int maxCharacter = 255;
    boolean asama2 = false;

    public GUI() {
        initComponents();

        jTabbedPane.setIconAt(0, new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-admin-50.png")));
        jTabbedPane.setIconAt(1, new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-student-50.png")));
        jTabbedPane.setIconAt(2, new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-teacher-50.png")));
        jTabbedPane.setEnabledAt(0, false);
        jTabbedPane.setEnabledAt(1, false);
        jTabbedPane.setEnabledAt(2, false);
        jTabbedPane.setSelectedIndex(3);

        FilePathCorrect.setVisible(false);
        Student_TeacherCreationCorrect.setVisible(false);

        LoginCorrectIcon.setVisible(false);

    }

    public static String[][] invertArray(String[][] original) {
        int numRows = original.length;
        int numCols = original[0].length;

        String[][] inverted = new String[numCols][numRows];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                inverted[j][i] = original[i][j];
            }
        }

        return inverted;
    }

    public void AdminStudentTableAction() {
        /////////// table 2 ///////////
        // Buttons actions
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                // Student Info Update
                StudentUpdate studentUpdate = new StudentUpdate(tablesReader.getStudentByStudentNo((int) AdminStudentTable.getValueAt(row, 1)));
                studentUpdate.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                // Student Delete
                /*
                int stdNo = (int) AdminStudentTable.getValueAt(row, 1);
                tablesReader.deleteStudent(stdNo);
                 */
            }

            @Override
            public void onView(int row) {
                // Student Info View
                System.out.println("View row : " + row);
                int stdNo = (int) AdminStudentTable.getValueAt(row, 1);
                Student studentToShow = tablesReader.getStudentByStudentNo(stdNo);
                InfoPopup studentInfo = new InfoPopup(studentToShow, true);
                studentInfo.setVisible(true);

            }
        };
        // -------

        // Adding the buttons
        AdminStudentTable.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender());
        AdminStudentTable.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event));
        /*
        AdminStudentTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });
         */
    }

    public void AdminLessonsTableAction() {
        /////////// table ///////////
        // Buttons actions
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
                System.out.println("Value: " + AdminLessonsTable.getValueAt(row, 1));
                lessonOperation.requestToAddLesson((int) AdminLessonsTable.getValueAt(row, 7), (String) AdminLessonsTable.getValueAt(row, 1));
                lessonOperation.approveOrRejectRequest((String) AdminLessonsTable.getValueAt(row, 1), (int) AdminLessonsTable.getValueAt(row, 7), (int) AdminLessonsTable.getValueAt(row, 2), "Onayli", asama2);
            }

            @Override
            public void onDelete(int row) {
                lessonOperation.cancelLessonRequest((int) AdminLessonsTable.getValueAt(row, 7), (String) AdminLessonsTable.getValueAt(row, 1));
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                System.out.println("Teacher Details Popup");
                Teacher teacher = tablesReader.getTeacherByTeacherNo((int) AdminLessonsTable.getValueAt(row, 2));
                InfoPopup infoPopup = new InfoPopup(teacher);
                infoPopup.setVisible(true);
            }
        };
        // -------

        // Adding the buttons
        AdminLessonsTable.getColumnModel().getColumn(8).setCellRenderer(new TableActionCellRender());
        AdminLessonsTable.getColumnModel().getColumn(8).setCellEditor(new TableActionCellEditor(event));
        /*
        StudentLessonTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        
        });
        
         */

        ////////// ------------ ////////////
    }
    
    
    

    public void AdminLessonsModifyTableAction() {
        /////////// table ///////////
        // Buttons actions
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
                System.out.println("Value: " + AdminLessonsModifyTable.getValueAt(row, 1));
                Lesson lesson = tablesOpertaion.getLessonFromNewLessons((String) AdminLessonsModifyTable.getValueAt(row, 1), (int) AdminLessonsModifyTable.getValueAt(row, 2));
                System.out.println("LESSON:" + lesson);
                LessonUpdate lessonUpdate = new LessonUpdate(lesson);
                lessonUpdate.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                tablesOpertaion.deleteNewLesson((String) AdminLessonsModifyTable.getValueAt(row, 0), (String) AdminLessonsModifyTable.getValueAt(row, 1),
                        (String) AdminLessonsModifyTable.getValueAt(row, 4), (int) AdminLessonsModifyTable.getValueAt(row, 2));
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                System.out.println("nothing");

            }
        };
        // -------

        // Adding the buttons
        AdminLessonsModifyTable.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        AdminLessonsModifyTable.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        /*
        StudentLessonTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        
        });
        
         */

        ////////// ------------ ////////////
    }

    public void AdminTeacherTableAction() {
        /////////// table 2 ///////////
        // Buttons actions
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
                int teacherNo = (int) AdminTeacherTable.getValueAt(row, 1);
                TeacherUpdate teacherUpdate = new TeacherUpdate(tablesReader.getTeacherByTeacherNo(teacherNo));
                teacherUpdate.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                System.out.println("Delete row : " + row);
                /*
                int teacherNo = (int) AdminTeacherTable.getValueAt(row, 1);
                tablesReader.deleteTeacher(teacherNo);
                 */

            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                int teacherNo = (int) AdminTeacherTable.getValueAt(row, 1);
                Teacher teacherToShow = tablesReader.getTeacherByTeacherNo(teacherNo);
                InfoPopup teacherInfo = new InfoPopup(teacherToShow);
                teacherInfo.setVisible(true);
            }
        };
        // -------

        // Adding the buttons
        AdminTeacherTable.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        AdminTeacherTable.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
        /*
        AdminTeacherTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });
         */
    }

    public void StudentLessonsTableAction() {
        /////////// table ///////////
        // Buttons actions
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
                System.out.println("Value: " + StudentLessonsTable.getValueAt(row, 1));
                lessonOperation.requestToAddLesson(loggedStudent.getStudentNo(), (String) StudentLessonsTable.getValueAt(row, 1));

            }

            @Override
            public void onDelete(int row) {
                lessonOperation.cancelLessonRequest(loggedStudent.getStudentNo(), (String) StudentLessonsTable.getValueAt(row, 1));
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                System.out.println("Teacher Details Popup");
                Teacher teacher = tablesReader.getTeacherByTeacherNo((int) StudentLessonsTable.getValueAt(row, 2));
                InfoPopup infoPopup = new InfoPopup(teacher);
                infoPopup.setVisible(true);
            }
        };
        // -------

        // Adding the buttons
        StudentLessonsTable.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        StudentLessonsTable.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        /*
        StudentLessonTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        
        });
        
         */

        ////////// ------------ ////////////
    }

    public void TeacherRequestTableAction() {
        /////////// table ///////////
        // Buttons actions
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
                System.out.println("Value: " + TeacherRequestsTable.getValueAt(row, 1));
                lessonOperation.approveOrRejectRequest((String) TeacherRequestsTable.getValueAt(row, 1), (int) TeacherRequestsTable.getValueAt(row, 2), loggedTeacher.getTeacherNo(), "Onayli", asama2);
            }

            @Override
            public void onDelete(int row) {
                lessonOperation.approveOrRejectRequest((String) TeacherRequestsTable.getValueAt(row, 1), (int) TeacherRequestsTable.getValueAt(row, 2), loggedTeacher.getTeacherNo(), "Reddedilmiş", asama2);
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);

            }
        };
        // -------

        // Adding the buttons
        TeacherRequestsTable.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        TeacherRequestsTable.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
        /*
        StudentLessonTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        
        });
        
         */

        ////////// ------------ ////////////
    }

    public void TeacherStudentTableAction() {
        /////////// table 2 ///////////
        // Buttons actions
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
            }

            @Override
            public void onDelete(int row) {
                System.out.println("Delete row : " + row);

            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                int studentNo = (int) TeacherStudentsTable.getValueAt(row, 1);
                Student studentToShow = tablesReader.getStudentByStudentNo(studentNo);
                InfoPopup studentInfo = new InfoPopup(studentToShow, false);/// Tum dersler listenmemeli 
                studentInfo.setVisible(true);
            }
        };
        // -------

        // Adding the buttons
        TeacherStudentsTable.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        TeacherStudentsTable.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        /*
        AdminTeacherTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });
         */
    }
    
    
    public void TeacherLessonsModifyTableAction() {
        /////////// table ///////////
        // Buttons actions
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
                System.out.println("Value: " + TeacherLessonTable.getValueAt(row, 1));
                Lesson lesson = tablesOpertaion.getLessonFromNewLessons((String) TeacherLessonTable.getValueAt(row, 1), loggedTeacher.getTeacherNo());
                System.out.println("LESSON:" + lesson);
                LessonUpdate lessonUpdate = new LessonUpdate(lesson);
                lessonUpdate.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                tablesOpertaion.deleteNewLesson((String) TeacherLessonTable.getValueAt(row, 0), (String) TeacherLessonTable.getValueAt(row, 1),
                        (String) TeacherLessonTable.getValueAt(row, 2), loggedTeacher.getTeacherNo());
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                System.out.println("nothing");

            }
        };
        // -------

        // Adding the buttons
        TeacherLessonTable.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
        TeacherLessonTable.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
        /*
        StudentLessonTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        
        });
        
         */

        ////////// ------------ ////////////
    }

    public void AdminTablesUpdater() {

        DefaultTableModel adminStudentTable = (DefaultTableModel) AdminStudentTable.getModel();
        DefaultTableModel adminTeacherTable = (DefaultTableModel) AdminTeacherTable.getModel();
        DefaultTableModel adminStudentRequestsTable = (DefaultTableModel) AdminRequestsTable.getModel();
        DefaultTableModel adminLessonsTable = (DefaultTableModel) AdminLessonsTable.getModel();
        DefaultTableModel adminOldLessonsTable = (DefaultTableModel) AdminOldLessonsTable.getModel();
        DefaultTableModel adminLessonsModifyTable = (DefaultTableModel) AdminLessonsModifyTable.getModel();

        adminStudentTable.setRowCount(0);
        adminTeacherTable.setRowCount(0);
        adminStudentRequestsTable.setRowCount(0);
        adminLessonsTable.setRowCount(0);
        adminOldLessonsTable.setRowCount(0);
        adminLessonsModifyTable.setRowCount(0);

        // Admin Students
        List<Student> studentsList = login.getAllStudents();

        // Populate the JTable with data from the list
        for (Student obj : studentsList) {
            //Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            Object[] rowData = {obj.getStudentName(), obj.getStudentNo(), obj.getIlgiAlanlar(), obj.getAcademicAverage(), obj.getRequests_count()};
            adminStudentTable.addRow(rowData);
        }
        AdminStudentTableAction();

        // Admin Teachers
        List<Teacher> teachersList = login.getAllTeachers();

        // Populate the JTable with data from the list
        for (Teacher obj : teachersList) {
            //Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            Object[] rowData = {obj.getTeacherName(), obj.getTeacherNo(), obj.getIlgiAlanlar()};
            adminTeacherTable.addRow(rowData);
        }
        AdminTeacherTableAction();

        //// Admin Students Requests
        List<Lesson> stdRequests = lessonOperation.getStudentsRequests();

        // Populate the JTable with data from the list      
        for (Lesson obj : stdRequests) {
            Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), obj.getStudentNo(), obj.getTeacherNo(), obj.getStatus()};
            adminStudentRequestsTable.addRow(rowData);
        }

        // Admin Lessons Table
        List<Lesson> offeredLessons = lessonOperation.getAllOfferedLessons();
        System.out.println("offered Lessons: " + offeredLessons);

        // Populate the JTable with data from the list      
        for (Lesson obj : offeredLessons) {
            Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), obj.getTeacherNo(), teacher.getTeacherName(), obj.getIlgiAlanlar(), obj.getTotalKont() + "/" + obj.getRemKont(), obj.getStatus(), obj.getStudentNo()};
            adminLessonsTable.addRow(rowData);
        }

        AdminLessonsTableAction();

        ///// Admin Old Lessons Table
        List<Lesson> allOldLessons = lessonOperation.getOldLessons(); // And Past Lessons

        // Populate the JTable with data from the list
        for (Lesson obj : allOldLessons) {

            Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name

            if (teacher != null) {
                Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), obj.getStudentNo(), teacher.getTeacherName(), obj.getTeacherNo(), obj.getStatus()};
                adminOldLessonsTable.addRow(rowData);
            } else {
                Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), obj.getStudentNo(), null, obj.getTeacherNo(), obj.getStatus()};
                adminOldLessonsTable.addRow(rowData);
            }

        }

        // Admin Lessons Modify Table
        // Populate the JTable with data from the list      
        for (Lesson obj : offeredLessons) {
            Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), obj.getTeacherNo(), teacher.getTeacherName(), obj.getIlgiAlanlar(), obj.getTotalKont() + "/" + obj.getRemKont()};
            adminLessonsModifyTable.addRow(rowData);
        }

        AdminLessonsModifyTableAction();

    }

    public void StudentTablesUpdater() {
        /// Student Lesson Table
        DefaultTableModel studentLessonTable = (DefaultTableModel) StudentLessonsTable.getModel();
        DefaultTableModel studentRequestsTable = (DefaultTableModel) StudentRequestsTable.getModel();
        DefaultTableModel studentOldLessonsTable = (DefaultTableModel) StudentOldLessonsTable.getModel();
        studentLessonTable.setRowCount(0);
        studentRequestsTable.setRowCount(0);
        studentOldLessonsTable.setRowCount(0);

        ////// Student Lesson Table
        List<Lesson> offeredLessons = lessonOperation.getAllOfferedLessons();

        Set<String> ilgiAlanList = new HashSet<>();

        // Populate the JTable with data from the list
        for (Lesson obj : offeredLessons) {
            Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            ilgiAlanList.add(obj.getIlgiAlanlar());
            Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), obj.getTeacherNo(), teacher.getTeacherName(), obj.getIlgiAlanlar(), obj.getTotalKont() + "/" + obj.getRemKont(), obj.getIlgiAlanlar()};
            studentLessonTable.addRow(rowData);
        }
        ilgiAlanComboBox.removeAllItems();
        for (String word : ilgiAlanList) {
            ilgiAlanComboBox.addItem(word);
        }

        StudentLessonsTableAction();
        /// -------------

        ///// Student Requests Table
        List<Lesson> studentRequests = lessonOperation.getStudentRequestsByStudentNo(loggedStudent.getStudentNo()); // And Past Lessons

        // Populate the JTable with data from the list
        for (Lesson obj : studentRequests) {
            Teacher teacher;
            teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), teacher.getTeacherName(), obj.getTeacherNo(), obj.getStatus()};
            studentRequestsTable.addRow(rowData);

        }

        ///// Student Old Lessons Table
        List<Lesson> studentOldLessons = lessonOperation.getStudentOldLessons(loggedStudent.getStudentNo()); // And Past Lessons

        // Populate the JTable with data from the list
        for (Lesson obj : studentOldLessons) {

            Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name

            if (teacher != null) {
                Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), teacher.getTeacherName(), obj.getTeacherNo(), obj.getStatus()};
                studentOldLessonsTable.addRow(rowData);
            } else {
                Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), null, obj.getTeacherNo(), obj.getStatus()};
                studentOldLessonsTable.addRow(rowData);
            }

        }

    }

    public void TeacherTablesUpdater() {

        DefaultTableModel teacherLessonTable = (DefaultTableModel) TeacherLessonTable.getModel();
        DefaultTableModel teacherStudentsTable = (DefaultTableModel) TeacherStudentsTable.getModel();
        DefaultTableModel teacherLessonScoringTable = (DefaultTableModel) TeacherLessonScoringTable.getModel();

        teacherLessonTable.setRowCount(0);
        teacherStudentsTable.setRowCount(0);
        teacherLessonScoringTable.setRowCount(0);

        /// Teacher Lesson Table
        List<Lesson> teacherLessons = lessonOperation.getTeacherLessons(loggedTeacher.getTeacherNo());

        // Populate the JTable with data from the list
        for (Lesson obj : teacherLessons) {
            //Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), obj.getIlgiAlanlar(), obj.getRemKont(), obj.getStatus()};
            teacherLessonTable.addRow(rowData);
        }

        TeacherLessonsModifyTableAction();
        /// -------------
        ///// Teacher Requests Table
        DefaultTableModel teacherRequestsTable = (DefaultTableModel) TeacherRequestsTable.getModel();
        teacherRequestsTable.setRowCount(0);

        List<Lesson> studentRequests = lessonOperation.getIncomingRequestsForTeacher(loggedTeacher.getTeacherNo());

        // Populate the JTable with data from the list
        for (Lesson obj : studentRequests) {
            Student student = tablesReader.getStudentByStudentNo(obj.getStudentNo()); // for Teacher name
            Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), student.getStudentNo(), obj.getStatus()};
            teacherRequestsTable.addRow(rowData);
        }

        TeacherRequestTableAction();

        // Teacher Students
        List<Student> studentsList = login.getAllStudents();

        // Populate the JTable with data from the list
        for (Student obj : studentsList) {
            //Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            Object[] rowData = {obj.getStudentName(), obj.getStudentNo(), obj.getIlgiAlanlar(), obj.getAcademicAverage(), obj.getRequests_count()};
            teacherStudentsTable.addRow(rowData);
        }
        TeacherStudentTableAction();

        /// Teacher Lesson Scoring Table
        List<Lesson> oldLessons = lessonOperation.getTeacherOldLessons(loggedTeacher.getTeacherNo());

        // Populate the JTable with data from the list
        for (Lesson obj : oldLessons) {
            //Teacher teacher = tablesReader.getTeacherByTeacherNo(obj.getTeacherNo()); // for Teacher name
            Object[] rowData = {obj.getLessonName(), obj.getLessonCode(), obj.getIlgiAlanlar(), obj.getTeacherNo(), obj.getStatus()};
            teacherLessonScoringTable.addRow(rowData);
        }

    }

    public double Katsayi_x_HarfNotu(int katsayi, String harfNotu) {
        if (harfNotu.equals("AA")) {
            return 4 * katsayi;
        } else if (harfNotu.equals("BA")) {
            return 3.5 * katsayi;
        } else if (harfNotu.equals("BB")) {
            return 3 * katsayi;
        } else if (harfNotu.equals("CB")) {
            return 2.50 * katsayi;
        } else if (harfNotu.equals("CC")) {
            return 2 * katsayi;
        } else if (harfNotu.equals("DC")) {
            return 1.5 * katsayi;
        } else if (harfNotu.equals("DD")) {
            return 1.5 * katsayi;
        } else {
            System.out.println("0 Harf");
            return 0;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        YoneticiTab = new javax.swing.JPanel();
        AllowedSymbolsPanel = new javax.swing.JPanel();
        CreateStudentButton = new javax.swing.JButton();
        CreateTeacherButton = new javax.swing.JButton();
        NoField = new javax.swing.JTextField();
        NameField = new javax.swing.JTextField();
        Student_TeacherCreationCorrect = new javax.swing.JButton();
        RandomTeacherCountField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Asama1Button = new javax.swing.JButton();
        priorityAssignStudentsToLessonsButton = new javax.swing.JButton();
        StudentNameLabel6 = new javax.swing.JLabel();
        MaxCharacterCountField = new javax.swing.JTextField();
        MaxCharacterCountButton = new javax.swing.JButton();
        TotalKontField = new javax.swing.JTextField();
        StudentNameLabel7 = new javax.swing.JLabel();
        TotalKontButton = new javax.swing.JButton();
        NewLessonPopupButton1 = new javax.swing.JButton();
        Asama2Button = new javax.swing.JButton();
        PasswordField = new javax.swing.JTextField();
        ilgiAlanField = new javax.swing.JTextField();
        RandomStudentCountButton = new javax.swing.JButton();
        RandomStudentCountField = new javax.swing.JTextField();
        randomlyAddLessonsToStudentsButton = new javax.swing.JButton();
        assignStudentsToSelectedLessonsButton = new javax.swing.JButton();
        RandomNewLessonCountField = new javax.swing.JTextField();
        RandomNewLessonCountButton = new javax.swing.JButton();
        RandomTeacherCountButton = new javax.swing.JButton();
        StudentNameLabel8 = new javax.swing.JLabel();
        StudentNameLabel9 = new javax.swing.JLabel();
        StudentNameLabel10 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        AdminStudentTable = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        AdminTeacherTable = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        AdminRequestsTable = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        AdminLessonsTable = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        AdminOldLessonsTable = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        AdminLessonsModifyTable = new javax.swing.JTable();
        NewLessonPopupButton2 = new javax.swing.JButton();
        UpdateButton2 = new javax.swing.JButton();
        OgrenciTab = new javax.swing.JPanel();
        Title = new javax.swing.JPanel();
        StudentNameLabel = new javax.swing.JLabel();
        PathMessage = new javax.swing.JLabel();
        StudentMyInfoButton = new javax.swing.JButton();
        Fpath = new javax.swing.JPanel();
        Confirm = new javax.swing.JButton();
        SelectPath = new javax.swing.JButton();
        FilePathCorrect = new javax.swing.JButton();
        PathArea = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        OcrArea = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jLabel3 = new javax.swing.JLabel();
        BottomMessages = new javax.swing.JPanel();
        StdMessageReadButton = new javax.swing.JButton();
        StdMessageSendButton = new javax.swing.JButton();
        UpdateButton = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        StudentLessonsTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        StudentRequestsTable = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        StudentOldLessonsTable = new javax.swing.JTable();
        ilgiAlanComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        HocaTab = new javax.swing.JPanel();
        TeacherNameLabel = new javax.swing.JLabel();
        TeacherMessaageReadButton = new javax.swing.JButton();
        TeacherMessageSendButton = new javax.swing.JButton();
        UpdateButton1 = new javax.swing.JButton();
        ilgiAlanTextField = new javax.swing.JTextField();
        CreateTeacherButton1 = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        TeacherLessonTable = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        TeacherStudentsTable = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        TeacherRequestsTable = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        TeacherLessonScoringTable = new javax.swing.JTable();
        StudentNameLabel5 = new javax.swing.JLabel();
        CreateScoringFormulaButton = new javax.swing.JButton();
        TeacherMyInfoButton = new javax.swing.JButton();
        NewLessonPopupButton3 = new javax.swing.JButton();
        Login = new javax.swing.JPanel();
        IdField = new javax.swing.JTextField();
        LoginTitle = new javax.swing.JLabel();
        GirisButton = new javax.swing.JButton();
        CikisButton = new javax.swing.JButton();
        LoginCorrectIcon = new javax.swing.JButton();
        PasField = new javax.swing.JPasswordField();
        LoginTypeBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1180, 700));
        setResizable(false);

        pnlRoot.setBackground(new java.awt.Color(22, 22, 22));
        pnlRoot.setLayout(new java.awt.BorderLayout());

        jTabbedPane.setBackground(new java.awt.Color(21, 25, 28));
        jTabbedPane.setForeground(new java.awt.Color(102, 204, 255));
        jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane.setToolTipText("");
        jTabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTabbedPane.setPreferredSize(new java.awt.Dimension(200, 200));
        jTabbedPane.setRequestFocusEnabled(false);

        YoneticiTab.setBackground(new java.awt.Color(34, 40, 44));
        YoneticiTab.setPreferredSize(new java.awt.Dimension(100, 100));
        YoneticiTab.setLayout(null);

        AllowedSymbolsPanel.setBackground(new java.awt.Color(34, 40, 44));
        AllowedSymbolsPanel.setLayout(null);

        CreateStudentButton.setBackground(new java.awt.Color(0, 51, 51));
        CreateStudentButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CreateStudentButton.setText("Create Student");
        CreateStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateStudentButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(CreateStudentButton);
        CreateStudentButton.setBounds(170, 80, 140, 30);

        CreateTeacherButton.setBackground(new java.awt.Color(0, 51, 51));
        CreateTeacherButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CreateTeacherButton.setText("Create Teacher");
        CreateTeacherButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateTeacherButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(CreateTeacherButton);
        CreateTeacherButton.setBounds(20, 80, 140, 30);

        NoField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NoField.setText("ID");
        NoField.setPreferredSize(new java.awt.Dimension(300, 22));
        NoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoFieldActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(NoField);
        NoField.setBounds(150, 50, 129, 30);

        NameField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NameField.setText("Name-Surname");
        NameField.setPreferredSize(new java.awt.Dimension(300, 22));
        NameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameFieldActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(NameField);
        NameField.setBounds(20, 50, 129, 30);

        Student_TeacherCreationCorrect.setBackground(new java.awt.Color(34, 40, 44));
        Student_TeacherCreationCorrect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_in_progress_30px.png"))); // NOI18N
        Student_TeacherCreationCorrect.setAlignmentX(0.5F);
        Student_TeacherCreationCorrect.setBorderPainted(false);
        Student_TeacherCreationCorrect.setContentAreaFilled(false);
        Student_TeacherCreationCorrect.setDefaultCapable(false);
        Student_TeacherCreationCorrect.setFocusPainted(false);
        Student_TeacherCreationCorrect.setFocusable(false);
        AllowedSymbolsPanel.add(Student_TeacherCreationCorrect);
        Student_TeacherCreationCorrect.setBounds(430, 10, 36, 37);

        RandomTeacherCountField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        RandomTeacherCountField.setText("Random teacher count");
        RandomTeacherCountField.setPreferredSize(new java.awt.Dimension(300, 22));
        RandomTeacherCountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RandomTeacherCountFieldActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(RandomTeacherCountField);
        RandomTeacherCountField.setBounds(160, 160, 141, 20);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Admin Panel");
        AllowedSymbolsPanel.add(jLabel9);
        jLabel9.setBounds(260, 0, 187, 45);

        Asama1Button.setBackground(new java.awt.Color(51, 0, 51));
        Asama1Button.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Asama1Button.setText("Stage 1");
        Asama1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Asama1ButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(Asama1Button);
        Asama1Button.setBounds(580, 100, 100, 20);

        priorityAssignStudentsToLessonsButton.setBackground(new java.awt.Color(102, 0, 0));
        priorityAssignStudentsToLessonsButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        priorityAssignStudentsToLessonsButton.setText("Not ortalamasına gore Atama");
        priorityAssignStudentsToLessonsButton.setEnabled(false);
        priorityAssignStudentsToLessonsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priorityAssignStudentsToLessonsButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(priorityAssignStudentsToLessonsButton);
        priorityAssignStudentsToLessonsButton.setBounds(560, 160, 250, 20);

        StudentNameLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        StudentNameLabel6.setForeground(new java.awt.Color(255, 255, 255));
        StudentNameLabel6.setText("Message limit");
        AllowedSymbolsPanel.add(StudentNameLabel6);
        StudentNameLabel6.setBounds(820, 10, 140, 25);

        MaxCharacterCountField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MaxCharacterCountField.setText("Maksimum character count");
        MaxCharacterCountField.setPreferredSize(new java.awt.Dimension(300, 22));
        MaxCharacterCountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxCharacterCountFieldActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(MaxCharacterCountField);
        MaxCharacterCountField.setBounds(820, 40, 180, 30);

        MaxCharacterCountButton.setBackground(new java.awt.Color(0, 51, 102));
        MaxCharacterCountButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        MaxCharacterCountButton.setText("Update");
        MaxCharacterCountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MaxCharacterCountButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(MaxCharacterCountButton);
        MaxCharacterCountButton.setBounds(820, 70, 100, 20);

        TotalKontField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TotalKontField.setText("Total quota limit");
        TotalKontField.setPreferredSize(new java.awt.Dimension(300, 22));
        TotalKontField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalKontFieldActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(TotalKontField);
        TotalKontField.setBounds(630, 40, 180, 30);

        StudentNameLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        StudentNameLabel7.setForeground(new java.awt.Color(255, 255, 255));
        StudentNameLabel7.setText("Quota limit");
        AllowedSymbolsPanel.add(StudentNameLabel7);
        StudentNameLabel7.setBounds(630, 10, 160, 25);

        TotalKontButton.setBackground(new java.awt.Color(0, 51, 102));
        TotalKontButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TotalKontButton.setText("Update");
        TotalKontButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TotalKontButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(TotalKontButton);
        TotalKontButton.setBounds(630, 70, 100, 20);

        NewLessonPopupButton1.setBackground(new java.awt.Color(0, 102, 102));
        NewLessonPopupButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        NewLessonPopupButton1.setForeground(new java.awt.Color(255, 255, 255));
        NewLessonPopupButton1.setText("Add a new lesson");
        NewLessonPopupButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewLessonPopupButton1ActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(NewLessonPopupButton1);
        NewLessonPopupButton1.setBounds(20, 120, 190, 20);

        Asama2Button.setBackground(new java.awt.Color(102, 0, 0));
        Asama2Button.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Asama2Button.setText("Stage 2");
        Asama2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Asama2ButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(Asama2Button);
        Asama2Button.setBounds(690, 100, 100, 20);

        PasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PasswordField.setText("Password");
        PasswordField.setPreferredSize(new java.awt.Dimension(300, 22));
        PasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordFieldActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(PasswordField);
        PasswordField.setBounds(280, 50, 141, 30);

        ilgiAlanField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ilgiAlanField.setText("Fields of interest");
        ilgiAlanField.setPreferredSize(new java.awt.Dimension(300, 22));
        ilgiAlanField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ilgiAlanFieldActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(ilgiAlanField);
        ilgiAlanField.setBounds(420, 50, 140, 30);

        RandomStudentCountButton.setBackground(new java.awt.Color(102, 0, 102));
        RandomStudentCountButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        RandomStudentCountButton.setForeground(new java.awt.Color(255, 255, 255));
        RandomStudentCountButton.setText("Create");
        RandomStudentCountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RandomStudentCountButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(RandomStudentCountButton);
        RandomStudentCountButton.setBounds(10, 180, 90, 23);

        RandomStudentCountField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        RandomStudentCountField.setText("Random sdnt count");
        RandomStudentCountField.setPreferredSize(new java.awt.Dimension(300, 22));
        RandomStudentCountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RandomStudentCountFieldActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(RandomStudentCountField);
        RandomStudentCountField.setBounds(10, 160, 141, 20);

        randomlyAddLessonsToStudentsButton.setBackground(new java.awt.Color(102, 0, 0));
        randomlyAddLessonsToStudentsButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        randomlyAddLessonsToStudentsButton.setText("Rastgele Atama");
        randomlyAddLessonsToStudentsButton.setEnabled(false);
        randomlyAddLessonsToStudentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomlyAddLessonsToStudentsButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(randomlyAddLessonsToStudentsButton);
        randomlyAddLessonsToStudentsButton.setBounds(560, 130, 250, 20);

        assignStudentsToSelectedLessonsButton.setBackground(new java.awt.Color(102, 0, 0));
        assignStudentsToSelectedLessonsButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        assignStudentsToSelectedLessonsButton.setText("Belirli derslere gore atama");
        assignStudentsToSelectedLessonsButton.setEnabled(false);
        assignStudentsToSelectedLessonsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignStudentsToSelectedLessonsButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(assignStudentsToSelectedLessonsButton);
        assignStudentsToSelectedLessonsButton.setBounds(560, 190, 250, 20);

        RandomNewLessonCountField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        RandomNewLessonCountField.setText("Random new lesson count");
        RandomNewLessonCountField.setPreferredSize(new java.awt.Dimension(300, 22));
        RandomNewLessonCountField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RandomNewLessonCountFieldActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(RandomNewLessonCountField);
        RandomNewLessonCountField.setBounds(310, 160, 170, 20);

        RandomNewLessonCountButton.setBackground(new java.awt.Color(102, 0, 102));
        RandomNewLessonCountButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        RandomNewLessonCountButton.setForeground(new java.awt.Color(255, 255, 255));
        RandomNewLessonCountButton.setText("Create");
        RandomNewLessonCountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RandomNewLessonCountButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(RandomNewLessonCountButton);
        RandomNewLessonCountButton.setBounds(310, 180, 90, 23);

        RandomTeacherCountButton.setBackground(new java.awt.Color(102, 0, 102));
        RandomTeacherCountButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        RandomTeacherCountButton.setForeground(new java.awt.Color(255, 255, 255));
        RandomTeacherCountButton.setText("Create");
        RandomTeacherCountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RandomTeacherCountButtonActionPerformed(evt);
            }
        });
        AllowedSymbolsPanel.add(RandomTeacherCountButton);
        RandomTeacherCountButton.setBounds(160, 180, 90, 23);

        StudentNameLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        StudentNameLabel8.setForeground(new java.awt.Color(255, 255, 255));
        StudentNameLabel8.setText("Ders secimi gerekli (Ders Ekleme)");
        AllowedSymbolsPanel.add(StudentNameLabel8);
        StudentNameLabel8.setBounds(820, 190, 210, 20);

        StudentNameLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        StudentNameLabel9.setForeground(new java.awt.Color(255, 255, 255));
        StudentNameLabel9.setText("Hoca onceligi gerekli (Hocalar)");
        AllowedSymbolsPanel.add(StudentNameLabel9);
        StudentNameLabel9.setBounds(820, 210, 210, 10);

        StudentNameLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        StudentNameLabel10.setForeground(new java.awt.Color(255, 255, 255));
        StudentNameLabel10.setText("Hoca onceligi gerekli (Hocalar)");
        AllowedSymbolsPanel.add(StudentNameLabel10);
        StudentNameLabel10.setBounds(820, 160, 210, 20);

        YoneticiTab.add(AllowedSymbolsPanel);
        AllowedSymbolsPanel.setBounds(0, 6, 1016, 220);

        AdminStudentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Full Name", "Student ID", "Fields Of Interest", "GPA", "Requests Count", "Process"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        AdminStudentTable.setRowHeight(40);
        AdminStudentTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane3.setViewportView(AdminStudentTable);

        jTabbedPane3.addTab("Students", jScrollPane3);

        AdminTeacherTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Teacher Full Name", "Teacher ID", "Fields Of Interest", "Teacher Random Order", "Process"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        AdminTeacherTable.setRowHeight(40);
        AdminTeacherTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane7.setViewportView(AdminTeacherTable);

        jTabbedPane3.addTab("Teachers", jScrollPane7);

        AdminRequestsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lesson", "Lesson Code", "Student ID", "Teacher ID", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        AdminRequestsTable.setRowHeight(40);
        AdminRequestsTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane9.setViewportView(AdminRequestsTable);

        jTabbedPane3.addTab("Requests", jScrollPane9);

        AdminLessonsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Lesson", "Lesson Code", "Teacher ID", "Teacher Full Name", "Fields Of Interest", "Quotas", "Status", "Add Student ID", "Process"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        AdminLessonsTable.setRowHeight(40);
        AdminLessonsTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane5.setViewportView(AdminLessonsTable);

        jTabbedPane3.addTab("Adding Lesson (Request Create)", jScrollPane5);

        AdminOldLessonsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Lesson", "Lesson Code", "Student ID", "Teacher Full Name", "Teacher ID", "Lesson Grade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        AdminOldLessonsTable.setRowHeight(40);
        AdminOldLessonsTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane13.setViewportView(AdminOldLessonsTable);

        jTabbedPane3.addTab("Past Lessons", jScrollPane13);

        AdminLessonsModifyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Lesson", "Lesson Code", "Teacher ID", "Teacher Full Name", "Fields Of Interest", "Quotas", "Process"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        AdminLessonsModifyTable.setRowHeight(40);
        AdminLessonsModifyTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane14.setViewportView(AdminLessonsModifyTable);

        jTabbedPane3.addTab("New Lessons Info Update", jScrollPane14);

        YoneticiTab.add(jTabbedPane3);
        jTabbedPane3.setBounds(0, 250, 1030, 370);

        NewLessonPopupButton2.setBackground(new java.awt.Color(102, 0, 102));
        NewLessonPopupButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        NewLessonPopupButton2.setForeground(new java.awt.Color(255, 255, 255));
        NewLessonPopupButton2.setText("Sort Stdnt GPA");
        NewLessonPopupButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewLessonPopupButton2ActionPerformed(evt);
            }
        });
        YoneticiTab.add(NewLessonPopupButton2);
        NewLessonPopupButton2.setBounds(10, 230, 120, 23);

        UpdateButton2.setBackground(new java.awt.Color(0, 102, 51));
        UpdateButton2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        UpdateButton2.setForeground(new java.awt.Color(255, 255, 255));
        UpdateButton2.setText("Update");
        UpdateButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButton2ActionPerformed(evt);
            }
        });
        YoneticiTab.add(UpdateButton2);
        UpdateButton2.setBounds(880, 234, 146, 30);

        jTabbedPane.addTab("Admin", YoneticiTab);

        OgrenciTab.setBackground(new java.awt.Color(34, 40, 44));
        OgrenciTab.setPreferredSize(new java.awt.Dimension(50, 100));
        OgrenciTab.setLayout(null);

        Title.setBackground(new java.awt.Color(34, 40, 44));
        Title.setToolTipText("");
        Title.setPreferredSize(new java.awt.Dimension(1000, 100));
        Title.setLayout(null);

        StudentNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        StudentNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        StudentNameLabel.setText("Teacher Panel");
        Title.add(StudentNameLabel);
        StudentNameLabel.setBounds(155, 0, 299, 45);

        PathMessage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PathMessage.setForeground(new java.awt.Color(255, 51, 51));
        Title.add(PathMessage);
        PathMessage.setBounds(498, 51, 383, 0);

        StudentMyInfoButton.setBackground(new java.awt.Color(0, 102, 102));
        StudentMyInfoButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        StudentMyInfoButton.setForeground(new java.awt.Color(255, 255, 255));
        StudentMyInfoButton.setText("My Info");
        StudentMyInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentMyInfoButtonActionPerformed(evt);
            }
        });
        Title.add(StudentMyInfoButton);
        StudentMyInfoButton.setBounds(790, 10, 130, 34);

        OgrenciTab.add(Title);
        Title.setBounds(97, 5, 930, 46);

        Fpath.setBackground(new java.awt.Color(34, 40, 44));
        Fpath.setToolTipText("");
        Fpath.setPreferredSize(new java.awt.Dimension(1000, 100));
        Fpath.setLayout(null);

        Confirm.setBackground(new java.awt.Color(51, 102, 0));
        Confirm.setText("Confirm");
        Confirm.setPreferredSize(new java.awt.Dimension(100, 22));
        Confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmActionPerformed(evt);
            }
        });
        Fpath.add(Confirm);
        Confirm.setBounds(230, 70, 90, 23);

        SelectPath.setText("Select File");
        SelectPath.setPreferredSize(new java.awt.Dimension(100, 22));
        SelectPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectPathActionPerformed(evt);
            }
        });
        Fpath.add(SelectPath);
        SelectPath.setBounds(330, 70, 90, 23);

        FilePathCorrect.setBackground(new java.awt.Color(34, 40, 44));
        FilePathCorrect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_in_progress_30px.png"))); // NOI18N
        FilePathCorrect.setAlignmentX(0.5F);
        FilePathCorrect.setBorderPainted(false);
        FilePathCorrect.setContentAreaFilled(false);
        FilePathCorrect.setDefaultCapable(false);
        FilePathCorrect.setFocusPainted(false);
        FilePathCorrect.setFocusable(false);
        Fpath.add(FilePathCorrect);
        FilePathCorrect.setBounds(433, 24, 36, 49);

        PathArea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PathArea.setText("Transcript.pdf");
        PathArea.setPreferredSize(new java.awt.Dimension(300, 22));
        PathArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PathAreaActionPerformed(evt);
            }
        });
        Fpath.add(PathArea);
        PathArea.setBounds(20, 30, 408, 37);

        OcrArea.setEditable(false);
        OcrArea.setBackground(new java.awt.Color(34, 40, 44));
        OcrArea.setColumns(20);
        OcrArea.setRows(5);
        OcrArea.setText("OCR TEXT");
        OcrArea.setFocusable(false);
        jScrollPane6.setViewportView(OcrArea);

        Fpath.add(jScrollPane6);
        jScrollPane6.setBounds(475, 4, 260, 90);
        Fpath.add(jTabbedPane1);
        jTabbedPane1.setBounds(710, 0, 110, 100);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Choose Transcript File:");
        Fpath.add(jLabel3);
        jLabel3.setBounds(20, 10, 170, 20);

        OgrenciTab.add(Fpath);
        Fpath.setBounds(5, 47, 1000, 170);

        BottomMessages.setPreferredSize(new java.awt.Dimension(1000, 200));

        javax.swing.GroupLayout BottomMessagesLayout = new javax.swing.GroupLayout(BottomMessages);
        BottomMessages.setLayout(BottomMessagesLayout);
        BottomMessagesLayout.setHorizontalGroup(
            BottomMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        BottomMessagesLayout.setVerticalGroup(
            BottomMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        OgrenciTab.add(BottomMessages);
        BottomMessages.setBounds(1336, 372, 1000, 490);

        StdMessageReadButton.setBackground(new java.awt.Color(0, 0, 0));
        StdMessageReadButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        StdMessageReadButton.setForeground(new java.awt.Color(255, 255, 255));
        StdMessageReadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-bell-30.png"))); // NOI18N
        StdMessageReadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StdMessageReadButtonActionPerformed(evt);
            }
        });
        OgrenciTab.add(StdMessageReadButton);
        StdMessageReadButton.setBounds(10, 0, 40, 37);

        StdMessageSendButton.setBackground(new java.awt.Color(0, 0, 0));
        StdMessageSendButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        StdMessageSendButton.setForeground(new java.awt.Color(255, 255, 255));
        StdMessageSendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-send-30.png"))); // NOI18N
        StdMessageSendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StdMessageSendButtonActionPerformed(evt);
            }
        });
        OgrenciTab.add(StdMessageSendButton);
        StdMessageSendButton.setBounds(50, 0, 40, 37);

        UpdateButton.setBackground(new java.awt.Color(0, 102, 51));
        UpdateButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        UpdateButton.setForeground(new java.awt.Color(255, 255, 255));
        UpdateButton.setText("Update");
        UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButtonActionPerformed(evt);
            }
        });
        OgrenciTab.add(UpdateButton);
        UpdateButton.setBounds(880, 234, 146, 30);

        StudentLessonsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Lesson", "Lesson Code", "Teacher ID", "Teacher Full Name", "Fields Of Interest", "Quotas", "Process"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        StudentLessonsTable.setRowHeight(40);
        StudentLessonsTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane1.setViewportView(StudentLessonsTable);

        jTabbedPane2.addTab("New Lessons", jScrollPane1);

        StudentRequestsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Lesson", "Lesson Code", "Teacher Full Name", "Teacher ID", "Process"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        StudentRequestsTable.setRowHeight(40);
        StudentRequestsTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane2.setViewportView(StudentRequestsTable);

        jTabbedPane2.addTab("My Lesson Requests", jScrollPane2);

        StudentOldLessonsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Lesson", "Lesson Code", "Teacher", "Teacher ID", "Lesson Grade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        StudentOldLessonsTable.setRowHeight(40);
        StudentOldLessonsTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane12.setViewportView(StudentOldLessonsTable);

        jTabbedPane2.addTab("Past Lessons", jScrollPane12);

        OgrenciTab.add(jTabbedPane2);
        jTabbedPane2.setBounds(0, 250, 1030, 370);

        ilgiAlanComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ilgiAlanComboBoxActionPerformed(evt);
            }
        });
        OgrenciTab.add(ilgiAlanComboBox);
        ilgiAlanComboBox.setBounds(720, 230, 150, 22);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("FIelds Of Interest");
        OgrenciTab.add(jLabel2);
        jLabel2.setBounds(580, 230, 130, 20);

        jTabbedPane.addTab("Student", OgrenciTab);

        HocaTab.setBackground(new java.awt.Color(34, 40, 44));
        HocaTab.setPreferredSize(new java.awt.Dimension(100, 100));
        HocaTab.setLayout(null);

        TeacherNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        TeacherNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        TeacherNameLabel.setText("Teacher Panel");
        HocaTab.add(TeacherNameLabel);
        TeacherNameLabel.setBounds(250, 10, 160, 32);

        TeacherMessaageReadButton.setBackground(new java.awt.Color(0, 0, 0));
        TeacherMessaageReadButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        TeacherMessaageReadButton.setForeground(new java.awt.Color(255, 255, 255));
        TeacherMessaageReadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-bell-30.png"))); // NOI18N
        TeacherMessaageReadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeacherMessaageReadButtonActionPerformed(evt);
            }
        });
        HocaTab.add(TeacherMessaageReadButton);
        TeacherMessaageReadButton.setBounds(10, 0, 40, 37);

        TeacherMessageSendButton.setBackground(new java.awt.Color(0, 0, 0));
        TeacherMessageSendButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        TeacherMessageSendButton.setForeground(new java.awt.Color(255, 255, 255));
        TeacherMessageSendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-send-30.png"))); // NOI18N
        TeacherMessageSendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeacherMessageSendButtonActionPerformed(evt);
            }
        });
        HocaTab.add(TeacherMessageSendButton);
        TeacherMessageSendButton.setBounds(50, 0, 40, 37);

        UpdateButton1.setBackground(new java.awt.Color(0, 102, 51));
        UpdateButton1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        UpdateButton1.setForeground(new java.awt.Color(255, 255, 255));
        UpdateButton1.setText("Update");
        UpdateButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButton1ActionPerformed(evt);
            }
        });
        HocaTab.add(UpdateButton1);
        UpdateButton1.setBounds(880, 234, 146, 30);

        ilgiAlanTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ilgiAlanTextField.setText("Field Of Interest");
        ilgiAlanTextField.setPreferredSize(new java.awt.Dimension(300, 22));
        ilgiAlanTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ilgiAlanTextFieldActionPerformed(evt);
            }
        });
        HocaTab.add(ilgiAlanTextField);
        ilgiAlanTextField.setBounds(10, 80, 240, 30);

        CreateTeacherButton1.setBackground(new java.awt.Color(0, 51, 102));
        CreateTeacherButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CreateTeacherButton1.setText("Update");
        CreateTeacherButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateTeacherButton1ActionPerformed(evt);
            }
        });
        HocaTab.add(CreateTeacherButton1);
        CreateTeacherButton1.setBounds(10, 120, 120, 27);

        TeacherLessonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Lesson", "Lesson Code", "Fields Of Interest", "Quota", "Process"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TeacherLessonTable.setRowHeight(40);
        TeacherLessonTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane4.setViewportView(TeacherLessonTable);

        jTabbedPane4.addTab("My New Lessons", jScrollPane4);

        TeacherStudentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Full Name", "Student ID", "Fields Of Interest", "GPA", "Requests Count", "Scoring Formula", "Process"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TeacherStudentsTable.setRowHeight(40);
        TeacherStudentsTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane10.setViewportView(TeacherStudentsTable);
        if (TeacherStudentsTable.getColumnModel().getColumnCount() > 0) {
            TeacherStudentsTable.getColumnModel().getColumn(2).setResizable(false);
        }

        jTabbedPane4.addTab("Students", jScrollPane10);

        TeacherRequestsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Lesson", "Lesson Code", "Student ID", "Status", "Process"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TeacherRequestsTable.setRowHeight(40);
        TeacherRequestsTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane8.setViewportView(TeacherRequestsTable);

        jTabbedPane4.addTab("Requests", jScrollPane8);

        TeacherLessonScoringTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Lesson", "Lesson Code", "Fields Of Interest", "Teacher ID", "Status", "Coefficient"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        TeacherLessonScoringTable.setRowHeight(40);
        TeacherLessonScoringTable.setSelectionBackground(new java.awt.Color(56, 138, 112));
        jScrollPane11.setViewportView(TeacherLessonScoringTable);

        jTabbedPane4.addTab("Student Scoring Furmula", jScrollPane11);

        HocaTab.add(jTabbedPane4);
        jTabbedPane4.setBounds(0, 250, 1030, 370);

        StudentNameLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        StudentNameLabel5.setForeground(new java.awt.Color(255, 255, 255));
        StudentNameLabel5.setText("Fields Of Interest Update");
        HocaTab.add(StudentNameLabel5);
        StudentNameLabel5.setBounds(10, 50, 230, 25);

        CreateScoringFormulaButton.setBackground(new java.awt.Color(102, 0, 102));
        CreateScoringFormulaButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CreateScoringFormulaButton.setForeground(new java.awt.Color(255, 255, 255));
        CreateScoringFormulaButton.setText("Create Student Scoring Formula");
        CreateScoringFormulaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateScoringFormulaButtonActionPerformed(evt);
            }
        });
        HocaTab.add(CreateScoringFormulaButton);
        CreateScoringFormulaButton.setBounds(510, 230, 330, 30);

        TeacherMyInfoButton.setBackground(new java.awt.Color(0, 102, 102));
        TeacherMyInfoButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        TeacherMyInfoButton.setForeground(new java.awt.Color(255, 255, 255));
        TeacherMyInfoButton.setText("My Info");
        TeacherMyInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TeacherMyInfoButtonActionPerformed(evt);
            }
        });
        HocaTab.add(TeacherMyInfoButton);
        TeacherMyInfoButton.setBounds(890, 20, 130, 34);

        NewLessonPopupButton3.setBackground(new java.awt.Color(102, 0, 102));
        NewLessonPopupButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        NewLessonPopupButton3.setForeground(new java.awt.Color(255, 255, 255));
        NewLessonPopupButton3.setText("Sort Stdnt GPA");
        NewLessonPopupButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewLessonPopupButton3ActionPerformed(evt);
            }
        });
        HocaTab.add(NewLessonPopupButton3);
        NewLessonPopupButton3.setBounds(10, 230, 120, 23);

        jTabbedPane.addTab("Teacher", HocaTab);

        IdField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IdField.setText("789456123");
        IdField.setPreferredSize(new java.awt.Dimension(300, 22));
        IdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdFieldActionPerformed(evt);
            }
        });

        LoginTitle.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        LoginTitle.setForeground(new java.awt.Color(204, 204, 204));
        LoginTitle.setText("Login Page");

        GirisButton.setBackground(new java.awt.Color(51, 102, 0));
        GirisButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        GirisButton.setText("Login");
        GirisButton.setPreferredSize(new java.awt.Dimension(100, 22));
        GirisButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GirisButtonActionPerformed(evt);
            }
        });

        CikisButton.setBackground(new java.awt.Color(204, 0, 0));
        CikisButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        CikisButton.setText("Logout");
        CikisButton.setPreferredSize(new java.awt.Dimension(100, 22));
        CikisButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CikisButtonActionPerformed(evt);
            }
        });

        LoginCorrectIcon.setBackground(new java.awt.Color(34, 40, 44));
        LoginCorrectIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_in_progress_30px.png"))); // NOI18N
        LoginCorrectIcon.setAlignmentX(0.5F);
        LoginCorrectIcon.setBorderPainted(false);
        LoginCorrectIcon.setContentAreaFilled(false);
        LoginCorrectIcon.setDefaultCapable(false);
        LoginCorrectIcon.setFocusPainted(false);
        LoginCorrectIcon.setFocusable(false);
        LoginCorrectIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginCorrectIconActionPerformed(evt);
            }
        });

        PasField.setText("kocaeli41");

        LoginTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Student", "Teacher" }));

        javax.swing.GroupLayout LoginLayout = new javax.swing.GroupLayout(Login);
        Login.setLayout(LoginLayout);
        LoginLayout.setHorizontalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LoginTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(LoginTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(LoginCorrectIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(IdField, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginLayout.createSequentialGroup()
                            .addComponent(CikisButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(GirisButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(PasField)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        LoginLayout.setVerticalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(LoginTitle)
                .addGap(1, 1, 1)
                .addComponent(LoginTypeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IdField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PasField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GirisButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(CikisButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoginCorrectIcon)
                .addGap(456, 456, 456))
        );

        jTabbedPane.addTab("   Login", Login);

        pnlRoot.add(jTabbedPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlRoot, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void IdFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdFieldActionPerformed

    private void GirisButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GirisButtonActionPerformed
        // TODO add your handling code here:
        int enteredUsername = Integer.parseInt(IdField.getText());
        String enteredPassword = PasField.getText();

        boolean isValidLogin = login.StudentLogin(enteredUsername, enteredPassword);
        boolean isValidLogin2 = login.teacherLogin(enteredUsername, enteredPassword);
        boolean isValidLogin3 = login.adminLogin(enteredUsername, enteredPassword);

        if (LoginTypeBox.getSelectedItem().toString().equals("Ogrenci") && isValidLogin) {
            // Successfully logged in
            System.out.println("Student Valid Login");
            jTabbedPane.setEnabledAt(1, true);
            loggedStudent = login.studentLoginInfo(enteredUsername, enteredPassword);
            StudentNameLabel.setText(loggedStudent.getStudentName());
            StudentTablesUpdater();

        } else if (LoginTypeBox.getSelectedItem().toString().equals("Hoca") && isValidLogin2) {
            // Successfully logged in
            System.out.println("Hoca Valid Login");
            jTabbedPane.setEnabledAt(2, true);
            loggedTeacher = login.teacherLoginInfo(enteredUsername, enteredPassword);
            TeacherNameLabel.setText(loggedTeacher.getTeacherName());
            //lessonOperation.addLessonsForAllTeachers();
            lessonOperation.addApprovedLessonsForAllStudents();
            TeacherTablesUpdater();

        } else if (LoginTypeBox.getSelectedItem().toString().equals("Yonetici") && isValidLogin3) {
            // Successfully logged in
            System.out.println("Yonetici Valid Login");
            jTabbedPane.setEnabledAt(0, true);
            //lessonOperation.addLessonsForAllTeachers();
            lessonOperation.addApprovedLessonsForAllStudents();
            AdminTablesUpdater();

        } else {
            System.out.println("Unvalid Login");
        }

    }//GEN-LAST:event_GirisButtonActionPerformed

    private void CikisButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CikisButtonActionPerformed
        // TODO add your handling code here:
        if (LoginTypeBox.getSelectedIndex() == 0) /// admin logout
        {
            jTabbedPane.setEnabledAt(0, false);
            System.out.println("Admin Logged out");
        } else if (LoginTypeBox.getSelectedIndex() == 1) {
            loggedStudent = null;
            jTabbedPane.setEnabledAt(1, false);
            System.out.println("Ogrenci Logged out");
        } else if (LoginTypeBox.getSelectedIndex() == 2) {
            loggedTeacher = null;
            jTabbedPane.setEnabledAt(2, false);
            System.out.println("Hoca Logged out");
        }


    }//GEN-LAST:event_CikisButtonActionPerformed

    private void LoginCorrectIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginCorrectIconActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoginCorrectIconActionPerformed

    private void UpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButtonActionPerformed

        //lessonOperation.addLessonsForAllTeachers();
        lessonOperation.addApprovedLessonsForAllStudents();
        StudentTablesUpdater();

    }//GEN-LAST:event_UpdateButtonActionPerformed

    private void PathAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PathAreaActionPerformed

    }//GEN-LAST:event_PathAreaActionPerformed

    private void SelectPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectPathActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileFilter filter = new FileNameExtensionFilter("PDF file", "pdf");
        fileChooser.addChoosableFileFilter(filter);

        fileChooser.setCurrentDirectory(new File("."));
        int resp = fileChooser.showOpenDialog(null); // response

        if (resp != 1) {
            File f = fileChooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            System.out.println(filename);

            PathArea.setText(filename);

        }
    }//GEN-LAST:event_SelectPathActionPerformed

    private void ConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmActionPerformed
        Filepath = PathArea.getText();
        FilePathCorrect.setVisible(true);
        PathMessage.setText(null);
        PdfToText pdfToText = new PdfToText();
        List<Lesson> lessons = pdfToText.readTranskript(Filepath);
        double gno = pdfToText.readGNO(Filepath);
        System.out.println("X GNO:" + gno);
        //lessonOperation.addLessonsToStudent(loggedStudent.getStudentNo(), gno, lessons);
        lessonOperation.addOcrLessons(lessons, loggedStudent.getStudentNo());

        String CompleteOcr = "";
        for (int i = 0; i < lessons.size(); i++) {
            CompleteOcr += lessons.get(i).getLessonCode() + " " + lessons.get(i).getLessonName() + " " + lessons.get(i).getStatus() + "\n";
        }

        OcrArea.setText(CompleteOcr);


    }//GEN-LAST:event_ConfirmActionPerformed

    private void StdMessageReadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StdMessageReadButtonActionPerformed
        // TODO add your handling code here:
        NotificationsRead n = new NotificationsRead(loggedStudent);
        n.setVisible(true);
    }//GEN-LAST:event_StdMessageReadButtonActionPerformed

    private void CreateStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateStudentButtonActionPerformed
        // TODO add your handling code here:
        //studentArray.add(new Student(NameField.getText(), Integer.parseInt(NoField.getText()), LessonsForAddDepartmentFilterField.getText(), null, 0));
        tablesOpertaion.createStudent(NameField.getText(), Integer.parseInt(NoField.getText()), PasswordField.getText(), ilgiAlanField.getText());

    }//GEN-LAST:event_CreateStudentButtonActionPerformed

    private void NoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoFieldActionPerformed

    private void NameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameFieldActionPerformed

    private void CreateTeacherButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateTeacherButtonActionPerformed
        // TODO add your handling code here:
        //teacherArray.add(new Teacher(NameField.getText(), Integer.parseInt(NoField.getText()), LessonsForAddDepartmentFilterField.getText(), null));
        tablesOpertaion.createTeacher(NameField.getText(), Integer.parseInt(NoField.getText()), PasswordField.getText(), ilgiAlanField.getText());
    }//GEN-LAST:event_CreateTeacherButtonActionPerformed

    private void StdMessageSendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StdMessageSendButtonActionPerformed
        // TODO add your handling code here:
        NotificationsSend n = new NotificationsSend(loggedStudent, maxCharacter);
        n.setVisible(true);
    }//GEN-LAST:event_StdMessageSendButtonActionPerformed

    private void UpdateButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButton1ActionPerformed
        // TODO add your handling code here:
        //lessonOperation.addLessonsForAllTeachers();
        lessonOperation.addApprovedLessonsForAllStudents();
        TeacherTablesUpdater();
    }//GEN-LAST:event_UpdateButton1ActionPerformed

    private void TeacherMessageSendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeacherMessageSendButtonActionPerformed
        // TODO add your handling code here:
        NotificationsSend n = new NotificationsSend(loggedTeacher, maxCharacter);
        n.setVisible(true);
    }//GEN-LAST:event_TeacherMessageSendButtonActionPerformed

    private void TeacherMessaageReadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeacherMessaageReadButtonActionPerformed
        // TODO add your handling code here:
        NotificationsRead n = new NotificationsRead(loggedTeacher);
        n.setVisible(true);
    }//GEN-LAST:event_TeacherMessaageReadButtonActionPerformed

    private void UpdateButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButton2ActionPerformed
        // TODO add your handling code here:
        //lessonOperation.addLessonsForAllTeachers();
        lessonOperation.addApprovedLessonsForAllStudents();
        AdminTablesUpdater();
    }//GEN-LAST:event_UpdateButton2ActionPerformed

    private void ilgiAlanTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ilgiAlanTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ilgiAlanTextFieldActionPerformed

    private void CreateTeacherButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateTeacherButton1ActionPerformed
        // TODO add your handling code here:
        lessonOperation.updateTeacherDepartment(loggedTeacher.getTeacherNo(), ilgiAlanTextField.getText());
    }//GEN-LAST:event_CreateTeacherButton1ActionPerformed

    private void RandomTeacherCountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RandomTeacherCountButtonActionPerformed
        ArrayList<Teacher> randomTeacher = randomizer.randomTeacherCreator(Integer.parseInt(RandomTeacherCountField.getText()));

        System.out.println("Random Teacher Count: " + randomTeacher.size());
        tablesOpertaion.addTeachers(randomTeacher);

    }//GEN-LAST:event_RandomTeacherCountButtonActionPerformed

    private void RandomTeacherCountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RandomTeacherCountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RandomTeacherCountFieldActionPerformed

    private void Asama1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Asama1ButtonActionPerformed
        // TODO add your handling code here:
        asama2 = false;
        assignStudentsToSelectedLessonsButton.setEnabled(false);
        priorityAssignStudentsToLessonsButton.setEnabled(false);
        randomlyAddLessonsToStudentsButton.setEnabled(false);
        
        RandomStudentCountButton.setEnabled(true);
        RandomTeacherCountButton.setEnabled(true);
        RandomNewLessonCountButton.setEnabled(true);
    }//GEN-LAST:event_Asama1ButtonActionPerformed

    private void priorityAssignStudentsToLessonsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priorityAssignStudentsToLessonsButtonActionPerformed
        // TODO add your handling code here:

        List<TeacherPriority> teacherPriority = new ArrayList<TeacherPriority>();

        for (int i = 0; i < AdminTeacherTable.getRowCount(); i++) {
            if (AdminTeacherTable.getValueAt(i, 3) != null) {
                int hocaNo = (int) AdminTeacherTable.getValueAt(i, 1);
                int priority = (int) AdminTeacherTable.getValueAt(i, 3);
                teacherPriority.add(new TeacherPriority(hocaNo, priority));
            }

        }

        if (!teacherPriority.isEmpty()) {
            lessonOperation.priorityAssignStudentsToLessons(teacherPriority);
            System.out.println("teacherPriority size: " + teacherPriority.size());
            System.out.println("Not ortalamasına gore Atama yapildi");
        } else
            System.out.println("Priority is empty");
    }//GEN-LAST:event_priorityAssignStudentsToLessonsButtonActionPerformed

    private void MaxCharacterCountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxCharacterCountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MaxCharacterCountFieldActionPerformed

    private void MaxCharacterCountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MaxCharacterCountButtonActionPerformed
        // TODO add your handling code here:
        maxCharacter = Integer.parseInt(MaxCharacterCountField.getText());
    }//GEN-LAST:event_MaxCharacterCountButtonActionPerformed

    private void TotalKontFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalKontFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalKontFieldActionPerformed

    private void TotalKontButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TotalKontButtonActionPerformed
        // TODO add your handling code here:
        lessonOperation.updateTotalKontForAllLessons(Integer.parseInt(TotalKontField.getText()));
    }//GEN-LAST:event_TotalKontButtonActionPerformed

    private void ilgiAlanComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ilgiAlanComboBoxActionPerformed
        // TODO add your handling code here:

        DefaultTableModel model = (DefaultTableModel) StudentLessonsTable.getModel(); // Cast the model to DefaultTableModel

        // Create a RowSorter and associate it with the table model
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(model);
        StudentLessonsTable.setRowSorter(rowSorter);
        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + ilgiAlanComboBox.getSelectedItem())); // Case-insensitive filter

    }//GEN-LAST:event_ilgiAlanComboBoxActionPerformed

    private void NewLessonPopupButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewLessonPopupButton1ActionPerformed
        // TODO add your handling code here:
        NewLessonPopup newLessonPopup = new NewLessonPopup();
        newLessonPopup.setVisible(true);
    }//GEN-LAST:event_NewLessonPopupButton1ActionPerformed

    private void Asama2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Asama2ButtonActionPerformed
        // TODO add your handling code here:
        asama2 = true;
        assignStudentsToSelectedLessonsButton.setEnabled(true);
        priorityAssignStudentsToLessonsButton.setEnabled(true);
        randomlyAddLessonsToStudentsButton.setEnabled(true);
        
        RandomStudentCountButton.setEnabled(false);
        RandomTeacherCountButton.setEnabled(false);
        RandomNewLessonCountButton.setEnabled(false);
        
    }//GEN-LAST:event_Asama2ButtonActionPerformed

    private void PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordFieldActionPerformed

    private void NewLessonPopupButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewLessonPopupButton2ActionPerformed
        DefaultTableModel model = (DefaultTableModel) AdminStudentTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        AdminStudentTable.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING)); // 3 is the column index, change it if necessary
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }//GEN-LAST:event_NewLessonPopupButton2ActionPerformed

    private void ilgiAlanFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ilgiAlanFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ilgiAlanFieldActionPerformed

    private void RandomStudentCountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RandomStudentCountButtonActionPerformed
        // TODO add your handling code here:
        ArrayList<Student> randomStudents = randomizer.randomStudentCreator(Integer.parseInt(RandomStudentCountField.getText()));

        System.out.println("Random Student Count: " + randomStudents.size());

        tablesOpertaion.addStudents(randomStudents); // Upload the random students to student table

        lessonOperation.addRandomLessonsToOldLessons(randomStudents);

    }//GEN-LAST:event_RandomStudentCountButtonActionPerformed

    private void RandomStudentCountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RandomStudentCountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RandomStudentCountFieldActionPerformed

    private void CreateScoringFormulaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateScoringFormulaButtonActionPerformed
        // TODO add your handling code here:
        List<LessonWeight> lessonWeightsList = new ArrayList<>();

        for (int i = 0; i < TeacherLessonScoringTable.getRowCount(); i++) {
            // Check if the weight column is filled (you can adjust the column index based on your actual table)
            Object weightValue = TeacherLessonScoringTable.getValueAt(i, 5); // Assuming weight is in the 5th column (0-based index) Katsayi

            if (weightValue != null && !weightValue.toString().isEmpty()) {
                LessonWeight lessonWeight = new LessonWeight();

                // Assuming you have columns for Lesson Name, Lesson Code, Teacher No, and Weight
                lessonWeight.setLessonName(TeacherLessonScoringTable.getValueAt(i, 0).toString());
                lessonWeight.setLessonCode(TeacherLessonScoringTable.getValueAt(i, 1).toString());
                lessonWeight.setTeacherNo(Integer.parseInt(TeacherLessonScoringTable.getValueAt(i, 3).toString()));
                lessonWeight.setWeight(Integer.parseInt(weightValue.toString()));

                lessonWeightsList.add(lessonWeight);
            }
        }

        System.out.println("**: " + lessonWeightsList.size());

        DefaultTableModel teacherStudentsTable = (DefaultTableModel) TeacherStudentsTable.getModel(); // to put the results of formula for students

        for (int i = 0; i < teacherStudentsTable.getRowCount(); i++) {
            List<Lesson> studentOldLessons = lessonOperation.getStudentOldLessons(Integer.parseInt(TeacherStudentsTable.getValueAt(i, 1).toString()));
            double puan = 0;
            int total = 1;

            for (int j = 0; j < studentOldLessons.size(); j++) {

                for (int k = 0; k < lessonWeightsList.size(); k++) {
                    if (studentOldLessons.get(j).getLessonCode().equals(lessonWeightsList.get(k).getLessonCode())
                            && (studentOldLessons.get(j).getTeacherNo() == lessonWeightsList.get(k).getTeacherNo())) {

                        System.out.println("SS");
                        puan += Katsayi_x_HarfNotu(lessonWeightsList.get(k).getWeight(), studentOldLessons.get(j).getStatus());
                        System.out.println("puan: " + puan);
                        total += lessonWeightsList.get(k).getWeight();
                    }

                }

            }
            puan = puan / total;
            System.out.println(TeacherStudentsTable.getValueAt(i, 1) + " Formul Sonucu: " + puan);
            teacherStudentsTable.setValueAt(puan, i, 5);
        }


    }//GEN-LAST:event_CreateScoringFormulaButtonActionPerformed

    private void TeacherMyInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TeacherMyInfoButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Teacher Details Popup");
        
        Teacher teacher = tablesReader.getTeacherByTeacherNo(loggedTeacher.getTeacherNo());
        InfoPopup infoPopup = new InfoPopup(teacher);
        infoPopup.setVisible(true);
    }//GEN-LAST:event_TeacherMyInfoButtonActionPerformed

    private void StudentMyInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentMyInfoButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Teacher Details Popup");
        
        Student student = tablesReader.getStudentByStudentNo(loggedStudent.getStudentNo());
        InfoPopup infoPopup = new InfoPopup(student, true);
        infoPopup.setVisible(true);
    }//GEN-LAST:event_StudentMyInfoButtonActionPerformed

    private void randomlyAddLessonsToStudentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomlyAddLessonsToStudentsButtonActionPerformed
        // TODO add your handling code here:
        lessonOperation.randomlyAssignStudentsToLessons();
        System.out.println("Rastgele Atama Yapildi");
    }//GEN-LAST:event_randomlyAddLessonsToStudentsButtonActionPerformed

    private void assignStudentsToSelectedLessonsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignStudentsToSelectedLessonsButtonActionPerformed

        List<TeacherPriority> teacherPriority = new ArrayList<TeacherPriority>();

        for (int i = 0; i < AdminTeacherTable.getRowCount(); i++) {
            if (AdminTeacherTable.getValueAt(i, 3) != null) {
                int hocaNo = (int) AdminTeacherTable.getValueAt(i, 1);
                int priority = (int) AdminTeacherTable.getValueAt(i, 3);
                teacherPriority.add(new TeacherPriority(hocaNo, priority));
            }

        }
        
        // Assuming 'lessonTable' is the name of your JTable
// Step 1: Get the selected rows
        int[] selectedRows = AdminLessonsTable.getSelectedRows();

// Step 2 and 3: Extract and store the information
        List<String> selectedLessonCodes = new ArrayList<>();
        List<Integer> selectedTeacherNumbers = new ArrayList<>();

        for (int row : selectedRows) {
            String lessonCode = (String) AdminLessonsTable.getValueAt(row, 1); // Assuming lessonCode is in the second column (index 1)
            int teacherNo = (int) AdminLessonsTable.getValueAt(row, 2); // Assuming teacherNo is in the third column (index 2)

            selectedLessonCodes.add(lessonCode);
            selectedTeacherNumbers.add(teacherNo);
        }
        System.out.println("Lesson Selection Count: " + selectedLessonCodes.size());
        System.out.println("teacherPriority size: " + teacherPriority.size());
        if ( (selectedLessonCodes.size() <= 5) && (!teacherPriority.isEmpty()) ) // Step 4: Pass the lists to the method
        {
            lessonOperation.assignStudentsToSelectedLessons(selectedLessonCodes, selectedTeacherNumbers, teacherPriority);
            System.out.println("Belirli derslere gore atama yapildi");
        }
        else if (selectedLessonCodes.size() > 5)
        System.out.println("5 ten Fazla Ders Secilmis");  
        
        else if (teacherPriority.isEmpty())
        System.out.println("Priority is empty");


    }//GEN-LAST:event_assignStudentsToSelectedLessonsButtonActionPerformed

    private void NewLessonPopupButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewLessonPopupButton3ActionPerformed
        DefaultTableModel model = (DefaultTableModel) TeacherStudentsTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        TeacherStudentsTable.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING)); // 3 is the column index, change it if necessary
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }//GEN-LAST:event_NewLessonPopupButton3ActionPerformed

    private void RandomNewLessonCountFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RandomNewLessonCountFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RandomNewLessonCountFieldActionPerformed

    private void RandomNewLessonCountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RandomNewLessonCountButtonActionPerformed
        // TODO add your handling code here:
        lessonOperation.addRandomLessonsToNewLessons(Integer.parseInt(RandomNewLessonCountField.getText()));
    }//GEN-LAST:event_RandomNewLessonCountButtonActionPerformed

    public String Filepath;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable AdminLessonsModifyTable;
    private javax.swing.JTable AdminLessonsTable;
    private javax.swing.JTable AdminOldLessonsTable;
    private javax.swing.JTable AdminRequestsTable;
    private javax.swing.JTable AdminStudentTable;
    private javax.swing.JTable AdminTeacherTable;
    private javax.swing.JPanel AllowedSymbolsPanel;
    private javax.swing.JButton Asama1Button;
    private javax.swing.JButton Asama2Button;
    private javax.swing.JPanel BottomMessages;
    private javax.swing.JButton CikisButton;
    private javax.swing.JButton Confirm;
    private javax.swing.JButton CreateScoringFormulaButton;
    private javax.swing.JButton CreateStudentButton;
    private javax.swing.JButton CreateTeacherButton;
    private javax.swing.JButton CreateTeacherButton1;
    private javax.swing.JButton FilePathCorrect;
    private javax.swing.JPanel Fpath;
    private javax.swing.JButton GirisButton;
    private javax.swing.JPanel HocaTab;
    private javax.swing.JTextField IdField;
    private javax.swing.JPanel Login;
    private javax.swing.JButton LoginCorrectIcon;
    private javax.swing.JLabel LoginTitle;
    private javax.swing.JComboBox<String> LoginTypeBox;
    private javax.swing.JButton MaxCharacterCountButton;
    private javax.swing.JTextField MaxCharacterCountField;
    private javax.swing.JTextField NameField;
    private javax.swing.JButton NewLessonPopupButton1;
    private javax.swing.JButton NewLessonPopupButton2;
    private javax.swing.JButton NewLessonPopupButton3;
    private javax.swing.JTextField NoField;
    private javax.swing.JTextArea OcrArea;
    private javax.swing.JPanel OgrenciTab;
    private javax.swing.JPasswordField PasField;
    private javax.swing.JTextField PasswordField;
    private javax.swing.JTextField PathArea;
    private javax.swing.JLabel PathMessage;
    private javax.swing.JButton RandomNewLessonCountButton;
    private javax.swing.JTextField RandomNewLessonCountField;
    private javax.swing.JButton RandomStudentCountButton;
    private javax.swing.JTextField RandomStudentCountField;
    private javax.swing.JButton RandomTeacherCountButton;
    private javax.swing.JTextField RandomTeacherCountField;
    private javax.swing.JButton SelectPath;
    private javax.swing.JButton StdMessageReadButton;
    private javax.swing.JButton StdMessageSendButton;
    private javax.swing.JTable StudentLessonsTable;
    private javax.swing.JButton StudentMyInfoButton;
    private javax.swing.JLabel StudentNameLabel;
    private javax.swing.JLabel StudentNameLabel10;
    private javax.swing.JLabel StudentNameLabel5;
    private javax.swing.JLabel StudentNameLabel6;
    private javax.swing.JLabel StudentNameLabel7;
    private javax.swing.JLabel StudentNameLabel8;
    private javax.swing.JLabel StudentNameLabel9;
    private javax.swing.JTable StudentOldLessonsTable;
    private javax.swing.JTable StudentRequestsTable;
    private javax.swing.JButton Student_TeacherCreationCorrect;
    private javax.swing.JTable TeacherLessonScoringTable;
    private javax.swing.JTable TeacherLessonTable;
    private javax.swing.JButton TeacherMessaageReadButton;
    private javax.swing.JButton TeacherMessageSendButton;
    private javax.swing.JButton TeacherMyInfoButton;
    private javax.swing.JLabel TeacherNameLabel;
    private javax.swing.JTable TeacherRequestsTable;
    private javax.swing.JTable TeacherStudentsTable;
    private javax.swing.JPanel Title;
    private javax.swing.JButton TotalKontButton;
    private javax.swing.JTextField TotalKontField;
    private javax.swing.JButton UpdateButton;
    private javax.swing.JButton UpdateButton1;
    private javax.swing.JButton UpdateButton2;
    private javax.swing.JPanel YoneticiTab;
    private javax.swing.JButton assignStudentsToSelectedLessonsButton;
    private javax.swing.JComboBox<String> ilgiAlanComboBox;
    private javax.swing.JTextField ilgiAlanField;
    private javax.swing.JTextField ilgiAlanTextField;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JButton priorityAssignStudentsToLessonsButton;
    private javax.swing.JButton randomlyAddLessonsToStudentsButton;
    // End of variables declaration//GEN-END:variables
}
