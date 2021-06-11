package gui;

import util.Point;

public class BinNumberField extends NumberField {

	public BinNumberField(Point location, float width, ComponentCarrier parent) {
		super(location, width, parent);
		this.number =1;
		this.displayLbl.setText("1");
		this.minimum = 1;
		this.maximum = 32;
	}

	public void increment() {
		if (2*number <= maximum) {
			number*=2;
		}
		
		displayLbl.setText(""+number);
	}
	
	public void decrement() {
		if (number/2 >= minimum) {
			number/=2;
		}
		
		displayLbl.setText(""+number);
	}
}
