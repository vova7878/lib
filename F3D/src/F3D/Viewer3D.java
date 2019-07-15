package F3D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class Viewer3D {

    ArrayList obj;
    Component parent;
    boolean b1, b2;

    public Viewer3D(Component parent, ArrayList l, Boolean b1, Boolean b2) {
        obj = l;
        this.parent = parent;
        this.b1 = b1;
        this.b1 = b2;
    }

    public Viewer3D(Component parent) {
        this(parent, new ArrayList(), true, true);
    }

    public void paint(Graphics2D g) {
        BufferedImage img = new BufferedImage(parent.getWidth(), parent.getHeight(), BufferedImage.TYPE_INT_ARGB);
        double[] zBuffer = new double[img.getWidth() * img.getHeight()];
        for (int q = 0; q < zBuffer.length; q++) {
            zBuffer[q] = Double.NEGATIVE_INFINITY;
        }
        for (Object obj : obj) {
            Triangle t = (Triangle) obj;
            Vector3D v1 = t.v1.clone();
            Vector3D v2 = t.v2.clone();
            Vector3D v3 = t.v3.clone();
            if (b1) {
                v1.x = (v1.x / 5000) * (v1.z + 5000);
                v1.y = (v1.y / 5000) * (v1.z + 5000);
                v2.x = (v2.x / 5000) * (v2.z + 5000);
                v2.y = (v2.y / 5000) * (v2.z + 5000);
                v3.x = (v3.x / 5000) * (v3.z + 5000);
                v3.y = (v3.y / 5000) * (v3.z + 5000);
            }
            Color c;
            if (b2) {
                c = t.getColor(Vector3D.minus(new Triangle(v1, v2, v3, t.color).Centre(), new Vector3D(0, 0, 200)).getlength());
            } else {
                c = t.color;
            }
            v1.x += img.getWidth() / 2;
            v1.y += img.getHeight() / 2;
            v2.x += img.getWidth() / 2;
            v2.y += img.getHeight() / 2;
            v3.x += img.getWidth() / 2;
            v3.y += img.getHeight() / 2;
            int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
            int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
            int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
            int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));
            double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);
            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
                    double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
                    double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                        double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
                        int zIndex = y * img.getWidth() + x;
                        if (zBuffer[zIndex] < depth) {
                            img.setRGB(x, y, c.getRGB());
                            zBuffer[zIndex] = depth;
                        }
                    }
                }
            }
        }
        g.drawImage(img, 0, 0, null);
    }

    public void turnx(double g) {
        double m[][] = Vector3D.turnxMatrix(g);
        for (Object obj : obj) {
            ((Triangle) obj).toMatrix(m);
        }
    }

    public void turny(double g) {
        double m[][] = Vector3D.turnyMatrix(g);
        for (Object obj : obj) {
            ((Triangle) obj).toMatrix(m);
        }
    }

    public void turnz(double g) {
        double m[][] = Vector3D.turnzMatrix(g);
        for (Object obj : obj) {
            ((Triangle) obj).toMatrix(m);
        }
    }

    public void toSize(double g) {
        double m[][] = {{g, 0, 0}, {0, g, 0}, {0, 0, g}};
        for (Object obj : obj) {
            ((Triangle) obj).toMatrix(m);
        }
    }
}
