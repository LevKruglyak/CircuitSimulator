package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

public class GraphicsPlus {
	private Graphics2D g;

	public GraphicsPlus(Graphics g) {
		this.g = (Graphics2D) g;
	}
	private static int counter = 0;
	public void drawRect(Rectangle rect) {
		g.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
		counter++;
	}

	public void fillRect(Rectangle rect) {
		g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
		counter++;
	}

	public void setColor(Color c) {
		g.setColor(c);
	}

	public void translate(Point p) {
		g.translate(p.getX(), p.getY());
	}

	public void fillCircle(Point center, float radius) {
		g.fillOval((int) (center.getX() - radius), (int) (center.getY() - radius), (int) (radius * 2),
				(int) (radius * 2));
		counter++;
	}

	public void drawString(String str, int x, int y) {
		g.drawString(str, x, y);
	}

	public void setFont(Font font) {
		g.setFont(font);
	}

	public void drawLine(Point p1, Point p2) {
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		counter++;
	}

	public static final int LEFT_JUSTIFIED = 0;
	public static final int RIGHT_JUSTIFIED = 1;
	public static final int CENTER_JUSTIFIED = 2;

	public void drawCenteredString(Rectangle bound, AttributedString text, int justification) {
		if (!(justification == CENTER_JUSTIFIED)) {
			bound = bound.scale(0.9f);
		}

		text.addAttribute(TextAttribute.FONT, new Font("Courier", Font.BOLD, g.getFont().getSize()));
		text.addAttribute(TextAttribute.SWAP_COLORS, g.getColor());

		AttributedCharacterIterator iterator = text.getIterator();
		int start = iterator.getBeginIndex();
		int end = iterator.getEndIndex();
		FontRenderContext frc = g.getFontRenderContext();
		LineBreakMeasurer mr = new LineBreakMeasurer(iterator, frc);
		mr.setPosition(start);

		if (justification == LEFT_JUSTIFIED) {
			float x = 5;
			float y = 5;

			while (mr.getPosition() < end) {
				TextLayout layout = mr.nextLayout(bound.getWidth());

				y += layout.getAscent();
				float dx = layout.isLeftToRight() ? 0 : bound.getWidth() - layout.getAdvance();

				layout.draw(g, x + dx, y);
				y += layout.getDescent() + layout.getLeading();
			}

		} else if (justification == RIGHT_JUSTIFIED) {
			float x = -5 + bound.getWidth();
			float y = 5;

			while (mr.getPosition() < end) {
				TextLayout layout = mr.nextLayout(bound.getWidth());

				y += layout.getAscent();
				float dx = !layout.isLeftToRight() ? 0 : layout.getAdvance();
				layout.draw(g, x - dx, y);

				y += layout.getDescent() + layout.getLeading();
			}
		} else if (justification == CENTER_JUSTIFIED) {
			float height = 0;
			LineBreakMeasurer mr2 = new LineBreakMeasurer(iterator, frc);
			while (mr2.getPosition() < end) {
				TextLayout layout2 = mr2.nextLayout(bound.getWidth());
				height += layout2.getAscent() + layout2.getDescent() + layout2.getLeading();
			}

			float y = (bound.getY() + ((bound.getHeight() - height) / 2f));

			while (mr.getPosition() < end) {
				TextLayout layout = mr.nextLayout(bound.getWidth());
				float advance = layout.getAdvance();
				float x = (bound.getWidth() - layout.getAdvance()) / 2f + advance;

				y += layout.getAscent();
				float dx = !layout.isLeftToRight() ? 0 : layout.getAdvance();

				layout.draw(g, x - dx, y);

				y += layout.getDescent() + layout.getLeading();
			}

		}
	}

	public void setStroke(Stroke s) {
		g.setStroke(s);
	}

	public Stroke getStroke() {
		return g.getStroke();
	}

	public void drawImage(BufferedImage img, Rectangle rect) {
		g.drawImage(img, (int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), null);
		counter++;
	}

	public AffineTransform getAffineTransform() {
		return g.getTransform();
	}

	public void setAffineTransform(AffineTransform form) {
		g.setTransform(form);
	}

	public void scale(float scale) {
		g.scale(scale, scale);
	}

	public void drawCircle(Point center, float radius) {
		g.drawOval((int) (center.getX() - radius), (int) (center.getY() - radius), (int) (radius * 2),
				(int) (radius * 2));
		counter++;
	}

	public void fillPolygon(Polygon segment) {
		g.fillPolygon(segment);
	}

	public void drawPolygon(Polygon segment) {
		g.drawPolygon(segment);
	}

	public Graphics2D getGraphics() {
		return g;
	}

	public void drawString(String string, Point center) {
		g.drawString(string, center.getX(), center.getY());
	}

	private final static int zero[] = { 1, 1, 1, 1, 1, 1, 0 };
	private final static int one[] = { 0, 1, 1, 0, 0, 0, 0 };
	private final static int two[] = { 1, 1, 0, 1, 1, 0, 1 };
	private final static int three[] = { 1, 1, 1, 1, 0, 0, 1 };
	private final static int four[] = { 0, 1, 1, 0, 0, 1, 1 };
	private final static int five[] = { 1, 0, 1, 1, 0, 1, 1 };
	private final static int six[] = { 1, 0, 1, 1, 1, 1, 1 };
	private final static int seven[] = { 1, 1, 1, 0, 0, 0, 0 };
	private final static int eight[] = { 1, 1, 1, 1, 1, 1, 1 };
	private final static int nine[] = { 1, 1, 1, 1, 0, 1, 1 };

	private final static Color off = new Color(0.4f, 0.2f, 0.2f, 0.1f);
	private final static Color on = new Color(1f, 0f, 0f, 1f);

	public int[] getNumber(int n) {
		switch (n) {
		case 0:
			return zero;
		case 1:
			return one;
		case 2:
			return two;
		case 3:
			return three;
		case 4:
			return four;
		case 5:
			return five;
		case 6:
			return six;
		case 7:
			return seven;
		case 8:
			return eight;
		case 9:
			return nine;
		default:
			return zero;
		}
	}

	public void drawSegment(Point p, int dig) {
		int[] onSegments = getNumber(dig);
		Polygon[] polys = createSegmentsAt((int)p.getX(), (int)p.getY());
		
		for (int i = 0; i < 7; i++) {
			if (onSegments[i] == 1) {
				g.setColor(on);
			} else {
				g.setColor(off);
			}
			
			g.fillPolygon(polys[i]);
		}
	}
	
	private Polygon[] createSegmentsAt(int x, int y) {
		Polygon[] segments = new Polygon[7];

		segments[0] = new Polygon();
		segments[0].addPoint(x + 20, y + 8);
		segments[0].addPoint(x + 90, y + 8);
		segments[0].addPoint(x + 98, y + 15);
		segments[0].addPoint(x + 90, y + 22);
		segments[0].addPoint(x + 20, y + 22);
		segments[0].addPoint(x + 12, y + 15);

		segments[1] = new Polygon();
		segments[1].addPoint(x + 91, y + 23);
		segments[1].addPoint(x + 98, y + 18);
		segments[1].addPoint(x + 105, y + 23);
		segments[1].addPoint(x + 105, y + 81);
		segments[1].addPoint(x + 98, y + 89);
		segments[1].addPoint(x + 91, y + 81);

		segments[2] = new Polygon();
		segments[2].addPoint(x + 91, y + 97);
		segments[2].addPoint(x + 98, y + 89);
		segments[2].addPoint(x + 105, y + 97);
		segments[2].addPoint(x + 105, y + 154);
		segments[2].addPoint(x + 98, y + 159);
		segments[2].addPoint(x + 91, y + 154);

		segments[3] = new Polygon();
		segments[3].addPoint(x + 20, y + 155);
		segments[3].addPoint(x + 90, y + 155);
		segments[3].addPoint(x + 98, y + 162);
		segments[3].addPoint(x + 90, y + 169);
		segments[3].addPoint(x + 20, y + 169);
		segments[3].addPoint(x + 12, y + 162);

		segments[4] = new Polygon();
		segments[4].addPoint(x + 5, y + 97);
		segments[4].addPoint(x + 12, y + 89);
		segments[4].addPoint(x + 19, y + 97);
		segments[4].addPoint(x + 19, y + 154);
		segments[4].addPoint(x + 12, y + 159);
		segments[4].addPoint(x + 5, y + 154);

		segments[5] = new Polygon();
		segments[5].addPoint(x + 5, y + 23);
		segments[5].addPoint(x + 12, y + 18);
		segments[5].addPoint(x + 19, y + 23);
		segments[5].addPoint(x + 19, y + 81);
		segments[5].addPoint(x + 12, y + 89);
		segments[5].addPoint(x + 5, y + 81);

		segments[6] = new Polygon();
		segments[6].addPoint(x + 20, y + 82);
		segments[6].addPoint(x + 90, y + 82);
		segments[6].addPoint(x + 95, y + 89);
		segments[6].addPoint(x + 90, y + 96);
		segments[6].addPoint(x + 20, y + 96);
		segments[6].addPoint(x + 15, y + 89);

		return segments;
	}
}
