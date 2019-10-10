/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */
package Expression;

public class LogBase2Expression implements Expression {
    private Expression number;

    public LogBase2Expression(Expression expr) {
        number = expr;
    }

    @Override
    public Double interpret() {
        return (Math.log(number.interpret()) / Math.log(2));
    }

}
