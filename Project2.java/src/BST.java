import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> implements Tree<T>{
    public FileWriter bstWriter;
    BST(FileWriter bstWriter){
        this.bstWriter = bstWriter;
    }
    private Node<T> root;


    public void BinarySearch(T data){
        root = new Node<T>(data);
    }



    public Tree<T> add(T data) throws IOException {
        root = add(data, root);
        return this;
    }
    private Node<T> add(T data, Node<T> node) throws IOException {
        if (node == null) {
            return new Node<>(data);
        }
        bstWriter.write(node.getData() + ": New node being added with IP:" + data + "\n");
        if (data.compareTo(node.getData()) < 0) {
            node.setLeftChild(add(data, node.getLeftChild()));

        } else if (data.compareTo(node.getData()) > 0) {
            node.setRightChild(add(data, node.getRightChild()));
        }

        return node;
    }
    @Override
    public Tree<T> delete(T data) throws IOException {
        root = delete(data, root, null,true);
        return this;
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
                    bstWriter.write(parrot.getData()+": Leaf Node Deleted: "+node.getData()+ "\n");
                }
                node = null;


            }else{
                if (node.getLeftChild() == null) {
                    if(flag==true){
                        bstWriter.write(parrot.getData() + ": Node with single child Deleted: " + node.getData()+ "\n");

                    }

                    return node.getRightChild();
                } else if (node.getRightChild() == null) {
                    if(flag==true){
                        bstWriter.write(parrot.getData() + ": Node with single child Deleted: " + node.getData()+ "\n");

                    }

                    return node.getLeftChild();
                }
                node.setData(getMin(node.getRightChild()));
                bstWriter.write(parrot.getData()+": Non Leaf Node Deleted; removed: "+data + " replaced: "+node.getData()+ "\n" );
                node.setRightChild(delete(node.getData(), node.getRightChild(),node,false));
            }

        }
        return node;
    }

    @Override
    public boolean isEmpty() {
        return root==null;
    }
    @Override
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

    private T   getMin(Node<T> node) {
        if (node.getLeftChild() != null) {
            return getMin(node.getLeftChild());
        }
        return node.getData();
    }
    public void print() {
        printInOrder(root);
    }

    private void printInOrder(Node<T> node) {
        if (node == null) {
            return;
        }
        printInOrder(node.getLeftChild());
        System.out.println(node);
        printInOrder(node.getRightChild());
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
            bstWriter.write(sender+": Sending message to: " + receiver+ "\n");
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

                    bstWriter.write(senderList.get(i)+": Transmission from: " + senderList.get(i-1)+" receiver: " +receiver + " sender:"+sender+ "\n" );
                }
            }else{
                for (int i = senderList.size()-1; i >=1; i -=2 ){

                    bstWriter.write(senderList.get(i)+": Transmission from: " + senderList.get(i-1)+" receiver: " +receiver + " sender:"+sender+ "\n" );
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
                bstWriter.write(receiverList.get(i) + ": Transmission from: " + receiverList.get(i + 1) + " receiver: " + receiver + " sender:" + sender+ "\n");
            }
            bstWriter.write(receiver + ": Received message from: "+sender+ "\n");
        }
        return root;
    }


}
