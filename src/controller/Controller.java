package controller;

import graphics.rasterizer.LineRasterizer;
import graphics.rasterizer.PolygonOutlineRasterizer;
import graphics.rasterizer.PolygonRasterizer;
import graphics.rasterizer.TrivialLineRasterizer;
import model.Line;
import model.Point;
import model.Polygon;
import view.Canvas;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles user input and controls the application flow related to the {@link Canvas}.
 * The controller coordinates input events, canvas operations, and rendering updates.
 *
 * @author PGRF FIM UHK
 * @version 2026
 */
public class Controller {

    private Point startPoint;
    private Point currentPoint;

    private final Polygon polygon;
    private final Canvas canvas;
    private final LineRasterizer rasterizer;
    private final PolygonRasterizer polygonRasterizer;


    private final List<Line> lines = new ArrayList<Line>();
    private static final int LINE_COLOR = Color.WHITE.getRGB();
    private static final int PREVIEW_COLOR = Color.RED.getRGB();

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- Constructors -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= */

    public Controller(Canvas canvas) {
        this.canvas = canvas;
        this.polygon = new Polygon();
        this.rasterizer = new TrivialLineRasterizer(canvas.getRaster());
        this.polygonRasterizer = new PolygonOutlineRasterizer(rasterizer);

        lines.add(new Line(new Point(100, 100), new Point(300, 100), Color.WHITE.getRGB()));
        lines.add(new Line(new Point(300, 100), new Point(350, 250), Color.WHITE.getRGB()));
        lines.add(new Line(new Point(350, 250), new Point(200, 350), Color.WHITE.getRGB()));
        lines.add(new Line(new Point(200, 350), new Point(300, 250), Color.WHITE.getRGB()));

    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Main functions -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- */

    public void init() {
        canvas.clear();
        polygonRasterizer.rasterize(new Polygon(lines));
        // Obsluha vstupu z myši
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                //TODO: vzit aktualni pozici myši, pridat uvolneni leveho tlacitka, dokoncit vykresleni polygonu
                // 1. Levé tlačítko:
                //    - Pokud polygon ještě není dokončený, zahájit kreslení úsečky pomocí startLine(e).
                startPoint = getPoint(e);
                currentPoint = startPoint;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO: Dokončit rozpracovanou úsečku při uvolnění levého tlačítka.
                // 1. Ověřit, zda existuje previewLine.
                // 2. Získat aktuální pozici myši jako koncový bod úsečky.
                // 3. Vytvořit novou úsečku s barvou LINE_COLOR.
                //    - Počáteční bod převzít z previewLine.
                //    - Koncový bod získat z aktuální pozice myši.
                // 4. Přidat vytvořenou úsečku do polygonu.
                // 5. Zrušit previewLine.
                // 6. Překreslit scénu.

                lines.add(new Line(startPoint, getPoint(e), LINE_COLOR));
                currentPoint = null;
                startPoint = null;
                render();
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO: Aktualizovat náhled rozpracované úsečky.
                // 1. Ověřit, zda existuje previewLine.
                // 2. Zachovat její počáteční bod.
                // 3. Aktualizovat koncový bod podle aktuální pozice myši.
                // 4. Překreslit scénu.

                if (startPoint == null) {
                    return;
                }
                currentPoint = getPoint(e);
                render();
            }
        });

        // Obsluha vstupu z klávesnice
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO: Při stisknutí klávesy C vymazat polygon.
                // 1. Odstranit všechny úsečky polygonu.
                // 2. Zrušit případný náhled (previewLine).
                // 3. Obnovit stav polygonu tak, aby bylo možné začít kreslit nový.
                // 4. Překreslit scénu.
            }
        });
        canvas.repaint();
    }

    private void render() {
        canvas.clear();
        // Vykreslení dosud vytvořených úseček polygonu.
        // TODO: Zajistit, aby se uzavírací úsečka vykreslila pouze u dokončeného polygonu.
        polygonRasterizer.rasterize(polygon);
        // TODO: Vykreslit náhled rozpracované úsečky.
        // 1. Ověřit, zda previewLine není null.
        // 2. Vykreslit previewLine pomocí rasterizeru.
        canvas.repaint();
    }

    private void starLine(MouseEvent e) {
        // TODO: Zahájit kreslení nové úsečky polygonu.
        // 1. Ověřit, zda polygon ještě není dokončený.
        // 2. Získat aktuální pozici myši pomocí getPoint(e).
        // 3. Určit počáteční bod nové úsečky:
        //    - Pokud polygon obsahuje úsečky, použít koncový bod poslední úsečky.
        //    - Pokud je polygon prázdný, použít aktuální pozici myši.
        // 4. Vytvořit previewLine s barvou PREVIEW_COLOR.
        //    - Počáteční bod odpovídá bodu určenému v předchozím kroku.
        //    - Koncový bod odpovídá aktuální pozici myši.
        // 5. Překreslit scénu.
    }

    private void finishPolygon(MouseEvent e) {
        // TODO: Dokončit polygon.
        // 1. Ověřit, zda polygon obsahuje dostatečný počet vrcholů (alespoň 3).
        // 2. Označit polygon jako dokončený.
        // 3. Zajistit, aby rasterizér vykreslil spojovací úsečku mezi posledním a prvním vrcholem polygonu.
        // 4. Po dokončení již neumožnit přidávání dalších úseček.
        //
        // TIP: Do třídy Polygon můžete přidat atribut boolean isFinished
        //      a odpovídající metody pro zjištění a změnu tohoto stavu.
        //      PolygonOutlineRasterizer pak může podle tohoto stavu
        //      rozhodnout, zda má vykreslit uzavírací úsečku.
        render();
    }

    private Point getPoint(MouseEvent e) {
        return new Point(e.getX(), e.getY());
    }

}
