/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */

public class CellReferenceUnwrapper {

    public static String parse(String str) {
        try {
            String[] tokens = str.split(" ");
            StringBuilder builder = new StringBuilder();
            for (String token : tokens) {
                if (token.contains("$")) {
                    //get the value out of the reference
                    String unwrappedToken = getEquation(token);
                    builder.append(parse(unwrappedToken));
                } else {
                    builder.append(token);
                }
            }
            return getPostfix(builder.toString());
        } catch (StackOverflowError error) {
            return "Error";
        }
    }

    private static String getPostfix(String str) {
        return InfixtoPostfix.infixToPostFix(str);
    }

    private static String getEquation(String label) {
        Spreadsheet spreadsheet = Spreadsheet.instance();
        return spreadsheet.find(label).getEquation();
    }
}
