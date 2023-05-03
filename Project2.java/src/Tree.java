import java.io.IOException;

public interface Tree<T extends Comparable<T>> {

    Tree<T> add(T data) throws IOException;

    Tree<T> delete(T data) throws IOException;

    boolean isEmpty();

    T getMax();

    T getMin();

    void print();

    Tree<T> sendMes(T senderData, T receiverData) throws IOException;

    void BinarySearch(T data);

}
