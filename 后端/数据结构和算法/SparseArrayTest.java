package com.lj.algorithms.sparsearray;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * @author luojing
 * @version 1.0
 * @date 2021/11/16 9:56
 * 一个简单的五子棋
 * 1.可以添加棋子（黑子1，白子2）
 * 2.可以悔棋
 * 3.可以判断输赢
 */
public class SparseArrayTest {

    private static final int CHESS_ROW = 11;
    private static final int CHESS_COL = 11;

    //当前落子
    @Getter
    private int currentPieces = 1;
    //棋盘
    private int[][] chess = new int[CHESS_ROW][CHESS_COL];
    //稀疏数组的头部
    private Sparse<Integer> sparseHead = new Sparse<>(CHESS_ROW, CHESS_COL, 0);
    //稀疏数组
    private List<Sparse<Integer>> sparseList = new ArrayList<Sparse<Integer>>() {{
        add(0, sparseHead);
    }};


    /**
     * 向稀疏数组中添加元素
     * 头部的值需要加一
     */
    private void addSparse(int row, int col) {
        Sparse<Integer> sparse = new Sparse<>(row, col, currentPieces);
        sparseList.add(sparse);
        sparseHead.content++;
    }

    private Sparse<Integer> removeLastSparse() {
        if (sparseList.size() == 1) {
            throw new RuntimeException("没有棋子不能再悔棋了！");
        }
        //移除最后一个元素
        return sparseList.remove(sparseList.size() - 1);
    }

    /**
     * 落子
     *
     * @return true表示当前棋子获胜
     */
    public Boolean addPieces(int row, int col) {
        if (!isChessDot(row, col)) {
            throw new RuntimeException("棋盘越界!请正确输入！");
        }
        //如果当前已经下过子了不能再下
        if (chess[row][col] != 0) {
            throw new RuntimeException("重复落子");
        }
        //设置棋子
        chess[row][col] = currentPieces;
        //稀疏数组
        addSparse(row, col);
        //判断输赢如果赢了直接返回不用反转棋子
        if (isSuccess(row, col)) {
            return true;
        }
        //反转棋子由对方落子
        currentPiecesReversal();
        return false;
    }

    /**
     * 判断输赢
     *
     * @return true表示当前棋子赢了
     */
    private Boolean isSuccess(int row, int col) {
        //判断横着
        int dot1 = findDotCount(row, col, x -> x, y -> y - 1);
        int dot2 = findDotCount(row, col, x -> x, y -> y + 1);
        if (dot1 + dot2 + 1 >= 5) {
            return true;
        }
        //判断竖着
        dot1 = findDotCount(row, col, x -> x - 1, y -> y);
        dot2 = findDotCount(row, col, x -> x + 1, y -> y);
        if (dot1 + dot2 + 1 >= 5) {
            return true;
        }
        //判断左斜
        dot1 = findDotCount(row, col, x -> x - 1, y -> y - 1);
        dot2 = findDotCount(row, col, x -> x + 1, y -> y + 1);
        if (dot1 + dot2 + 1 >= 5) {
            return true;
        }
        //判断右斜
        dot1 = findDotCount(row, col, x -> x - 1, y -> y + 1);
        dot2 = findDotCount(row, col, x -> x + 1, y -> y - 1);
        if (dot1 + dot2 + 1 >= 5) {
            return true;
        }

        return false;
    }

    /**
     * 以某个点为基点查找指定算法方向上的点个数
     *
     * @return 返回和当前点相同的点个数(不包含基点)
     */
    private int findDotCount(int row, int col, DotAlgorithms xDot, DotAlgorithms yDot) {
        int count = 0;
        int x = row;
        int y = col;
        while (true) {
            x = xDot.dot(x);
            y = yDot.dot(y);
            if (isChessDot(x, y) && chess[x][y] == currentPieces) {
                count++;
                if (count == 4) {
                    return count;
                }
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * 判断点是否在棋盘内
     */
    private Boolean isChessDot(int row, int col) {
        if (row < 0 || row > CHESS_ROW - 1) {
            return false;
        }
        if (col < 0 || col > CHESS_COL - 1) {
            return false;
        }
        return true;
    }

    /**
     * 悔棋
     */
    public void undo() {
        //获取从稀疏数组移除的最后的一个稀疏元素
        Sparse<Integer> lastSparse = removeLastSparse();
        //棋盘清除
        chess[lastSparse.row][lastSparse.col] = 0;
        //反转棋子
        currentPiecesReversal();
    }

    /**
     * 当前棋子反转
     */
    private void currentPiecesReversal() {
        if (currentPieces == 1) {
            currentPieces = 2;
        } else {
            currentPieces = 1;
        }
    }

    public void showChess() {
        //第一行
        StringJoiner rowJoiner = new StringJoiner("  ", "   ", " ");
        for (int i = 0; i < CHESS_COL; i++) {
            rowJoiner.add(String.format("%2d", i));
        }
        System.out.println(rowJoiner);
        for (int i = 0; i < chess.length; i++) {
            System.out.print(String.format("%2d", i));
            StringJoiner joiner = new StringJoiner("│", "│", "│");
            for (int anInt : chess[i]) {
                switch (anInt) {
                    case 0:
                        joiner.add("   ");
                        break;
                    case 1:
                        joiner.add(" ● ");
                        break;
                    case 2:
                        joiner.add(" ○ ");
                        break;
                    default:
                        break;
                }

            }
            System.out.println(joiner);
        }

    }

    /**
     * 判断向那个方向查找点
     */
    private interface DotAlgorithms {
        int dot(int dot);
    }

    @Data
    @AllArgsConstructor
    private static class Sparse<T> {
        private int row;
        private int col;
        private T content;
    }

    public static void main(String[] args) throws IOException {
        SparseArrayTest sparseArrayTest = new SparseArrayTest();
        while (true) {
            sparseArrayTest.showChess();
            System.out.print("a:落子,u:悔棋,e:退出:");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            switch (next) {
                case "a":
                    try {
                        System.out.print((sparseArrayTest.getCurrentPieces() == 1 ? "黑子" : "白子") + "请落子（棋盘坐标,号隔开）:");
                        String[] split = scanner.next().split(",");
                        if (sparseArrayTest.addPieces(Integer.parseInt(split[0]), Integer.parseInt(split[1]))) {
                            sparseArrayTest.showChess();
                            System.out.println((sparseArrayTest.getCurrentPieces() == 1 ? "黑子" : "白子") + "恭喜获胜！游戏结束！");
                            System.exit(0);
                        }
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "u":
                    try {
                        sparseArrayTest.undo();
                    } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "e":
                    System.exit(0);
                default:
                    break;
            }
        }

    }

}
