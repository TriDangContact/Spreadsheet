/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */
package State;

public class EquationState implements SpreadsheetState {

    @Override
    public void switchState(SpreadsheetContext context) { context.setState(new ResultState()); }

    @Override
    public String toString() { return "Equation State"; }
}
