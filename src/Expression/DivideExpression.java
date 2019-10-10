/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */
package Expression;

public class DivideExpression implements Expression {
    private Expression left;
    private Expression right;

    public DivideExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Double interpret() {
        if (right.interpret() == 0) {
            return 0.0;
        }
        return left.interpret() / right.interpret();
    }
}
