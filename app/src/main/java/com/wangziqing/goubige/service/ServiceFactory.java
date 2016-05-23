package com.wangziqing.goubige.service;


/**
 * Created by WZQ_PC on 2016/2/19 0019.
 */
public class ServiceFactory {
    private ServiceFactory(){

    }
    private static final GoodService goodService=new GoodService();
    private static final SortsService sortsService =new SortsService();
    private static final UsersService usersService =new UsersService();
    private static final ShareService shareService =new ShareService();
    private static final CommentService commentService=new CommentService();
    public static GoodService getGoodService(){
        return goodService;
    }
    public static SortsService getSortsService(){
        return sortsService;
    }
    public static UsersService getUsersService(){
        return usersService;
    }
    public static ShareService getShareService(){return shareService;}
    public static CommentService getCommentService(){
        return commentService;
    }
}
