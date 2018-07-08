// ----------------------------------------------------------------------------
// Copyright 2013, VHCSoft JSC.
// All rights reserved
// ----------------------------------------------------------------------------
// Change History:
//  2013.12.11  datnh
//     - Initial release
// ----------------------------------------------------------------------------

package vn.com.vhc.manager;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.vhc.common.AppConfig;
import vn.com.vhc.common.AppEnv;
import vn.com.vhc.exception.AppConfigException;
import vn.com.vhc.util.DbUtil;
import vn.com.vhc.util.QueueUtil;

/**
 * Alarm forwarding server
 * @author datnh
 */
public class AlarmForwardingServer {

	private static final Logger logger = LoggerFactory.getLogger(AlarmForwardingServer.class);
	
	private static ChannelFactory factory = null;
	private static ServerBootstrap bootstrap = null;
	private static ChannelGroup channels = new DefaultChannelGroup(AlarmForwardingServer.class.getName());
	
	/**
	 * Start server socket
	 * @throws AppConfigException 
	 */
	public static void start() throws AppConfigException {
		// database util
		DbUtil.init();
		
		// message queue util
		QueueUtil.init();
		
		factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),  Executors.newCachedThreadPool());
		
		bootstrap = new ServerBootstrap(factory);
		
		bootstrap.setPipelineFactory(new AlarmPipelineFactory());
		
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		
		PropertiesConfiguration config = AppConfig.getPropertiesConfig();
		
		int port = config.getInt(AppEnv.PORT, AppEnv.DEFAULT_PORT);
		
		Channel channel = bootstrap.bind(new InetSocketAddress(port));
		channels.add(channel);
		
		logger.info("[INFO] Start Alarm forwarding at port {}, channel {}", port, channel.getId());
	}
	
	/**
	 * Stop server
	 */
	public static void close() {
		ChannelGroupFuture future = channels.close();
		future.awaitUninterruptibly();
		factory.releaseExternalResources();
		logger.info("[INFO] Stop Server.. done!");
	}
	
	public static void main(String arg[]) throws AppConfigException {
		start();
	}
	
}
