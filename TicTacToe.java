import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TicTacToe extends JFrame implements ActionListener {
    private int xScore, oScore, moveCounter; // store score and move counter help for draw
    private boolean isX; // flag for player
    private JLabel turnLabel, scoreLabel, resultLabel;
    private JButton[][] board;
    private JDialog resultDialog;

    public TicTacToe() {
        super("Tic Tac Toe");
        setSize(CommonConstants.FRAME_SIZE);
        getContentPane().setBackground(CommonConstants.BACKGRAUND_COLOR);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        //int vars
         createResultDialog();
        board = new JButton[3][3];

        //Start from x
        isX = true;
        addGuiComponent();
    }

    private void addGuiComponent() {
        //bar label
        JLabel barLabel = new JLabel();
        barLabel.setBackground(CommonConstants.BAR_COLOR);
        barLabel.setBounds(0,0,CommonConstants.FRAME_SIZE.width, 43);
        barLabel.setOpaque(true);


        //turn Label
        turnLabel = new JLabel(CommonConstants.X_LABEL);//1st player is X
        turnLabel.setHorizontalAlignment(SwingUtilities.CENTER);
        turnLabel.setFont(new Font("Dialog",Font.PLAIN,30));
        turnLabel.setPreferredSize(new Dimension(100,turnLabel.getPreferredSize().height));
        //getPreferredSize used because size adjusted with text
        turnLabel.setBackground(CommonConstants.BACKGRAUND_COLOR);
        turnLabel.setForeground(CommonConstants.BAR_COLOR);
        turnLabel.setOpaque(true);
        turnLabel.setBounds((CommonConstants.FRAME_SIZE.width - turnLabel.getPreferredSize().width)/2,2,
                            turnLabel.getPreferredSize().width, turnLabel.getPreferredSize().height);

        //Score Label
        scoreLabel = new JLabel(CommonConstants.SCORE_LABEL);
        scoreLabel.setFont(new Font("Dialog",Font.PLAIN,40));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(CommonConstants.BAR_COLOR);
        scoreLabel.setBounds(0, turnLabel.getY()+ turnLabel.getPreferredSize().height + 25,
                            CommonConstants.FRAME_SIZE.width,scoreLabel.getPreferredSize().height );

        //Game board
        GridLayout gridLayout = new GridLayout(3,3);
        JPanel boardPanel = new JPanel(gridLayout);
        boardPanel.setBounds(0,scoreLabel.getY() + scoreLabel.getPreferredSize().height+35,
                                CommonConstants.BOARD_SIZE.width, CommonConstants.BOARD_SIZE.height);

        //create board
        for (int i = 0; i < board.length; i ++) {
            for (int j = 0; j < board[i].length; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Disalog", Font.CENTER_BASELINE,180));
                button.setPreferredSize(CommonConstants.BUTTON_SIZE);
                button.setBackground(CommonConstants.BACKGRAUND_COLOR);
                button.addActionListener(this);
                button.setBorder(BorderFactory.createDashedBorder(CommonConstants.BAR_COLOR));
                // add button to board
                board[i][j] = button;
                boardPanel.add(board[i][j]);
            }
        }

        //reset button
        JButton resetButton = new JButton("RESET");
        resetButton.setFont(new Font("Dialog",Font.PLAIN,20));
        resetButton.addActionListener(this);
        resetButton.setBackground(CommonConstants.BACKGRAUND_COLOR);
        resetButton.setForeground(CommonConstants.BAR_COLOR);
        resetButton.setBounds(
                (CommonConstants.FRAME_SIZE.width - resetButton.getPreferredSize().width)/2,
                CommonConstants.FRAME_SIZE.height-100,
                resetButton.getPreferredSize().width,
                resetButton.getPreferredSize().height
        );

        getContentPane().add(turnLabel);//Add this before barLabel because we want to show on barLabel
        getContentPane().add(barLabel);
        getContentPane().add(scoreLabel);
        getContentPane().add(boardPanel);
        getContentPane().add(resetButton);
    }
    private void createResultDialog() {
        resultDialog = new JDialog();
        resultDialog.getContentPane().setBackground(CommonConstants.BACKGRAUND_COLOR);
        resultDialog.setResizable(false);
        resultDialog.setTitle("RESULT");
        resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(2, 1));
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetGame();
            }
        });
        //result label
        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Dialog", Font.PLAIN,18));
        resultLabel.setForeground(CommonConstants.BAR_COLOR);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //restart button
        JButton restartButton = new JButton("PLAY AGAIN");
        restartButton.setBackground(CommonConstants.BACKGRAUND_COLOR);
        restartButton.setForeground(CommonConstants.BAR_COLOR);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(restartButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("RESET") || command.equals("PLAY AGAIN")) {
            resetGame();

            if (command.equals("RESET"))
                xScore = oScore = 0;

            if (command.equals("PLAY AGAIN"))
                resultDialog.setVisible(false);
        } else {
            //player move
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("")) {
                moveCounter++;

                //X
                if (isX) {
                    button.setText(CommonConstants.X_LABEL);
                    button.setForeground(CommonConstants.BAR_COLOR);

                    //update turn label
                    turnLabel.setText(CommonConstants.O_LABEL);
                    turnLabel.setBackground(CommonConstants.BACKGRAUND_COLOR);

                    //Update player
                    isX = false;
                } else {

                    //O
                    button.setText(CommonConstants.O_LABEL);
                    button.setForeground(CommonConstants.BAR_COLOR);

                    turnLabel.setText(CommonConstants.X_LABEL);
                    turnLabel.setBackground(CommonConstants.BACKGRAUND_COLOR);

                    isX = true;
                }

                if (isX) {
                    checkOWin();
                }{
                    checkXWin();
                }

                checkDraw();

                scoreLabel.setText("X: " + xScore + "| O: " + oScore);
            }

            repaint();
            revalidate();
        }
    }

    private void checkXWin() {
            String result = "X Win!!!";

            for (int r = 0; r < board.length; r++){
                if (board[r][0].getText().equals("X") && board[r][1].getText().equals("X") && board[r][2].getText().equals("X") ) {
                    resultLabel.setText(result);

                    resultDialog.setVisible(true);
                    xScore++;
                }
            }

            for (int c = 0; c < board.length; c++){
                if (board[0][c].getText().equals("X") && board[1][c].getText().equals("X") &&  board[2][c].getText().equals("X")){
                    resultLabel.setText(result);

                    resultDialog.setVisible(true);
                    xScore++;
                }
            }

        if (board[0][0].getText().equals("X") && board[1][1].getText().equals("X") &&  board[2][2].getText().equals("X")){
            resultLabel.setText(result);

            resultDialog.setVisible(true);
            xScore++;
        }

        if (board[2][0].getText().equals("X") && board[1][1].getText().equals("X") &&  board[0][2].getText().equals("X")){
            resultLabel.setText(result);

            resultDialog.setVisible(true);
            xScore++;
        }
    }

    private void checkOWin() {
        String result = "O Win!!!";

        for (int r = 0; r < board.length; r++){
            if (board[r][0].getText().equals("0") && board[r][1].getText().equals("0") && board[r][2].getText().equals("0") ) {
                resultLabel.setText(result);

                resultDialog.setVisible(true);
                oScore++;
            }
        }

        for (int c = 0; c < board.length; c++){
            if (board[0][c].getText().equals("0") && board[1][c].getText().equals("0") &&  board[2][c].getText().equals("0")){
                resultLabel.setText(result);

                resultDialog.setVisible(true);
                oScore++;
            }
        }

        if (board[0][0].getText().equals("0") && board[1][1].getText().equals("0") &&  board[2][2].getText().equals("0")){
            resultLabel.setText(result);

                resultDialog.setVisible(true);
            oScore++;
        }



        if (board[2][0].getText().equals("0") && board[1][1].getText().equals("0") &&  board[0][2].getText().equals("0")){
            resultLabel.setText(result);

            resultDialog.setVisible(true);
            oScore++;
        }


    }

    private void checkDraw() {
        //more than 9 moves = draw
        if (moveCounter >= 9) {
            resultLabel.setText("DRAW!");
            resultDialog.setVisible(true);
        }
    }

    private void resetGame() {
        isX = true;
        turnLabel.setText(CommonConstants.X_LABEL);
        turnLabel.setBackground(CommonConstants.BAR_COLOR);
        //Reset score
        scoreLabel.setText(CommonConstants.SCORE_LABEL);
        moveCounter = 0;
        //reset board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setText("");
            }
        }
    }

}

