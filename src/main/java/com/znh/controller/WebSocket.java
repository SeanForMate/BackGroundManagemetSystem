package com.znh.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.znh.model.commons.Message;
import com.znh.model.commons.ReponseMessage;
import com.znh.util.PublicUtil;
import com.znh.util.RedisUtil;

@ServerEndpoint(value="/websocket/{userno}")
@Component
public class WebSocket {
	
	//  这里使用静态，让 service 属于类
    private static RedisUtil redisUtil;
	
	@Autowired
	public void setRedisUtil(RedisUtil redisUtil) {
		WebSocket.redisUtil = redisUtil;
	}

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<String, WebSocket>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session WebSocketsession;
	// 当前发消息的人员编号
	private String userno = "";

	/**
	 * 连接建立成功调用的方法
	 *
	 * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam(value = "userno") String param, Session WebSocketsession, EndpointConfig config) {
		this.userno = param;
		this.WebSocketsession = WebSocketsession;
		// 将用户连接上来的对象存到一个线程安全的Set中
		webSocketSet.put(param, this);
		// 判断用户是否有要消费的消息记录
		Map<Object,Object> dataMap = new HashMap<Object,Object>();
		// 判断是否有好友发送消息的key是否存在
		if(redisUtil.exists(param+"Count")){
			dataMap = redisUtil.hmget(param+"Count");
			if(dataMap.size()>0) {
				ReponseMessage rm = new ReponseMessage(0,"成功",dataMap);
				try {
					webSocketSet.get(param).sendMessage(JSON.toJSONString(rm));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// addOnlineCount();
		// System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
     * 连接关闭调用的方法
     */
	@OnClose
	public void onClose() {
		if(!userno.equals("")) {
			webSocketSet.remove(userno);  //从set中删除
			// subOnlineCount();		//在线数减1
			// System.out.println("有一连接关闭！当前在线人数为"+getOnlineCount());
		}
	}

	
	/**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		// 接收到的消息进行JSON格式化
		Message msgInfo = JSON.parseObject(message, Message.class);
		String uuid = msgInfo.getUuid();
		String fromUserId = msgInfo.getFormUser();
		String formUserName = msgInfo.getFormUserName();
		String sendUserId = msgInfo.getSendUser();
		String sendMessage = msgInfo.getMsgText();
		// 发送时间
		String now = getNowTime();
		// 判断接收人是否在线
		if (webSocketSet.get(sendUserId) != null) {
			try {
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("fromUserId", fromUserId);
				dataMap.put("formUserName", formUserName);
				dataMap.put("msg", sendMessage);
				dataMap.put("time", now);
				ReponseMessage rm = new ReponseMessage(1,"成功",dataMap);
				webSocketSet.get(sendUserId).sendMessage(JSON.toJSONString(rm));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			// 用户不在线将消息存到redis数据库中
			Map<String,Object> msgDataMap = new HashMap<String,Object>();
			msgDataMap.put("fromUserId", fromUserId);
			msgDataMap.put("formUserName", formUserName);
			msgDataMap.put("msg", sendMessage);
			// 消息存储
			redisUtil.hmset(sendUserId, uuid, JSON.toJSONString(msgDataMap));
			// 好友发送消息记录
			redisUtil.hmset(sendUserId+"Count", fromUserId, Integer.toString(1));
		}
	}
		
	/**
     * 给指定的人发送消息
     * @param message
     */
	//@OnMessage
	public void sendToUser(String message) {
		// 接收到的消息进行JSON格式化
		Message msgInfo = JSON.parseObject(message, Message.class);
		String uuid = msgInfo.getUuid();
		String fromUserId = msgInfo.getFormUser();
		String formUserName = msgInfo.getFormUserName();
		String sendUserId = msgInfo.getSendUser();
		String sendMessage = msgInfo.getMsgText();
		// 发送时间
		String now = getNowTime();
		// 
		Message msg = new Message(uuid,fromUserId,formUserName,sendUserId,sendMessage);
		// 判断缓存中是否有目标好友的消息
		if(PublicUtil.dataMap.get(sendUserId)!=null) {
			PublicUtil.dataMap.get(sendUserId).add(msg);
		}else {
			// 消息存放在缓存中
			List<Message> dataList = new ArrayList<Message>();
			dataList.add(msg);
			PublicUtil.dataMap.put(sendUserId, dataList);
		}
		try {
			//	给目标好友发送消息存在缓存中先
			//List<Message> list = PublicUtil.dataMap.get(sendUserId);
			
			
			if (webSocketSet.get(sendUserId) != null) {
				webSocketSet.get(sendUserId).sendMessage(now + "用户" + userno + "发来消息：" + " <br/> " + sendMessage);
			}else {
				System.out.println("当前用户不在线");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
     * 给所有人发消息
     * @param message
     */
	@SuppressWarnings("unused")
	private void sendAll(String message) {
		String now = getNowTime();
		String sendMessage = message.split("[|]")[0];
		//遍历HashMap
		for(String key : webSocketSet.keySet()) {
			//判断接收用户是否是当前发消息的用户
			try {
				if (!userno.equals(key)) {
					webSocketSet.get(key).sendMessage(now + "用户" + userno + "发来消息：" + " <br/> " + sendMessage);
					System.out.println("key = " + key);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	/**
     * 获取当前时间
     *
     * @return
     */
	private String getNowTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		return time;
	}
		
	/**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}


	/**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
	 public void sendMessage(String message) throws IOException {
		this.WebSocketsession.getBasicRemote().sendText(message);
	}


	public static synchronized int getOnlineCount() {
		return onlineCount;
	}


	public static synchronized void addOnlineCount() {
		WebSocket.onlineCount++;
	}


	public static synchronized void subOnlineCount() {
		WebSocket.onlineCount--;
	}
}