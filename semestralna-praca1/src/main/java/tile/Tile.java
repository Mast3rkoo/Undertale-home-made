package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    // Pripravenie textur
    public int tileArrayLenght;
    public String[] tileArrayPaths;

    public Tile() {
        tileArrayLenght = 79;
        tileArrayPaths = new String[tileArrayLenght];

        // Pripravenie ciest pre kazdu texturu

        // Room 1 textury
        tileArrayPaths[0] = "/res/tiles/room1/Black-background.png";
        tileArrayPaths[1] = "/res/tiles/room1/Dark-gray-back.png";
        tileArrayPaths[2] = "/res/tiles/room1/Introduction-ground-middle.png";
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
    }

}
