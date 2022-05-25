public class SimpleDownsamplingFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] inputPixels = img.getBWPixelGrid();
        short[][] outputPixels = new short[inputPixels.length / 2][inputPixels[0].length / 2];

        int rc = 0;
        int cc = 0;

        for (int r = 0; r < inputPixels.length - 1; r += 2) {
            for (int c = 0; c < inputPixels[r].length - 1; c += 2) {
                outputPixels[rc][cc] = inputPixels[r][c];
                cc++;
            }
            rc++;
            cc = 0;
        }

        img.setPixels(outputPixels);
        return img;
    }
}

