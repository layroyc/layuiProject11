package com.hp.dao;

import com.hp.bean.Visit;
import com.hp.util.DBHelper;
import com.hp.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitDao {
    //增加
    public int insertVisit(Visit visit){
        //1.加载连接
        Connection connection = DBHelper.getConnection();
        //2.书写sql语句
        String  sql = "insert into t_visit values (null,?,?,?,?,?)";
        //预编译
        PreparedStatement ps=null;
        int i=0;
        try {
            ps=connection.prepareStatement(sql);
            ps.setInt(1,visit.getUser_id());
            ps.setInt(2,visit.getCust_id());
            ps.setString(3,visit.getVisit_desc());
            ps.setString(4,visit.getVisit_time());
            ps.setString(5,visit.getCreate_time());
            //4.执行预编译对象
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

   // 多表联查的 全查
    public List<Map> selectAllVisit(Map map){
        List lists = new ArrayList();
        String page = (String) map.get("page");
        String limit = (String) map.get("limit");

        String user_id= (String) map.get("user_id");
        String cust_id= (String) map.get("cust_id");
        String cust_name= (String) map.get("cust_name");
        String username= (String) map.get("username");
        String visit_time= (String) map.get("visit_time");


        //1.创建出 连接对象
        Connection connection = DBHelper.getConnection();
        //2.创建出SQL语句
       // String sql = "select v.*,c.cust_name as cust_name,u.username as username from t_visit v join t_customer c on c.id=v.cust_id join t_user u on u.id=v.user_id ORDER BY v.visit_time desc,c.cust_name desc ";
       String sql = "select v.*,c.cust_name as cust_name,u.username as username from t_visit v join t_customer c on c.id=v.cust_id join t_user u on u.id=v.user_id where 1=1 ";
        if (null!=user_id&&user_id.length()>0){
            sql = sql + " and v.user_id   =  "+user_id+"   ";
        }
        if (null!=cust_id&&cust_id.length()>0){
            sql = sql + " and v.cust_id   =  "+cust_id+"   ";
        }
        if (null!=cust_name&&cust_name.length()>0){
            sql = sql + " and cust_name   like  '%"+cust_name+"%'   ";
        }
        if (null!=username&&username.length()>0){
            sql = sql + " and u.username   like  '%"+username+"%'   ";
        }
        if (null!=visit_time&&visit_time.length()>0){
            sql = sql + " and v.visit_time   like  '%"+visit_time+"%'   ";
        }
        sql = sql+" ORDER BY v.visit_time desc,cust_name desc";
        sql = sql + " limit  ? ,  ?";
        System.out.println(" +++++++++++++++++++");
        System.out.println(" dao de limit sql = " + sql);

        //3.使用连接对象 获取 预编译对象
        PreparedStatement ps = null;
        ResultSet rs = null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page), Integer.parseInt(limit));//因为第一个需要?求出来
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());//这是索引
            ps.setInt(2,Integer.parseInt(limit));

            //4.执行sql
            rs = ps.executeQuery();
            while(rs.next()){
                Map dataMap=new HashMap();
                dataMap.put("id",rs.getInt("id"));
                dataMap.put("user_id",rs.getInt("user_id"));
                dataMap.put("cust_id",rs.getInt("cust_id"));
                dataMap.put("visit_desc",rs.getString("visit_desc"));
                dataMap.put("visit_time",rs.getString("visit_time"));
                dataMap.put("create_time",rs.getString("create_time"));
                dataMap.put("cust_name",rs.getString("cust_name"));
                dataMap.put("username",rs.getString("username"));

                lists.add(dataMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }


    //2.带参数的查总条数
    public int selectAllByParamVisitCount(Map map){
        int total=0;
        //1.加载连接
        Connection connection = DBHelper.getConnection();
        //2.书写sql语句
        String  sql = "select count(*) total from t_visit v join t_customer c on c.id=v.cust_id join t_user u on u.id=v.user_id where 1=1";
        //3.预编译
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            //4.执行
            rs=ps.executeQuery();
            if(rs.next()){
                total=rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    // 删除
    public int deleteByVisitId(Integer id) {
        //1.加载连接
        Connection connection = DBHelper.getConnection();
        //2.书写sql语句
        String sql = "delete from t_visit where id=?";
        //预编译
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            //执行
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return i;

        }
    }

    //新增
    public int addVisit(Visit visit){
        //1.创建出 连接对象
        Connection conn = DBHelper.getConnection();
        //2.创建出SQL语句
        String sql = "insert into t_visit values (null,?,?,?,?,?)";
        PreparedStatement ps = null;
        int i = 0;
        try {
            //3.使用连接对象 获取 预编译对象
            ps = conn.prepareStatement(sql);
            ps.setInt(1,visit.getUser_id());
            ps.setInt(2,visit.getCust_id());
            ps.setString(3,visit.getVisit_desc());
            ps.setString(4,visit.getVisit_time());
            ps.setString(5,visit.getCreate_time());
            //4.执行预编译对象
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    public static void main(String[] args) {

    }

}
