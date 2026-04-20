public class Matrix2D {

    static void fill(int[][] m, int value) {
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[i].length; j++)
                m[i][j] = value;
    }

    static void drawRect(int[][] m) {
        int n = m.length;
        fill(m, 255); // fundal alb

        for (int i = n / 3; i < 2 * n / 3; i++)
            for (int j = n / 4; j < 3 * n / 4; j++)
                m[i][j] = 0;
    }

    static void drawCircle(int[][] m) {
        int n = m.length;

        double cx = n / 2.0, cy = n / 2.0;
        double r = n / 3.0;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                double dx = i - cx;
                double dy = j - cy;
                m[i][j] = (dx * dx + dy * dy <= r * r) ? 0 : 255;
            }
    }

    static void printAscii(int[][] m) {
        int n = m.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(m[i][j] == 0 ? "█" : " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {


        int n = Integer.parseInt(args[0]);
        String tip = args[1];

        int[][] m = new int[n][n];

        long start = System.nanoTime();

        if (tip.equals("rect")) {
            drawRect(m);
        } else if (tip.equals("circle")) {
            drawCircle(m);
        } else {
            System.out.println("Tip invalid. Foloseste rect sau circle.");
            return;
        }

        long end = System.nanoTime();

        if (n > 80) {
            System.out.println("Runtime = " + (end - start) + " ns");
            return;
        }

        printAscii(m);
    }
}
