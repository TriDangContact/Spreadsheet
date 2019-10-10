/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

public class CellData implements PropertyChangeListener, CellDataMemento {
    private PropertyChangeSupport propertyChangeSupport;
    private String label;
    private String result;
    private String equation;
    private Boolean hasCircularReference;
    private List<CellData> observersList;
    private List<CellData> referencesList;
    private CellData cellDataContents;

    public CellData(String label, String result, String equation) {
        propertyChangeSupport = new PropertyChangeSupport(this);
        propertyChangeSupport.addPropertyChangeListener(this);
        this.label = label;
        this.result = result;
        this.equation = equation;
        this.observersList = new LinkedList<>();
        this.referencesList = new LinkedList<>();
        this.hasCircularReference = false;
    }

    public String getLabel() { return label; }
    public String getResult() { return result; }
    public String getEquation() { return equation; }
    public List<CellData> getReferencesList() { return referencesList; }
    public List<CellData> getObserversList() { return observersList; }
    public Boolean hasCircularReference() { return hasCircularReference; }

    public void setResult(String result) {
        String oldValue = this.result;
        this.result = result;
        propertyChangeSupport.firePropertyChange("result", oldValue, result);
    }

    public void setEquation(String equation) {
        String oldValue = this.equation;
        this.equation = equation;
        propertyChangeSupport.firePropertyChange("equation", oldValue, equation);
    }

    public void setHasCircularReference(Boolean ref) {
        this.hasCircularReference = ref;
    }

    public void evaluate() {
        EquationEvaluator evaluator = new EquationEvaluator();
        setResult(evaluator.processInput(getEquation()));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String eventName = event.getPropertyName();
        switch (eventName) {
            case "result":
                notifyObservers();
                break;
            case "equation":
                notifyObservers();
                break;
        }
    }

    public void addObserver(CellData cellData) {
        if (!observersList.contains(cellData)) {
            observersList.add(cellData);
        }
    }

    public CellData removeObserver(String label) {
        for (CellData cellData : this.observersList) {
            if (cellData.getLabel().equals(label)) {
                observersList.remove(cellData);
                return cellData;
            }
        }
        return null;
    }

    public void registerSelfAsObserver() {
        for (CellData cellData: referencesList) {
            cellData.addObserver(this);
        }
    }

    public void notifyObservers() {
        for (CellData cellData: observersList) {
            cellData.evaluate();
        }
    }

    // PART OF UNDO FEATURE
    public void setState(CellData cellData) {
        cellDataContents = cellData;
    }

    public CellData getState() {
        return cellDataContents;
    }
    public CellDataMemento save() {
        CellDataMemento content = null;
        try {
            content = (CellDataMemento) this.clone();
        } catch (CloneNotSupportedException notReachable) {
            return content;
        }
        return content;
    }

    public CellData restore(CellDataMemento cellDataMemento) {
        CellData newData = (CellData) cellDataMemento;
        this.label = newData.getLabel();
        this.result = newData.getResult();
        this.equation = newData.getEquation();
        this.observersList = newData.getObserversList();
        this.referencesList = newData.getReferencesList();
        this.hasCircularReference = newData.hasCircularReference();
        this.cellDataContents = newData.getState();
        return newData;
    }

}
