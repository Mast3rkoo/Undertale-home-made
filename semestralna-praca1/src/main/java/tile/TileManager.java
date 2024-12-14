package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import semestralka.GamePanel;

public class TileManager {
    private GamePanel gp;
    private Tile[] tileRoom1;
    private Tile[] tileRoom2;
    private Tile[] tileRoom3;
    private int[][] mapTileNumberRoom1;
    private int[][] mapTileNumberRoom2;
    private int[][] mapTileNumberRoom3;
    private int tileArrayLenghtRoom1 = 42;
    private int tileArrayLenghtRoom2 = 33;
    private int tileArrayLenghtRoom3 = 58;
    private String[] tileArrayPathsRoom1;
    private String[] tileArrayPathsRoom2;
    private String[] tileArrayPathsRoom3;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tileRoom1 = new Tile[tileArrayLenghtRoom1];
        mapTileNumberRoom1 = new int[gp.getMaxWorldColumn()][gp.getMaxWorldRow()];
        tileArrayPathsRoom1 = new String[tileArrayLenghtRoom1];

        tileRoom2 = new Tile[tileArrayLenghtRoom2];
        mapTileNumberRoom2 = new int[gp.getMaxWorldColumn()][gp.getMaxWorldRow()];
        tileArrayPathsRoom2 = new String[tileArrayLenghtRoom2];

        tileRoom3 = new Tile[tileArrayLenghtRoom3];
        mapTileNumberRoom3 = new int[gp.getMaxWorldColumn()][gp.getMaxWorldRow()];
        tileArrayPathsRoom3 = new String[tileArrayLenghtRoom3];

        loadMap("/res/maps/map001.txt", 1);
    }

    private void prepareTilePathsRoom1() {

        // Room 1 textury
        tileArrayPathsRoom1[0] = "/res/tiles/room1/Black-background.png";
        tileArrayPathsRoom1[1] = "/res/tiles/room1/Dark-gray-back.png";

        // Specialne tiles ktore vyvolavaju enemakov
        tileArrayPathsRoom1[2] = "/res/tiles/room1/Dark-gray-back.png";

        // Portalove textury
        tileArrayPathsRoom1[3] = "/res/tiles/room1/portal/row-1-column-1.png";
        tileArrayPathsRoom1[4] = "/res/tiles/room1/portal/row-1-column-2.png";
        tileArrayPathsRoom1[5] = "/res/tiles/room1/portal/row-1-column-3.png";
        tileArrayPathsRoom1[6] = "/res/tiles/room1/portal/row-2-column-1.png";
        tileArrayPathsRoom1[7] = "/res/tiles/room1/portal/row-2-column-2.png";
        tileArrayPathsRoom1[8] = "/res/tiles/room1/portal/row-2-column-3.png";
        tileArrayPathsRoom1[9] = "/res/tiles/room1/portal/row-3-column-1.png";
        tileArrayPathsRoom1[10] = "/res/tiles/room1/portal/row-3-column-2.png";
        tileArrayPathsRoom1[11] = "/res/tiles/room1/portal/row-3-column-3.png";
        tileArrayPathsRoom1[12] = "/res/tiles/room1/portal/row-4-column-1.png";
        tileArrayPathsRoom1[13] = "/res/tiles/room1/portal/row-4-column-2.png";
        tileArrayPathsRoom1[14] = "/res/tiles/room1/portal/row-4-column-3.png";

        // Cela textura kvietkov
        tileArrayPathsRoom1[15] = "/res/tiles/room1/flower/row-1-column-1.png";
        tileArrayPathsRoom1[16] = "/res/tiles/room1/flower/row-1-column-2.png";
        tileArrayPathsRoom1[17] = "/res/tiles/room1/flower/row-1-column-3.png";
        tileArrayPathsRoom1[18] = "/res/tiles/room1/flower/row-1-column-4.png";
        tileArrayPathsRoom1[19] = "/res/tiles/room1/flower/row-1-column-5.png";
        tileArrayPathsRoom1[20] = "/res/tiles/room1/flower/row-1-column-6.png";
        tileArrayPathsRoom1[21] = "/res/tiles/room1/flower/row-1-column-7.png";
        tileArrayPathsRoom1[22] = "/res/tiles/room1/flower/row-1-column-8.png";
        tileArrayPathsRoom1[23] = "/res/tiles/room1/flower/row-1-column-9.png";
        tileArrayPathsRoom1[24] = "/res/tiles/room1/flower/row-2-column-1.png";
        tileArrayPathsRoom1[25] = "/res/tiles/room1/flower/row-2-column-2.png";
        tileArrayPathsRoom1[26] = "/res/tiles/room1/flower/row-2-column-3.png";
        tileArrayPathsRoom1[27] = "/res/tiles/room1/flower/row-2-column-4.png";
        tileArrayPathsRoom1[28] = "/res/tiles/room1/flower/row-2-column-5.png";
        tileArrayPathsRoom1[29] = "/res/tiles/room1/flower/row-2-column-6.png";
        tileArrayPathsRoom1[30] = "/res/tiles/room1/flower/row-2-column-7.png";
        tileArrayPathsRoom1[31] = "/res/tiles/room1/flower/row-2-column-8.png";
        tileArrayPathsRoom1[32] = "/res/tiles/room1/flower/row-2-column-9.png";
        tileArrayPathsRoom1[33] = "/res/tiles/room1/flower/row-3-column-1.png";
        tileArrayPathsRoom1[34] = "/res/tiles/room1/flower/row-3-column-2.png";
        tileArrayPathsRoom1[35] = "/res/tiles/room1/flower/row-3-column-3.png";
        tileArrayPathsRoom1[36] = "/res/tiles/room1/flower/row-3-column-4.png";
        tileArrayPathsRoom1[37] = "/res/tiles/room1/flower/row-3-column-5.png";
        tileArrayPathsRoom1[38] = "/res/tiles/room1/flower/row-3-column-6.png";
        tileArrayPathsRoom1[39] = "/res/tiles/room1/flower/row-3-column-7.png";
        tileArrayPathsRoom1[40] = "/res/tiles/room1/flower/row-3-column-8.png";
        tileArrayPathsRoom1[41] = "/res/tiles/room1/flower/row-3-column-9.png";
    }

    private void prepareTilePathsRoom2() {
        // Room 2 textury
        tileArrayPathsRoom2[0] = "/res/tiles/room2/Black-background.png";
        tileArrayPathsRoom2[1] = "/res/tiles/room2/row-1-column-1.png";
        tileArrayPathsRoom2[2] = "/res/tiles/room2/row-1-column-2.png";
        tileArrayPathsRoom2[3] = "/res/tiles/room2/row-1-column-3.png";
        tileArrayPathsRoom2[4] = "/res/tiles/room2/row-1-column-4.png";
        tileArrayPathsRoom2[5] = "/res/tiles/room2/row-1-column-5.png";
        tileArrayPathsRoom2[6] = "/res/tiles/room2/row-1-column-6.png";
        tileArrayPathsRoom2[7] = "/res/tiles/room2/row-1-column-7.png";
        tileArrayPathsRoom2[8] = "/res/tiles/room2/row-1-column-8.png";
        tileArrayPathsRoom2[9] = "/res/tiles/room2/row-1-column-9.png";
        tileArrayPathsRoom2[10] = "/res/tiles/room2/row-1-column-10.png";
        tileArrayPathsRoom2[11] = "/res/tiles/room2/row-1-column-11.png";
        tileArrayPathsRoom2[12] = "/res/tiles/room2/row-2-column-1.png";
        tileArrayPathsRoom2[13] = "/res/tiles/room2/row-2-column-2.png";
        tileArrayPathsRoom2[14] = "/res/tiles/room2/row-2-column-3.png";
        tileArrayPathsRoom2[15] = "/res/tiles/room2/row-2-column-4.png";
        tileArrayPathsRoom2[16] = "/res/tiles/room2/row-2-column-5.png";
        tileArrayPathsRoom2[17] = "/res/tiles/room2/row-2-column-6.png";
        tileArrayPathsRoom2[18] = "/res/tiles/room2/row-2-column-7.png";
        tileArrayPathsRoom2[19] = "/res/tiles/room2/row-2-column-8.png";
        tileArrayPathsRoom2[20] = "/res/tiles/room2/row-2-column-9.png";
        tileArrayPathsRoom2[21] = "/res/tiles/room2/row-2-column-10.png";
        tileArrayPathsRoom2[22] = "/res/tiles/room2/row-2-column-11.png";
        tileArrayPathsRoom2[23] = "/res/tiles/room2/row-3-column-1.png";
        tileArrayPathsRoom2[24] = "/res/tiles/room2/row-3-column-2.png";
        tileArrayPathsRoom2[25] = "/res/tiles/room2/row-3-column-3.png";
        tileArrayPathsRoom2[26] = "/res/tiles/room2/row-3-column-4.png";
        tileArrayPathsRoom2[27] = "/res/tiles/room2/row-3-column-5.png";
        tileArrayPathsRoom2[28] = "/res/tiles/room2/row-3-column-6.png";
        tileArrayPathsRoom2[29] = "/res/tiles/room2/row-3-column-7.png";
        tileArrayPathsRoom2[30] = "/res/tiles/room2/row-3-column-8.png";
        tileArrayPathsRoom2[31] = "/res/tiles/room2/row-3-column-9.png";
        tileArrayPathsRoom2[32] = "/res/tiles/room2/row-3-column-10.png";
    }

    private void prepareTilePathsRoom3() {
        // Room 3 textury
        tileArrayPathsRoom3[0] = "/res/tiles/room3/row-1-column-1.png";
        tileArrayPathsRoom3[1] = "/res/tiles/room3/row-1-column-2.png";
        tileArrayPathsRoom3[2] = "/res/tiles/room3/row-1-column-3.png";
        tileArrayPathsRoom3[3] = "/res/tiles/room3/row-1-column-4.png";
        tileArrayPathsRoom3[4] = "/res/tiles/room3/row-1-column-5.png";
        tileArrayPathsRoom3[5] = "/res/tiles/room3/row-1-column-6.png";
        tileArrayPathsRoom3[6] = "/res/tiles/room3/row-1-column-7.png";
        tileArrayPathsRoom3[7] = "/res/tiles/room3/row-1-column-8.png";
        tileArrayPathsRoom3[8] = "/res/tiles/room3/row-1-column-9.png";
        tileArrayPathsRoom3[9] = "/res/tiles/room3/row-1-column-10.png";
        tileArrayPathsRoom3[10] = "/res/tiles/room3/row-1-column-11.png";
        tileArrayPathsRoom3[11] = "/res/tiles/room3/row-2-column-1.png";
        tileArrayPathsRoom3[12] = "/res/tiles/room3/row-2-column-2.png";
        tileArrayPathsRoom3[13] = "/res/tiles/room3/row-2-column-3.png";
        tileArrayPathsRoom3[14] = "/res/tiles/room3/row-2-column-4.png";
        tileArrayPathsRoom3[15] = "/res/tiles/room3/row-2-column-5.png";
        tileArrayPathsRoom3[16] = "/res/tiles/room3/row-2-column-6.png";
        tileArrayPathsRoom3[17] = "/res/tiles/room3/row-2-column-7.png";
        tileArrayPathsRoom3[18] = "/res/tiles/room3/row-2-column-8.png";
        tileArrayPathsRoom3[19] = "/res/tiles/room3/row-2-column-9.png";
        tileArrayPathsRoom3[20] = "/res/tiles/room3/row-2-column-10.png";
        tileArrayPathsRoom3[21] = "/res/tiles/room3/row-2-column-11.png";
        tileArrayPathsRoom3[22] = "/res/tiles/room3/row-3-column-1.png";
        tileArrayPathsRoom3[23] = "/res/tiles/room3/row-3-column-2.png";
        tileArrayPathsRoom3[24] = "/res/tiles/room3/row-3-column-3.png";
        tileArrayPathsRoom3[25] = "/res/tiles/room3/row-3-column-4.png";
        tileArrayPathsRoom3[26] = "/res/tiles/room3/row-3-column-5.png";
        tileArrayPathsRoom3[27] = "/res/tiles/room3/row-3-column-6.png";
        tileArrayPathsRoom3[28] = "/res/tiles/room3/row-3-column-7.png";
        tileArrayPathsRoom3[29] = "/res/tiles/room3/row-3-column-8.png";
        tileArrayPathsRoom3[30] = "/res/tiles/room3/row-3-column-9.png";
        tileArrayPathsRoom3[31] = "/res/tiles/room3/row-3-column-10.png";
        tileArrayPathsRoom3[32] = "/res/tiles/room3/row-3-column-11.png";
        tileArrayPathsRoom3[33] = "/res/tiles/room3/row-4-column-1.png";
        tileArrayPathsRoom3[34] = "/res/tiles/room3/row-4-column-2.png";
        tileArrayPathsRoom3[35] = "/res/tiles/room3/row-4-column-3.png";
        tileArrayPathsRoom3[36] = "/res/tiles/room3/row-4-column-4.png";
        tileArrayPathsRoom3[37] = "/res/tiles/room3/row-4-column-5.png";
        tileArrayPathsRoom3[38] = "/res/tiles/room3/row-4-column-6.png";
        tileArrayPathsRoom3[39] = "/res/tiles/room3/row-4-column-7.png";
        tileArrayPathsRoom3[40] = "/res/tiles/room3/row-4-column-8.png";
        tileArrayPathsRoom3[41] = "/res/tiles/room3/row-4-column-9.png";
        tileArrayPathsRoom3[42] = "/res/tiles/room3/row-4-column-10.png";
        tileArrayPathsRoom3[43] = "/res/tiles/room3/row-4-column-11.png";
        tileArrayPathsRoom3[44] = "/res/tiles/room3/row-5-column-1.png";
        tileArrayPathsRoom3[45] = "/res/tiles/room3/row-5-column-2.png";
        tileArrayPathsRoom3[46] = "/res/tiles/room3/row-5-column-3.png";
        tileArrayPathsRoom3[47] = "/res/tiles/room3/row-5-column-4.png";
        tileArrayPathsRoom3[48] = "/res/tiles/room3/row-5-column-5.png";
        tileArrayPathsRoom3[49] = "/res/tiles/room3/row-5-column-6.png";
        tileArrayPathsRoom3[50] = "/res/tiles/room3/row-5-column-7.png";
        tileArrayPathsRoom3[51] = "/res/tiles/room3/row-5-column-8.png";
        tileArrayPathsRoom3[52] = "/res/tiles/room3/row-5-column-9.png";
        tileArrayPathsRoom3[53] = "/res/tiles/room3/row-5-column-10.png";
        tileArrayPathsRoom3[54] = "/res/tiles/room3/row-5-column-11.png";
        tileArrayPathsRoom3[55] = "/res/tiles/room3/row-6-column-1.png";
        tileArrayPathsRoom3[56] = "/res/tiles/room3/row-6-column-2.png";
        tileArrayPathsRoom3[57] = "/res/tiles/room3/row-6-column-3.png";
    }

    private void initializeTiles(int room) {
        switch (room) {
            case 1:
                prepareTilePathsRoom1();
                for (int i = 0; i < tileArrayLenghtRoom1; i++) {
                    tileRoom1[i] = new Tile();
                }
                int[] collisionTilesRoom1 = { 0, 12, 13, 14 };
                for (int collisionTile : collisionTilesRoom1) {
                    tileRoom1[collisionTile].setCollision(true);
                }
                tileRoom1[2].setEncounter(true);
                break;
            case 2:
                prepareTilePathsRoom2();
                for (int i = 0; i < tileArrayLenghtRoom2; i++) {
                    tileRoom2[i] = new Tile();
                }
                int[] collisionTilesRoom2 = { 0, 1, 3, 4, 5, 6, 7, 12, 13, 14, 19, 15, 31, 32 };
                for (int collisionTileRoom2 : collisionTilesRoom2) {
                    tileRoom2[collisionTileRoom2].setCollision(true);
                }
                break;
            case 3:
                prepareTilePathsRoom3();
                for (int i = 0; i < tileArrayLenghtRoom3; i++) {
                    tileRoom3[i] = new Tile();
                }
                int[] collisionTilesRoom3 = { 12, 46, 47, 48, 23, 52, 53, 54, 55, 56, 57, 42 };
                for (int collisionTileRoom3 : collisionTilesRoom3) {
                    tileRoom3[collisionTileRoom3].setCollision(true);
                }
                break;
        }
        getTileImage(room);
    }

    public Tile getTile(int room, int tileNumber) {
        switch (room) {
            case 1:
                return tileRoom1[tileNumber];
            case 2:
                return tileRoom2[tileNumber];
            case 3:
                return tileRoom3[tileNumber];
            default:
                return null;
        }
    }

    public int getMapTileNumber(int room, int column, int row) {
        switch (room) {
            case 1:
                return mapTileNumberRoom1[column][row];
            case 2:
                return mapTileNumberRoom2[column][row];
            case 3:
                return mapTileNumberRoom3[column][row];
            default:
                return 0;
        }
    }

    public void resetEncounterTile(int room, int tileNumber) {
        System.out.println("Resetting encounter tile " + tileNumber);
        switch (room) {
            case 1:
                tileRoom1[tileNumber].setEncounter(false);
                break;
            case 2:
                tileRoom2[tileNumber].setEncounter(false);
                break;
            case 3:
                tileRoom3[tileNumber].setEncounter(false);
                break;
            default:
                break;
        }
    }

    public void setCollisionTile(int room, int tileNumber) {
        switch (room) {
            case 1:
                tileRoom1[tileNumber].setCollision(true);
                break;
            case 2:
                tileRoom2[tileNumber].setCollision(true);
                break;
            case 3:
                tileRoom3[tileNumber].setCollision(true);
                break;
            default:
                break;
        }
    }

    public void getTileImage(int room) {
        switch (room) {
            case 1:
                try {
                    for (int i = 0; i < tileArrayLenghtRoom1; i++) {
                        BufferedImage img = ImageIO.read(getClass().getResourceAsStream(tileArrayPathsRoom1[i]));
                        tileRoom1[i].setImage(img);
                    }
                } catch (IOException e) {
                    System.err.println("Error loading tile images: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    for (int i = 0; i < tileArrayLenghtRoom2; i++) {
                        BufferedImage img = ImageIO.read(getClass().getResourceAsStream(tileArrayPathsRoom2[i]));
                        tileRoom2[i].setImage(img);
                    }
                } catch (IOException e) {
                    System.err.println("Error loading tile images: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    for (int i = 0; i < tileArrayLenghtRoom3; i++) {
                        BufferedImage img = ImageIO.read(getClass().getResourceAsStream(tileArrayPathsRoom3[i]));
                        tileRoom3[i].setImage(img);
                    }
                } catch (IOException e) {
                    System.err.println("Error loading tile images: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    public void loadMap(String filePath, int room) {
        initializeTiles(room);
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int column = 0;
            int row = 0;

            // Lokacia hraca na mape
            int worldMapX = gp.getTileSize() * 7;
            int worldMapY = gp.getTileSize() * 2;

            gp.getPlayer().setDefaultValues(worldMapX, worldMapY);

            while (column < gp.getMaxWorldColumn() && row < gp.getMaxWorldRow()) {

                String line = br.readLine();

                while (column < gp.getMaxWorldColumn()) {
                    String[] numbers = line.split(" {1,4}");

                    int number = Integer.parseInt(numbers[column]);
                    switch (room) {
                        case 1:
                            mapTileNumberRoom1[column][row] = number;
                            break;
                        case 2:
                            mapTileNumberRoom2[column][row] = number;
                            break;
                        case 3:
                            mapTileNumberRoom3[column][row] = number;
                            break;
                    }
                    column++;
                }
                if (column == gp.getMaxWorldColumn()) {
                    column = 0;
                    row++;
                }

            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, int room) {
        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < gp.getMaxWorldColumn() && worldRow < gp.getMaxWorldRow()) {

            int tileNumber = 0;
            switch (room) {
                case 1:
                    tileNumber = mapTileNumberRoom1[worldColumn][worldRow];
                    break;
                case 2:
                    tileNumber = mapTileNumberRoom2[worldColumn][worldRow];
                    break;
                case 3:
                    tileNumber = mapTileNumberRoom3[worldColumn][worldRow];
                    break;
                default:
                    break;
            }

            int worldX = worldColumn * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

            if (worldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                    worldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                    worldY + gp.getTileSize() > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                    worldY - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {

                switch (room) {
                    case 1:
                        g2.drawImage(tileRoom1[tileNumber].getImage(), screenX, screenY, gp.getTileSize(),
                                gp.getTileSize(),
                                null);
                        break;
                    case 2:
                        g2.drawImage(tileRoom2[tileNumber].getImage(), screenX, screenY, gp.getTileSize(),
                                gp.getTileSize(),
                                null);
                        break;
                    case 3:
                        g2.drawImage(tileRoom3[tileNumber].getImage(), screenX, screenY, gp.getTileSize(),
                                gp.getTileSize(),
                                null);
                        break;
                    default:
                        break;
                }

            }
            worldColumn++;

            if (worldColumn == gp.getMaxWorldColumn()) {
                worldColumn = 0;
                worldRow++;
            }

        }

    }
}
