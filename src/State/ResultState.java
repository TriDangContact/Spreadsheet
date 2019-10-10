/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */
package State;

public class ResultState implements SpreadsheetState {

    @Override
    public void switchState(SpreadsheetContext context) { context.setState(new EquationState()); }

    @Override
    public String toString() { return "Result State"; }
}
