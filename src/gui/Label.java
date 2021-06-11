package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import util.GraphicsPlus;
import util.Rectangle;

public class Label extends Component {
	
	private String text;
	private int fontSize;
	private String font;

	private Color background;
	private Color textColor;
	
	private int justification;
	
	private BufferedImage image;
	
	public Label(ComponentCarrier parent, Rectangle rect, String text) {
		super(rect, parent);
		this.image = (new BufferedImage((int)getWidth(), (int)getHeight(),
				BufferedImage.TYPE_4BYTE_ABGR));
	
		this.setText(text);
		this.setFontSize(20);
		this.setFont("CourierNew");
		
		this.setJustification(GraphicsPlus.CENTER_JUSTIFIED);
		
		this.setBackground(Color.WHITE);
		this.setTextColor(Color.BLACK);
		
		updateImage();
	}
	
	public Label(Label label) {
		super(label.getBoundBox(), label.getParent());
		this.image = (new BufferedImage((int)getWidth(), (int)getHeight(),
				BufferedImage.TYPE_4BYTE_ABGR));
	
		this.setText(label.getText());
		this.setFontSize(label.getFontSize());
		this.setFont(label.getFont());
		
		this.setJustification(label.getJustification());
		
		this.setBackground(label.getBackground());
		this.setTextColor(label.getTextColor());
		updateImage();
	}

	public void updateImage() {
		if (text.equals("")) {
			text = " ";
		}
		
		GraphicsPlus g2 = new GraphicsPlus(image.createGraphics());
		g2.getGraphics().clearRect(0, 0, (int)getWidth(), (int)getHeight());
		
		g2.setColor(background);
		g2.getGraphics().fillRect(0, 0, (int)getWidth(), (int)getHeight());
		
		g2.setFont(new Font(font, Font.PLAIN, fontSize));
		g2.setColor(textColor);

		g2.drawCenteredString(getBoundBox().zero(), new AttributedString(text), justification);
	}
	
	@Override
	public void render(GraphicsPlus g) {
		//updateImage();
		g.drawImage(image, getBoundBox());
		
		super.render(g);
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
		updateImage();
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
		updateImage();
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
		updateImage();
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		updateImage();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		updateImage();
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getJustification() {
		return justification;
	}

	public void setJustification(int justification) {
		this.justification = justification;
		updateImage();
	}
}
