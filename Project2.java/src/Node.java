/*import lombok.Data;
import lombok.NonNull;
@Data
public class Node<T extends Comparable<T>> {

    @NonNull
    private T data;
    private int height = 1;
    private Node<T> leftChild;
    private Node<T> rightChild;

}*/
public class Node<T extends Comparable<T>> {


    private T data;
    private int height = 1;
    private Node<T> leftChild;
    private Node<T> rightChild;
    public Node(T data) {
        this.data = data;

    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public T getData() {
        return data;
    }
   }



