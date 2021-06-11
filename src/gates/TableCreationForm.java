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

public class TableCreationForm extends Form {
	private String header;

	public TableCreationForm(GameState state) {
		super(null, state);
		Point dim = new Point(250, 280);
		Point dimensions = state.getGame().getBoundBox().getBounds();
		Rectangle rect = new Rectangle((dimensions.getX() - dim.getX()) / 2, (dimensions.getY() - dim.getY()) / 2,
				dim.getX(), dim.getY());

		this.setBoundBox(rect);

		this.header = "Create Truth Table";

		this.setBackground(Color.DARK_GRAY);

		Label descLabel = new Label(this, new Rectangle(10, 50, dim.getX() - 20, 30), "Input Size:");
		descLabel.setBackground(Color.DARK_GRAY);
		descLabel.setTextColor(Color.WHITE);
		this.addComponent(descLabel);

		NumberField inSize = new NumberField(new Point(50, 90), 150, this);
		inSize.setMinimum(1);
		inSize.setValue(1);
		inSize.setMaximum(20);
		this.addComponent(inSize);
		
		Label descLabel1 = new Label(this, new Rectangle(10, 140, dim.getX() - 20, 30), "Output Size:");
		descLabel1.setBackground(Color.DARK_GRAY);
		descLabel1.setTextColor(Color.WHITE);
		this.addComponent(descLabel1);

		NumberField outSize = new NumberField(new Point(50, 180), 150, this);
		outSize.setMinimum(1);
		outSize.setValue(1);
		outSize.setMaximum(20);
		this.addComponent(outSize);

		Label headerLabel = new Label(this, new Rectangle(0, 0, dim.getX(), 30), header);
		headerLabel.setBackground(Color.WHITE);
		headerLabel.setTextColor(Color.BLACK);

		this.addComponent(headerLabel);

		Form form = this;

		Button createButton = new Button(this, new Rectangle(dim.getX() / 2 + 5, dim.getY() - 40, 80, 30), "Create");
		createButton.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				((CircuitRunningState) state).addGate(new TableGate(null, null, inSize.getValue(), outSize.getValue()));
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
