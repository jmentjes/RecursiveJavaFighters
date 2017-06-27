package model.com.impl;

import de.github.GSGJ.API.json.JSONCore;
import de.github.GSGJ.API.netty.nettycom.Decoder;
import de.github.GSGJ.API.netty.nettycom.Encoder;
import model.com.AbstractTransceiver;
import model.com.ClientHandler;
import model.com.eventmanagement.AsynchronTaskHandler;
import model.com.eventmanagement.events.*;
import model.eventsystem.Event;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by Kojy on 26.06.2017.
 */
public class TransceiverImpl extends AbstractTransceiver {
    private static Logger logger = LoggerFactory.getLogger(TransceiverImpl.class);
    protected InetAddress inetAddress;
    protected int port;
    private ChannelFactory clientFactory;
    private ChannelGroup channelGroup;
    private ClientHandler handler;
    private AsynchronTaskHandler asynchronTaskHandler;

    public TransceiverImpl() {
        this(false, null, 0);
    }

    public TransceiverImpl(boolean connect, InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;

        this.asynchronTaskHandler = new AsynchronTaskHandler(this);
        Thread thread = new Thread(this.asynchronTaskHandler);
        thread.setDaemon(true);
        thread.start();

        if (connect) {
            this.start();
        }
    }

    public void start() {
        // For production scenarios, use limited sized thread pools
        this.clientFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        this.channelGroup = new DefaultChannelGroup(this + "-channelGroup");
        this.handler = new ClientHandler(channelGroup, asynchronTaskHandler);
        ChannelPipelineFactory pipelineFactory = new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("encoder", new Encoder());
                pipeline.addLast("decoder", new Decoder());
                pipeline.addLast("handler", handler);
                return pipeline;
            }
        };

        ClientBootstrap bootstrap = new ClientBootstrap(this.clientFactory);
        bootstrap.setOption("reuseAddress", true);
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
        bootstrap.setPipelineFactory(pipelineFactory);


        boolean connected = bootstrap.connect(new InetSocketAddress(inetAddress, port)).awaitUninterruptibly().isSuccess();
        if (!connected) {
            this.stop();
        }
    }

    public void stop() {
        if (this.channelGroup != null) {
            this.channelGroup.close();
        }
        if (this.clientFactory != null) {
            this.clientFactory.releaseExternalResources();
        }
    }

    public void send(JSONObject object) {
        this.handler.sendMessage(object);
    }

    public void receive(JSONObject jsonObject) {
        String service = (String) jsonObject.get(JSONCore.CORE.SERVICE.getId());
        Event event = null;

        switch (service) {
            case "de.github.GSGJ.services.usermanagement.UserManagementService":
                event = new UsermanagementEvent(jsonObject);
                break;
            case "de.github.GSGJ.services.requestmanagement.RequestManagementService":
                event = new RequestManagementEvent(jsonObject);
                break;
            case "de.github.GSGJ.services.lobbymanagement.LobbyManagementService":
                event = new LobbyManagementEvent(jsonObject);
                break;
            case "de.github.GSGJ.services.gamemanagement.GameManagementService":
                event = new GameManagementEvent(jsonObject);
                break;
            case "de.github.GSGJ.services.chatmanagement.ChatManagementService":
                event = new ChatManagementEvent(jsonObject);
                break;
            default:
                logger.error("Service not recognized");
                break;
        }
        if (event == null) return;

        notifyListeners(event);
    }
}
