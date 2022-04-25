package swing;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Backend.LiftRepository;

/**
 * 
 * @author Manuel
 *
 * Diese Klasse sorgt dafür, dass alle Swing Klassen miteinander verbunden werden. Hier wird der Header, sowie auch der Body für
 * die unterschiedlichen Panels zur verfügung gestellt.
 *
 */

public class MainPage {
	LiftRepository liftRep = LiftRepository.read();
	WorkoutPage workout = new WorkoutPage();
	MeasurementsPage measurements = new MeasurementsPage();
	ProfilePage profile = new ProfilePage();

	public JFrame mainWindow() {
		//Add Site Panels
		JPanel profileSitePanel = profile.profilePage();
		JPanel workoutSitePanel = workout.workoutSite();
		JPanel measurementsSitePanel = measurements.measurementsSite();
		
		
		//set JFrame
		JFrame frame = new JFrame();
		
		
		//Window
		frame.setTitle("Lift");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setMinimumSize(new Dimension(1600, 900));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		//Layout	
		Container main = frame.getContentPane();
		main.setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		
		JPanel measurementsPanel = new JPanel(new GridBagLayout());
		JPanel profilePanel = new JPanel(new GridBagLayout());
		JPanel workoutPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		JPanel contentPanel = new JPanel();
		CardLayout content = new CardLayout();
		contentPanel.setLayout(content);
		
		
		//set ContenPanel
		contentPanel.add(workoutSitePanel, "workout");
		contentPanel.add(measurementsSitePanel, "measurements");
		contentPanel.add(profileSitePanel, "profile");
		content.show(contentPanel, "workout");
		
		
		//icons
		//Profile
		ImageIcon iProfile = new ImageIcon(getClass().getResource("../Images/Profil.png"));
		Image profileImage = iProfile.getImage();
		Image scaleProfile = profileImage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
		iProfile = new ImageIcon(scaleProfile);
		
		//Workout
		ImageIcon iWorkout = new ImageIcon(getClass().getResource("../Images/Workout.png"));
		Image workoutImg = iWorkout.getImage();
		Image scaleWorkout = workoutImg.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH);
		iWorkout = new ImageIcon(scaleWorkout);
		
		//Measurements
		ImageIcon iMeasurements = new ImageIcon(getClass().getResource("../Images/Measurements.png"));
		Image measurementsImg = iMeasurements.getImage();
		Image scaleMeasurements = measurementsImg.getScaledInstance(70, 70,  java.awt.Image.SCALE_SMOOTH);
		iMeasurements = new ImageIcon(scaleMeasurements);
		
		
		//Label
		JLabel profile = new JLabel(iProfile);
		JLabel profileText = new JLabel("PROFILE");
		profilePanel.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e) {
				content.show(contentPanel, "profile");
		    }
		});
		
		
		JLabel workout = new JLabel(iWorkout);
		JLabel workoutText = new JLabel("WORKOUT");
		workoutPanel.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e) {
				content.show(contentPanel, "workout");
		    }
		});
		
		JLabel measurements = new JLabel(iMeasurements);
		JLabel measurementsText = new JLabel("MEASUREMENTS");
		measurementsPanel.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e) {
				content.show(contentPanel, "measurements");
		    }
		});
		
		
		//add to profilePanel
		gbc.insets = new Insets(0,0,0,5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		profilePanel.add(profile, gbc);
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		profilePanel.add(profileText, gbc);
		
		
		//add to workoutPanel
		gbc.insets = new Insets(0,0,0,5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		workoutPanel.add(workout, gbc);
		
		gbc.insets = new Insets(0,5,0,0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		workoutPanel.add(workoutText, gbc);
		
		
		//add to measurementsPanel
		gbc.insets = new Insets(0,0,0,5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		measurementsPanel.add(measurements, gbc);
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		measurementsPanel.add(measurementsText, gbc);
		
		
		//add to mainPanel
		gbc.insets = new Insets(0,0,0,150);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(profilePanel, gbc);
		
		gbc.insets = new Insets(0,150,0,150);
		gbc.gridx = 1;
		gbc.gridy = 0;
		mainPanel.add(workoutPanel, gbc);
		
		gbc.insets = new Insets(0,150,0,0);
		gbc.gridx = 2;
		gbc.gridy = 0;
		mainPanel.add(measurementsPanel, gbc);
		
		
		//add to main
		main.add(mainPanel, BorderLayout.NORTH);
		main.add(contentPanel, BorderLayout.CENTER);
		
		
		//Design
		Color darkBlue = new Color(18, 25, 33);
		Color orange = new Color(255, 177, 106);
		
		mainPanel.setBackground(darkBlue);
		
		profilePanel.setBackground(darkBlue);
		profileText.setFont(new Font("ARIAL", Font.BOLD, 30));
		profileText.setForeground(orange);
		
		workoutPanel.setBackground(darkBlue);
		workoutText.setFont(new Font("ARIAL", Font.BOLD, 30));
		workoutText.setForeground(orange);
		
		measurementsPanel.setBackground(darkBlue);
		measurementsText.setFont(new Font("ARIAL", Font.BOLD, 30));
		measurementsText.setForeground(orange);
		
		
		//frame
		frame.setIconImage(workoutImg);
		frame.setVisible(true);
		
		return frame;
	}	
	
	
	public static void main(String[] args) {
		LiftRepository liftRep = LiftRepository.read();
		liftRep.fillMeasurementsArray();
		liftRep.fillOneRMExerciseArray();
		liftRep.fillProfileList();
		
		EventQueue.invokeLater(new Runnable() {
				
			@Override
			public void run() {
				MainPage main = new MainPage();
				main.mainWindow();
			}
		});
	}	
}
