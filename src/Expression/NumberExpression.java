/*
 * @author Tri Dang
 * CS635
 * RedID: 814009034
 * Assignment 2
 */
package Expression;

public class NumberExpression implements Expression {
    private Double number;

    public NumberExpression(String str) {
        this.number = Double.parseDouble(str);
    }

    public NumberExpression(Double number) {
        this.number = number;
    }

    @Override
    public Double interpret() {
        return number;
    }
}
