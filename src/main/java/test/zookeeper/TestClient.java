package test.zookeeper;

import java.io.IOException;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class TestClient implements Watcher {

	ZooKeeper zk;
	String hostPort;
	TestClient(String hostPort) {
		this.hostPort = hostPort;
	}
	void startZK() throws IOException {
		zk = new ZooKeeper(hostPort, 15000, this);
	}
	public void process(WatchedEvent e) {
		System.out.println(e);
	}
	public static void main(String args[])
			throws Exception {
		String zookeeperConnectionString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
		client.start();

		TestClient m = new TestClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183");
		m.startZK();
		m.zk.getSessionId();
		m.zk.close();
		// wait for a bit
		Thread.sleep(60000);
	}
}
