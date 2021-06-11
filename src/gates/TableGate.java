package gates;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import circuits.CircuitPanel;
import circuits.CircuitRunningState;
import circuits.Port;
import gui.Button;
import gui.Form;
import gui.Label;
import util.Point;
import util.Rectangle;
import util.Util;

public class TableGate extends Gate {
	private int inSize;
	private int outSize;

	public TableGate(CircuitPanel panel, Point position, int inSize, int outSize) {
		super(panel, new Rectangle(position, new Point(200, 200)), Color.DARK_GRAY, inSize, "TABLE");

		Label lbl = new Label(this, new Rectangle(20, 0, getWidth() - 40, getHeight()), "");
		lbl.setBackground(Color.DARK_GRAY);
		lbl.setFontSize(40);

		lbl.setText("TABLE");
		this.addComponent(lbl);

		this.setBordered(true);
		this.setBorder(Color.GRAY);

		this.inSize = inSize;
		this.outSize = outSize;
		
		Button editButton = new Button(this, new Rectangle(160, 160, 30, 30), "E");
		editButton.setBackground(Color.LIGHT_GRAY);
		editButton.addEventListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Form blank = new Form(new Rectangle(0, 0, 1, 1), getPanel().getState());
				getPanel().getState().addForm(blank);

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
						return ".tta";
					}

				});
				int userSelection = fileChooser.showOpenDialog(getPanel().getState().getGame().getFrame());

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					try {
						cases = parse(CircuitRunningState.readFileAsString(fileToSave.getAbsolutePath()));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				getPanel().getState().removeForm(blank);
			}
		});
		this.addComponent(editButton);

		super.addPort(new Port(this, new Point(100, 170), Port.SINGLE_INPUT, inSize));
		super.addPort(new Port(this, new Point(100, 30), Port.OUTPUT, outSize));
		this.setNeedsDisable(false);
		this.cases = new ArrayList<int[]>();
	}

	private List<int[]> cases;

	private static boolean[] toBinary(int[] arr, int startIndex) {
		boolean[] bin = new boolean[arr.length - startIndex];
		for (int i = startIndex; i < arr.length; i++) {
			if (arr[i] == 1) {
				bin[i - startIndex] = true;
			}
		}

		return bin;
	}

	private static boolean matches(int[] arr, boolean[] data, int endIndex) {
		for (int i = 0; i < endIndex; i++) {
			if (arr[i] != 2) {
				if (arr[i] == 0 && data[i]) {
					return false;
				}

				if (arr[i] == 1 && !data[i]) {
					return false;
				}
			}
		}

		return true;
	}

	public static List<int[]> parse(String str) {
		String formatted = "";
		for (int i = 0; i < str.length(); i++) {
			if ("012-".contains(str.substring(i, i + 1))) {
				formatted += (str.substring(i, i + 1));
			}
		}
		String[] lines = formatted.split("-");
		List<int[]> result = new ArrayList<int[]>();
		for (String line : lines) {
			result.add(Util.toIntArray(line));
		}

		return result;
	}

	@Override
	public boolean[] getOutputState(int index) {
		boolean[] input = Util.reverse(getInputStates(0));

		for (int[] arr : cases) {
			if (matches(arr, input, inSize)) {
				return toBinary(arr, inSize);
			}
		}

		return new boolean[outSize];
	}

	protected String saveContents() {
		return outSize + "," + Util.toString(cases);
	}

	public void setData(List<int[]> cases) {
		this.cases = cases;
	}

	public static Gate loadGate(CircuitPanel panel, Point location, String other, int size) {
		String[] data = other.split(",");
		TableGate gt = new TableGate(panel, location, size, Integer.parseInt(data[0]));
		if (data.length == 2) {
			gt.setData(parse(data[1]));
		}
		return gt;
	}
}
