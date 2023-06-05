package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one player's BattleSalvo board
 */
public class GameBoard {
  private final int height;
  private final int width;
  private final ArrayList<ArrayList<BoardCell>> board = new ArrayList<>();

  /**
   * Creates a GameBoard object with the given height and width
   *
   * @param height int representing the height of the game board
   * @param width int representing the width of the game board
   */
  public GameBoard(int height, int width) {
    if (validConstructorArgs(height) && validConstructorArgs(width)) {
      this.height = height;
      this.width = width;
    } else {
      throw new IllegalArgumentException("Height and Width must be between 6 and 15 inclusive");
    }

    initBoard();
  }

  /**
   * Determines if the height and width follow the correct range of [6, 15]
   *
   * @param dimension the int value of the height or width being test
   * @return boolean: true if valid args, false otherwise
   */
  private boolean validConstructorArgs(int dimension) {
    return (dimension >= 6) && (dimension <= 15);
  }

  /**
   * Produces a 2D arraylist that represents the starting position of the board, filled with empty
   */
  private void initBoard() {
    for (int i = 0; i < height; i++) {
      board.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        board.get(i).add(BoardCell.EMPTY);
      }
    }
  }

  /**
   * Provides the current board of this GameBoard
   *
   * @return a 2D arraylist that represents the current state of this board
   */
  public ArrayList<ArrayList<BoardCell>> getBoard() {
    return new ArrayList<>(this.board);
  }

  /**
   * Changes all the ships on this board to empty's to show to the opponent while playing
   *
   * @return a 2D arraylist showing only hits and misses, but no ships
   */
  public ArrayList<ArrayList<BoardCell>> getBoardForOpponent() {
    ArrayList<ArrayList<BoardCell>> resultBoard = new ArrayList<>();
    for (ArrayList<BoardCell> boardCells : this.board) {
      resultBoard.add(new ArrayList<>(boardCells));
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (resultBoard.get(i).get(j) == BoardCell.SHIP) {
          resultBoard.get(i).set(j, BoardCell.EMPTY);
        }
      }
    }
    return resultBoard;
  }

  /**
   * Sets the correct coords in this board to Ship from the given list of ships
   *
   * @param ship a single ship to be added to the board
   */
  public void setUp(Ship ship) {
    for (int i = 0; i < ship.getCoords().size(); i++) {
      Coord currentCoord = ship.getCoords().get(i);
      this.board.get(currentCoord.getY()).set(currentCoord.getX(), BoardCell.SHIP);
    }
  }

  /**
   * Updates the cells of the board based on the given coords
   *
   * @param shots the list of coords representing the opponent's coords
   */
  public void updateFromSalvo(List<Coord> shots) {
    for (Coord coord : shots) {
      if (this.board.get(coord.getY()).get(coord.getX()) == BoardCell.SHIP) {
        this.board.get(coord.getY()).set(coord.getX(), BoardCell.HIT);
      } else {
        this.board.get(coord.getY()).set(coord.getX(), BoardCell.MISS);
      }
    }

  }

  /**
   * Returns the number of empty spaces on this board for the purposes of limiting shots if there
   * are less empty spaces than opponent's remaining ships
   *
   * @return int representing the number of un-guessed and unfilled cells on the board
   */
  public int getEmptySpaces() {
    int count = 0;
    for (ArrayList<BoardCell> boardCells : this.board) {
      for (BoardCell boardCell : boardCells) {
        if (boardCell == BoardCell.EMPTY || boardCell == BoardCell.SHIP) {
          count++;
        }
      }
    }
    return count;
  }


}
