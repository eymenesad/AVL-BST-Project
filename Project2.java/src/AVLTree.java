import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AVLTree<T extends Comparable<T>> implements Tree<T> {
    public FileWriter avlWriter;
    AVLTree(FileWriter avlWriter){
        this.avlWriter = avlWriter;
    }
    private Node<T> root;
    public void BinarySearch(T data){
        root = new Node<T>(data);
    }


    @Override
    public Tree<T> add(T data) throws IOException {
        root = add(data, root);
        return this;
    }
    private Node<T> add(T data, Node<T> node) throws IOException {
        if (node == null) {
            return new Node<>(data);
        }
        avlWriter.write(node.getData() + ": New node being added with IP:" + data + "\n");
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftChild(add(data, node.getLeftChild()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightChild(add(data, node.getRightChild()));
        } else {
            return node;
        }
        updateHeight(node);
        return applyRotation(node);
    }
    @Override
    public Tree<T> delete(T data) throws IOException {
        root = delete(data, root, null,true);
        return null;
    }

    private Node<T> delete(T data, Node<T> node, Node<T> parrot,boolean flag) throws IOException {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftChild(delete(data, node.getLeftChild(), node, flag));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightChild(delete(data, node.getRightChild(), node, flag));
        } else {
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                if(flag == true){
                    avlWriter.write(parrot.getData()+": Leaf Node Deleted: "+node.getData()+ "\n");
                }
                node = null;

            }else{
                if (node.getLeftChild() == null) {
                    if(flag==true){
                        avlWriter.write(parrot.getData() + ": Node with single child Deleted: " + node.getData()+ "\n");

                    }

                    return node.getRightChild();
                } else if (node.getRightChild() == null) {
                    if(flag==true){
                        avlWriter.write(parrot.getData() + ": Node with single child Deleted: " + node.getData()+ "\n");

                    }

                    return node.getLeftChild();
                }
                node.setData(getMin(node.getRightChild()));
                avlWriter.write(parrot.getData()+": Non Leaf Node Deleted; removed: "+data + " replaced: "+node.getData()+ "\n" );
                node.setRightChild(delete(node.getData(), node.getRightChild(),node,false));
            }
        }
        if (node != null) {
            updateHeight(node);
        }

        return applyRotation(node);
    }
    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        return getMax(root);
    }

    private T getMax(Node<T> node) {
        if (node.getRightChild() != null) {
            return getMax(node.getRightChild());
        }
        return node.getData();
    }

    @Override
    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        return getMin(root);
    }

    private T getMin(Node<T> node) {
        if (node.getLeftChild() != null) {
            return getMin(node.getLeftChild());
        }
        return node.getData();
    }
    public void print() {
        print(root);
    }

    private void print(Node<T> node) {
        if (node != null) {
            print(node.getLeftChild());
            System.out.println(node);
            print(node.getRightChild());
        }
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    private Node<T> applyRotation(Node<T> node) throws IOException {
        int balance = balance(node);
        if (balance > 1) {
            if (balance(node.getLeftChild()) < 0) {
                node.setLeftChild(rotateLeft(node.getLeftChild()));
                avlWriter.write("Rebalancing: left-right rotation"+"\n");
            }else{
                avlWriter.write("Rebalancing: right rotation"+"\n");
            }
            return rotateRight(node);
        }
        if (balance < -1) {
            if (balance(node.getRightChild()) > 0) {
                node.setRightChild(rotateRight(node.getRightChild()));
                avlWriter.write("Rebalancing: right-left rotation"+"\n");
            }else{
                avlWriter.write("Rebalancing: left rotation"+"\n");
            }
            return rotateLeft(node);
        }
        return node;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeftChild();
        Node<T> centerNode = leftNode.getRightChild();
        leftNode.setRightChild(node);
        node.setLeftChild(centerNode);
        updateHeight(node);
        updateHeight(leftNode);
        return leftNode;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRightChild();
        Node<T> centerNode = rightNode.getLeftChild();
        rightNode.setLeftChild(node);
        node.setRightChild(centerNode);
        updateHeight(node);
        updateHeight(rightNode);
        return rightNode;
    }

    private void updateHeight(Node<T> node) {
        int maxHeight = Math.max(
                height(node.getLeftChild()),
                height(node.getRightChild())
        );
        node.setHeight(maxHeight + 1);
    }

    private int balance(Node<T> node) {
        return node != null ? height(node.getLeftChild()) - height(node.getRightChild()) : 0;
    }

    private int height(Node<T> node) {
        return node != null ? node.getHeight() : 0;
    }
    public Tree<T> sendMes(T senderData, T receiverData) throws IOException {

        sendMes(root,senderData,receiverData);
        return this;
    }
    private Node<T> sendMes(Node<T> root, T sender, T receiver) throws IOException {
        if(root == null){
            return null;

        }
        if (root.getData().compareTo(sender)> 0 && root.getData().compareTo(receiver)> 0 ){
            return sendMes(root.getLeftChild(),sender,receiver);
        }if (root.getData().compareTo(sender)< 0 && root.getData().compareTo(receiver)< 0 ){
            return sendMes(root.getRightChild(),sender,receiver);
        }

        if ((root.getData().compareTo(sender)>= 0 && root.getData().compareTo(receiver)<= 0)
                || (root.getData().compareTo(sender)<= 0 && root.getData().compareTo(receiver)>= 0)){
            ArrayList<String> senderList = new ArrayList<>();
            ArrayList<String> receiverList = new ArrayList<>();
            Node<T> temp = root;
            avlWriter.write(sender+": Sending message to: " + receiver+ "\n");
            while(!temp.getData().equals(sender)){
                if(temp.getData().compareTo(sender)>0){
                    senderList.add(temp.getLeftChild().getData().toString());
                    senderList.add(temp.getData().toString());
                    temp = temp.getLeftChild();
                }else if(temp.getData().compareTo(sender)<0){
                    senderList.add(temp.getRightChild().getData().toString());
                    senderList.add(temp.getData().toString());
                    temp = temp.getRightChild();
                }

            }
            if (receiver.equals(root.getData())){
                for (int i = senderList.size()-1; i >=2; i -=2 ){

                    avlWriter.write(senderList.get(i)+": Transmission from: " + senderList.get(i-1)+" receiver: " +receiver + " sender:"+sender+ "\n" );
                }
            }else{
                for (int i = senderList.size()-1; i >=1; i -=2 ){

                    avlWriter.write(senderList.get(i)+": Transmission from: " + senderList.get(i-1)+" receiver: " +receiver + " sender:"+sender+ "\n" );
                }
            }

            temp = root;
            while(!temp.getData().equals(receiver) ){
                if(temp.getData().compareTo(receiver)>0){
                    receiverList.add(temp.getLeftChild().getData().toString());
                    receiverList.add(temp.getData().toString());
                    //System.out.println(temp.getLeftChild().getData() + ": Transmission from: "+  temp.getData() + " receiver: "+ receiver + " sender:" + sender);
                    temp = temp.getLeftChild();
                }else if(temp.getData().compareTo(receiver)<0){
                    receiverList.add(temp.getRightChild().getData().toString());
                    receiverList.add(temp.getData().toString());
                    //System.out.println(temp.getRightChild().getData() + ": Transmission from: "+  temp.getData() + " receiver: "+ receiver + " sender:" + sender);
                    temp = temp.getRightChild();
                }
            }
            for(int i = 0; i<receiverList.size()-2; i+=2){
                avlWriter.write(receiverList.get(i) + ": Transmission from: " + receiverList.get(i + 1) + " receiver: " + receiver + " sender:" + sender+ "\n");
            }
            avlWriter.write(receiver + ": Received message from: "+sender+ "\n");
        }
        return root;
    }
}
