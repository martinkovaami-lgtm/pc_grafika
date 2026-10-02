package graphics.rasterizer;

import graphics.Raster;
import model.Line;

public class TrivialLineRasterizer implements LineRasterizer {

    private final Raster raster;

    public TrivialLineRasterizer(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void rasterize(Line line) {
        rasterize(
                line.getPoint1().getX(),
                line.getPoint1().getY(),
                line.getPoint2().getX(),
                line.getPoint2().getY(),
                line.getColor()
        );
    }

    private void rasterize(int x1, int y1, int x2, int y2, int color) {
        // TODO: Dokončit implementaci

        float k = (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;

        // TODO: X2 = X1- vertikální čára

        if (x1 == x2) {
            int startY = Math.min(y1, y2);
            int endY = Math.max(y1, x2);
            for (int y = startY; y <= endY; y++){
                raster.setPixel(x1, y, color);
            }
            return;
        }





        // TODO: X2 < X1 je nunté prohodit X2 a X1

        //aby se cara delala na druhou stranu

        if (x2 < x1){
            int tempX = x1;
            x1 = x2;
            x2 = tempX;

            int tempY = y1;
            y1 = y2;
            y2 = tempY;

        }

        // TODO: Pokud je (y2 - y1) > (x2- x1) - jdeme po y

        if (Math.abs(x2 - y1) > Math.abs(x2 - x1)){
            int startY = Math.min(y1, y2);
            int endY = Math.max(y1, y2);
            for (int y = startY; y <= endY; y++){
                float x = (y-q) / k;
                raster.setPixel(Math.round(x), y, color);
            }

        }

        //aby to fungovalo i u vodorovne (jdemem po x)
        else {
            int startX = Math.min(x1, x2);
            int endX = Math.max(x1, x2);

            for (int x = startX; x <= endX; x++) {
                float y = k * x + q;
                raster.setPixel(x, Math.round(y), color);
            }
        }


        for (int x = x1; x <= x2; x++) {
            float y = k * x + q;
            raster.setPixel(x, Math.round(y), color);
        }

    }

}
