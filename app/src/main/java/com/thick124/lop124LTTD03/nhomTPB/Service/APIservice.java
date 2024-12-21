package com.thick124.lop124LTTD03.nhomTPB.Service;

public class APIservice {
    private static String base_url="http://tpbcuoiki.atwebpages.com/server/";
    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
