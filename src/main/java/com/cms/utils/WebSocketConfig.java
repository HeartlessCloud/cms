//package com.cms.utils;
//
////import com.cms.config.WebConfig;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//
//@Component
//@Slf4j
//@ServerEndpoint("/websocket/{page-name}") //暴露的ws应用的路径
//public class WebSocketConfig {
//    @Autowired
//    private GetInfoMapper getInfoMapper;
//
//    private final Gson gson = new Gson();
//
//    /**
//     * 客户端与服务端连接成功时
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("page-name") String pageName){
//        /*
//            do something for onOpen
//            与当前客户端连接成功时
//         */
//        log.info("Connected to client: " + pageName);
//
//        // Fetch data using WebSocketService
//        String competitionNames = gson.toJson(getInfoMapper.getLatestCompetitionNames());
//        log.info("比赛名称列表为：{}", competitionNames);
//
//        try {
//            // Send the data to the client
//            session.getBasicRemote().sendText(competitionNames);
//        } catch (IOException e) {
//            log.error("Error sending data to client: " + pageName, e);
//        }
//    }
//
//
//    /**
//     * 客户端与服务端连接关闭
//     * @param session
//     * @param username
//     */
//    @OnClose
//    public void onClose(Session session,@PathParam("username") String username){
//        /*
//            do something for onClose
//            与当前客户端连接关闭时
//         */
//
//    }
//
//    /**
//     * 客户端与服务端连接异常
//     * @param error
//     * @param session
//     * @param username
//     */
//    @OnError
//    public void onError(Throwable error,Session session,@PathParam("username") String username) {
//        /*
//            do something for onError
//            与当前客户端连接异常时
//         */
//        error.printStackTrace();
//    }
//
//    /**
//     * 客户端向服务端发送消息
//     * @param message
//     * @param username
//     * @throws IOException
//     */
//    @OnMessage
//    public void onMsg(Session session,String message,@PathParam("username") String username) throws IOException {
//        /*
//            do something for onMessage
//            收到来自当前客户端的消息时
//         */
//
//    }
//
//}
//
