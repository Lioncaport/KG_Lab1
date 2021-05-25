package workspace;

import javax.swing.*;
import java.awt.*;

public class place extends JPanel
{
    public int[][] shape3d;
    public int[][] kor;
    public int[][] axis;
    public int betta;
    public int hslid;
    public int vslid;

    public place()
    {
        shape3d = new int[][]{
                {5,0,0},{0,0,0},{0,5,0},{5,5,0},
                {5,0,5},{0,0,5},{0,5,5},{5,5,5}};
        kor = new int[][]{
                {10,0,0},{0,10,0},{0,0,10}};
        axis = new int[][]{
                {0,0,0},{0,0,0}};
        betta = 0;
        hslid = 0;
        vslid = 0;

        setPreferredSize(new Dimension(1000,500));
    }

    // перемножение матриц
    public double[][] MultiMatrix(double[][] a, double[][] b)
    {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++)
        {
            for (int j = 0; j < b[0].length; j++)
            {
                for (int k = 0; k < b[0].length; k++)
                {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        int width = 1000;
        int hight = 500;
        super.paintComponent(g);

        // матрицы поворота изображения
        double[][] hMatrix = new double[][]{
                {Math.cos(-Math.toRadians(hslid)),0,-Math.sin(-Math.toRadians(hslid))},
                {0,1,0},
                {Math.sin(-Math.toRadians(hslid)),0,Math.cos(-Math.toRadians(hslid))}};
        double[][] vMatrix = new double[][]{
                {1,0,0},{0,Math.cos(Math.toRadians(vslid)),Math.sin(Math.toRadians(vslid))},
                {0,-Math.sin(Math.toRadians(vslid)),Math.cos(Math.toRadians(vslid))}};

        // отрисовка координатных осей
        double[][] Rkor = new double[3][3];
        for (int i = 0; i < 3; i++)
        {
            Rkor[i][0] = kor[i][0];
            Rkor[i][1] = kor[i][1];
            Rkor[i][2] = kor[i][2];
        }
        Rkor = MultiMatrix(Rkor,hMatrix);
        Rkor = MultiMatrix(Rkor,vMatrix);

        for (int i = 0; i < 3; i++)
        {
            int sx = width / 2 + (int) Rkor[i][0] *100;
            int sy = hight / 2 - (int) Rkor[i][1] *100;
            g.setColor(Color.red);
            g.drawLine(width / 2,hight / 2, sx, sy);
        }

        // отрисовка 3д фигуры
        int[][] sh2d = new int[8][2];
        double[][] result = new double[8][3];
        for (int i = 0; i < 8; i++)
        {
            result[i][0] = shape3d[i][0];
            result[i][1] = shape3d[i][1];
            result[i][2] = shape3d[i][2];
        }
        result = MultiMatrix(result,hMatrix);
        result = MultiMatrix(result,vMatrix);

        for (int i = 0; i < 8; i++)
        {
            sh2d[i][0] = width / 2 + (int) (result[i][0] * 10);
            sh2d[i][1] = hight / 2 - (int) (result[i][1] * 10);
        }
        g.setColor(Color.blue);
        g.drawLine(sh2d[0][0],sh2d[0][1],sh2d[1][0],sh2d[1][1]);
        g.drawLine(sh2d[1][0],sh2d[1][1],sh2d[2][0],sh2d[2][1]);
        g.drawLine(sh2d[2][0],sh2d[2][1],sh2d[3][0],sh2d[3][1]);
        g.drawLine(sh2d[0][0],sh2d[0][1],sh2d[3][0],sh2d[3][1]);
        g.drawLine(sh2d[4][0],sh2d[4][1],sh2d[5][0],sh2d[5][1]);
        g.drawLine(sh2d[5][0],sh2d[5][1],sh2d[6][0],sh2d[6][1]);
        g.drawLine(sh2d[6][0],sh2d[6][1],sh2d[7][0],sh2d[7][1]);
        g.drawLine(sh2d[4][0],sh2d[4][1],sh2d[7][0],sh2d[7][1]);
        g.drawLine(sh2d[0][0],sh2d[0][1],sh2d[4][0],sh2d[4][1]);
        g.drawLine(sh2d[1][0],sh2d[1][1],sh2d[5][0],sh2d[5][1]);
        g.drawLine(sh2d[2][0],sh2d[2][1],sh2d[6][0],sh2d[6][1]);
        g.drawLine(sh2d[3][0],sh2d[3][1],sh2d[7][0],sh2d[7][1]);

        g.setColor(Color.MAGENTA);
        g.fillOval(sh2d[0][0],sh2d[0][1],5,5);
        g.fillOval(sh2d[1][0],sh2d[1][1],5,5);
        g.setColor(Color.orange);
        g.fillOval(sh2d[2][0],sh2d[2][1],5,5);
        g.fillOval(sh2d[3][0],sh2d[3][1],5,5);

        // отрисовка оси поворота
        int[][] ax = new int[2][2];
        double[][] Rax = new double[2][3];
        for (int i = 0; i < 2; i++)
        {
            Rax[i][0] = axis[i][0];
            Rax[i][1] = axis[i][1];
            Rax[i][2] = axis[i][2];
        }
        Rax = MultiMatrix(Rax,hMatrix);
        Rax = MultiMatrix(Rax,vMatrix);

        for (int i = 0; i < 2; i++)
        {
            ax[i][0] = width / 2 + (int) (Rax[i][0] * 10);
            ax[i][1] = hight / 2 - (int) (Rax[i][1] * 10);
        }
        g.setColor(Color.green);
        g.drawLine(ax[0][0],ax[0][1],ax[1][0],ax[1][1]);

        // вычисления углов для поворотов
        double dx = axis[1][0] - axis[0][0];
        double dy = axis[1][1] - axis[0][1];
        double dz = axis[1][2] - axis[0][2];
        double gamma = (dz == 0 ? 0 : (dx == 0 ? Math.toRadians(90) : Math.atan(dz/dx)));
        double alpha = Math.asin(dy/Math.sqrt(dx*dx+dy*dy+dz*dz));

        // перенос начала координат
        for (int i = 0; i < 8; i++)
        {
            result[i][0] = shape3d[i][0] - axis[0][0];
            result[i][1] = shape3d[i][1] - axis[0][1];
            result[i][2] = shape3d[i][2] - axis[0][2];
        }

        // поворот вокруг оси y
        double[][] matrix2 = new double[][]{
                {Math.cos(-gamma),0,Math.sin(-gamma)},
                {0,1,0},
                {-Math.sin(-gamma),0,Math.cos(-gamma)}};
        result = MultiMatrix(result,matrix2);

        // поворот вокруг оси z
        double[][] matrix3 = new double[][]{
                {Math.cos(alpha),-Math.sin(alpha),0},
                {Math.sin(alpha),Math.cos(alpha),0},
                {0,0,1}};
        result = MultiMatrix(result,matrix3);

        // поворот вокруг оси x
        double[][] matrix4 = new double[][]{
                {1,0,0},
                {0,Math.cos(Math.toRadians(betta)),-Math.sin(Math.toRadians(betta))},
                {0,Math.sin(Math.toRadians(betta)),Math.cos(Math.toRadians(betta))}};
        result = MultiMatrix(result,matrix4);

        // обратный поворот вокруг оси z
        double[][] matrix5 = new double[][]{
                {Math.cos(-alpha),-Math.sin(-alpha),0},
                {Math.sin(-alpha),Math.cos(-alpha),0},
                {0,0,1}};
        result = MultiMatrix(result,matrix5);

        // обратный поворот вокруг оси y
        double[][] matrix6 = new double[][]{
                {Math.cos(gamma),0,Math.sin(gamma)},
                {0,1,0},
                {-Math.sin(gamma),0,Math.cos(gamma)}};
        result = MultiMatrix(result,matrix6);

        // перенос начала координат обратно
        for (int i = 0; i < 8; i++)
        {
            result[i][0] += axis[0][0];
            result[i][1] += axis[0][1];
            result[i][2] += axis[0][2];
        }

        // отрисовка повернутой фигуры
        result = MultiMatrix(result,hMatrix);
        result = MultiMatrix(result,vMatrix);

        for (int i = 0; i < 8; i++)
        {
            sh2d[i][0] = width / 2 + (int) (result[i][0] * 10);
            sh2d[i][1] = hight / 2 - (int) (result[i][1] * 10);
        }
        g.setColor(Color.black);
        g.drawLine(sh2d[0][0],sh2d[0][1],sh2d[1][0],sh2d[1][1]);
        g.drawLine(sh2d[1][0],sh2d[1][1],sh2d[2][0],sh2d[2][1]);
        g.drawLine(sh2d[2][0],sh2d[2][1],sh2d[3][0],sh2d[3][1]);
        g.drawLine(sh2d[0][0],sh2d[0][1],sh2d[3][0],sh2d[3][1]);
        g.drawLine(sh2d[4][0],sh2d[4][1],sh2d[5][0],sh2d[5][1]);
        g.drawLine(sh2d[5][0],sh2d[5][1],sh2d[6][0],sh2d[6][1]);
        g.drawLine(sh2d[6][0],sh2d[6][1],sh2d[7][0],sh2d[7][1]);
        g.drawLine(sh2d[4][0],sh2d[4][1],sh2d[7][0],sh2d[7][1]);
        g.drawLine(sh2d[0][0],sh2d[0][1],sh2d[4][0],sh2d[4][1]);
        g.drawLine(sh2d[1][0],sh2d[1][1],sh2d[5][0],sh2d[5][1]);
        g.drawLine(sh2d[2][0],sh2d[2][1],sh2d[6][0],sh2d[6][1]);
        g.drawLine(sh2d[3][0],sh2d[3][1],sh2d[7][0],sh2d[7][1]);

        g.setColor(Color.MAGENTA);
        g.fillOval(sh2d[0][0],sh2d[0][1],5,5);
        g.fillOval(sh2d[1][0],sh2d[1][1],5,5);
        g.setColor(Color.orange);
        g.fillOval(sh2d[2][0],sh2d[2][1],5,5);
        g.fillOval(sh2d[3][0],sh2d[3][1],5,5);
    }
}