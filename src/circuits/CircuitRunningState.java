package circuits;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import engine.Game;
import engine.GameState;
import gates.ADDERGate;
import gates.ANDGate;
import gates.ClockGate;
import gates.CombineGate;
import gates.CounterGate;
import gates.DFlipFlopGate;
import gates.DLatchGate;
import gates.DecoderGate;
import gates.EqualGate;
import gates.ExtendGate;
import gates.Gate;
import gates.LEDGate;
import gates.MultiplexerGate;
import gates.NANDGate;
import gates.NORGate;
import gates.NOTGate;
import gates.ORGate;
import gates.PulserGate;
import gates.RegistersCreationForm;
import gates.RerouteGate;
import gates.SRLatchGate;
import gates.SegmentDisplayGate;
import gates.SegmentInputGate;
import gates.SplitGate;
import gates.TableCreationForm;
import gates.ToggleGate;
import gates.XNORGate;
import gates.XORGate;
import gui.AlertForm;
import gui.Button;
import gui.Form;
import gui.Label;
import gui.NumberField;
import gui.Panel;
import gui.TabbedPanel;
import util.GraphicsPlus;
import util.Point;
import util.Rectangle;

public class CircuitRunningState extends GameState {

	private CircuitPanel panel;
	private Panel mainPanel;
	private TabbedPanel sidePanel;

	private int MAX_BIT_SIZE = 16;
	private int CLOCK_SPEED = 51;
	private int NORMAL_UPU = 3;
	private int TURBO_UPU = 251;

	private Label dataLabel;

	public CircuitRunningState(Game game) {
		super(game);

		Form form = new Form(new Rectangle(0, 0, 1900, 1020), this);
		mainPanel = new Panel(new Rectangle(0, 0, 1900, 1020), form);
		mainPanel.setBackground(new Color(0.0f, 0.2f, 0.2f));
		form.addComponent(mainPanel);

		sidePanel = new TabbedPanel(new Rectangle(20, 50, 160, 900), form, Color.BLACK, 2);
		sidePanel.setBackground(Color.BLACK);
		sidePanel.setBordered(true);
		mainPanel.addComponent(sidePanel);

		panel = new CircuitPanel(this, form, new Rectangle(195, 50, 780 + 900, 950));
		panel.setBackground(Color.BLACK);
		panel.stop();
		mainPanel.addComponent(panel);

		setupMainPanel();
		setupSidePanel();

		addForm(form);
	}

	public void run() {
		runBtn.setText("Stop");
		panel.run();
		panel.deselectableAll();
		turboBtn.setEnabled(true);
	}

	public void stop() {
		runBtn.setText("Run");
		panel.stop();
		panel.selectableAll();
		turboBtn.setEnabled(false);
	}

	private Button turboBtn;
	private Button runBtn;

	private void setupMainPanel() {
		dataLabel = new Label(mainPanel, new Rectangle(1650, 10, 280, 30), "UPS: 60 | FPS: 60");
		dataLabel.setFontSize(20);
		dataLabel.setBackground(mainPanel.getBackground());
		dataLabel.setJustification(GraphicsPlus.RIGHT_JUSTIFIED);
		dataLabel.setTextColor(Color.WHITE);
		dataLabel.setBackground(mainPanel.getBackground());
		mainPanel.addComponent(dataLabel);

		fileLocation = "";
		turboBtn = new Button(mainPanel, new Rectangle(80, 10, 80, 30), "Turbo");
		turboBtn.setFontSize(20);
		mainPanel.addComponent(turboBtn);
		turboBtn.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggleTurboMode();

				if (turboBtn.getText().contentEquals("Turbo")) {
					turboBtn.setText("Normal");
				} else {
					turboBtn.setText("Turbo");
				}
			}

		});
		turboBtn.setEnabled(false);

		runBtn = new Button(mainPanel, new Rectangle(10, 10, 60, 30), "Run");
		runBtn.setFontSize(20);
		mainPanel.addComponent(runBtn);
		runBtn.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (runBtn.getText().contentEquals("Run")) {
					run();
				} else {
					stop();
				}
			}

		});

		CircuitRunningState st = this;

		Label lbl = new Label(mainPanel, new Rectangle(20, 970, 100, 40), "Size: ");
		lbl.setBackground(mainPanel.getBackground());
		lbl.setTextColor(Color.WHITE);
		lbl.setJustification(GraphicsPlus.LEFT_JUSTIFIED);
		mainPanel.addComponent(lbl);

		bitSize = new NumberField(new Point(80, 965), 100, mainPanel);
		bitSize.setMinimum(1);
		bitSize.setValue(1);
		bitSize.setMaximum(MAX_BIT_SIZE);
		bitSize.setBackground(mainPanel.getBackground());
		mainPanel.addComponent(bitSize);

		Label fileLocation = new Label(mainPanel, new Rectangle(620, 10, 900, 30),
				"Current File: " + this.fileLocation);
		fileLocation.setFontSize(20);
		fileLocation.setBackground(mainPanel.getBackground());
		fileLocation.setJustification(GraphicsPlus.LEFT_JUSTIFIED);
		fileLocation.setTextColor(Color.WHITE);
		mainPanel.addComponent(fileLocation);

		Button settBtn = new Button(mainPanel, new Rectangle(170, 10, 100, 30), "Settings");
		settBtn.setFontSize(20);
		settBtn.addEventListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addForm(new SettingsForm(st));
			}
		});
		mainPanel.addComponent(settBtn);

		Button saveBtn = new Button(mainPanel, new Rectangle(380, 10, 60, 30), "Save");
		saveBtn.setFontSize(20);
		saveBtn.addEventListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
				save();
			}
		});
		mainPanel.addComponent(saveBtn);

		Button saveAsBtn = new Button(mainPanel, new Rectangle(280, 10, 90, 30), "Save As");
		saveAsBtn.setFontSize(20);
		saveAsBtn.addEventListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop();
				Form blank = new Form(new Rectangle(0, 0, 1, 1), st);
				addForm(blank);
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");

				fileChooser.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File arg0) {
						return true;
					}

					@Override
					public String getDescription() {
						return ".cb";
					}

				});

				int userSelection = fileChooser.showSaveDialog(getGame().getFrame());

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					((CircuitRunningState) st).setFileLocation(fileToSave.getAbsolutePath());
					fileLocation.setText("Current File: " + fileToSave.getAbsolutePath());
					save();

				}

				removeForm(blank);
			}
		});
		mainPanel.addComponent(saveAsBtn);

		Button loadBtn = new Button(mainPanel, new Rectangle(450, 10, 60, 30), "Load");
		loadBtn.setFontSize(20);
		mainPanel.addComponent(loadBtn);
		loadBtn.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
				Form blank = new Form(new Rectangle(0, 0, 1, 1), st);
				addForm(blank);

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setApproveButtonMnemonic('L');
				fileChooser.setApproveButtonText("Load");
				fileChooser.setDialogTitle("Specify a file to load");
				fileChooser.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File arg0) {
						return true;
					}

					@Override
					public String getDescription() {
						return ".cb";
					}

				});
				int userSelection = fileChooser.showOpenDialog(getGame().getFrame());

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					mainPanel.removeComponent(panel);

					File fileToSave = fileChooser.getSelectedFile();
					((CircuitRunningState) st).setFileLocation(fileToSave.getAbsolutePath());
					fileLocation.setText("Current File: " + fileToSave.getAbsolutePath());

					try {
						panel = new CircuitPanel(st, mainPanel, panel.getBoundBox(),
								readFileAsString(fileToSave.getAbsolutePath()));
						panel.update();

						mainPanel.addComponent(panel);
					} catch (Exception e1) {
						addForm(new AlertForm(st, new Point(300, 200), "There was an error when loading the file."));
					}
				}

				removeForm(blank);
			}
		});

		Button refreshBtn = new Button(mainPanel, new Rectangle(520, 10, 90, 30), "Refresh");
		refreshBtn.setFontSize(20);
		refreshBtn.addEventListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					File fileToSave = new File(st.fileLocation);
					panel = new CircuitPanel(st, mainPanel, panel.getBoundBox(),
							readFileAsString(fileToSave.getAbsolutePath()));
					panel.update();

					mainPanel.addComponent(panel);
				} catch (Exception e1) {
					addForm(new AlertForm(st, new Point(300, 200), "There was an error when loading the file."));
				}

			}
		});
		mainPanel.addComponent(refreshBtn);
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}

	private void save() {
		String content = panel.save();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileLocation));
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			addForm(new AlertForm(this, new Point(300, 200), "There was an error when saving the file."));
		}
	}

	protected void setFileLocation(String absolutePath) {
		this.fileLocation = absolutePath;
	}

	private String fileLocation;

	private int x;
	private int y;

	private int form;

	private void addLbl(String text) {
		Label lbl = new Label(sidePanel, new Rectangle(10, 10 + 60 * y, 140, 50), text);
		lbl.setBackground(Color.BLACK);
		lbl.setTextColor(Color.WHITE);
		lbl.setFontSize(20);
		sidePanel.getTab(form).addComponent(lbl);
		x = 0;
		y++;

		if (y == 14) {
			y = 0;
			form++;
		}
	}

	private void addBtn(String text, Color col, ActionListener act) {
		Button btn = new Button(sidePanel, new Rectangle(10 + 75 * x, 10 + 60 * y, 65, 50), text);
		btn.setBackground(col);
		btn.setFontSize(20);
		btn.addEventListener(act);
		sidePanel.getTab(form).addComponent(btn);

		if (x == 0) {
			x++;
		} else {
			x--;
			y++;
		}

		if (y == 14) {
			y = 0;
			form++;
		}
	}

	public void addGate(Gate gate) {
		panel.deselectAll();
		if (!panel.isRunning()) {
			gate.setLocation(panel.transformToLocalCoordinates(getMouseLocation()));
			gate.setParent(panel);
			gate.setPanel(panel);
			panel.addComponent(gate);
			panel.switchState(new PlaceState(this, panel, getMouseLocation(), gate));
			gate.setSelected(true);
		}
	}

	private void setupSidePanel() {
		x = 0;
		y = 0;

		form = 0;

		GameState st = this;

		addLbl("Logic Gates");
		addBtn("AND", ANDGate.getANDColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new ANDGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("NAND", NANDGate.getNANDColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new NANDGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("NOT", NOTGate.getNOTColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new NOTGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("OR", ORGate.getORColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new ORGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("XOR", XORGate.getXORColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new XORGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("XNOR", XNORGate.getXNORColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new XNORGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("ADD", ADDERGate.getADDERColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new ADDERGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("NOR", NORGate.getNORColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new NORGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("EQU", EqualGate.getEqualColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new EqualGate(null, null, bitSize.getValue()));
			}
		});
		y++;

		addLbl("Seq. Gates");
		addBtn("CLO", ClockGate.getCLOCKColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new ClockGate(null, null));
			}
		});
		addBtn("DL", DLatchGate.getDColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new DLatchGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("DFF", DFlipFlopGate.getDColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new DFlipFlopGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("SRL", SRLatchGate.getSRColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new SRLatchGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("PULS", PulserGate.getPULSERColor(), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new PulserGate(null, null));
			}
		});
		addBtn("REG", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addForm(new RegistersCreationForm(st, bitSize.getValue()));
			}
		});

		addLbl("Misc. Gates");
		addBtn("TOG", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new ToggleGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("LED", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new LEDGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("IN", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new SegmentInputGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("OUT", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new SegmentDisplayGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("RERO", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new RerouteGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("EXT", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new ExtendGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("COUN", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new CounterGate(null, null, Math.max(2, bitSize.getValue())));
			}
		});
		addBtn("DECO", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int size = Math.max(2, Math.min((int) (Math.log(bitSize.getValue()) / Math.log(2)), 16));
				if (size == 2) {
					size = 1;
				} else if (size == 4) {
					size = 2;
				} else if (size == 8) {
					size = 3;
				} else if (size == 16) {
					size = 4;
				}
				addGate(new DecoderGate(null, null, size));
			}
		});
		addBtn("SPLI", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new SplitGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("COMB", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new CombineGate(null, null, bitSize.getValue()));
			}
		});
		addBtn("TABL", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addForm(new TableCreationForm(st));
			}
		});
		addBtn("MULT", Color.LIGHT_GRAY, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGate(new MultiplexerGate(null, null, bitSize.getValue()));
			}
		});
	}

	private NumberField bitSize;

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		panel.mouseWheelMoved(-e.getWheelRotation());
	}

	public void toggleTurboMode() {
		turboMode = !turboMode;
	}

	private boolean turboMode = false;

	@Override
	public void render(GraphicsPlus g) {
		sidePanel.setEnabled(true);
		if (panel.getInputState().getType().equals("PLACE_STATE")) {
			sidePanel.setBorder(Color.GREEN);
		} else if (panel.isRunning()) {
			sidePanel.setBorder(Color.RED);
			sidePanel.setEnabled(false);
		} else {
			sidePanel.setBorder(Color.WHITE);
		}
		super.render(g);
	}

	@Override
	public void update() {
		if (getGame().getTicks() % 100 ==0)
		dataLabel.setText("UPS: " + getGame().getLoop().getThread().getUPS() + " | " + "FPS: " + getGame().getLoop().getThread().getFPS());

		if (getForms().size() == 1) {
			Point move = new Point(0, 0);

			if (keyPressed(KeyEvent.VK_UP)) {
				move = move.add(new Point(0, 1));
			}
			if (keyPressed(KeyEvent.VK_DOWN)) {
				move = move.add(new Point(0, -1));
			}
			if (keyPressed(KeyEvent.VK_LEFT)) {
				move = move.add(new Point(1, 0));
			}
			if (keyPressed(KeyEvent.VK_RIGHT)) {
				move = move.add(new Point(-1, 0));
			}

			if (move.magnitude() > 0.01f) {
				move = move.normalize().times(4);
				panel.shift(move);
			}

			if (keyPressed(KeyEvent.VK_DELETE) || keyPressed(KeyEvent.VK_BACK_SPACE)) {
				panel.delete();
			}

			if (keyPressed(KeyEvent.VK_ESCAPE)) {
				panel.escape();
			}

			if (keyPressed(KeyEvent.VK_Z)) {
				panel.zoom(0.3f);
			} else if (keyPressed(KeyEvent.VK_X)) {
				panel.zoom(-0.3f);
			}

			int cap = NORMAL_UPU;
			if (turboMode && panel.isRunning()) {
				cap = TURBO_UPU;
			}

			for (int i = 0; i < cap; i++) {
				panel.update();
			}
		}
	}

	public int getMAX_BIT_SIZE() {
		return MAX_BIT_SIZE;
	}

	public void setMAX_BIT_SIZE(int mAX_BIT_SIZE) {
		MAX_BIT_SIZE = mAX_BIT_SIZE;
		bitSize.setMaximum(MAX_BIT_SIZE);
	}

	public int getCLOCK_SPEED() {
		return CLOCK_SPEED;
	}

	public void setCLOCK_SPEED(int cLOCK_SPEED) {
		CLOCK_SPEED = cLOCK_SPEED;
	}

	public int getNORMAL_UPU() {
		return NORMAL_UPU;
	}

	public void setNORMAL_UPU(int nORMAL_UPU) {
		NORMAL_UPU = nORMAL_UPU;
	}

	public int getTURBO_UPU() {
		return TURBO_UPU;
	}

	public void setTURBO_UPU(int tURBO_UPU) {
		TURBO_UPU = tURBO_UPU;
	}
}
