package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminSearchForm {
    private JFrame frame = new JFrame();
    private JTabbedPane tabbedPane1;
    private JButton searchButton;
    private JButton backToMenuButton;
    private JTextField IDField;
    private JTextField ratingField;
    private JCheckBox searchWithIDCheckBox;
    private JCheckBox searchWithRatingCheckBox;
    private JPanel coachSearching;
    private JLabel IDLabel;
    private JLabel ratingLabel;
    private JPanel rootPanel;
    private Admin admin;
    private DisplayAllRecord displayFrame;
    private coachTabServer coachTabManager;

    public AdminSearchForm(Admin admin){
        this.admin = admin;
        displayFrame = new DisplayAllRecord(admin,false);
        coachTabManager = new coachTabServer();
        searchButton.addActionListener(new searchButtonListener());
        frame.setTitle("Search Record");
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public class coachTabServer {
        public coachTabServer() {
            searchWithIDCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (searchWithIDCheckBox.isSelected())
                        IDField.setEditable(true);
                    else {
                        IDField.setEditable(false);
                        IDField.setText("");
                    }
                }
            });
            searchWithRatingCheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (searchWithRatingCheckBox.isSelected())
                        ratingField.setEditable(true);
                    else {
                        ratingField.setEditable(false);
                        ratingField.setText("");
                    }
                }
            });
        }
    }

    private class searchButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean proceed = true; // determine whether values provided are valid
            ArrayList<Coach> searchResult = new ArrayList<>();
            switch (tabbedPane1.getSelectedIndex()){
                case 0:
                    // Validate all the values provided
                    Integer ratingProvided = null;
                    String idProvided = null;
                    if (searchWithRatingCheckBox.isSelected()){
                        try {
                            ratingProvided = Integer.parseInt(ratingField.getText());
                        }catch (Exception error){
                            ratingField.setBorder(new LineBorder(Color.RED,2));
                            ratingField.setToolTipText("This is not an integer/The field is empty");
                            proceed = false;
                        }
                    }
                    if (searchWithIDCheckBox.isSelected() && IDField.getText().isEmpty()){
                        IDField.setBorder(new LineBorder(Color.RED,2));
                        IDField.setToolTipText("The field is empty");
                        proceed = false;
                    }
                    else if (searchWithIDCheckBox.isSelected())
                        idProvided = IDField.getText();

                    // Start searching once value provided is validated
                    if (proceed){
                        if (ratingProvided != null && idProvided != null)
                            searchResult = admin.searchCoach(displayFrame.coachPanelManager.getCoachList(),idProvided,ratingProvided);
                        else if (ratingProvided != null)
                            searchResult = admin.searchCoach(displayFrame.coachPanelManager.getCoachList(),ratingProvided);
                        else if (idProvided != null)
                            searchResult = admin.searchCoach(displayFrame.coachPanelManager.getCoachList(),idProvided);
                        else
                            JOptionPane.showMessageDialog(frame,"Please select a search mode","Error",JOptionPane.ERROR_MESSAGE);
                        if (searchResult.size() == 0)
                            JOptionPane.showMessageDialog(frame,"Sorry, no result was found","Warning",JOptionPane.WARNING_MESSAGE);
                        else
                            displayFrame.coachPanelManager.showFoundCoaches(searchResult);
                            displayFrame.frame.setVisible(true);
                            frame.dispose();
                    }
                    // Return dialog for invalid
                    else
                        JOptionPane.showMessageDialog(frame,"Invalid values provided","Error",JOptionPane.ERROR_MESSAGE);
                    break;
                case 1:
                    break;
                default :
                    break;
            }

        }
    }
    private class backToMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            new AdminMenu(admin);
        }
    }

}
