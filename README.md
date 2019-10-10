# Spreadsheet
This is a simple Spreadsheet program developed using Java to demonstrate the usage of multiple Java design patterns such as Interpreter, State, Memento, and Observer. The simple GUI is implemented using Java Swing, mostly intended for testing basic Spreadsheet functions.

## Features
- Allows user to input values and equations into spreadsheet cells
- Equations should be in Postfix Expression
- Operations supported: addition, subtraction, multiplication, division, log, sin
- Equations can also reference another cell in the Spreadsheet
- Can detect circular referencing of cells (ex: a->b b->c c->a)
- Cells should automatically update if a cell it referenced was changed, just like data in reactive programming
- User should be able to undo the previous action.

## Screenshot
| | | |
-------------------------|-------------------------|-------------------------
|<img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot1.png" width="150"> |<img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot2.png" width="150">| <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot3.png" width="150"> |
|<img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot4.png" width="150">| <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot5.png" width="150"> | <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot6.png" width="150">|
|<img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot7.png" width="150"> | <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot8.png" width="150">| <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot9.png" width="150"> |
