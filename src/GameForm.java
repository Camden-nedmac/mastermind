import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

public class GameForm extends JFrame {
    private JPanel contentPanel;
    private JButton red;
    private JButton green;
    private JButton orange;
    private JButton magenta;
    private JButton blue;
    private JButton guess;
    private JPanel buttonPanel;
    private JButton delete;
    private JButton testWin;
    private JButton RESETButton;

    private JPanel a,aa,aaa,aaaa;
    private JPanel b,bb,bbb,bbbb;
    private JPanel c,cc,ccc,cccc;
    private JPanel d,dd,ddd,dddd;
    private JPanel e,ee,eee,eeee;
    private JPanel f,ff,fff,ffff;
    private JPanel g,gg,ggg,gggg;
    private JPanel h,hh,hhh,hhhh;
    private JPanel i,ii,iii,iiii;
    private JPanel j,jj,jjj,jjjj;

    JPanel[][] guesses = {
            {a,aa,aaa,aaaa},
            {b,bb,bbb,bbbb},
            {c,cc,ccc,cccc},
            {d,dd,ddd,dddd},
            {e,ee,eee,eeee},
            {f,ff,fff,ffff},
            {g,gg,ggg,gggg},
            {h,hh,hhh,hhhh},
            {i,ii,iii,iiii},
            {j,jj,jjj,jjjj}
    };

    private JPanel a1,aa1,aaa1,aaaa1;
    private JPanel b1,bb1,bbb1,bbbb1;
    private JPanel c1,cc1,ccc1,cccc1;
    private JPanel d1,dd1,ddd1,dddd1;
    private JPanel e1,ee1,eee1,eeee1;
    private JPanel f1,ff1,fff1,ffff1;
    private JPanel g1,gg1,ggg1,gggg1;
    private JPanel h1,hh1,hhh1,hhhh1;
    private JPanel i1,ii1,iii1,iiii1;
    private JPanel j1,jj1,jjj1,jjjj1;


    JPanel[][] results = {
            {a1,aa1,aaa1,aaaa1},
            {b1,bb1,bbb1,bbbb1},
            {c1,cc1,ccc1,cccc1},
            {d1,dd1,ddd1,dddd1},
            {e1,ee1,eee1,eeee1},
            {f1,ff1,fff1,ffff1},
            {g1,gg1,ggg1,gggg1},
            {h1,hh1,hhh1,hhhh1},
            {i1,ii1,iii1,iiii1},
            {j1,jj1,jjj1,jjjj1}
    };

    int turn = 0;
    int pegNum = 0;
    Color[] answer = getAnswer();

    public void start(){
        setContentPane(contentPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        listenToButtons();
        for(int i = 0; i < 10; i++){
            for(int x = 0; x < 4; x++){
                guesses[i][x].setMinimumSize(new Dimension(50,50));
                results[i][x].setMinimumSize(new Dimension(25,25));
                guesses[i][x].setBorder(BorderFactory.createLineBorder(Color.black));
                results[i][x].setBorder(BorderFactory.createLineBorder(Color.black));
                guesses[i][x].setBackground(Color.gray);
                results[i][x].setBackground(Color.gray);
            }
        }
        contentPanel.repaint();
    }
    public Color[] getAnswer(){
        Color[] colours = new Color[4];
        for(int i = 0; i < 4; i++){
            int x = (int)Math.floor(Math.random()*5);
            switch (x) {
                case 0 -> colours[i] = Color.red;
                case 1 -> colours[i] = Color.orange;
                case 2 -> colours[i] = Color.green;
                case 3 -> colours[i] = Color.blue;
                case 4 -> colours[i] = Color.magenta;
            }
        }
        return colours;
    }

    public void listenToButtons(){
        red.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addPeg(Color.RED);
            }
        });
        green.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addPeg(Color.GREEN);
            }
        });
        orange.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addPeg(Color.ORANGE);
            }
        });
        magenta.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addPeg(Color.MAGENTA);
            }
        });
        blue.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addPeg(Color.BLUE);
            }
        });
        guess.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
        delete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        RESETButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {reset();}
        });
    }

    public void addPeg(Color color){
        if(pegNum < 4 && turn < 10){
            guesses[turn][pegNum].setBackground(color);
            pegNum++;
        }
    }

    public void checkGuess(){
        ArrayList<Color> result = new ArrayList<>();

        if(pegNum == 4){
            Color[] pegs = new Color[4];
            Color[] mutableAnswer = answer.clone();
            for(int i = 0; i < 4; i++){
                pegs[i] = guesses[turn][i].getBackground();
            }
            //check greens
            for(int i = 0; i < 4; i++){
                if(mutableAnswer[i].equals(pegs[i])){
                    result.add(Color.green);
                    //black is used to indicate skip comparing this colour in the yellow checking stage
                    //pink is used so that it never returns true when compared to any other colours in the game
                    mutableAnswer[i] = Color.black;
                    pegs[i] = Color.pink;
                }
            }
            //check yellows
            for (int i = 0; i < 4; i++) {
                if(!mutableAnswer[i].equals(Color.black)){
                    for(int j = 0; j < 4; j++) {
                        if(mutableAnswer[i].equals(pegs[j])){
                            result.add(Color.yellow);
                            pegs[j] = Color.pink;
                            break;
                        }
                    }
                }

            }

            //append result to gui
            for(int i = 0; i < result.size(); i++){
                results[turn][i].setBackground(result.get(i));
            }
            //check for win amd for loss
            if(result.size() == 4 && result.get(3) == Color.green){
                gameWon();
            } else if(turn == 9){
                for(int j = 0; j <= 9; j++){
                    for(int i = 0; i < 4; i++){
                        results[j][i].setBackground(Color.red);
                        guesses[j][i].setBackground(Color.gray);
                    }
                }
            }
            turn++;
            pegNum = 0;
        }
    }
    public void delete(){
        if(pegNum - 1 >= 0) {
            guesses[turn][pegNum - 1].setBackground(Color.gray);
            pegNum--;
        }
    }
    public void reset(){
        for(int j = 0; j < 10; j++){
            for(int i = 0; i < 4; i++){
                guesses[j][i].setBackground(Color.gray);
                results[j][i].setBackground(Color.gray);
                getAnswer();
            }
        }
        turn = 0;
    }
    public void gameWon(){
        red.removeActionListener(red.getActionListeners()[0]);
        green.removeActionListener(green.getActionListeners()[0]);
        orange.removeActionListener(orange.getActionListeners()[0]);
        magenta.removeActionListener(magenta.getActionListeners()[0]);
        blue.removeActionListener(blue.getActionListeners()[0]);
        delete.removeActionListener(delete.getActionListeners()[0]);
        guess.removeActionListener(guess.getActionListeners()[0]);
    }
}
