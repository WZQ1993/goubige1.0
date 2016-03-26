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
    public static GoodService getGoodService(){
        return goodService;
    }
    public static SortsService getSortsService(){
        return sortsService;
    }
    public static UsersService getUsersService(){
        return usersService;
    }
}
