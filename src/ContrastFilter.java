import processing.core.PApplet;

import java.util.Scanner;

public class ContrastFilter implements PixelFilter {
    private double cVal;

    public ContrastFilter() {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter a contrast value between 0.0 and 2.0: ");
        cVal = kb.nextDouble();
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();


        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
                double px = (pixels[r][c] / 255.0) - 0.5;
                px = ((px * cVal) + 0.5) * 255;

                if (px > 255) px = 255;
                if (px < 0) px = 0;
                pixels[r][c] = (short)px;
            }
        }

        img.setPixels(pixels);
        return img;
    }
}
