package com.example.josh.battleshipsgame;

import java.util.Random;

public class PlayerLogic extends BattleshipGameBase<BattleshipGameBase.ShipData> {

    public Integer[][] playerGrid;
    private Integer[] shipSizes = {5, 4, 3, 3, 2};
    private int botHits = 0, playerHits = 0, totalHits;
    private int rows;
    private int columns;
    private int shipCount = 0;
    private ShipData[] ships;

    //Class constructor
    public PlayerLogic(int rows, int columns, ShipData[] ships) {
        super(columns, rows, ships);
        this.columns = 10;
        this.rows = 10;
        this.ships = ships;
    }

    @Override
    public int currentGuessAt(int column, int row) {
        if (playerGrid[column][row] == -1) {
            playerGrid[column][row] = 0;
            System.out.println("Shot miss");
        } else if (playerGrid[column][row] == 0) {
            playerGrid[column][row] = 0;
            System.out.println("Shot miss");
        } else {
            playerGrid[column][row] = 1;
            System.out.println("Shot hit");
            playerHits++;
        }
        return 0;
    }

    //Checks to see if a player has sunk all of the opponents ships
    public int checkForWinner() {
        //Finds out the total hits needed to sink entire fleet
        for (int i = 0; i < shipSizes.length; i++) {
            totalHits = totalHits + shipSizes[i];
        }
        //Checks if either player or bot has sunk the whole fleet
        if (playerHits == totalHits) {
            System.out.println("Player is the winner");
            return 1;
        }
        else if (botHits == totalHits) {
            System.out.println("Bot wins");
            return 2;
        }
        else
            return 0;
    }

    @Override
    public int placedCell(int column, int row) {
        return 0;
    }

    public void setPlayersShips() {
        //Loops through all of the ships
        for (ShipData ship : ships) {
            if (ship.isHorizontal()) {
                for (int u = 0; u < ship.getSize(); u++) {
                    playerGrid[ship.getLeft() + u][ship.getTop()] = ship.getSize();
                }
            } else {
                for (int d = 0; d < ship.getSize(); d++) {
                    playerGrid[ship.getLeft()][ship.getTop() + d] = ship.getSize();
                }
            }
        }
    }

    //Places the players ships in the place where they did in the setup screen
    public void placePlayersShips(int column, int row) {
        Random random = new Random();
        int shipCol;
        int shipRow;
        boolean horizontal;
        boolean overlapX;
        boolean overlapY;
        boolean overlap;
        boolean validPlacement;

        //Check that all of the ships are not already placed
        if (shipCount == 5) {
            System.out.println("All ships are placed");
        } else {

            //Sets coordinates for the ship from the user input and its rotation
            shipCol = column;
            shipRow = row;

            //Randomoly set the ships rotation
            validPlacement = true;
            horizontal = random.nextBoolean();
            ships[shipCount] = new BattleshipGameBase.ShipData(shipSizes[shipCount], horizontal, shipCol, shipRow);

            //If the ship exceeds the size of the grid set placement to false
            if (ships[shipCount].getTop() > 9 || ships[shipCount].getBottom() > 9 || ships[shipCount].getLeft() > 9 || ships[shipCount].getRight() > 9) {
                validPlacement = false;
                System.out.println("Invalid placement as the ship is out of bounds");
            }
            //Loops though all previously placed ships and checks the new placement
            //doesnt overlap
            if (shipCount > 0) {
                for (int j = 0; j < shipCount; j++) {
                    if (ships[shipCount] != null) {
                        overlapX = (ships[shipCount].getLeft() >= ships[j].getLeft() - 1 && ships[shipCount].getLeft() <= ships[j].getRight() + 1);
                        overlapY = (ships[shipCount].getTop() >= ships[j].getTop() - 1 && ships[shipCount].getTop() <= ships[j].getBottom() + 1);
                        overlap = (overlapX && overlapY);

                        //If there is an overlap set the validPlacement as false
                        if (overlap == true) {
                            validPlacement = false;
                            System.out.println("Invalid placement as there is an overlap");
                        }
                    }
                }
            }

            //Doesnt place the ship if its not valid
            if (validPlacement == false) {
                System.out.println("Please try again");
            } else {
                //Marks the ships location in the grid depending on its orientation
                if (ships[shipCount].isHorizontal()) {

                    //Marks the array with 8 where the ship is
                    for (int u = 0; u < ships[shipCount].getSize(); u++) {
                        playerGrid[ships[shipCount].getLeft() + u][ships[shipCount].getTop()] = 8;
                        System.out.println("Ship has been placed horizontal");
                        System.out.println("Ship has been placed: " + shipCount);
                    }
                } else {

                    for (int d = 0; d < ships[shipCount].getSize(); d++) {
                        playerGrid[ships[shipCount].getLeft()][ships[shipCount].getTop() + d] = 8;
                        System.out.println("Ship has been placed vertical");
                        System.out.println("Ship has been placed: " + shipCount);
                    }
                }
                //Creates the ship object
                ships[shipCount] = new BattleshipGameBase.ShipData(shipSizes[shipCount], horizontal, shipCol, shipRow);

                //Increment to count how many ships have been placed so far
                shipCount++;
            }
        }

    }

    //Sets up the players array
    public void createPlayerArray(int column, int row) {
        //Create the gameGrid[] the same size as the game grid
        for (int c = 0; c < column; c++) {
            for (int r = 0; r < row; r++) {
                playerGrid = new Integer[c + 1][r + 1];
            }
        }
        for (int y = 0; y < playerGrid.length; y++) {
            for (int x = 0; x < playerGrid.length; x++) {
                playerGrid[y][x] = -1;
            }
        }
    }

}





