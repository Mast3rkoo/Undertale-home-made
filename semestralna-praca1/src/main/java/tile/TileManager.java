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
    private Tile[] tile;
    private int[][] mapTileNumber;
    private int tileArrayLenght = 83; // Set the correct tile array length
    private String[] tileArrayPaths;
    private int[] encounterTiles = { 2, 3, 4, 5, 6, 7, 79, 80, 81, 82 };

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[tileArrayLenght]; // Initialize the tile array
        mapTileNumber = new int[gp.getMaxWorldColumn()][gp.getMaxWorldRow()];
        tileArrayPaths = new String[tileArrayLenght]; // Prepare paths array

        prepareTilePaths(); // Load all tile paths into the array
        initializeTiles(); // Initialize the Tile array
        getTileImage(); // Load tile images

        for (int encounterTile : encounterTiles) {
            tile[encounterTile].setEncounter(true);
        }

        loadMap("/res/maps/map001.txt"); // Load the map file
    }

    private void prepareTilePaths() {
        // Pripravenie ciest pre kazdu texturu

        // Room 1 textury
        tileArrayPaths[0] = "/res/tiles/room1/Black-background.png";
        tileArrayPaths[1] = "/res/tiles/room1/Dark-gray-back.png";

        // Specialne tiles ktore vyvolavaju enemakov
        tileArrayPaths[2] = "/res/tiles/room1/Dark-gray-back.png";
        tileArrayPaths[3] = "/res/tiles/room1/Introduction-ground-middle.png";
        tileArrayPaths[4] = "/res/tiles/room1/Introduction-ground-middle.png";
        tileArrayPaths[5] = "/res/tiles/room1/Introduction-ground-middle.png";
        tileArrayPaths[6] = "/res/tiles/room1/Introduction-ground-middle.png";
        tileArrayPaths[7] = "/res/tiles/room1/Introduction-ground-middle.png";

        // Portalove textury
        tileArrayPaths[8] = "/res/tiles/room1/portal/row-1-column-1.png";
        tileArrayPaths[9] = "/res/tiles/room1/portal/row-1-column-2.png";
        tileArrayPaths[10] = "/res/tiles/room1/portal/row-1-column-3.png";
        tileArrayPaths[11] = "/res/tiles/room1/portal/row-2-column-1.png";
        tileArrayPaths[12] = "/res/tiles/room1/portal/row-2-column-2.png";
        tileArrayPaths[13] = "/res/tiles/room1/portal/row-2-column-3.png";
        tileArrayPaths[14] = "/res/tiles/room1/portal/row-3-column-1.png";
        tileArrayPaths[15] = "/res/tiles/room1/portal/row-3-column-2.png";
        tileArrayPaths[16] = "/res/tiles/room1/portal/row-3-column-3.png";
        tileArrayPaths[17] = "/res/tiles/room1/portal/row-4-column-1.png";
        tileArrayPaths[18] = "/res/tiles/room1/portal/row-4-column-2.png";
        tileArrayPaths[19] = "/res/tiles/room1/portal/row-4-column-3.png";

        // Cela textura kvietkov
        tileArrayPaths[20] = "/res/tiles/room1/flower/row-1-column-1.png";
        tileArrayPaths[21] = "/res/tiles/room1/flower/row-1-column-2.png";
        tileArrayPaths[22] = "/res/tiles/room1/flower/row-1-column-3.png";
        tileArrayPaths[23] = "/res/tiles/room1/flower/row-1-column-4.png";
        tileArrayPaths[24] = "/res/tiles/room1/flower/row-1-column-5.png";
        tileArrayPaths[25] = "/res/tiles/room1/flower/row-1-column-6.png";
        tileArrayPaths[26] = "/res/tiles/room1/flower/row-1-column-7.png";
        tileArrayPaths[27] = "/res/tiles/room1/flower/row-1-column-8.png";
        tileArrayPaths[28] = "/res/tiles/room1/flower/row-1-column-9.png";
        tileArrayPaths[29] = "/res/tiles/room1/flower/row-2-column-1.png";
        tileArrayPaths[30] = "/res/tiles/room1/flower/row-2-column-2.png";
        tileArrayPaths[31] = "/res/tiles/room1/flower/row-2-column-3.png";
        tileArrayPaths[32] = "/res/tiles/room1/flower/row-2-column-4.png";
        tileArrayPaths[33] = "/res/tiles/room1/flower/row-2-column-5.png";
        tileArrayPaths[34] = "/res/tiles/room1/flower/row-2-column-6.png";
        tileArrayPaths[35] = "/res/tiles/room1/flower/row-2-column-7.png";
        tileArrayPaths[36] = "/res/tiles/room1/flower/row-2-column-8.png";
        tileArrayPaths[37] = "/res/tiles/room1/flower/row-2-column-9.png";
        tileArrayPaths[38] = "/res/tiles/room1/flower/row-3-column-1.png";
        tileArrayPaths[39] = "/res/tiles/room1/flower/row-3-column-2.png";
        tileArrayPaths[40] = "/res/tiles/room1/flower/row-3-column-3.png";
        tileArrayPaths[41] = "/res/tiles/room1/flower/row-3-column-4.png";
        tileArrayPaths[42] = "/res/tiles/room1/flower/row-3-column-5.png";
        tileArrayPaths[43] = "/res/tiles/room1/flower/row-3-column-6.png";
        tileArrayPaths[44] = "/res/tiles/room1/flower/row-3-column-7.png";
        tileArrayPaths[45] = "/res/tiles/room1/flower/row-3-column-8.png";
        tileArrayPaths[46] = "/res/tiles/room1/flower/row-3-column-9.png";

        // Room 2 textury
        tileArrayPaths[47] = "/res/tiles/room2/row-1-column-1.png";
        tileArrayPaths[48] = "/res/tiles/room2/row-1-column-2.png";
        tileArrayPaths[49] = "/res/tiles/room2/row-1-column-3.png";
        tileArrayPaths[50] = "/res/tiles/room2/row-1-column-4.png";
        tileArrayPaths[51] = "/res/tiles/room2/row-1-column-5.png";
        tileArrayPaths[52] = "/res/tiles/room2/row-1-column-6.png";
        tileArrayPaths[53] = "/res/tiles/room2/row-1-column-7.png";
        tileArrayPaths[54] = "/res/tiles/room2/row-1-column-8.png";
        tileArrayPaths[55] = "/res/tiles/room2/row-1-column-9.png";
        tileArrayPaths[56] = "/res/tiles/room2/row-1-column-10.png";
        tileArrayPaths[57] = "/res/tiles/room2/row-1-column-11.png";
        tileArrayPaths[58] = "/res/tiles/room2/row-2-column-1.png";
        tileArrayPaths[59] = "/res/tiles/room2/row-2-column-2.png";
        tileArrayPaths[60] = "/res/tiles/room2/row-2-column-3.png";
        tileArrayPaths[61] = "/res/tiles/room2/row-2-column-4.png";
        tileArrayPaths[62] = "/res/tiles/room2/row-2-column-5.png";
        tileArrayPaths[63] = "/res/tiles/room2/row-2-column-6.png";
        tileArrayPaths[64] = "/res/tiles/room2/row-2-column-7.png";
        tileArrayPaths[65] = "/res/tiles/room2/row-2-column-8.png";
        tileArrayPaths[66] = "/res/tiles/room2/row-2-column-9.png";
        tileArrayPaths[67] = "/res/tiles/room2/row-2-column-10.png";
        tileArrayPaths[68] = "/res/tiles/room2/row-2-column-11.png";
        tileArrayPaths[69] = "/res/tiles/room2/row-3-column-1.png";
        tileArrayPaths[70] = "/res/tiles/room2/row-3-column-2.png";
        tileArrayPaths[71] = "/res/tiles/room2/row-3-column-3.png";
        tileArrayPaths[72] = "/res/tiles/room2/row-3-column-4.png";
        tileArrayPaths[73] = "/res/tiles/room2/row-3-column-5.png";
        tileArrayPaths[74] = "/res/tiles/room2/row-3-column-6.png";
        tileArrayPaths[75] = "/res/tiles/room2/row-3-column-7.png";
        tileArrayPaths[76] = "/res/tiles/room2/row-3-column-8.png";
        tileArrayPaths[77] = "/res/tiles/room2/row-3-column-9.png";
        tileArrayPaths[78] = "/res/tiles/room2/row-3-column-10.png";

        // Dummy textura
        tileArrayPaths[79] = "/res/enemy/dummy/row-1-column-1.png";
        tileArrayPaths[80] = "/res/enemy/dummy/row-1-column-2.png";
        tileArrayPaths[81] = "/res/enemy/dummy/row-2-column-1.png";
        tileArrayPaths[82] = "/res/enemy/dummy/row-2-column-2.png";

    }

    private void initializeTiles() {
        for (int i = 0; i < tileArrayLenght; i++) {
            tile[i] = new Tile(); // Create a new Tile instance for each array slot
        }
    }

    public Tile getTile(int tileNumber) {
        return tile[tileNumber];
    }

    public int getMapTileNumber(int column, int row) {
        return mapTileNumber[column][row];
    }

    public void resetEncounterTile(int tileNumber) {
        System.out.println("Resetting encounter tile " + tileNumber);
        tile[tileNumber].setEncounter(false);
    }

    public void getTileImage() {
        try {
            for (int i = 0; i < tileArrayLenght; i++) {
                // Load image and assign using the setter method
                BufferedImage img = ImageIO.read(getClass().getResourceAsStream(tileArrayPaths[i]));
                tile[i].setImage(img); // Use the setter to assign the image
            }
        } catch (IOException e) {
            System.err.println("Error loading tile images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int column = 0;
            int row = 0;

            // Load the map and calculate spawn location here (just as an example)
            int worldMapX = gp.getTileSize() * 7;
            int worldMapY = gp.getTileSize() * 2;

            // After the map is loaded, set the getPlayer()'s spawn location
            gp.getPlayer().setDefaultValues(worldMapX, worldMapY);

            while (column < gp.getMaxWorldColumn() && row < gp.getMaxWorldRow()) {

                String line = br.readLine();

                while (column < gp.getMaxWorldColumn()) {
                    String numbers[] = line.split(" {1,2}");

                    int number = Integer.parseInt(numbers[column]);
                    mapTileNumber[column][row] = number;
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

    public void draw(Graphics2D g2) {
        int worldColumn = 0;
        int worldRow = 0;

        int[] collisionTiles = { 0, 17, 18, 19, 50, 51, 52, 53, 58, 59, 60, 61, 65 };

        for (int collisionTile : collisionTiles) {
            tile[collisionTile].setCollision(true);
        }

        while (worldColumn < gp.getMaxWorldColumn() && worldRow < gp.getMaxWorldRow()) {

            int tileNumber = mapTileNumber[worldColumn][worldRow];

            int worldX = worldColumn * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

            if (worldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                    worldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                    worldY + gp.getTileSize() > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                    worldY - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {

                g2.drawImage(tile[tileNumber].getImage(), screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
            }
            worldColumn++;

            if (worldColumn == gp.getMaxWorldColumn()) {
                worldColumn = 0;
                worldRow++;
            }

        }

    }
}
