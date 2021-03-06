package htwz.algorithm.dijkstra;

import java.util.Arrays;

/**
 * @Description:
 * @Author wangy
 * @Date 2020/8/16 14:24
 * @Version V1.0.0
 **/
public class DijkstraAlgorithm {
}

class Gragh {

    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;
        matrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, N, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, N, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, N, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, N};

        Gragh gragh = new Gragh(vertex, matrix);
        gragh.showGraph();

        gragh.djs(6);

        gragh.showR();

    }

    //顶点数组
    private char[] vertex;

    //邻接矩阵
    private int[][] matrix;

    private VisitedVertex vv;

    public Gragh(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    public void showGraph() {
        for (int[] link : matrix) {
            System.out.println(Arrays.toString(link));
        }
    }

    //迪杰斯特拉算法实现
    public void djs(int index) {
        vv = new VisitedVertex(vertex.length, index);
        //更新index顶点到周围顶点的距离和前驱顶点
        update(index);
        for (int j = 1; j < vertex.length; j++) {
            index = vv.updateArr();
            update(index);
        }
    }

    //更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
    public void update(int index) {
        int len = 0;
        //根据遍历我们的邻接矩阵，matrix[index]行
        for (int j = 0; j < matrix[index].length; j++) {
            //len含义：出发顶点到index顶点的距离 + 从index顶点到j顶点的距离之和
            len = vv.getDis(index) + matrix[index][j];
            if (!vv.in(j) && len < vv.getDis(j)) {
                vv.updatePre(j, index);//更新j顶点的前驱为index顶点
                vv.updateDis(j, len);//更新出发顶点到j顶点的距离
            }
        }
    }

    public void showR() {
        vv.show();
    }



}

class VisitedVertex {

    //记录各个顶点是否访问过，1表示访问过，0表示未访问，会动态更新
    public int[] already_arr;

    //每个下标对应的值为前一个顶点下标，会动态更新
    public int[] pre_visited;

    //记录出发顶点到其他所有顶点的距离，比如G为出发点，就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放在dis
    public int[] dis;

    /**
     *
     * @param length 表示顶点的个数
     * @param index 出发顶点对应的下标，比如G顶点，下标就是6
     */
    public VisitedVertex(int length, int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];
        Arrays.fill(dis, 65535);
        this.already_arr[index] = 1;
        this.dis[index] = 0;//设置出发顶点的访问距离为0
    }

    /**
     * 判断index顶点是否访问过
     * @param index
     * @return
     */
    public boolean in(int index) {
        return already_arr[index] == 1;
    }

    /**
     * 更新出发顶点到index顶点的距离
     * @param index
     * @param len
     */
    public void updateDis(int index, int len) {
        dis[index] = len;
    }

    /**
     * 更新pre这个顶点的前驱节点为index节点
     * @param pre
     * @param index
     */
    public void updatePre(int pre, int index) {
        pre_visited[pre] = index;
    }

    /**
     * 返回出发顶点到index顶点的距离
     * @param index
     */
    public int getDis(int index) {
        return dis[index];
    }

    /**
     * 继续选择并返回新的访问顶点
     * @return
     */
    public int updateArr() {
        int min = 65535, index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            if (already_arr[i] == 0 && dis[i] < min) {
                min = dis[i];
                index = i;
            }
        }
        already_arr[index] = 1;
        return index;
    }

    /**
     * 展示最终的结果
     */
    public void show() {
        for (int i : already_arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i : pre_visited) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i : dis) {
            System.out.print(i + " ");
        }
    }
}