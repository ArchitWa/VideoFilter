import java.util.ArrayList;
import java.util.Scanner;

public class TileFilter implements PixelFilter {
    private int rows, cols;

    public TileFilter() {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter the number of rows and columns separated by a space: ");
        String res = kb.nextLine();
        rows = Integer.parseInt(res.split(" ")[0]);
        cols = Integer.parseInt(res.split(" ")[1]);
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] iRed = img.getRedChannel();
        short[][] iGreen = img.getGreenChannel();
        short[][] iBlue = img.getBlueChannel();

        ArrayList<short[][]> dChannels = downsampleChannels(iRed, iGreen, iBlue);

        short[][] oPixels = new short[dChannels.get(0).length * rows][dChannels.get(0)[0].length * cols];
        short[][] oRed = new short[oPixels.length][oPixels[0].length];
        short[][] oGreen = new short[oPixels.length][oPixels[0].length];
        short[][] oBlue = new short[oPixels.length][oPixels[0].length];

        int lR = 0, lC = 0;

        for (int r = 0; r < oRed.length; r++) {
            if (lR == iRed.length / 2) lR = 0;
            for (int c = 0; c < oRed[0].length; c++) {
                if (lC == iRed[0].length / 2) lC = 0;
                oRed[r][c] = dChannels.get(0)[lR][lC];
                oGreen[r][c] = dChannels.get(1)[lR][lC];
                oBlue[r][c] = dChannels.get(2)[lR][lC];

                lC++;
            }
            lR++;
        }

        DImage nImg = new DImage(oPixels[0].length, oPixels.length);
        nImg.setPixels(oPixels);
        nImg.setColorChannels(oRed, oGreen, oBlue);
        return nImg;
    }

    private ArrayList<short[][]> downsampleChannels(short[][] iRed, short[][] iGreen, short[][] iBlue) {
        ArrayList<short[][]> dChannels = new ArrayList<>();
        short[][] dRed = new short[iRed.length / 2][iRed[0].length / 2];
        short[][] dGreen = new short[iGreen.length / 2][iGreen[0].length / 2];
        short[][] dBlue = new short[iBlue.length / 2][iBlue[0].length / 2];

        for (int r = 0; r < iRed.length; r += 2) {
            for (int c = 0; c < iRed[0].length; c += 2) {
                dRed[r / 2][c / 2] = iRed[r][c];
                dGreen[r / 2][c / 2] = iGreen[r][c];
                dBlue[r / 2][c / 2] = iBlue[r][c];
            }
        }

        dChannels.add(dRed);
        dChannels.add(dGreen);
        dChannels.add(dBlue);
        return dChannels;
    }
}
