package gui;

import java.awt.Color;

import util.GraphicsPlus;
import util.Rectangle;

public class Button extends Label {

	public Button(ComponentCarrier parent, Rectangle rect, String text) {
		super(parent, rect, text);
		
		this.expandOnHover = true;
	}
	
	private boolean expandOnHover;
	private static final float expandScale = 1.02f;
	
	@Override
	public void render(GraphicsPlus g) {
		Rectangle boundBox = getBoundBox();
		
		if (expandOnHover && isEnabled() && isMouseOver()) {
			boundBox = boundBox.scale(expandScale);
		}
		
		g.drawImage(getImage(), boundBox);
		super.render(g);
		
		if (isPressed() && isEnabled()) {
			g.setColor(new Color(0f,0f,0f,0.5f));
			g.fillRect(boundBox);
		}
	}

	public void setExpandOnHover(boolean b) {
		this.expandOnHover = b;
	}
}
