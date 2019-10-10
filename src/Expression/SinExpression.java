/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */

package Expression;

public class SinExpression implements Expression {
    private Expression number;

    public SinExpression(Expression expr) {
        number = expr;
    }

    @Override
    public Double interpret() {
        return Math.sin(number.interpret());
    }

}
