/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */

import State.EquationState;
import State.ResultState;

import javax.swing.*;
import java.awt.*;
import java.util.EmptyStackException;

public class SpreadsheetGUI {
    private static final String RESULT_STATE_TITLE = "Switch to Equation View";
    private static final String EQUATION_STATE_TITLE = "Switch to Value View";

    private JFrame frame;
    private JButton viewSwitchBtn;
    private JButton undoBtn;
    private JScrollPane scrollPane;
    private JTable table;
    JTextField inputField1;
    JTextField inputField2;
    JRadioButton radioButton1;
    JRadioButton radioButton2;
    JButton doneBtn;
    private Spreadsheet spreadsheet;
    private String[] tableColumns;

    public SpreadsheetGUI() {
        initialize();
        addComponents();
    }

    private void initialize() {
        frame = new JFrame("Spreadsheet");
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(9, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addComponents() {
        GridLayout layout = new GridLayout();
        layout.setVgap(20);
        layout.setVgap(20);
        layout.setVgap(20);
        layout.setVgap(20);
        layout.setVgap(20);
        layout.setVgap(20);

        viewSwitchBtn = new JButton(RESULT_STATE_TITLE);
        viewSwitchBtn.addActionListener( event -> {
            switchState();
            refreshTable();
        });

        undoBtn = new JButton("Undo");
        undoBtn.addActionListener( event -> undo());

        //generate the cells based on the data we have
        spreadsheet = Spreadsheet.instance();
        tableColumns = new String[spreadsheet.size()];

        for (int index = 0; index<spreadsheet.size(); index++) {
            tableColumns[index] = spreadsheet.getLabel(index);
        }

        table = new JTable(spreadsheet.createEmptyRows(1, tableColumns.length), tableColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scrollPane = new JScrollPane(table);

        inputField1 = new JTextField();
        inputField2 = new JTextField();

        radioButton1 = new JRadioButton("Input Value");
        radioButton1.setSelected(true);
        radioButton2 = new JRadioButton("Input Equation");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);

        doneBtn = new JButton("Done");
        doneBtn.addActionListener(event -> done());

        frame.add(viewSwitchBtn);
        frame.add(undoBtn);
        frame.add(scrollPane);
        frame.add(new JPanel());
        frame.add(new JLabel("Input:"));
        frame.add(inputField1);
        frame.add(new JLabel("At Cell:"));
        frame.add(inputField2);
        frame.add(doneBtn);

        frame.setVisible(true);
    }

    private void undo() {
        try {
            CellData restored = (CellData) spreadsheet.popHistory();
            int index = spreadsheet.indexOf(restored.getLabel());
            spreadsheet.getArray()[index] = restored;
            refreshCell(index);
        } catch (EmptyStackException exception) {
            inputField1.setText("No more history to Undo");
        }
    }

    // we have to check for valid inputs, and then evaluate them and refresh the cells
    private void done() {
        String inputValue = inputField1.getText();
        String cellLocation = inputField2.getText();
        EquationEvaluator evaluator = new EquationEvaluator();
        int index = spreadsheet.indexOf(cellLocation);
        if (index >= 0) {
            if (evaluator.isValidEquation(inputValue)) {
                if (spreadsheet.getState() instanceof ResultState) {
                    spreadsheet.setResult(index, inputValue);
                    spreadsheet.setEquation(index, inputValue);
                } else {
                    spreadsheet.setEquation(index, inputValue);
                    if (!spreadsheet.isCircularReference(index)) {
                        spreadsheet.evaluate(index);
                    } else {
                        spreadsheet.setResult(index, "ERROR: Circular Reference");
                    }
                }
                inputField1.setText("");
                inputField2.setText("");
                refreshCell(index);
            } else {
                inputField1.setText("Please enter valid value");
            }
        } else{
            inputField2.setText("Please enter valid cell");
        }
    }

    private void switchState() {
        if (spreadsheet.getState() instanceof ResultState) {
            viewSwitchBtn.setText(EQUATION_STATE_TITLE);
            spreadsheet.switchState();
        } else if (spreadsheet.getState() instanceof EquationState) {
            viewSwitchBtn.setText(RESULT_STATE_TITLE);
            spreadsheet.switchState();
        }
    }

    // we need to get the list of observers for cell that was changed, and refresh the cell for those observers
    private void refreshCell(int index) {
        CellData cellData = spreadsheet.getArray()[index];
        Object[] observers = cellData.getObserversList().toArray();

        if (spreadsheet.getState() instanceof ResultState) {
            table.setValueAt(spreadsheet.getResult(index), 0, index);
            for (Object obj : observers) {
                CellData cell = (CellData) obj;
                int observerIndex = spreadsheet.indexOf(cell.getLabel());
                table.setValueAt(spreadsheet.getResult(observerIndex), 0, observerIndex);
            }
        } else {
            table.setValueAt(spreadsheet.getEquation(index), 0, index);
            for (Object obj : observers) {
                CellData cell = (CellData) obj;
                int observerIndex = spreadsheet.indexOf(cell.getLabel());
                table.setValueAt(spreadsheet.getEquation(observerIndex), 0, observerIndex);
            }
        }
    }
    
    public void refreshTable() {
        if (spreadsheet.getState() instanceof ResultState) {
            for (int index = 0; index < spreadsheet.size(); index++) {
                table.setValueAt(spreadsheet.getResult(index), 0, index);
            }
        } else {
            for (int index = 0; index < spreadsheet.size(); index++) {
                table.setValueAt(spreadsheet.getEquation(index), 0, index);
            }
        }
    }

    public static void main(String [] args) {
        new SpreadsheetGUI();
    }


}





