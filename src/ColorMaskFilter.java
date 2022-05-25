public class ColorMaskFilter implements PixelFilter, Interactive {
    private short tRed, tGreen, tBlue;

    @Override
    public DImage processImage(DImage img) {
        short[][] iRed = img.getRedChannel();
        short[][] iGreen = img.getGreenChannel();
        short[][] iBlue = img.getBlueChannel();

        for (int r = 0; r < iRed.length; r++) {
            for (int c = 0; c < iRed[0].length; c++) {
                int dR = (iRed[r][c] - tRed);
                int dG = (iGreen[r][c] - tGreen);
                int dB = (iBlue[r][c] - tBlue);

                int dist = (int) Math.sqrt((dR * dR) + (dG * dG) + (dB * dB));
                int cl;

                if (dist > 75) cl = 0;
                else cl = 255;

                iRed[r][c] = (short) cl;
                iGreen[r][c] = (short) cl;
                iBlue[r][c] = (short) cl;
            }
        }

        img.setColorChannels(iRed, iGreen, iBlue);
        return img;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {
        short[][] iRed = img.getRedChannel();
        short[][] iGreen = img.getGreenChannel();
        short[][] iBlue = img.getBlueChannel();

        tRed = iRed[mouseY][mouseX];
        tGreen = iGreen[mouseY][mouseX];
        tBlue = iBlue[mouseY][mouseX];
    }

    @Override
    public void keyPressed(char key) {

    }
}

