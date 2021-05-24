package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;

public class DisplayAllRecord {
    public JFrame frame;
    private JTabbedPane tabbedPane1;
    private JPanel rootPanel;
    private JPanel studentRecordPanel;
    private JPanel coachRecordPanel;
    private JPanel sportsRecordPanel;
    private JScrollPane studentTableContainer;
    private JScrollPane coachTableContainer;
    private JScrollPane sportsTableContainer;
    private JTable studentRecordTable;
    private JTable coachRecordTable;
    private JTable sportsRecordTable;
    private JComboBox<Comparator <Coach>> sortByCoachMenu;
    private JComboBox<Comparator <Student>> sortByStudentMenu;
    private JComboBox<Comparator <Sports>> sortBySportsMenu;
    private JLabel orderLabel;
    private ButtonGroup radioButtonGroup = new ButtonGroup();
    private JRadioButton ascendingRadioButton;
    private JRadioButton descendingRadioButton;
    private JButton sortTableButton;
    private JButton backToMenuButton;
    private JButton modifyDetailsButton;
    private JPanel scheduleRecordPanel;
    private JScrollPane scheduleTableContainer;
    private JTable scheduleRecordTable;
    private JComboBox<Schedule> scheduleSelector;
    private JComboBox<Comparator<Session>> sortByScheduleMenu;
    private DefaultTableModel coachTableModel;
    private DefaultTableModel studentTableModel = (DefaultTableModel) studentRecordTable.getModel();
    private DefaultTableModel sportsTableModel;
    private DefaultTableModel scheduleTableModel;
    private Admin admin;
    public setCoachPanel coachPanelManager;
    private setStudentPanel studentPanelManager;
    public setSportsPanel sportsPanelManager;
    private final setSchedulePanel schedulePanelManger;

    /*  Class : setCoachPanel
        Description : Responsible for setting up/ changing components in Coach Tab (Coach Tab Manager)
     */
    public class setCoachPanel {

        private ArrayList<Coach> coachList = new ArrayList<>();

        public setCoachPanel (){
            getAllCoach();
            prepareCoachTable();
            updateCoachTable();
            setSortDropMenu();
        }

        public void clearUpdateTable() {
            clearCoachTable();
            updateCoachTable();
        }

        /*  Method Name : getAllCoach
            Description : Read coach file and instantiate all coach objects & store it in array list
         */

        private void getAllCoach () {
            String[] coachFileContent = FileServer.readFile(admin.getSportsCenterCode(),"Coach.txt");
            for (String coachInfo: coachFileContent){
                Coach coach = new Coach(coachInfo.split("\\|"));
                coachList.add(coach);
            }
        }

        /*  Method Name : prepareCoachTable
            Description : Set the number of columns in JTable
         */
        private void prepareCoachTable (){
            coachRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            coachTableModel = new DefaultTableModel(Coach.getAllAttributes(),0){
                @Override
                public boolean isCellEditable (int row, int column){ return false; }
            };
            coachRecordTable.setModel(coachTableModel);
            coachRecordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        /*  Method Name : updateCoachTable
            Description : Add rows in JTable based on the order of arrayList containing coaches
                          (pair with clearCoachTable Method to clear & update table order)
         */
        private void updateCoachTable() {
            for (Coach coach : coachList){
                coachTableModel.addRow(coach.toString().split("\\|"));
            }
        }

        public void showFoundCoaches(ArrayList<Coach>searchResults){
            clearCoachTable();
            for (Coach coach: searchResults){
                coachTableModel.addRow(coach.toString().split("\\|"));
            }
            tabbedPane1.setSelectedIndex(1);
        }

        /*  Method Name : prepareCoachTable
            Description : Set the number of columns in JTable
         */
        private void setSortDropMenu (){
            sortByCoachMenu.addItem(new Coach.sortByID());
            sortByCoachMenu.addItem(new Coach.sortByRating());
            sortByCoachMenu.addItem(new Coach.sortByPay());
        }

        /*  Method Name : prepareCoachTable
            Description : Clear all the rows in JTable
         */
        private void clearCoachTable() {coachTableModel.setRowCount(0);}
        public ArrayList<Coach> getCoachList() {return coachList;}

    }

    /*  Class : setStudentPanel
        Description : Responsible for setting up/ changing components in Coach Tab (Student Tab Manager)
     */

    private class setStudentPanel {
        private ArrayList<Student> studentList = new ArrayList<>();

        public setStudentPanel() {
            getAllStudent();
            prepareStudentTable();
            updateStudentTable();
            setDropMenu();
        }

        private void getAllStudent () {
            String[] studentFileContent = FileServer.readFile(admin.getSportsCenterCode(),"Student.txt");
            for (String studentInfo : studentFileContent){
                Student student = new Student(studentInfo.split("\\|"));
                studentList.add(student);
            }
        }

        private void prepareStudentTable() {
            studentRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for (String column : Student.getAllAttributes())
                studentTableModel.addColumn(column);
        }

        private void updateStudentTable() {
            for (Student student:studentList)
                studentTableModel.addRow(student.toString().split("\\|"));
        }

        private void clearStudentTable() {studentTableModel.setRowCount(0);}

        private void setDropMenu () {sortByStudentMenu.addItem(new Student.sortByName());}

    }

    private class setSportsPanel {
        private ArrayList<Sports> sportsArrayList = new ArrayList<>();
        private setSportsPanel(){
            getAllSports();
            prepareSportsTable();
            updateSportsTable();
            setSortDropMenu();
        }
        private void getAllSports() {
            String[] sportsFileContent = FileServer.readFile(admin.getSportsCenterCode(),"Sports.txt");
            for (String sportsInfo : sportsFileContent){
                Sports sports = new Sports(admin.getSportsCenterCode(),sportsInfo.split("\\|"));
                sportsArrayList.add(sports);
            }
        }
        private void prepareSportsTable() {

            for (String column : Sports.getAllAttributes()){
                sportsRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                sportsTableModel = new DefaultTableModel(Sports.getAllAttributes(),0){
                    @Override
                    public boolean isCellEditable (int row, int column ){return false;}
                };
                sportsRecordTable.setModel(sportsTableModel);
                sportsRecordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
        }

        private void updateSportsTable() {
            for (Sports sport : sportsArrayList){
                sportsTableModel.addRow(sport.toString().split("\\|"));
            }
        }
        private void setSortDropMenu() {
            sortBySportsMenu.addItem(new Sports.sortByName());
            sortBySportsMenu.addItem(new Sports.sortByFees());
        }
        private void clearSportsTable() {sportsTableModel.setRowCount(0);}
        public ArrayList<Sports> getSportsArrayList() {
            return sportsArrayList;
        }
    }

    public class setSchedulePanel {
        private ArrayList<Schedule> weeklySchedule = new ArrayList<>();

        public setSchedulePanel () {
            getWeeklySchedule();
            prepareScheduleTable();
            updateScheduleTable();
            prepareScheduleSelector();
            setSortDropMenu();
        }
        private void getWeeklySchedule() {
            String[] scheduleFileContent = FileServer.readFile(admin.getSportsCenterCode(),"Schedule.txt");
            for (int line = 0; line<7 ; line++){
                String[] tokens = scheduleFileContent[line].split("\\|");
                weeklySchedule.add(new Schedule(admin.getSportsCenterCode(),tokens[0],tokens)); // a bit inefficient
            }
        }

        private void prepareScheduleTable() {
            scheduleRecordTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            scheduleTableModel = new DefaultTableModel(Schedule.getAllAttributes(),0){
               @Override
               public boolean isCellEditable(int row, int column) {return false;}
            };
            scheduleRecordTable.setModel(scheduleTableModel);
            scheduleRecordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        private void updateScheduleTable(Schedule schedule) {
                for (Session session : schedule.getAllSession())
                scheduleTableModel.addRow(session.toString().split("\\|"));
        }

        private void updateScheduleTable() {
            updateScheduleTable(weeklySchedule.get(0));
        }

        private void clearScheduleTable() {scheduleTableModel.setRowCount(0);}

        private void prepareScheduleSelector() {
            for (Schedule schedule : weeklySchedule)
                scheduleSelector.addItem(schedule);
            for (Sports sports: sportsPanelManager.getSportsArrayList())
                scheduleSelector.addItem(sports.getSchedule());

            scheduleSelector.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    clearScheduleTable();
                    updateScheduleTable((Schedule)scheduleSelector.getSelectedItem());
                }
            });
        }

        private void setSortDropMenu (){
            sortByScheduleMenu.addItem(new Session.sortByDay());
            sortByScheduleMenu.addItem(new Session.sortByName());
        }

    }

    /*  Class : sortTableButtonListener
        Description : Calls method to sort the list of objects (student,coach) in specified order
     */

    private class sortTableButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedTab = tabbedPane1.getSelectedIndex();
            switch (selectedTab){
                case 0 :
                    studentPanelManager.clearStudentTable();
                    admin.sort(studentPanelManager.studentList,(Comparator<Student>)sortByStudentMenu.getSelectedItem(),ascendingRadioButton.isSelected());
                    studentPanelManager.updateStudentTable(); // display the table in sorted order
                   break;
                case 1 :
                    coachPanelManager.clearCoachTable();
                    admin.sort(coachPanelManager.coachList,(Comparator<Coach>) sortByCoachMenu.getSelectedItem(),ascendingRadioButton.isSelected());
                    coachPanelManager.updateCoachTable();
                    break;
                case 2:
                    sportsPanelManager.clearSportsTable();
                    admin.sort(sportsPanelManager.sportsArrayList,(Comparator<Sports>)sortBySportsMenu.getSelectedItem(),ascendingRadioButton.isSelected());
                    sportsPanelManager.updateSportsTable();
                    break;
                default:
                    schedulePanelManger.clearScheduleTable();
                    admin.sort(((Schedule)scheduleSelector.getSelectedItem()).getAllSession(),(Comparator<Session>)sortByScheduleMenu.getSelectedItem(),ascendingRadioButton.isSelected());
                    schedulePanelManger.updateScheduleTable((Schedule) scheduleSelector.getSelectedItem());
                    break;
            }
        }
    }

    private class modifyDetailsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // determine which tab is selected
            int tabNumber = tabbedPane1.getSelectedIndex();
            // check whether it is -1
            switch (tabNumber){
                case 0 :
                    break;
                case 1:
                    int row = coachRecordTable.getSelectedRow();
                    Coach targetCoach;
                    if (row == -1)
                        JOptionPane.showMessageDialog(frame,"Please select a row to modify");
                    else {
                        targetCoach = coachPanelManager.coachList.get(row);
                        frame.setVisible(false);
                        new AdminModifyMenu(targetCoach,admin,DisplayAllRecord.this);
                    }
                case 2:
                    break;
            }
        }
    }




    public DisplayAllRecord (Admin admin,boolean visible) {
        this.admin = admin;
        coachPanelManager = new setCoachPanel();
        studentPanelManager = new setStudentPanel();
        sportsPanelManager = new setSportsPanel();
        schedulePanelManger = new setSchedulePanel();
        radioButtonGroup.add(ascendingRadioButton);
        radioButtonGroup.add(descendingRadioButton);
        ascendingRadioButton.setSelected(true);
        sortTableButton.addActionListener(new sortTableButtonListener());
        modifyDetailsButton.addActionListener(new modifyDetailsListener());
        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AdminMenu(admin);
            }
        });
        frame = new JFrame("Showing All Records");
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(visible);

    }

    public DisplayAllRecord (Admin admin){
        this(admin,true);
    }


}
