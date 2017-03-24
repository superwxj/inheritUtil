package com.mkyong.customer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mkyong.customer.dao.CustomerDAO;
import com.mkyong.customer.model.Customer;


/***
 * 搜索库中的表名
 *SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'appconfig_dev'
 */

/****
 * 搜索某个表的字段名，类型，备注 等
 SELECT
 COLUMN_NAME as '列名',
 COLUMN_COMMENT as '备注',
 DATA_TYPE as '字段类型' ,
 COLUMN_TYPE as '长度加类型'
 FROM `information_schema`.`COLUMNS` where `TABLE_SCHEMA`='appconfig_dev' and `TABLE_NAME`='activity' order by COLUMN_NAME;
 *
 * */
public class JdbcCustomerDAO implements CustomerDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Customer customer) {

        String sql = "INSERT INTO CUSTOMER " +
                "(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customer.getCustId());
            ps.setString(2, customer.getName());
            ps.setInt(3, customer.getAge());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public Customer findByCustomerId(int custId) {
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'appconfig_dev'";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            Customer customer = null;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getObject("TABLE_NAME"));
            }
           /* if (rs.next()) {
            }*/
            rs.close();
            ps.close();
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    @Override
    public List<Object> findAllTableName(String dataName) {
        String sql = "";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<Object> tableNameList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tableNameList.add(rs.getObject("TABLE_NAME"));
            }
            return tableNameList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}




