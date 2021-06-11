package gates;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import circuits.CircuitRunningState;
import engine.GameState;
import gui.Button;
import gui.Form;
import gui.Label;
import gui.NumberField;
import util.Point;
import util.Rectangle;

public class RegistersCreationForm extends Form {
	private String header;

	public RegistersCreationForm(GameState state, int size) {
		super(null, state);
		Point dim = new Point(250, 200);
		Point dimensions = state.getGame().getBoundBox().getBounds();
		Rectangle rect = new Rectangle((dimensions.getX() - dim.getX()) / 2, (dimensions.getY() - dim.getY()) / 2,
				dim.getX(), dim.getY());

		this.setBoundBox(rect);

		this.header = "Create Register Array";

		this.setBackground(Color.DARK_GRAY);

		Label descLabel = new Label(this, new Rectangle(10, 50, dim.getX() - 20, 30), "Address Size:");
		descLabel.setBackground(Color.DARK_GRAY);
		descLabel.setTextColor(Color.WHITE);
		this.addComponent(descLabel);

		NumberField bitSize = new NumberField(new Point(50, 90), 150, this);
		bitSize.setMinimum(0);
		bitSize.setValue(0);
		bitSize.setMaximum(11);
		this.addComponent(bitSize);

		Label headerLabel = new Label(this, new Rectangle(0, 0, dim.getX(), 30), header);
		headerLabel.setBackground(Color.WHITE);
		headerLabel.setTextColor(Color.BLACK);

		this.addComponent(headerLabel);

		Form form = this;

		Button createButton = new Button(this, new Rectangle(dim.getX() / 2 + 5, dim.getY() - 40, 80, 30), "Create");
		createButton.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				((CircuitRunningState) state).addGate(new RegisterArrayGate(null, null, size, bitSize.getValue()));
				form.closeForm();
			}

		});
		this.addComponent(createButton);

		Button cancelButton = new Button(this, new Rectangle(dim.getX() / 2 - 85, dim.getY() - 40, 80, 30), "Cancel");
		cancelButton.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				form.closeForm();
			}

		});
		this.addComponent(cancelButton);

		headerLabel.setFontSize(20);
	}
}
