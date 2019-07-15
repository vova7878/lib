package F3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.onedroid.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class DemoViewer {

    static ArrayList tris;
    static Viewer3D view;
    static boolean cb = true;

    public static void main(String[] args) throws Exception {
        //GifWriter out = new GifWriter(new File("video.gif"), BufferedImage.TYPE_INT_ARGB);
        tris = new ArrayList<>();
        tris = getF();
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JPanel pz = new JPanel(new GridLayout(2, 1));
        JPanel px = new JPanel(new GridLayout(2, 1));
        JPanel py = new JPanel(new GridLayout(1, 4));
        pz.setPreferredSize(new Dimension(20, 20));
        px.setPreferredSize(new Dimension(20, 20));
        py.setPreferredSize(new Dimension(20, 20));
        JButton b1 = new JButton();
        JButton b2 = new JButton();
        JButton b3 = new JButton();
        JButton b4 = new JButton();
        JButton b5 = new JButton();
        JButton b6 = new JButton();
        JButton b7 = new JButton();
        JButton b8 = new JButton();
        pz.add(b1);
        pz.add(b2);
        px.add(b3);
        px.add(b4);
        py.add(b5);
        py.add(b6);
        py.add(b7);
        py.add(b8);
        frame.add(pz, BorderLayout.WEST);
        frame.add(px, BorderLayout.EAST);
        frame.add(py, BorderLayout.SOUTH);
        JPanel renderPanel = new JPanel() {
            public void paint(Graphics g2) {
                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = img.createGraphics();
                g.setColor(Color.gray);
                g.fillRect(0, 0, getWidth(), getHeight());
                view.paint(g);
                g2.drawImage(img, 0, 0, null);
                /*try {
                    out.write(img);
                } catch (IOException ex) {
                    Logger.getLogger(DemoViewer.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
        };
        view = new Viewer3D(renderPanel, tris, true, false);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.turnz(Math.PI / 90);
                renderPanel.repaint();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.turnz(-Math.PI / 90);
                renderPanel.repaint();
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.turnx(Math.PI / 90);
                renderPanel.repaint();
            }
        });
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.turnx(-Math.PI / 90);
                renderPanel.repaint();
            }
        });
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.turny(-Math.PI / 90);
                renderPanel.repaint();
            }
        });
        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.turny(Math.PI / 90);
                renderPanel.repaint();
            }
        });
        b7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.toSize(10 / 9.0);
                renderPanel.repaint();
            }
        });
        b8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.toSize(0.9);
                renderPanel.repaint();
            }
        });
        frame.add(renderPanel, BorderLayout.CENTER);
        renderPanel.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static ArrayList inflate(ArrayList tris) {
        ArrayList result = new ArrayList<>();
        for (Object tr : tris) {
            Triangle t = (Triangle) tr;
            Vector3D m1 = new Vector3D((t.v1.x + t.v2.x) / 2, (t.v1.y + t.v2.y) / 2, (t.v1.z + t.v2.z) / 2);
            Vector3D m2 = new Vector3D((t.v2.x + t.v3.x) / 2, (t.v2.y + t.v3.y) / 2, (t.v2.z + t.v3.z) / 2);
            Vector3D m3 = new Vector3D((t.v1.x + t.v3.x) / 2, (t.v1.y + t.v3.y) / 2, (t.v1.z + t.v3.z) / 2);
            result.add(new Triangle(t.v1.clone(), m1.clone(), m3.clone(), cb ? t.color : new Color(t.color.getRed() * 3 / 4, t.color.getGreen() * 3 / 4, t.color.getBlue() * 3 / 4)));
            result.add(new Triangle(t.v2.clone(), m1.clone(), m2.clone(), cb ? t.color : new Color(t.color.getRed() * 3 / 4, t.color.getGreen() * 3 / 4, t.color.getBlue() * 3 / 4)));
            result.add(new Triangle(t.v3.clone(), m2.clone(), m3.clone(), cb ? t.color : new Color(t.color.getRed() * 3 / 4, t.color.getGreen() * 3 / 4, t.color.getBlue() * 3 / 4)));
            result.add(new Triangle(m1.clone(), m2.clone(), m3.clone(), t.color));
        }
        return result;
    }

    public static void toSphere(ArrayList result, int r) {
        for (Object re : result) {
            Triangle t = (Triangle) re;
            for (Vector3D v : new Vector3D[]{t.v1, t.v2, t.v3}) {
                double l = v.getlength() / r;
                v.x /= l;
                v.y /= l;
                v.z /= l;
            }
        }
    }

    public static ArrayList getF() {
        ArrayList out = new ArrayList();
        int t = 1;
        for (int x = -200; x < 200; x += t) {
            for (int z = -200; z < 200; z += t) {
                Vector3D v1 = new Vector3D(x, getF(new CDouble(x, z)).a, z),
                        v2 = new Vector3D(x + t, getF(new CDouble(x + t, z)).a, z),
                        v3 = new Vector3D(x, getF(new CDouble(x, z + t)).a, z + t),
                        v4 = new Vector3D(x + t, getF(new CDouble(x + t, z + t)).a, z + t);
                Triangle t1 = new Triangle(v1.clone(), v2.clone(), v3.clone(), null),
                        t2 = new Triangle(v4.clone(), v2.clone(), v3.clone(), null);
                out.add(t1);
                out.add(t2);
            }
        }
        for (Object o : out) {
            Triangle h = (Triangle) o;
            Vector3D vec = h.Centre();
            CDouble d = getF(new CDouble(vec.x, vec.z));
            h.color = Color.getHSBColor((float) ((d.getFi()) / (Math.PI * 2)),
                    1,
                    (float) (1 - Math.pow(2, -d.getModule() / 2)));
        }
        return out;
    }

    public static CDouble getF(CDouble z) {
        double m = 40;
        z.divide(new CDouble(m, 0));
        return CDouble.multiply(CDouble.minus(CDouble.pow(z, new CDouble(3, 0)), new CDouble(1, 0)), new CDouble(m, 0));
    }
}
