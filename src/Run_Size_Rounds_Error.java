import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Run_Size_Rounds_Error {
    static List<Node> nodes;
    static CountDownLatch begin;
    static CountDownLatch end;
    static int size=1000;
    static int rounds;
    public static void main(String[] args) throws IOException {
        File outfile=new File("src/out/节点个数-收敛轮数、误差.csv");
        FileOutputStream fos = new FileOutputStream(outfile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        System.out.println("size,rounds,error");
        bw.write("size,rounds,error\n");
        for(int j=20;j<size;j=j+10)
        {
            nodes= new ArrayList<>();
            rounds=0;
            double sum=0;
            for(int i=1;i<j;i++)
            {
                double randomValue = Math.random()*100;
                Node node = new Node(0,randomValue,1.2);
                nodes.add(node);
                sum+=randomValue;
            }
            for(int i=1;i<=1;i++)
            {
                double randomValue = Math.random()*100;
                Node node = new Node(1,randomValue,1.2);
                sum+=randomValue;
                nodes.add(node);
            }
            ExecutorService threadPool = Executors.newFixedThreadPool(size);
            boolean flag=true;
            while (flag)
            {
                flag=false;
                end=new CountDownLatch(j);
                begin=new CountDownLatch(1);
                rounds ++;
                List<Future<Boolean>> futures = new ArrayList<>();
                for(Node node:nodes)
                {
                    node.setNodes(nodes);
                    node.setBegin(begin);
                    node.setEnd(end);
                    Future<Boolean> result = threadPool.submit(node);
                    futures.add(result);
                }
                begin.countDown();
                try {
                    end.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    for (Future<Boolean> future : futures) {
                        if (future.get()) {
                            flag = true;
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            double error=0;
            double average = sum/j;
            for(Node node:nodes)
            {
                error+=Math.abs(node.value-average);
            }
            System.out.println(j+","+rounds+","+error/j);
            bw.write(j+","+rounds+","+error/j+"\n");
        }
        bw.close();
        fos.close();
        System.exit(1);
    }
}
