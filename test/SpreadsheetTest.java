/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpreadsheetTest {
    Spreadsheet instance;
    private CellData[] dataArray;
    String testStr1 = "3";
    String testStr2 = "$A 3 +";
    String testStr3 = "$A $B +";
    String expectedStr1 = "3";
    String expectedStr2 = "3 3 +";
    String expectedStr3 = "3 3 3 + +";

    private void initialize() {
        instance = Spreadsheet.instance();
        instance.setColumns(3);
        dataArray = instance.getArray();
        dataArray[0].setEquation(testStr1);
        dataArray[1].setEquation(testStr2);
        dataArray[2].setEquation(testStr3);
    }

    @Test
    void testCircularReference() {
        initialize();
        assertFalse(instance.isCircularReference(0));
        assertFalse(instance.isCircularReference(1));
        assertFalse(instance.isCircularReference(2));

        // circular reference
        dataArray[3].setEquation(dataArray[4].getLabel());
        dataArray[4].setEquation(dataArray[3].getLabel());

        assertTrue(instance.isCircularReference(3));
    }

    @Test
    void testRegisterObserver() {
        initialize();
        assertFalse(instance.isCircularReference(0));
        assertFalse(instance.isCircularReference(1));
        assertFalse(instance.isCircularReference(2));
        assertTrue(dataArray[0].getObserversList().contains(dataArray[1]));
        assertTrue(dataArray[0].getObserversList().contains(dataArray[2]));
        assertTrue(dataArray[1].getObserversList().contains(dataArray[2]));
    }

    @Test
    void testInfixtoPostfix() {
        String testStr4 = "3 + 2";
        String expectedStr4 = "3 2 +";
        testStr4 = InfixtoPostfix.infixToPostFix(testStr4);
        assertEquals(expectedStr4, testStr4);

        String testStr5 = "3 * 2 + 4";
        String expectedStr5 = "3 2 * 4 +";
        testStr5 = InfixtoPostfix.infixToPostFix(testStr5);
        assertEquals(expectedStr5, testStr5);
    }

    @Test
    void testCellReferenceUnwrapper() {
        initialize();
        String parsedStr1 = CellReferenceUnwrapper.parse(testStr1);
        String parsedStr2 = CellReferenceUnwrapper.parse(testStr2);
        String parsedStr3 = CellReferenceUnwrapper.parse(testStr3);

        assertEquals(expectedStr1, parsedStr1);
        assertEquals(expectedStr2, parsedStr2);
        assertEquals(expectedStr3, parsedStr3);

        EquationEvaluator evaluator = new EquationEvaluator();
        assertEquals("3.0", evaluator.processInput(testStr1));
        assertEquals("6.0", evaluator.processInput(testStr2));
        assertEquals("9.0", evaluator.processInput(testStr3));
    }

    @Test
    void testIsValidEquation() {
        EquationEvaluator evaluator = new EquationEvaluator();
        String validString1 = "$A";
        String validString2 = "3.0";
        String validString3 = "-3.0";
        String validString4 = "0.50";
        String validString5 = "5 sin";
        String validString6 = "30 log";
        String validString7 = "$A 5.23112 312 $BC log 3 sin 3.0 +";
        String invalidString1 = "$aaaaa $A 5.23112 312 $BC log 3 sin 3.0 +";
        String invalidString2 = "$bbbbb $A 5.23112 312 $BC log 3 sin 3.0 +";
        String invalidString3 = "$$$ $A 5.23112 312 $BC log 3 sin 3.0 +";
        String invalidString4 = "0..0 $A 5.23112 312 $BC log 3 sin 3.0 +";
        assertTrue(evaluator.isValidEquation(validString1));
        assertTrue(evaluator.isValidEquation(validString2));
        assertTrue(evaluator.isValidEquation(validString3));
        assertTrue(evaluator.isValidEquation(validString4));
        assertTrue(evaluator.isValidEquation(validString5));
        assertTrue(evaluator.isValidEquation(validString6));
        assertTrue(evaluator.isValidEquation(validString7));
        assertFalse(evaluator.isValidEquation(invalidString1));
        assertFalse(evaluator.isValidEquation(invalidString2));
        assertFalse(evaluator.isValidEquation(invalidString3));
        assertFalse(evaluator.isValidEquation(invalidString4));
    }

    @Test
    void testEquationEvaluate() {
        EquationEvaluator evalator = new EquationEvaluator();
        assertEquals("3.0", evalator.processInput("3.0"));
        assertEquals("3.0", evalator.processInput("3"));
        assertEquals("-3.0", evalator.processInput("-3.0"));
        assertEquals("-3.0", evalator.processInput("-3"));
    }

    @Test
    void testSaveHistory() {
        initialize();
        instance.addHistory(dataArray[0].save());
        instance.addHistory(dataArray[1].save());
        instance.addHistory(dataArray[2].save());

        CellData restored2 = (CellData) instance.popHistory();
        CellData restored1 = (CellData) instance.popHistory();
        CellData restored0 = (CellData) instance.popHistory();

        assertEquals(dataArray[0].getEquation(), restored0.getEquation());
        assertEquals(dataArray[1].getEquation(), restored1.getEquation());
        assertEquals(dataArray[2].getEquation(), restored2.getEquation());

        assertEquals(dataArray[0].getResult(), restored0.getResult());
        assertEquals(dataArray[1].getResult(), restored1.getResult());
        assertEquals(dataArray[2].getResult(), restored2.getResult());
    }

}
