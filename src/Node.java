import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Node implements Callable<Boolean> {
    double value;
    int status; //0-未感染 1-已感染 2-隔离
    double k; //兴趣损失
    double interest; //传播兴趣
    double isolateValue; //interest小于该值时，被隔离
    double minDifference;  //当value差值小于minDifference时，认为两节点value相等
    List<Node> nodes;
    CountDownLatch begin;
    CountDownLatch end;

    public Node(int status,double value,double k)
    {
        this.status=status;
        this.value=value;
        this.k = k;
        this.interest=1.0;
        this.isolateValue=0.01;
        this.minDifference=0.001;
    }

    public void setNodes(List<Node> nodes)
    {
        this.nodes=nodes;
    }

    public CountDownLatch getBegin() {
        return begin;
    }

    public void setBegin(CountDownLatch begin) {
        this.begin = begin;
    }

    public CountDownLatch getEnd() {
        return end;
    }

    public void setEnd(CountDownLatch end) {
        this.end = end;
    }

    public Boolean call() {
        try {
            begin.await();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        int self = nodes.indexOf(this);

        if(this.status==1 && this.interest>=Math.random()) {
            int random = (int)(Math.random()*nodes.size());
            while (random == self)
            {
                random=(int)(Math.random()*nodes.size());
            }
            Node node = nodes.get(random);
            if (self < random) {
                synchronized (node) {
                    synchronized (this) {
                        //当前节点可传播
                        if (node.status != 2 && Math.abs(node.value - this.value) > node.minDifference) {
                            double average = (node.value + this.value) / 2;
                            node.value = average;
                            node.status = 1;
                            this.value = average;
                            end.countDown();
//                            System.out.println(self + "感染了" + random);
                            return true;
                        }
                        //当前节点已隔离
                        else if (node.status == 2 || (node.status != 2 && Math.abs(node.value - this.value) <= this.minDifference)) {
                            this.interest = this.interest / this.k;
                            //兴趣值小于等于临界值，node变为已隔离状态
//                            System.out.println(self+"兴趣值下降");
                            if (this.interest <= this.isolateValue) {
                                this.status = 2;
//                                System.out.println(self+"被隔离");
                            }
                            end.countDown();
                            return false;
                        }
                    }
                }
            } else {
                synchronized (this) {
                    synchronized (node) {
                        //当前节点可传播
                        if (node.status != 2 && Math.abs(node.value - this.value) > node.minDifference) {
                            double average = (node.value + this.value) / 2;
                            node.value = average;
                            node.status = 1;
                            this.value = average;
                            end.countDown();
//                            System.out.println(self + "感染了" + random);
                            return true;
                        }
                        //当前节点已隔离
                        else if (node.status == 2 || (node.status != 2 && Math.abs(node.value - this.value) <= this.minDifference)) {
                            this.interest = this.interest / this.k;
                            //兴趣值小于等于临界值，node变为已隔离状态
//                            System.out.println(self+"兴趣值下降");
                            if (this.interest <= this.isolateValue) {
                                this.status = 2;
//                                System.out.println(self+"被隔离");
                            }
                            end.countDown();
                            return false;
                        }
                    }
                }
            }
        }
        end.countDown();
        return false;
    }
}

