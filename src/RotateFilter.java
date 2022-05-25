import java.util.Scanner;

public class RotateFilter implements PixelFilter {
    private double rAng;

    public RotateFilter() {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter the angle to rotate the image by: ");
        rAng = 120;
    }

    @Override
    public DImage processImage(DImage img) {

        short[][] pixels = img.getBWPixelGrid();
        rAng = rAng * Math.PI / 180.0; // Changes angle to radians

        // Trig to find the bounding box of rectangle with minimum area
        double oC = (pixels.length * Math.abs(Math.cos(rAng))) + (pixels[0].length * Math.abs(Math.sin(rAng)));
        double oD = (pixels.length * Math.abs(Math.sin(rAng))) + (pixels[0].length * Math.abs(Math.cos(rAng)));

        short[][] outputPixels = new short[(int) Math.round(oC)][(int) Math.round(oD)]; // Initialize output grid

        // Middle coordinates of image
        int iMX = pixels[0].length / 2;
        int iMY = pixels.length / 2;

        int oMX = outputPixels[0].length / 2;
        int oMY = outputPixels.length / 2;

        // Loop to smooth out black pixels
        for (int r = 1; r < outputPixels.length - 1; r++) {
            for (int c = 1; c < outputPixels[0].length - 1; c++) {
                int x = c - oMX;
                int y = oMY - r;

                int xPrime = (int) Math.round((x * Math.cos((2 * Math.PI) - rAng)) - (y * Math.sin((2 * Math.PI) - rAng)));
                int yPrime = (int) Math.round((x * Math.sin((2 * Math.PI) - rAng)) + (y * Math.cos((2 * Math.PI) - rAng)));

                xPrime = xPrime + iMX;
                yPrime = iMY - yPrime;


                if (xPrime >= 1 && yPrime >= 1 && xPrime < pixels[0].length && yPrime < pixels.length) {
                    outputPixels[r][c] = applyGausianBlur(pixels, yPrime, xPrime);
                }
            }
        }


        img.setPixels(outputPixels);
        return img;
    }

    private short applyGausianBlur(short[][] arr, int row, int col) {
        double sum = 0;
        for (int r = -2; r <= 2; r++) {
            for (int c = -2; c <= 2; c++) {
                if (inBound(arr, row - r, col - c)) {
                    if ((r == -2 || r == 2)) {
                        if (c == -2 || c == 2) sum += (arr[row - r][col - c] / 273.0);
                        if (c == -1 || c == 1) sum += (arr[row - r][col - c] * 4 / 273.0);
                    }
                    if ((r == -1 || r == 1)) {
                        if (c == 1 || c == -1) sum += (arr[row - r][col - c] * 16 / 273.0);
                        if (c == -2 || c == 2) sum += (arr[row - r][col - c] * 4 / 273.0);
                    }
                    if (r == 0 && c == 0) sum += (arr[row - r][col - c] * 41 / 273.0);
                    if (r == 0) {
                        if (c == -1 || c == 1) sum += (arr[row - r][col - c] * 26 / 273.0);
                        if (c == -2 || c == 2) sum += (arr[row - r][col - c] * 7 / 273.0);
                    }
                    if (c == 0) {
                        if (r == -1 || r == 1) sum += (arr[row - r][col - c] * 26 / 273.0);
                        if (r == -2 || r == 2) sum += (arr[row - r][col - c] * 7 / 273.0);
                    }
                }
            }
        }
        return (short) sum;
    }

    private boolean inBound(short[][] arr, int r, int c) {
        return (arr.length - 1 >= r && arr[0].length - 1 >= c && r >= 0 && c >= 0);
    }


}
