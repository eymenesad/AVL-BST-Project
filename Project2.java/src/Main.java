import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {


        File file = new File(args[0]);
        Scanner sc = new Scanner(file);
        File BSToutput = new File(args[1]+"_bst.txt");
        FileWriter bstWriter = new FileWriter(BSToutput);
        File AVLoutput = new File(args[1]+"_avl.txt");
        FileWriter avlWriter = new FileWriter(AVLoutput);

        Tree<String> bst = new BST<>(bstWriter);
        Tree<String> avl = new AVLTree<>(avlWriter);

        String first = sc.nextLine();
        bst.BinarySearch(first.split(" ")[0]);
        avl.BinarySearch(first.split(" ")[0]);


        while (sc.hasNextLine()) {
            String[] tokens = sc.nextLine().split(" ");
            if (tokens[0].equals("ADDNODE")) {
                bst.add(tokens[1]);
                avl.add(tokens[1]);
            }
            else if (tokens[0].equals("DELETE")){
                bst.delete(tokens[1]);
                avl.delete(tokens[1]);
            }
            else if (tokens[0].equals(("SEND"))){
                bst.sendMes(tokens[1],tokens[2]);
                avl.sendMes(tokens[1],tokens[2]);
            }
            else{
                continue;
            }
        }
        sc.close();
        bstWriter.close();
        avlWriter.close();


    }
}
