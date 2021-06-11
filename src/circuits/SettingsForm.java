package circuits;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import engine.GameState;
import gui.Button;
import gui.Form;
import gui.Label;
import gui.NumberField;
import util.Point;
import util.Rectangle;

public class SettingsForm extends Form {
	public SettingsForm(GameState state) {
		super(null, state);
		CircuitRunningState st = (CircuitRunningState) state;
		
		Point dim = new Point(460, 300);
		Point dimensions = state.getGame().getBoundBox().getBounds();
		Rectangle rect = new Rectangle((dimensions.getX() - dim.getX()) / 2, (dimensions.getY() - dim.getY()) / 2,
				dim.getX(), dim.getY());

		this.setBoundBox(rect);

		Label headerLabel = new Label(this, new Rectangle(0, 0, dim.getX(), 30), "Edit Settings");
		headerLabel.setBackground(Color.WHITE);
		headerLabel.setTextColor(Color.BLACK);

		this.addComponent(headerLabel);

		Form form = this;
		
		Label descLabel = new Label(this, new Rectangle(10, 50, 230, 30), "Clock Speed:");
		descLabel.setBackground(Color.DARK_GRAY);
		descLabel.setTextColor(Color.WHITE);
		this.addComponent(descLabel);

		NumberField clockSpeed = new NumberField(new Point(50, 90), 150, this);
		clockSpeed.setMinimum(21);
		clockSpeed.setValue(st.getCLOCK_SPEED());
		clockSpeed.setDelta(10);
		clockSpeed.setMaximum(1001);
		this.addComponent(clockSpeed);
		
		Label descLabel1 = new Label(this, new Rectangle(260, 50, 150, 30), "Max Bit Size:");
		descLabel1.setBackground(Color.DARK_GRAY);
		descLabel1.setTextColor(Color.WHITE);
		this.addComponent(descLabel1);
		
		NumberField maxBitSize = new NumberField(new Point(250, 90), 150, this);
		maxBitSize.setMinimum(4);
		maxBitSize.setValue(st.getMAX_BIT_SIZE());
		maxBitSize.setMaximum(32);
		this.addComponent(maxBitSize);
		
		Label descLabel2 = new Label(this, new Rectangle(10, 150, 230, 30), "Normal UPU:");
		descLabel2.setBackground(Color.DARK_GRAY);
		descLabel2.setTextColor(Color.WHITE);
		this.addComponent(descLabel2);

		NumberField normalUPU = new NumberField(new Point(50, 190), 150, this);
		normalUPU.setMinimum(1);
		normalUPU.setValue(st.getNORMAL_UPU());
		normalUPU.setMaximum(30);
		this.addComponent(normalUPU);
		
		Label descLabel3 = new Label(this, new Rectangle(260, 150, 150, 30), "Turbo UPU:");
		descLabel3.setBackground(Color.DARK_GRAY);
		descLabel3.setTextColor(Color.WHITE);
		this.addComponent(descLabel3);
		
		NumberField turboUPU = new NumberField(new Point(250, 190), 150, this);
		turboUPU.setMinimum(51);
		turboUPU.setValue(st.getTURBO_UPU());
		turboUPU.setDelta(50);
		turboUPU.setMaximum(1001);
		this.addComponent(turboUPU);

		Button applyButton = new Button(this, new Rectangle(dim.getX() / 2 + 5, dim.getY() - 40, 80, 30), "Apply");
		applyButton.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				st.setCLOCK_SPEED(clockSpeed.getValue());
				st.setMAX_BIT_SIZE(maxBitSize.getValue());
				st.setTURBO_UPU(turboUPU.getValue());
				st.setNORMAL_UPU(normalUPU.getValue());
				form.closeForm();
			}

		});
		this.addComponent(applyButton);

		Button cancelButton = new Button(this, new Rectangle(dim.getX() / 2 - 85, dim.getY() - 40, 80, 30), "Cancel");
		cancelButton.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				form.closeForm();
			}

		});
		this.addComponent(cancelButton);
		
		this.setBackground(Color.DARK_GRAY);
	}
}
