package chain.reaction;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.BitSet;

//Creating class which extends Jframe
public class ChainReaction extends JFrame {

    //Initializing variables
    private String action_comm = "";
    private JButton[][] button = new JButton[9][9];
    private int[][] color = new int[9][9];
    private int[][] value = new int[9][9];
    private boolean turn = true;
    private String col = "RED";
    private BitSet Red_bits = new BitSet(81);
    private BitSet Green_bits = new BitSet(81);
    private int count = 0;

    //main function call constructor
    public static void main(String[] args) {
        int rows = 9;
        int cols = 9;
        ChainReaction gt = new ChainReaction(rows, cols);
        gt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gt.pack();
        gt.setVisible(true);
    }

    public ChainReaction() {

    }

    //constructor creates grid
    public ChainReaction(int rows, int cols) {

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                button[i][j] = new JButton("0");
                button[i][j].setActionCommand(Integer.toString(i * 9 + j + 1));
                button[i][j].addActionListener(actionListener);
                button[i][j].setBackground(Color.white);
                pane.add(button[i][j]);
            }
        }
    }
    //All the logic is performed inside mark function
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            //setting action_comm to the button pressed (range 0:80)
            action_comm = actionEvent.getActionCommand();
            mark();
        }
    };

    //marks the current button and calls is_exceeds
    public void mark() {
        int val = Integer.parseInt(action_comm);
        int x = ((val - 1) / 9);
        int y = (val - 1) % 9;
        //checking if its red's turn or green's
        if (turn) {
            if ((color[x][y] == 0) || (color[x][y] == 1)) {
                col = "RED";
                //setting all variables corresponding to current button
                set_color(x, y, col);
                //if current val exceeds the val its supposed to be less than,
                //then recursively set all the neighbours.
                if (is_exceeds(x, y)) {
                    run_rec(x, y);
                }
                turn = !turn;
                count++;
            }
            //System.out.println(action_comm);
        } else if ((color[x][y] == 0) || (color[x][y] == 2)) {
            col = "GREEN";
            set_color(x, y, col);
            if (is_exceeds(x, y)) {
                run_rec(x, y);
            }
            turn = !turn;
            count++;
        }
        //checking who wins!!
        if ((Red_bits.isEmpty()) && (count > 2)) {
            System.out.println("Green Wins");
        } else if ((Green_bits.isEmpty()) && (count > 2)) {
            System.out.println("Red Wins");
        }
    }

    //check if current val exceeds by calling find_num_poss
    public boolean is_exceeds(int x, int y) {
        int no_poss = find_num_poss(x, y);
        boolean ret = false;
        if (value[x][y] > no_poss) {
            ret = true;
        }
        return ret;
    }

    //Set a button to default white and corresponding variables
    public void set_button_white(int x, int y) {
        button[x][y].setText(Integer.toString(value[x][y]));
        button[x][y].setBackground(Color.white);
        value[x][y] = 0;
        color[x][y] = 0;
    }

    //set a button to corresponding color and the variables
    public void set_color(int x_1, int y_1, String col) {
        value[x_1][y_1] += 1;
        button[x_1][y_1].setText(Integer.toString(value[x_1][y_1]));
        if (col.equals("RED")) {
            color[x_1][y_1] = 1;
            Red_bits.set(x_1 * 9 + y_1);
            Green_bits.clear(x_1 * 9 + y_1);
            button[x_1][y_1].setBackground(Color.red);
        } else if (col.equals("GREEN")) {
            color[x_1][y_1] = 2;
            Red_bits.clear(x_1 * 9 + y_1);
            Green_bits.set(x_1 * 9 + y_1);
            button[x_1][y_1].setBackground(Color.green);
        }
    }

    //recursively set all neighbours
    public void run_rec(int x, int y) {
        set_button_white(x, y);
        if (turn) {
            col = "RED";
        } else {
            col = "GREEN";
        }
        if (x == 0) {
            set_data(x + 1, y, col);
        } else if (x == 8) {
            set_data(x - 1, y, col);
        } else {
            set_data(x - 1, y, col);
            set_data(x + 1, y, col);
        }
        if (y == 0) {
            set_data(x, y + 1, col);
        } else if (y == 8) {
            set_data(x, y - 1, col);
        } else {
            set_data(x, y + 1, col);
            set_data(x, y - 1, col);
        }

    }

    //set the corresponding button with data passed and check if it exceeds
    public void set_data(int x_1, int y_1, String col) {
        set_color(x_1, y_1, col);
        //if it exceeds then run recursion on the button
        if (is_exceeds(x_1, y_1)) {
            run_rec(x_1, y_1);
        }
    }

    //find the max possible number for a button
    public int find_num_poss(int x, int y) {
        int no_poss = 0;
        if (x > 0 && x < 8) {
            no_poss += 2;
        } else {
            no_poss += 1;
        }
        if (y > 0 && y < 8) {
            no_poss += 2;
        } else {
            no_poss += 1;
        }
        return no_poss - 1;
    }

}
