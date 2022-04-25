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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Backend.Exercise;
import Backend.LiftRepository;
import Backend.Plan;
import Backend.Set;
import Backend.Workout;
import swing.WorkoutPage;

/**
 * 
 * @author Manuel
 *
 * Über diese Klasse können Sets für dei Übungen hinzugefügt/bearbeitet/gelöscht werden, welche dann in den Exercise Klassen 
 * verarbeitet werden.
 *
 */

public class AddSet {
	LiftRepository liftRep = LiftRepository.read();
	WorkoutPage wp = new WorkoutPage();
	ErrorMessage error = new ErrorMessage();
	Exercise exercise = new Exercise();
	Set setValue;

	public JFrame addSetPopUp(Plan plan, Workout workout, Exercise exercise, JPanel chartPanel) {
		//set JFrame
		liftRep.clearSets(plan, workout, exercise);
		
		JFrame frame = new JFrame();
		
		//Window
		frame.setTitle("Add Set");
		frame.setSize(500, 550);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		
		//Layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		JPanel listPanel = new JPanel(new GridBagLayout());
		
		JPanel inputTextPanel = new JPanel(new GridBagLayout());
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
	
		
		//Exercise name
		JLabel wdh = new JLabel("WDH");
		JLabel kg = new JLabel("KG");
		JLabel set = new JLabel("SET: ");
		JTextArea repsText = new JTextArea(1,3);
		JTextArea kgText = new JTextArea(1,3);
		
		gbc.insets = new Insets(0,0,0,5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		inputTextPanel.add(wdh, gbc);
		
		gbc.insets = new Insets(0,5,0,0);
		gbc.gridx = 2;
		gbc.gridy = 0;
		inputTextPanel.add(kg, gbc);
		
		gbc.insets = new Insets(5,0,5,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		inputTextPanel.add(set, gbc);
		
		gbc.insets = new Insets(0,5,0,5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		inputTextPanel.add(repsText, gbc);
		
		gbc.insets = new Insets(0,5,0,0);
		gbc.gridx = 2;
		gbc.gridy = 1;
		inputTextPanel.add(kgText, gbc);
		
		
		//JList
		JList <Set> setList = new JList<>();
		DefaultListModel<Set> defSetList = new DefaultListModel<>();
		setList.setModel(liftRep.newSetGUIList(plan,workout,exercise,defSetList));
		setList.setFixedCellWidth(150);
		setList.setFixedCellHeight(30);
		JScrollPane jcp = new JScrollPane(setList);
		setList.getSelectionModel().addListSelectionListener(e -> {
			Set setListValue = setList.getSelectedValue();
			setValue = setListValue;
			if(setValue != null) {
				repsText.setText(Integer.toString(setValue.getReps()));
				kgText.setText(Double.toString(setValue.getWeight()));
			}
			else {
				repsText.setText("");
				kgText.setText("");
			}
		});
		
		gbc.insets = new Insets(0,0,0,10);
		gbc.gridx = 0;
		gbc.gridy = 1;
		listPanel.add(jcp, gbc);
		
		
		//Buttons
		JButton add = new JButton("ADD");
		add.setPreferredSize(new Dimension(100, 50));
		add.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					liftRep.safeAddSet(plan, workout, exercise, Integer.parseInt(repsText.getText()), Double.parseDouble(kgText.getText()));
			
					setList.setModel(liftRep.newSetGUIList(plan,workout,exercise,defSetList));
				} catch (NumberFormatException ex) {
					error.errorText("ERROR Please Insert a Valid Format");
				}
				repsText.setText("");
				kgText.setText("");
			}
		});
		
		JButton edit = new JButton("EDIT");
		edit.setPreferredSize(new Dimension(100, 50));
		edit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(setValue == null) {
					error.errorText("ERROR Please Select a Set");
				} else {
					try {
						liftRep.safeEditSet(plan, workout, exercise, setValue, Integer.parseInt(repsText.getText()), Double.parseDouble(kgText.getText()));
				
						setList.setModel(liftRep.newSetGUIList(plan,workout,exercise,defSetList));
					} catch (NumberFormatException ex) {
						error.errorText("ERROR Please Insert a Valid Format");
					}
				}
				repsText.setText("");
				kgText.setText("");
			}
		});
		
		JButton delete = new JButton("DELETE");
		delete.setPreferredSize(new Dimension(100, 50));
		delete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(setValue == null) {
					error.errorText("ERROR Please Select a Set!");
				} else {
					liftRep.safeDeleteSet(plan, workout, exercise, setValue);
				
					setList.setModel(liftRep.newSetGUIList(plan,workout,exercise,defSetList));
				}
			}
		});
		
		JButton safe = new JButton("SAFE");
		safe.setPreferredSize(new Dimension(200, 50));
		safe.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(setList.getModel().getSize() < 1) {
					error.errorText("ERROR Please Add a Set!");
				} else {
					liftRep.calculateProgression(plan, workout, exercise);
					
					liftRep.clearSets(plan, workout, exercise);
					setList.setModel(liftRep.newSetGUIList(plan,workout,exercise,defSetList));
				
					chartPanel.removeAll();
					chartPanel.add(wp.progressionChartPanel(plan, workout, exercise));
					chartPanel.revalidate();
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
		mainPanel.add(listPanel, gbc);
		
		gbc.insets = new Insets(10,0,5,0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(inputTextPanel , gbc);
		
		gbc.insets = new Insets(10,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 2; 
		mainPanel.add(buttonPanel, gbc);
		
		gbc.insets = new Insets(10,0,0,0);
		gbc.gridx = 0;
		gbc.gridy = 3; 
		mainPanel.add(safe, gbc);
		
		
		//Design
		Color darkBlue = new Color(18, 25, 33);
		Color yellow = new Color(250, 213, 125);
		Font text = new Font("ARIAL", Font.PLAIN, 20);
		
		mainPanel.setBackground(darkBlue);
		buttonPanel.setBackground(darkBlue);
		listPanel.setBackground(darkBlue);
		inputTextPanel.setBackground(darkBlue);
		
		jcp.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		jcp.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		jcp.setFont(text);
		jcp.setBackground(darkBlue);
		jcp.setForeground(Color.WHITE);
		
		setList.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		setList.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		setList.setFont(text);
		setList.setBackground(darkBlue);
		setList.setForeground(Color.WHITE);
		
		wdh.setForeground(Color.WHITE);
		wdh.setFont(text);
		
		kg.setForeground(Color.WHITE);
		kg.setFont(text);
		
		set.setForeground(Color.WHITE);
		set.setFont(text);
		
		repsText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		repsText.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		repsText.setFont(text);
		repsText.setBackground(darkBlue);
		repsText.setForeground(Color.WHITE);
		
		kgText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		kgText.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
		kgText.setFont(text);
		kgText.setBackground(darkBlue);
		kgText.setForeground(Color.WHITE);
		
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
		
		safe.setFont(text);
		safe.setBackground(yellow);
		safe.setForeground(darkBlue);
		safe.setBorder(null);
		
		//frame
		frame.add(mainPanel);
		
		frame.setVisible(true);
		
		return frame;
	}
}
