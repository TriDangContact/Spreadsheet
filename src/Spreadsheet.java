/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */

import State.ResultState;
import State.SpreadsheetContext;
import State.SpreadsheetState;

import java.util.List;
import java.util.Stack;

public class Spreadsheet {
    private static final int DEFAULT_COLUMNS = 9;

    private static Spreadsheet instance;
    private int columns = DEFAULT_COLUMNS;
    private CellData[] cellDataArray;
    private SpreadsheetContext context;
    private Stack<CellDataMemento> cellDataHistoryStack;

    private Spreadsheet() {
        cellDataArray = new CellData[columns];
        context = new SpreadsheetContext();
        cellDataHistoryStack = new Stack<>();
        int ascii = 'A';
        for (int index = 0; index<cellDataArray.length; index++) {
            int nextAscii = ascii + index;
            char currentChar = (char)nextAscii;
            cellDataArray[index] = new CellData("$" + currentChar, "", "");
        }
    }

    public static synchronized Spreadsheet instance() {
        if (instance == null) {
            instance = new Spreadsheet();
        }
        return instance;
    }

    public void setColumns(int columns) {
        if (columns > 0) {
            this.columns = columns;
        }
    }

    private SpreadsheetContext getContext() { return context; }
    public CellData[] getArray() { return cellDataArray; }
    public Stack<CellDataMemento> getHistoryStack() { return cellDataHistoryStack; }
    public String getLabel(int index) { return cellDataArray[index].getLabel(); }
    public String getResult(int index) { return cellDataArray[index].getResult(); }
    public String getEquation(int index) { return cellDataArray[index].getEquation(); }
    public int size() { return cellDataArray.length; }
    public SpreadsheetState getState() { return getContext().getState(); }
    public void switchState() { getContext().switchState(); }
    public String toString() { return getContext().toString(); }

    public void setResult(int index, String result) {
        CellDataMemento saved = cellDataArray[index].save();
        cellDataArray[index].setResult(result);
        addHistory(saved);
    }

    public void setEquation(int index, String equation) {
        CellDataMemento saved = cellDataArray[index].save();
        cellDataArray[index].setEquation(equation);
        addHistory(saved);
    }

    public void evaluate(int index) {
        cellDataArray[index].evaluate();
    }

    public CellData find(String label) {
        for (CellData cell : cellDataArray) {
            if (cell.getLabel().equals(label)) {
                return cell;
            }
        }
        return null;
    }

    public int indexOf(String label) {
        int index = 0;
        for (CellData cell : cellDataArray) {
            if (cell.getLabel().equals(label)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    //TODO: check for circular referencing
    public boolean isCircularReference(int index) {
        CellData cellData = cellDataArray[index];
        cellData.setHasCircularReference(false);
        try {
            cellData.getReferencesList().clear();
            findReferences(cellData);
            cellData.registerSelfAsObserver();
            return cellData.hasCircularReference();
        } catch (StackOverflowError error) {
            cellData.setHasCircularReference(true);
            return true;
        }
    }

    private void findReferences(CellData cellData) {
        String[] tokens = cellData.getEquation().split(" ");
        for (String token : tokens) {
            if (token.contains("$")) {
                List<CellData> referenceList = cellData.getReferencesList();
                // we check to see if the list already contains the current referenced cell, or itself
                referenceList.add(cellData);
                CellData referencedCellData = instance().find(token);
                if (referenceList.contains(referencedCellData)) {
                    cellData.setHasCircularReference(true);
                    throw new StackOverflowError();
                } else {
                    referenceList.add(referencedCellData);
                    findReferences(referencedCellData);
                }
                // recursive call to circular references of the referenced token
                // once we've done our checking, we can remove the self reference
                referenceList.remove(cellData);
            }
        }
    }

    public String[][] createEmptyRows(int rows, int columns) {
        String[][] tableRows = new String[rows][columns];
        if (getState() instanceof ResultState) {
            for (int index = 0; index<columns; index++) {
                tableRows[0][index] = getResult(index);
            }
        } else {
            for (int index = 0; index<columns; index++) {
                tableRows[0][index] = getEquation(index);
            }
        }
        return tableRows;
    }

    // PART OF UNDO FEATURE
//    private void saveHistory(int index, CellData saved) {
//        if (saved.getState() == null) {
//            System.out.println("Saved State is null");
//        } else {
//            System.out.println("Saved state label: " + saved.getState().getLabel() + " | result: " + saved.getState().getResult() + " | equation: " + saved.getState().getEquation());
//            cellDataArray[index].setState(saved);
//            addHistory(cellDataArray[index].save());
//        }
//    }

    public void addHistory(CellDataMemento state) {
        cellDataHistoryStack.add(state);
    }

    public CellDataMemento popHistory() {
        return cellDataHistoryStack.pop();
    }

}
