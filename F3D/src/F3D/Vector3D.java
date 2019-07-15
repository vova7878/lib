package F3D;

public class Vector3D implements Cloneable {

    double x;
    double y;
    double z;

    Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getlength() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static double getlength(Vector3D v1) {
        return Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
    }

    public void plus(Vector3D v2) {
        x += v2.x;
        y += v2.y;
        z += v2.z;
    }

    public static Vector3D plus(Vector3D v1, Vector3D v2) {
        return new Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    public void minus(Vector3D v2) {
        x -= v2.x;
        y -= v2.y;
        z -= v2.z;
    }

    public static Vector3D minus(Vector3D v1, Vector3D v2) {
        return new Vector3D(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    public double multiply(Vector3D v2) {
        return x * v2.x + y * v2.y + z * v2.z;
    }

    public static double multiply(Vector3D v1, Vector3D v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public void multiply2(Vector3D v2) {
        x *= v2.x;
        y *= v2.y;
        z *= v2.z;
    }

    public static Vector3D multiply2(Vector3D v1, Vector3D v2) {
        return new Vector3D(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
    }

    public double getCosFi(Vector3D v2) {
        return multiply(v2) / (getlength() * v2.getlength());
    }

    public static double getCosFi(Vector3D v1, Vector3D v2) {
        return multiply(v1, v2) / (v1.getlength() * v2.getlength());
    }

    public double getFi(Vector3D v2) {
        return Math.acos(getCosFi(v2));
    }

    public static double getFi(Vector3D v1, Vector3D v2) {
        return Math.acos(getCosFi(v1, v2));
    }

    public void toMatrix(double[][] m) {
        double x = this.x * m[0][0] + this.y * m[0][1] + this.z * m[0][2];
        double y = this.x * m[1][0] + this.y * m[1][1] + this.z * m[1][2];
        double z = this.x * m[2][0] + this.y * m[2][1] + this.z * m[2][2];
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3D toMatrix(Vector3D v1, double[][] m) {
        double x = v1.x * m[0][0] + v1.y * m[0][1] + v1.z * m[0][2];
        double y = v1.x * m[1][0] + v1.y * m[1][1] + v1.z * m[1][2];
        double z = v1.x * m[2][0] + v1.y * m[2][1] + v1.z * m[2][2];
        return new Vector3D(x, y, z);
    }

    public static double[][] turnxMatrix(double g) {
        double m[][] = {{1, 0, 0}, {0, Math.cos(g), -Math.sin(g)}, {0, Math.sin(g), Math.cos(g)}};
        return m;
    }

    public static double[][] turnyMatrix(double g) {
        double m[][] = {{Math.cos(g), 0, Math.sin(g)}, {0, 1, 0}, {-Math.sin(g), 0, Math.cos(g)}};
        return m;
    }

    public static double[][] turnzMatrix(double g) {
        double m[][] = {{Math.cos(g), -Math.sin(g), 0}, {Math.sin(g), Math.cos(g), 0}, {0, 0, 1}};
        return m;
    }

    public void turnx(double g) {
        double m[][] = turnxMatrix(g);
        toMatrix(m);
    }

    public void turny(double g) {
        double m[][] = turnyMatrix(g);
        toMatrix(m);
    }

    public void turnz(double g) {
        double m[][] = turnzMatrix(g);
        toMatrix(m);
    }

    public static Vector3D turnx(Vector3D v, double g) {
        double m[][] = turnxMatrix(g);
        return toMatrix(v, m);
    }

    public static Vector3D turny(Vector3D v, double g) {
        double m[][] = turnyMatrix(g);
        return toMatrix(v, m);
    }

    public static Vector3D turnz(Vector3D v, double g) {
        double m[][] = turnzMatrix(g);
        return toMatrix(v, m);
    }

    public Vector3D clone() {
        return new Vector3D(x, y, z);
    }
}
