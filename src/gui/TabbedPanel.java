package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import util.Point;
import util.Rectangle;

public class TabbedPanel extends Panel {
	
	private List<Panel> tabs;
	private int currentTabIndex;
	private Label pageNumber;
	
	public TabbedPanel(Rectangle boundBox, ComponentCarrier parent, Color backgroundColor, int numTabs) {
		super(boundBox, parent);
		
		Rectangle dimensionless = new Rectangle(0,0,getWidth(), getHeight());	
		tabs = new ArrayList<Panel>(numTabs);
		for (int i = 0; i < numTabs; i++) {
			Panel panel = new Panel(dimensionless,this);
			panel.setBackground(backgroundColor);
			tabs.add(panel);
		}
		
		this.currentTabIndex = 0;
		this.addComponent(tabs.get(currentTabIndex));
		
		
		pageNumber = new Label(this, new Rectangle(new Point(getWidth()/2, getHeight()-25),20), "0");
		pageNumber.setFontSize(20);
		pageNumber.setBackground(backgroundColor);
		pageNumber.setTextColor(Color.WHITE);
		
		Button increment = new Button(this, new Rectangle(getWidth()-60,getHeight()-40,50,30), ">");
		increment.setFontSize(30);
		increment.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentTabIndex < tabs.size()-1) {
					setTabIndex(currentTabIndex+1);
				}
				
				pageNumber.setText("" + currentTabIndex);
			}
			
		});
		this.addComponent(increment);
		
		Button decrement = new Button(this, new Rectangle(10,getHeight()-40,50,30), "<");
		decrement.setFontSize(30);
		decrement.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentTabIndex > 0) {
					setTabIndex(currentTabIndex-1);
				}
				
				pageNumber.setText("" + currentTabIndex);
			}
			
		});
		this.addComponent(decrement);
		
		this.addComponent(pageNumber);
	}

	public void setTabIndex(int index) {
		this.removeComponent(tabs.get(currentTabIndex));
		this.currentTabIndex = index;
		this.addComponent(0,tabs.get(currentTabIndex));
		pageNumber.setText("" + currentTabIndex);
	}
	
	public Panel getCurrentTab() {
		return tabs.get(currentTabIndex);
	}
	
	public int getCurrentTabIndex() {
		return currentTabIndex;
	}
	
	public List<Panel> getTabs() {
		return tabs;
	}
	
	public Panel getTab(int index) {
		return tabs.get(index);
	}
}
