/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */
package State;

public class SpreadsheetContext {
    private SpreadsheetState state;

    public SpreadsheetContext() { state = new ResultState(); }

    public void switchState() { state.switchState(this); }
    public void setState(SpreadsheetState state) {
        this.state = state;
    }
    public SpreadsheetState getState() { return state; }
    public String toString() {
        return state.toString();
    }

}
