package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import util.Point;
import util.Rectangle;

public class NumberField extends Panel {

	protected int number;
	protected Label displayLbl;
	private Button increaseBtn;
	private Button decreaseBtn;

	protected int maximum;
	protected int minimum;

	private int delta;

	public NumberField(Point location, float width, ComponentCarrier parent) {
		super(new Rectangle(location, new Point(width, 40)), parent);

		this.setBackground(Color.DARK_GRAY.darker());
		this.setBorder(this.getBackground());
		this.number = 0;
		this.delta = 1;
		displayLbl = new Label(parent, new Rectangle(5, 5, width - 40, 30), "" + number);
		displayLbl.setFontSize(20);
		displayLbl.setBackground(Color.LIGHT_GRAY);

		increaseBtn = new Button(parent, new Rectangle(width - 30, 5, 30, 13), " ");
		increaseBtn.setFontSize(20);
		increaseBtn.setExpandOnHover(false);
		increaseBtn.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				increment();
			}

		});

		decreaseBtn = new Button(parent, new Rectangle(width - 30, 22, 30, 13), " ");
		decreaseBtn.setFontSize(20);
		decreaseBtn.setExpandOnHover(false);
		decreaseBtn.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				decrement();
			}

		});

		this.addComponent(displayLbl);
		this.addComponent(increaseBtn);
		this.addComponent(decreaseBtn);

		this.setMaximum(100);
		this.setMinimum(0);
	}

	public void increment() {
		if (number < maximum) {
			number += delta;
		} else {
			number = minimum;
		}

		displayLbl.setText("" + number);
	}

	public void decrement() {
		if (number > minimum) {
			number -= delta;
		} else {
			number = maximum;
		}

		displayLbl.setText("" + number);
	}

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getValue() {
		return number;
	}

	public void setValue(int number) {
		this.number = number;
		displayLbl.setText("" + number);
	}

	public void setDelta(int i) {
		this.delta = i;
	}
}
