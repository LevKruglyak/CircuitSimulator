package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import engine.GameState;
import util.Point;
import util.Rectangle;

public class AlertForm extends Form {

	private String message;
	private String header;
	
	public AlertForm(GameState state, Point dim, String message) {
		super(null, state);
		Point dimensions = state.getGame().getBoundBox().getBounds();
		Rectangle rect = new Rectangle((dimensions.getX()-dim.getX())/2,(dimensions.getY()-dim.getY())/2, dim.getX(), dim.getY());
		
		this.setBoundBox(rect);
		this.message = message;
		this.header = "Error!";
		
		this.setBackground(Color.DARK_GRAY);
		
		Label messageLabel = new Label(this, new Rectangle(30,0,dim.getX()-60, dim.getY()), this.message);
		messageLabel.setBackground(getBackground());
		messageLabel.setTextColor(Color.WHITE);
		
		this.addComponent(messageLabel);
		
		Label headerLabel = new Label(this, new Rectangle(0,0,dim.getX(),30), header);
		headerLabel.setBackground(Color.WHITE);
		headerLabel.setTextColor(Color.BLACK);
		
		this.addComponent(headerLabel);
		
		Form form = this;
		
		Button OKButton = new Button(this, new Rectangle(dim.getX()/2-30,dim.getY()-40,60,30), "OK");
		OKButton.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				form.closeForm();
			}
			
		});
		this.addComponent(OKButton);
		
		headerLabel.setFontSize(20);
	}
}
