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
|<img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot1.jpg?raw=true" width="150"> |<img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot2.jpg?raw=true" width="150">| <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot3.jpg?raw=true" width="150"> |
|<img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot4.jpg?raw=true" width="150">| <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot5.jpg?raw=true" width="150"> | <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot6.jpg?raw=true" width="150">|
|<img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot7.jpg?raw=true" width="150"> | <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot8.jpg?raw=true" width="150">| <img src="https://github.com/TriDangContact/Spreadsheet/blob/master/screenshots/Screenshot9.jpg?raw=true" width="150"> |
