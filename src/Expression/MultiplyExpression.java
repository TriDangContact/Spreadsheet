/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */

package Expression;

public class MultiplyExpression implements Expression {
    private Expression left;
    private Expression right;

    public MultiplyExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Double interpret() {
        return left.interpret() * right.interpret();
    }
}
