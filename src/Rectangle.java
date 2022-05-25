import processing.core.PApplet;

import java.util.ArrayList;

public class Rectangle implements PixelFilter, Interactive, Drawable {
    private ArrayList<Location> corners;

    public Rectangle() {
        corners = new ArrayList<>();
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] iRed = img.getRedChannel();
        short[][] iGreen = img.getGreenChannel();
        short[][] iBlue = img.getBlueChannel();

        img.setColorChannels(iRed, iGreen, iBlue);
        return img;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {
        corners.add(new Location(mouseY, mouseX));
    }

    @Override
    public void keyPressed(char key) {

    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        if (corners.size() >= 2) {
            Location l1 = corners.get(0), l2 = corners.get(1);
            window.stroke(window.color(255, 0, 0));
            window.line(l1.y, l1.x, l1.y, l2.x);
            window.line(l2.y, l2.x, l1.y, l2.x);
            window.line(l2.y, l1.x, l2.y, l2.x);
            window.line(l1.y, l1.x, l2.y, l1.x);

        }
    }
}

