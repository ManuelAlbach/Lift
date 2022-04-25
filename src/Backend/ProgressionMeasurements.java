package Backend;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Manuel
 *
 * Die Klasse speichert die Progressionsdaten für die Körpermaße.
 *
 */

public class ProgressionMeasurements implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2173085807477023948L;
	
	double measurements;
	String date;
	
	
	//Getter & Setter
	public void setMeasurements(double measurements) {
		this.measurements = measurements;
	}
	
	public double getMeasurements() {
		return measurements;
	}
	
	public void setDate() {
		String pattern = "dd.MM.yy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		this.date = simpleDateFormat.format(new Date());	
	}
	
	public String getDate() {
		return date;
	}
}
