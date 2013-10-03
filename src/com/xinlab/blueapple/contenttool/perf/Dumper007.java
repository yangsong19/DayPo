package com.xinlab.blueapple.contenttool.perf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.xinlab.blueapple.contenttool.common.Xin007Property;
import com.xinlab.blueapple.contenttool.common.XinCommonUtil;

public class Dumper007 implements Runnable{

    private static final Logger  vulogger = Logger.getLogger(Dumper007.class);

    static final String WAP = "wap";
    static final String BASE = "base";
    static final String RE = "re";

    List<Node007> nodeBufferWap = null;
    List<Node007> nodeBufferBase = null;
    List<Node007> nodeBufferRe = null;
    int threshold = 5000;
    String hostname = null;

    public Dumper007() {
        super();

        // retrieve logging threshold
        threshold = XinCommonUtil.getIntFromString(Xin007Property.getProperty("007_buffer_threshold"),5000);
        nodeBufferWap = new ArrayList<Node007>(threshold);
        nodeBufferBase = new ArrayList<Node007>(threshold);
        nodeBufferRe = new ArrayList<Node007>(threshold);

        // figure out hostname to use
        hostname = System.getenv("HOSTNAME");
        if (hostname == null) hostname = System.getenv("COMPUTERNAME");
        if (hostname == null) hostname = "vuclip";
        vulogger.info("HOSTNAME: " + hostname);
    }

    public void run() {
        // TODO Auto-generated method stub
        while (true) {

            // WAP node buffer is full, need to be flushed to memcached
            if (nodeBufferWap.size() >= threshold) {
                // dump to memcached
                vulogger.info("need to dump WAP buffer to memcached");
                dumpWapData(nodeBufferWap);
                nodeBufferWap = new ArrayList<Node007>(threshold);
            }

            // BASE node buffer is full, need to be flushed to memcached
            if (nodeBufferBase.size() >= threshold) {
                // dump to memcached
                vulogger.info("need to dump BASE buffer to memcached");
                dumpBaseData(nodeBufferBase);
                nodeBufferBase = new ArrayList<Node007>(threshold);
            }

            // RE node buffer is full, need to be flushed to memcached
            if (nodeBufferRe.size() >= threshold) {
                // dump to memcached
                vulogger.info("need to dump RE buffer to memcached");
                dumpReData(nodeBufferRe);
                nodeBufferRe = new ArrayList<Node007>(threshold);
            }

            Node007 node = Log007.oo7Queue.poll();
            if (node == null) {
                // empty queue, wait a little bit
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else if (node instanceof WapNode007){
                // add the node to the buffer
                nodeBufferWap.add(node);
            }
            else if (node instanceof BaseNode007){
                // add the node to the buffer
                nodeBufferBase.add(node);
            }
            else if (node instanceof RENode007){
                // add the node to the buffer
                nodeBufferRe.add(node);
            }
        }
    }

    private void dumpWapData(List<Node007> nodeBuffer) {
        dumpData(nodeBuffer, WAP);
    }

    private void dumpBaseData(List<Node007> nodeBuffer) {
        dumpData(nodeBuffer, BASE);
    }

    private void dumpReData(List<Node007> nodeBuffer) {
        dumpData(nodeBuffer, RE);
    }

    private void dumpData(List<Node007> nodeBuffer, String module) {
        // get next key to use
        String nextCounterKey = "p_" + hostname + "_" + module;
        long nextCounter = MC007.mcc.incr(nextCounterKey);
        vulogger.info("Next Counter: " + nextCounter);

        // get next key to use
        String nextKey = hostname + "_" + module + "_" + nextCounter;
        vulogger.info("Next Key: " + nextKey);
        // yyyy-mm-dd-hh:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:", Locale.US);
        String insertDate = sdf.format(new Date(System.currentTimeMillis()));
        String [] items = new String[threshold + 2];
        items[0] = nextKey;
        items[1] = insertDate;
        for (int i=0; i<threshold; i++) {
            Node007 node = nodeBuffer.get(i);
            // need to replace "," with " "
            // because "," is used as the delimiter for logging
            String id = node.id;
            if (id != null) id = id.replace(',', ' ');
            if (node instanceof AggregateWapNode007) {
                AggregateWapNode007 anode = (AggregateWapNode007)node;
                items[i+2] = node.type + "," + id + "," + node.startTime + "," + anode.num + "," + anode.sum + "," + anode.max;
            }else if(node instanceof AggregateRENode007){
                AggregateRENode007 anode = (AggregateRENode007)node;
                items[i+2] = node.type + "," + id + "," + node.startTime + "," + anode.num + "," + anode.sum + "," + anode.max;
            }else {
                items[i+2] = node.type + "," + id + "," + node.startTime + "," + node.duration+(node.extra==null?"":(","+node.extra));
            }
            nodeBuffer.set(i, null);
        }
        MC007.set(nextKey, items);
    }

}
