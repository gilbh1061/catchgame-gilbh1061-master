
/**
 * This class manages the interactions between the different pieces of
 * the game: the Board, the Daleks, and the Doctor. It determines when
 * the game is over and whether the Doctor won or lost.
 */
public class CatchGame {

    /**
     * Instance variables go up here
     * Make sure to create a Board, 3 Daleks, and a Doctor
     */
    private Board gameBoard;
    private Doctor Doc;
    private Dalek dalek1;
    private Dalek dalek2;
    private Dalek dalek3;

    /**
     * The constructor for the game.
     * Use it to initialize your game variables.
     * (create people, set positions, etc.)
     */
    public CatchGame() {
        this.gameBoard = new Board(12, 12);
        this.Doc = new Doctor((int)(Math.random() * 11 + 1), (int)(Math.random() * 11 + 1));
        this.dalek1 = new Dalek((int)(Math.random() * 11 + 1), (int)(Math.random() * 11 + 1));
        this.dalek2 = new Dalek((int)(Math.random() * 11 + 1), (int)(Math.random() * 11 + 1));
        this.dalek3 = new Dalek((int)(Math.random() * 11 + 1), (int)(Math.random() * 11 + 1));
    }

    /**
     * The playGame method begins and controls a game: deals with when the user
     * selects a square, when the Daleks move, when the game is won/lost.
     */
    public void playGame() {
        boolean play = true;
        // place initial doctor peg
        this.gameBoard.putPeg(Board.GREEN, Doc.getRow(), Doc.getCol());
        // place initial dalek pegs
        this.gameBoard.putPeg(Board.BLACK, dalek1.getRow(), dalek1.getCol());
        this.gameBoard.putPeg(Board.BLACK, dalek2.getRow(), dalek2.getCol());
        this.gameBoard.putPeg(Board.BLACK, dalek3.getRow(), dalek3.getCol());

        // initial message
        this.gameBoard.displayMessage("Click on a square next to the doctor to move to it.");

        while (play) {
            boolean docTurn = true;
            
            // CHECK FOR DALEK CRASH
            if (this.dalek1.getRow() == this.dalek2.getRow() && this.dalek1.getCol() == this.dalek2.getCol()) {
                this.dalek1.crash();
                this.dalek2.crash();
                this.gameBoard.putPeg(Board.RED, dalek1.getRow(), dalek1.getCol());
            } 
            if (this.dalek3.getRow() == this.dalek2.getRow()
                    && this.dalek3.getCol() == this.dalek2.getCol()) {
                this.dalek3.crash();
                this.dalek2.crash();
                this.gameBoard.putPeg(Board.RED, dalek2.getRow(), dalek2.getCol());
            } 
            if (this.dalek1.getRow() == this.dalek3.getRow()
                    && this.dalek1.getCol() == this.dalek3.getCol()) {
                this.dalek1.crash();
                this.dalek3.crash();
                this.gameBoard.putPeg(Board.RED, dalek3.getRow(), dalek3.getCol());
            }
            if (dalek1.hasCrashed() && dalek2.hasCrashed() && dalek3.hasCrashed()) {
                this.gameBoard.displayMessage("Game over, Doctor Wins!");
                play = false;
            }

            // CHECK FOR DOCTOR CRASH hhhb
            if (this.dalek1.getRow() == this.Doc.getRow() && this.dalek1.getCol() == this.Doc.getCol()) {
                this.gameBoard.displayMessage("Game over, Doctor has crashed with Dalek 1.");
                this.gameBoard.putPeg(Board.YELLOW, this.Doc.getRow(), this.Doc.getCol());
                play = false;
            } else if (this.dalek2.getRow() == this.Doc.getRow() && this.dalek2.getCol() == this.Doc.getCol()) {
                this.gameBoard.displayMessage("Game over, Doctor has crashed with Dalek 2.");
                this.gameBoard.putPeg(Board.YELLOW, this.Doc.getRow(), this.Doc.getCol());
                play = false;
            } else if (this.dalek3.getRow() == this.Doc.getRow() && this.dalek3.getCol() == this.Doc.getCol()) {
                this.gameBoard.displayMessage("Game over, Doctor has crashed with Dalek 3.");
                this.gameBoard.putPeg(Board.YELLOW, this.Doc.getRow(), this.Doc.getCol());
                play = false;
            }
            
            // DOCTOR MOVEMENTS
            if (docTurn && play) {
                Coordinate click = this.gameBoard.getClick();
                this.gameBoard.displayMessage("");
                int col = Doc.getCol();
                int row = Doc.getRow();
                // if next to spot...
                if (((click.getCol() == col - 1 || click.getCol() == col + 1) && (click.getRow() == row - 1
                        || click.getRow() == row + 1))
                        || (click.getCol() == col && (click.getRow() == row - 1 || click.getRow() == row + 1))
                        || (click.getRow() == row && (click.getCol() == col - 1 || click.getCol() == col + 1))) {
                    // move to spot
                    col = click.getCol();
                    row = click.getRow();
                } else if (click.getCol() == col && click.getRow() == row) {
                    // stay put
                    col = Doc.getCol();
                    row = Doc.getRow();
                } else {
                    // move to random spot
                    col = (int) (Math.random() * (11 + 1));
                    row = (int) (Math.random() * (11 + 1));
                }
                // remove old doctor peg
                this.gameBoard.removePeg(this.Doc.getRow(), this.Doc.getCol());
                // add doctor peg
                this.Doc.move(row, col);
                this.gameBoard.putPeg(Board.GREEN, row, col);
                docTurn = false;
            }

            // DALEK MOVEMENTS
            if (docTurn == false && play) {

                // dalek1 movements
                if (this.dalek1.hasCrashed() == false) {
                    this.gameBoard.removePeg(this.dalek1.getRow(), this.dalek1.getCol());
                    this.dalek1.advanceTowards(this.Doc);
                }

                // dalek2 movements
                if (this.dalek2.hasCrashed() == false) {
                    this.gameBoard.removePeg(this.dalek2.getRow(), this.dalek2.getCol());
                    this.dalek2.advanceTowards(this.Doc);
                }

                // dalek3 movements
                if (this.dalek3.hasCrashed() == false) {
                    this.gameBoard.removePeg(this.dalek3.getRow(), this.dalek3.getCol());
                    this.dalek3.advanceTowards(this.Doc);
                }

                // print daleks
                this.gameBoard.putPeg(Board.BLACK, dalek1.getRow(), dalek1.getCol());
                this.gameBoard.putPeg(Board.BLACK, dalek2.getRow(), dalek2.getCol());
                this.gameBoard.putPeg(Board.BLACK, dalek3.getRow(), dalek3.getCol());

                docTurn = true;
            }

        }
    }

}
