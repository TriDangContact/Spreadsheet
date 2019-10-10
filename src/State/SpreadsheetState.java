/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */
package State;

public interface SpreadsheetState {
    void switchState(SpreadsheetContext context);
    String toString();
}
