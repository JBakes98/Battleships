package com.example.josh.battleshipsgame;

import java.util.Random;

public class BotLogic extends BattleshipGameBase<BattleshipGameBase.ShipData> {

    public Integer[][] botGrid;
    private Integer[] shipSizes = {5, 4, 3, 3, 2};
    private int botHits = 0, playerHits = 0, totalHits;
    private int rows;
    private int columns;
    private int shipCount = 0;
    private ShipData[] ships;

    public BotLogic(int rows, int columns, ShipData[] ships) {
        super(columns, rows, ships);
        this.columns = 10;
        this.rows = 10;
        this.ships = ships;
    }

    @Override
    public int currentGuessAt(int column, int row) {
        if (botGrid[column][row] == -1) {
            botGrid[column][row] = 0;
            System.out.println("Shot miss");
        } else if (botGrid[column][row] == 0) {
            botGrid[column][row] = 0;
            System.out.println("Shot miss");
        } else {
            botGrid[column][row] = 1;
            System.out.println("Shot hit");
            playerHits++;
        }
        return 0;
    }

    @Override
    public int placedCell(int column, int row) {
        return 0;
    }

    public void placeBotShips() {
        Random random = new Random();
        Integer[] shipSizes = {5, 4, 3, 3, 2};
        int shipCol;
        int shipRow;
        boolean horizontal;
        boolean overlapX;
        boolean overlapY;
        boolean overlap;
        boolean validPlacement;

        //Loop through all of the ships
        for (int i = 0; i < shipSizes.length; i++) {
            do {
                //Create random coordinates fot the ship and its rotation
                validPlacement = true;
                shipCol = random.nextInt(9);
                shipRow = random.nextInt(9);
                horizontal = random.nextBoolean();
                ships[i] = new BattleshipGameBase.ShipData(shipSizes[i], horizontal, shipCol, shipRow);

                //If the ship exceeds the size of the grid set placement to false
                if (ships[i].getTop() >= 9 || ships[i].getBottom() >= 9 || ships[i].getLeft() >= 9 || ships[i].getRight() >= 9) {
                    validPlacement = false;
                }

                //Loops though all previously placed ships and checks the new placement
                //doesnt overlap
                if (i > 0) {
                    for (int j = 0; j < i; j++) {
                        if (ships[i] != null) {
                            overlapX = (ships[i].getLeft() >= ships[j].getLeft() - 1 && ships[i].getLeft() <= ships[j].getRight() + 1);
                            overlapY = (ships[i].getTop() >= ships[j].getTop() - 1 && ships[i].getTop() <= ships[j].getBottom() + 1);
                            overlap = (overlapX && overlapY);

                            //If there is an overlap set the validPlacement as false
                            if (overlap == true) {
                                validPlacement = false;
                            }
                        }
                    }
                }
            } while (validPlacement == false);
        }

        //Marks the ships location in the grid depending on it orientation
        for (int y = 0; y < ships.length; y++) {
            for (ShipData ship : ships) {
                if (ship.isHorizontal()) {
                    for (int u = 0; u < ship.getSize(); u++) {
                        botGrid[ship.getLeft() + u][ship.getTop()] = ship.getSize();
                    }
                } else {
                    for (int d = 0; d < ship.getSize(); d++) {
                        botGrid[ship.getLeft()][ship.getTop() + d] = ship.getSize();
                    }
                }
            }
        }
    }

    //Set up the bots array
    public void createBotArray(int column, int row) {
        //Create the gameGrid[] the same size as the game grid
        for (int c = 0; c < column; c++) {
            for (int r = 0; r < row; r++) {
                //Player grid is where the players ships are stored, gameGrid stores the bots
                botGrid = new Integer[c+1][r+1];
            }
        }
        for (int y = 0; y < botGrid.length; y++) {
            for (int x = 0; x < botGrid.length; x++) {
                botGrid[y][x] = -1;
            }
        }
    }

}
