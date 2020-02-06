package gspd.ispd.util.structures;

import java.util.Stack;

/**
 * Stack with fixed size. When it overflows its maximum length, the
 * oldest element is removed in order to maintain its maximum length
 *
 * @param <E> the type of elements to be stacked
 */
public class FixedStack<E> extends Stack<E> {

    private int maxLength;

    public FixedStack(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public E push(E item) {
        if (size() >= maxLength) {
            remove(0);
        }
        return super.push(item);
    }

    @Override
    public synchronized E pop() {
        if (size() == 0)
            return null;
        else
            return super.pop();
    }
}
