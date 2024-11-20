package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import semestralka.GamePanel;

public class TileManager extends Tile {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNumber[][];
    public int worldMapX, worldMapY;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[tileArrayLenght];

        mapTileNumber = new int[gp.maxWorldColumn][gp.maxWorldRow];

        getTileImage();
        loadMap("/res/maps/map001.txt");
    }

    public void getTileImage() {
        try {
            for (int i = 0; i < tileArrayLenght; i++) {
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(getClass().getResourceAsStream(tileArrayPaths[i]));
            }
        } catch (IOException i) {
            i.printStackTrace();

        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int column = 0;
            int row = 0;

            // Load the map and calculate spawn location here (just as an example)
            worldMapX = gp.tileSize * 7;
            worldMapY = gp.tileSize * 2;

            // After the map is loaded, set the player's spawn location
            gp.player.setDefaultValues(worldMapX, worldMapY);

            while (column < gp.maxWorldColumn && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (column < gp.maxWorldColumn) {
                    String numbers[] = line.split(" {1,2}");

                    int number = Integer.parseInt(numbers[column]);
                    mapTileNumber[column][row] = number;
                    column++;
                }
                if (column == gp.maxWorldColumn) {
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
        tile[0].collision = true;
        tile[17].collision = true;
        tile[18].collision = true;
        tile[19].collision = true;

        while (worldColumn < gp.maxWorldColumn && worldRow < gp.maxWorldRow) {

            int tileNumber = mapTileNumber[worldColumn][worldRow];

            int worldX = worldColumn * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNumber].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            worldColumn++;

            if (worldColumn == gp.maxWorldColumn) {
                worldColumn = 0;
                worldRow++;
            }
        }

    }
}
