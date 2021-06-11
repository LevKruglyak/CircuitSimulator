package programmer;
import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Table extends JTable {
	private static final long serialVersionUID = -8868709573777871068L;
	private DefaultCellEditor cellEditor;
    private final DefaultTableModel model;
    public JTextField textField;

    public Table() {
        super(1, 2);
        this.model = (DefaultTableModel) getModel();
        init();
    }

    private void init() {
        initTable();
        initEditorComponent();
        initSelectionListener();
    }

    private void initTable() {
        setTableHeader(null);
        model.removeRow(0);
        columnModel.getColumn(0).setMaxWidth(25);;
    }

    private void initEditorComponent() {
        TableColumn column = getColumnModel().getColumn(1);
        textField = new JTextField();
        this.cellEditor = new DefaultCellEditor(textField);
        cellEditor.setClickCountToStart(1);
        column.setCellEditor(cellEditor);
        textField.setBorder(null);
        textField.setForeground(new Color(0, 100, 250));
    }

    private void initSelectionListener() {
        getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = getSelectedRow();
            if (selectedRow == -1) {
                return;
            }
            startEditingAtRow(selectedRow);
        });
    }
    
    public String get(int index) {
    	return (String) model.getValueAt(index, 1);
    }

    private void startEditingAtRow(int row) {
        if (isCellEditable(row, 1)) {
            editCellAt(row, 1);
            changeSelection(row, 1, false, false);
            textField.requestFocusInWindow();
        }
    }
    
    public void insertNewRow(String code) {
        model.insertRow(model.getRowCount(), new String[]{model.getRowCount()+"",code});
    }
    
    public List<String> getDataAsStringList() {
        @SuppressWarnings({ "rawtypes", "unchecked" })
		Vector<Vector> vectors = model.getDataVector();
        @SuppressWarnings("unchecked")
		List<String> rows = vectors.stream()
                                   .filter(Objects::nonNull)
                                   .map(vector -> vector.stream()
                                                        .findAny()
                                                        .orElse(null))
                                   .filter(Objects::nonNull)
                                   .map(Objects::toString)
                                   .filter(s -> !s.isEmpty())
                                   .collect(Collectors.toList());
        return rows;
    }

	public DefaultTableModel getTable() {
		// TODO Auto-generated method stub
		return model;
	}

}
