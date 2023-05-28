package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.InputStreamReader;


/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {


    private Chessboard model = new Chessboard();
    private ChessboardComponent view;
    private PlayerColor currentPlayer;
    private static final int DELAY = 1000; // 每秒触发一次计时器
    private static final int SECONDS = 30; // 计时秒数
    private JLabel timerLabel;
    private Timer timer;
    private int remainingSeconds;
    private ChessboardPoint justPoint;
    private ChessboardPoint nowPoint;
    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    int round=1;

    private void startTimer() {
        remainingSeconds = SECONDS;
        updateTimerLabel();
        timer.start();
    }


    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    private void updateTimerLabel() {
        timerLabel.setText(String.format("剩余时间：%02d:%02d", remainingSeconds / 60, remainingSeconds % 60));
    }

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }
    public GameController() {

    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        if(currentPlayer==PlayerColor.BLUE){
            round++;
        }


    }

    private boolean win() {
        // TODO: Check the board if there is a winner

        return false;
    }

    /*   public void solveTrap(ChessboardPoint selectedPoint, ChessboardPoint destPoint) {
           if (getGridAt(destPoint).getType() == GridType.TRAP && getGridAt(destPoint).getOwner() != getChessPieceAt(selectedPoint).getOwner()) {
               getTrapped(selectedPoint);
           } else if (getGridAt(selectedPoint).getType() == GridType.TRAP && getGridAt(selectedPoint).getOwner() != getChessPieceAt(selectedPoint).getOwner()) {
               exitTrap(selectedPoint);
           }
       }
   */
    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {

            // TODO: if the chess enter Dens or Traps and so on
            //先写进入巢穴,则对方赢
            if (model.decidedens(point, selectedPoint)) {
                //winner=currentPlayer;
                //弹窗显示对方赢了
                new winFrame(100,100,currentPlayer);
            }
            model.decidetraps(point, selectedPoint);
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            playMusic();
            view.repaint();
            FileWriter writer = null;
            try {
                writer = new FileWriter("history");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String toStore = new String();
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessboardPoint tosavepoint = new ChessboardPoint(i, j);
                    if (model.getChessPieceAt(tosavepoint) == null) {
                        toStore += "0";
                        continue;
                    }
                   else if (model.getChessPieceAt(tosavepoint).getName() == "Rat" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "i";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Cat" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "j";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Dog" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "k";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Wolf" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "l";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Leopard" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "m";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Tiger" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "n";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Lion" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "o";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Elephant" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "p";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Rat" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "a";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Cat" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "b";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Dog" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "c";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Wolf" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "d";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Leopard" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "e";
                    }
                   else  if (model.getChessPieceAt(tosavepoint).getName() == "Tiger" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "f";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Lion" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "g";
                    }
                    else if (model.getChessPieceAt(tosavepoint).getName() == "Elephant" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "h";
                    }

                }
                toStore += "\n";
            }
            System.out.println(toStore);
            try {
                writer.write(toStore);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                playMusic();
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            playMusic();
        }
        // TODO: Implement capture function
        else {
            justPoint = selectedPoint;
            nowPoint = point;
            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            playMusic();
            selectedPoint = null;
            view.repaint();
            swapColor();
            FileWriter writer = null;
            try {
                writer = new FileWriter("history.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String toStore = new String();
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessboardPoint tosavepoint = new ChessboardPoint(i, j);
                    if (model.getChessPieceAt(tosavepoint) == null) {
                        toStore += "0";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Rat" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "i";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Cat" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "j";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Dog" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "k";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Wolf" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "l";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Leopard" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "m";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Tiger" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "n";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Lion" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "o";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Elephant" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.BLUE) {
                        toStore += "p";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Rat" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "a";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Cat" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "b";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Dog" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "c";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Wolf" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "d";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Leopard" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "e";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Tiger" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "f";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Lion" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "g";
                    } else if (model.getChessPieceAt(tosavepoint).getName() == "Elephant" && model.getChessPieceAt(tosavepoint).getOwner() == PlayerColor.RED) {
                        toStore += "h";
                    } else {
                        toStore += "0";
                    }
                }
                toStore += "\n";
            }
            System.out.println(toStore);
            try {
                writer.write(toStore);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void restart() {
        model.removeAllPieces();
        model.initPieces();
        view.removeAllPieces();
        view.initiateChessComponent(model);
        view.repaint();
        currentPlayer = PlayerColor.BLUE;
        selectedPoint = null;
    }

    public void loadGameFromFile(String path) {
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            for (String s : lines) {
                System.out.println(s);
            }
            //全部消失
            model.removeAllPieces();
            //不变
            model.initPieces(lines);
            //全部消失
            view.removeAllPieces();
            //全部消失
            view.initiateChessComponent(model);
            //全部消失
            view.repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveGameToFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入文件名：");
        String fileName = reader.readLine();

        String userId = "user"; // 替换为用户ID或用户名
        String folderPath = "D:\\code\\New2\\resource\\"; // 根据用户ID创建文件夹路径
        String filePath = folderPath + "\\" + fileName + ".txt"; // 文件路径

        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs(); // 创建文件夹
        }

        File file = new File(filePath);
        file.createNewFile();

        FileWriter writer = new FileWriter(file);
        String toStore = new String();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint point = new ChessboardPoint(i, j);
                if (model.getChessPieceAt(point) == null) {
                    toStore += "0";
                } else if (model.getChessPieceAt(point).getName() == "Rat" && model.getChessPieceAt(point).getOwner() == PlayerColor.BLUE) {
                    toStore += "j";
                } else if (model.getChessPieceAt(point).getName() == "Cat" && model.getChessPieceAt(point).getOwner() == PlayerColor.BLUE) {
                    toStore += "k";
                } else if (model.getChessPieceAt(point).getName() == "Dog" && model.getChessPieceAt(point).getOwner() == PlayerColor.BLUE) {
                    toStore += "l";
                } else if (model.getChessPieceAt(point).getName() == "Wolf" && model.getChessPieceAt(point).getOwner() == PlayerColor.BLUE) {
                    toStore += "m";
                } else if (model.getChessPieceAt(point).getName() == "Leopard" && model.getChessPieceAt(point).getOwner() == PlayerColor.BLUE) {
                    toStore += "n";
                } else if (model.getChessPieceAt(point).getName() == "Tiger" && model.getChessPieceAt(point).getOwner() == PlayerColor.BLUE) {
                    toStore += "o";
                } else if (model.getChessPieceAt(point).getName() == "Lion" && model.getChessPieceAt(point).getOwner() == PlayerColor.BLUE) {
                    toStore += "p";
                } else if (model.getChessPieceAt(point).getName() == "Elephant" && model.getChessPieceAt(point).getOwner() == PlayerColor.BLUE) {
                    toStore += "q";
                } else if (model.getChessPieceAt(point).getName() == "Rat" && model.getChessPieceAt(point).getOwner() == PlayerColor.RED) {
                    toStore += "a";
                } else if (model.getChessPieceAt(point).getName() == "Cat" && model.getChessPieceAt(point).getOwner() == PlayerColor.RED) {
                    toStore += "b";
                } else if (model.getChessPieceAt(point).getName() == "Dog" && model.getChessPieceAt(point).getOwner() == PlayerColor.RED) {
                    toStore += "c";
                } else if (model.getChessPieceAt(point).getName() == "Wolf" && model.getChessPieceAt(point).getOwner() == PlayerColor.RED) {
                    toStore += "d";
                } else if (model.getChessPieceAt(point).getName() == "Leopard" && model.getChessPieceAt(point).getOwner() == PlayerColor.RED) {
                    toStore += "e";
                } else if (model.getChessPieceAt(point).getName() == "Tiger" && model.getChessPieceAt(point).getOwner() == PlayerColor.RED) {
                    toStore += "f";
                } else if (model.getChessPieceAt(point).getName() == "Lion" && model.getChessPieceAt(point).getOwner() == PlayerColor.RED) {
                    toStore += "g";
                } else if (model.getChessPieceAt(point).getName() == "Elephant" && model.getChessPieceAt(point).getOwner() == PlayerColor.RED) {
                    toStore += "h";
                } else {
                    toStore += "0";
                }
            }
            toStore += "\n";
        }
        System.out.println(toStore);
        writer.write(toStore);
        writer.close();

    }

    public void playMusic() {
        if (ChessGameFrame.isMusicPlaying) {
            try {
                // 加载音频文件
                File audioFile = new File("resource\\click.wav");

                // 创建音频输入流
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                // 获取音频格式
                AudioFormat format = audioStream.getFormat();

                // 创建数据行信息
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                // 打开数据行
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioStream);

                // 播放音乐
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    User user1 = new User("user1", "1");
    User user2 = new User("user2", "2");


}



