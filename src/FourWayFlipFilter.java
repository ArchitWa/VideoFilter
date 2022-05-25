public class FourWayFlipFilter implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();

        int lastColIdx = pixels[0].length - 1;
        int lastRowIdx = pixels.length - 1;

        for (int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length / 2; c++) {
                pixels[r][lastColIdx - c] = pixels[r][c];
            }
        }

        for (int r = 0; r < pixels.length / 2; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                pixels[lastRowIdx - r][c] = pixels[r][c];
            }
        }

        img.setPixels(pixels);
        return img;
    }
}

