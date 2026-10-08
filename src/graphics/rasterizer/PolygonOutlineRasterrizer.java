package graphics.rasterizer;

import graphics.rasterizer.LineRasterizer;
import graphics.rasterizer.PolygonRasterrizer;
import model.Line;
import model.Polygon;
import java.util.List;

public class PolygonOutlineRasterrizer implements PolygonRasterrizer {

    private final LineRasterizer lineRasterizer;

    public PolygonOutlineRasterrizerrizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    @Override
    public void rasterize(Polygon polygon) {
        List<Line> lines = polygon.getLines();
        rasterizeLines(lines);

        if (lines.size() > 1) {
            Line last = lines.getLast();
            Line first = lines.getFirst();
            lineRasterizer.rasterize(new Line(last.getPoint2(), first.getPoint1(), last.getColor()));        }
    }

    private void rasterizeLines(List<Line> lines) {
        for (Line line : lines) {
            lineRasterizer.rasterize(line);
        }
    }
}