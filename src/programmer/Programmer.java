package programmer;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileFilter;

public class Programmer {
	public static void main(String[] args) {
		Table table = new Table();
		JFrame frame = createFrame();
		JScrollPane pane = new JScrollPane(table);
		JPanel pnl = new JPanel();
		JSplitPane panel = new JSplitPane();
		JLabel lbl = new JLabel("File Location: ");
		lbl.setFont(new Font("Arial", Font.PLAIN, 15));

		JButton clr = new JButton("Clear");
		pnl.add(clr);
		clr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < table.getRowCount(); i++) {
					table.getTable().setValueAt("", i, 1);
				}
			}
		});

		JButton save = new JButton("Save");
		pnl.add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					BufferedWriter writer = new BufferedWriter(
							new FileWriter(lbl.getText().replaceAll("File Location: ", "")));
					String save = "";
					System.out.println(data0);

					for (int i = 0; i < table.getRowCount(); i++) {
						String str = table.get(i);
						String dat = "";
						if (!str.equals("")) {
							String[] data1 = str.split(" ");
							if (data1.length == 2) {
								dat += getBinary(data1[0]);
								dat += toBinary(Long.parseLong(data1[1]), 11);
							} else {
								if ("0123456789".contains(data1[0].substring(0, 1))) {
									dat += toBinary(Long.parseLong(data1[0]), 16);
								} else {
									dat += getBinary(data1[0]) + "00000000000";
								}
							}
						} else {
							dat = "0000000000000000";
						}
						data[i] = toDecimal(dat);
					}

					for (int i = 0; i < data.length; i++) {
						save += data[i] + "-";
					}
					writer.write(before + save + after);
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		JButton refresh = new JButton("Refresh");
		pnl.add(refresh);
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					data0 = readFileAsString(lbl.getText().replaceAll("File Location: ", ""));
					String[] data1 = data0.split("REGISTERARRAY,16,11,");
					before = data1[0] + "REGISTERARRAY,16,11,";
					String data2 = data1[1].split("g:")[0];
					for (int i = 0; i < 200; i++) {
						data[i] = Long.parseLong(data2.split("-")[i]);
						String str = toBinary(data[i], 16);
						String instruction = getInstruction(str.substring(0, 5));
						long param = toDecimal(str.substring(5, 16));
						if (param == 0) {
							table.insertNewRow(instruction);
						} else if (instruction.equals("")) {
							table.insertNewRow("" + param);
						} else {
							table.insertNewRow(instruction + " " + param);
						}
					}
					after = "";
					for (int i = 1; i < data1[1].split("g:").length; i++) {
						after += "g:" + data1[1].split("g:")[i];
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton load = new JButton("Load");
		pnl.add(load);
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
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
				int userSelection = fileChooser.showOpenDialog(frame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					try {
						lbl.setText("File Location: " + fileToSave.getAbsolutePath());
						data0 = readFileAsString(fileToSave.getAbsolutePath());
						String[] data1 = data0.split("REGISTERARRAY,16,11,");
						before = data1[0] + "REGISTERARRAY,16,11,";
						String data2 = data1[1].split("g:")[0];
						for (int i = 0; i < 200; i++) {
							data[i] = Long.parseLong(data2.split("-")[i]);
							String str = toBinary(data[i], 16);
							String instruction = getInstruction(str.substring(0, 5));
							long param = toDecimal(str.substring(5, 16));
							if (param == 0) {
								table.insertNewRow(instruction);
							} else if (instruction.equals("")) {
								table.insertNewRow("" + param);
							} else {
								table.insertNewRow(instruction + " " + param);
							}
						}
						after = "";
						for (int i = 1; i < data1[1].split("g:").length; i++) {
							after += "g:" + data1[1].split("g:")[i];
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		frame.add(panel);
		panel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.setRightComponent(pane);
		panel.setLeftComponent(pnl);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		pnl.add(lbl);
	}

	private static String data0 = "";

	private static String getInstruction(String bin) {
		switch (bin) {
		case ("00001"):
			return "LDA";
		case ("00010"):
			return "ADD";
		case ("00011"):
			return "SUB";
		case ("00100"):
			return "STA";
		case ("00101"):
			return "SC";
		case ("00110"):
			return "JMP";
		case ("00111"):
			return "JZ";
		case ("01000"):
			return "JC";
		case ("01001"):
			return "LDC";
		case ("01010"):
			return "STC";
		case ("01110"):
			return "OUT";
		case ("11111"):
			return "CLR";
		}

		return "";
	}

	private static String getBinary(String inst) {
		switch (inst) {
		case ("LDA"):
			return "00001";
		case ("ADD"):
			return "00010";
		case ("SUB"):
			return "00011";
		case ("STA"):
			return "00100";
		case ("SC"):
			return "00101";
		case ("JMP"):
			return "00110";
		case ("JZ"):
			return "00111";
		case ("JC"):
			return "01000";
		case ("LDC"):
			return "01001";
		case ("STC"):
			return "01010";
		case ("OUT"):
			return "01110";
		case ("CLR"):
			return "11111";
		}

		return "000000";
	}

	private static long[] data = new long[2048];

	public static int getDigit(long num, int base, int index) {
		long basePow = (long) Math.pow(base, index);
		return (int) ((num % (base * basePow)) / basePow);
	}

	public static int[] getDigits(long num, int base) {
		int[] digits = new int[numDigits(num, base)];
		for (int i = 0; i < digits.length; i++) {
			digits[i] = getDigit(num, base, i);
		}
		return digits;
	}

	public static int numDigits(long num, int base) {
		int digs = 0;
		num = Math.abs(num);
		while (num >= base) {
			num /= base;
			digs++;
		}
		return digs + 1;
	}

	public static String toBinary(long num, int size) {
		int[] digits = getDigits(num, 2);
		String str = "";

		for (int i = size - 1; i >= 0; i--) {
			if (i >= digits.length) {
				str += "0";
			} else {
				str += "" + digits[i];
			}
		}
		return str;
	}

	public static long toDecimal(String bin) {
		long num = 0;
		for (int i = bin.length() - 1; i >= 0; i--) {
			if (bin.substring(i, i + 1).equals("1"))
				num += (long) (Math.pow(2, bin.length() - 1 - i));
		}
		return num;
	}

	private static String before = "";
	private static String after = "";

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}

	private static JFrame createFrame() {
		JFrame frame = new JFrame("16 Bit Programmer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(600, 800));
		return frame;
	}
}
