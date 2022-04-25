package swingPopUps;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Backend.Exercise;
import Backend.LiftRepository;
import Backend.Plan;
import Backend.Workout;

/**
 * 
 * @author Manuel
 *
 * In dieser Klasse können Pläne hinzugefügt/bearbeitet/gelöscht werden
 *
 */

public class EditPlan {
	LiftRepository liftRep = LiftRepository.read();
	ErrorMessage error = new ErrorMessage();
	Plan planValue;
	
	public JFrame editPlanPopUp(JComboBox <Plan> planCombobox, JComboBox <Workout> workoutCombobox, JList <Exercise> exerList, DefaultListModel<Exercise> defExcersiceList) {
		JFrame frame = new JFrame();
		
		//Window
		frame.setTitle("Edit Plans");
		frame.setSize(500, 550);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
		//Layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		//Plan name
		JLabel name = new JLabel("Name");
		
		JTextArea nameText = new JTextArea(1,20);
		
		
		//JList
		JList <Plan> plansList = new JList<>();
		DefaultListModel<Plan> defPlanList = new DefaultListModel<>();
		plansList.setModel(liftRep.plansListGUI(defPlanList));
		plansList.setFixedCellWidth(400);
		plansList.setFixedCellHeight(30);
		JScrollPane jcp = new JScrollPane(plansList);
		plansList.getSelectionModel().addListSelectionListener(e -> {
			Plan planListValue = plansList.getSelectedValue();
			planValue = planListValue;
			if(planValue != null) {
				nameText.setText(planValue.getName());
			}
			else {
				nameText.setText("");
			}
		});
		
		
		//Buttons
		JButton add = new JButton("ADD");
		add.setPreferredSize(new Dimension(100, 50));
		add.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(nameText.getText().isEmpty()){
					error.errorText("Error please Insert a Text!");
				} else {
					liftRep.addPlan(nameText.getText());
					nameText.setText("");
				
					plansList.setModel(liftRep.plansListGUI(defPlanList));
				
					jcp.repaint();
					liftRep.plansComboBoxGui(planCombobox);
				}
			}
		});
		
		JButton edit = new JButton("EDIT");
		edit.setPreferredSize(new Dimension(100, 50));
		edit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(nameText.getText().isEmpty()){
					error.errorText("Error please Insert a Text!");
				} else {
					liftRep.editPlan(planValue, nameText.getText());
					nameText.setText("");
					jcp.repaint();
					liftRep.plansComboBoxGui(planCombobox);
				}
			}
		});
		
		JButton delete = new JButton("DELETE");
		delete.setPreferredSize(new Dimension(100, 50));
		delete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				liftRep.deletePlan(planValue);
				plansList.setModel(liftRep.plansListGUI(defPlanList));
				jcp.repaint();
				liftRep.plansComboBoxGui(planCombobox);
				liftRep.cleanWorkoutsComboBoxGui(workoutCombobox);
				if(liftRep.getPlansArray().isEmpty()) {
					defExcersiceList.clear();
					exerList.setModel(defExcersiceList);
				}
			}
		});

		gbc.insets = new Insets(0,0,0,10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		buttonPanel.add(add, gbc);
		
		gbc.insets = new Insets(0,10,0,10);
		gbc.gridx = 1;
		gbc.gridy = 0;
		buttonPanel.add(edit, gbc);
		
		gbc.insets = new Insets(0,10,0,0);
		gbc.gridx = 2;
		gbc.gridy = 0;
		buttonPanel.add(delete, gbc);
		
		
		//add to main
		gbc.insets = new Insets(0,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(jcp, gbc);
		
		gbc.insets = new Insets(10,0,5,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(name, gbc);
		
		gbc.insets = new Insets(5,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(nameText, gbc);
		
		gbc.insets = new Insets(10,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 3;
		mainPanel.add(buttonPanel, gbc);
		
		
		//Design
		Color darkBlue = new Color(18, 25, 33);
		Color yellow = new Color(250, 213, 125);
		Font text = new Font("ARIAL", Font.PLAIN, 20);
		
		mainPanel.setBackground(darkBlue);
		buttonPanel.setBackground(darkBlue);
		
		jcp.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		jcp.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		jcp.setFont(text);
		jcp.setBackground(darkBlue);
		jcp.setForeground(Color.WHITE);
		
		plansList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		plansList.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		plansList.setFont(text);
		plansList.setBackground(darkBlue);
		plansList.setForeground(Color.WHITE);
		
		name.setForeground(Color.WHITE);
		name.setFont(new Font("ARIAL", Font.BOLD | Font.ITALIC, 28));
		
		nameText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		nameText.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		nameText.setFont(text);
		nameText.setBackground(darkBlue);
		nameText.setForeground(Color.WHITE);
		
		add.setFont(text);
		add.setBackground(yellow);
		add.setForeground(darkBlue);
		add.setBorder(null);
		
		edit.setFont(text);
		edit.setBackground(yellow);
		edit.setForeground(darkBlue);
		edit.setBorder(null);
		
		delete.setFont(text);
		delete.setBackground(yellow);
		delete.setForeground(darkBlue);
		delete.setBorder(null);
		
		
		//frame
		frame.add(mainPanel);
		
		frame.setVisible(true);
		
		return frame;
	}
}
