package F3D;

import javax.swing.*;
import java.awt.*;

public class Triangle {

    Vector3D v1;
    Vector3D v2;
    Vector3D v3;
    Color color;

    Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Color color) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }

    public Vector3D Centre() {
        return new Vector3D((v1.x + v2.x + v3.x) / 3, (v1.y + v2.y + v3.y) / 3, (v1.z + v2.z + v3.z) / 3);
    }

    public double Distance(Vector3D v1) {
        return Vector3D.minus(Centre(), v1).getlength();
    }

    public Color getColor(double r) {
        double d = 170;
        r = d / (r);
        int red = (int) (color.getRed() * r);
        red = red < 0 ? 0 : red > 255 ? 255 : red;
        int green = (int) (color.getGreen() * r);
        green = green < 0 ? 0 : green > 255 ? 255 : green;
        int blue = (int) (color.getBlue() * r);
        blue = blue < 0 ? 0 : blue > 255 ? 255 : blue;
        return new Color(red, green, blue, color.getAlpha());
    }

    public void toMatrix(double[][] m) {
        v1.toMatrix(m);
        v2.toMatrix(m);
        v3.toMatrix(m);
    }
}
