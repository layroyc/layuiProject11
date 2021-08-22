package com.hp.service;

import com.hp.bean.Visit;
import com.hp.dao.VisitDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitService {
    //增加
    public Map insertVisit(Visit visit){
        Map map = new HashMap();

        VisitDao dao=new VisitDao();
        System.out.println("dao = " + dao);
        int i = dao.insertVisit(visit);
        System.out.println("i = " + i);
        if(i==1){
            map.put("code",0);
            map.put("msg","添加成功");
        }else{
            map.put("code",400);
            map.put("msg","添加不成功");
        }
        return map;
    }

    //全查
    public Map selectAllVisit(Map map){
        VisitDao dao=new VisitDao();
        List<Map> maps=dao.selectAllVisit(map);
        Map codeMap=new HashMap();
        codeMap.put("code",0);
        codeMap.put("data",maps);
        codeMap.put("msg","查询成功！");
        Map countMap =selectAllByParamVisitCount(map);
        int count= (int) countMap.get("data");
        codeMap.put("count",count);

        return codeMap;
    }

    //全查总条数
    public Map selectAllByParamVisitCount(Map map){
        Map codeMap=new HashMap();
        VisitDao dao=new VisitDao();
        int i=dao.selectAllByParamVisitCount(codeMap);
        codeMap.put("code",0);
        codeMap.put("data",i);
        codeMap.put("msg","总条数");
        return codeMap;
    }

    //单个的删除
    public Map VisitdeleteById(Integer id){
        System.out.println("进入到 service 层了---");
        VisitDao dao=new VisitDao();
        int i=dao.deleteByVisitId(id);
        System.out.println("i = " + i);
        Map map=new HashMap();
        if(i==1){
            map.put("code",0);
            map.put("msg","删除成功");
        }else{
            map.put("code",400);
            map.put("msg","删除不成功");
        }
        return map;
    }

    //新增
    public Map addVisit(Visit visit){
        System.out.println("进入到了visit 添加中" + visit);
        Map map = new HashMap();
        VisitDao visitDao = new VisitDao();
        int i = visitDao.addVisit(visit);
        if(i==1){
            map.put("code",0);
            map.put("msg","添加成功");
        }else{
            map.put("code",400);
            map.put("msg","添加不成功");
        }
        return map;
    }


}
