package com.office.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @ClassName: JdbcPool
 * @Description:编写数据库连接池
 * @author: 贾守会
 * @date: 2018-5-4 下午10:07:23
 *
 */
public class JdbcPool {

	/**
	 * Connections 使用Vector来存放数据库链接，Vector具备线程安全性
	 */
	private static Vector<Connection> Connections = new Vector<Connection>();

	static {
		// 在静态代码块中加载ApplicationContext.xml数据库配置文件
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("spring/ApplicationContext.xml");
			DriverManagerDataSource dataSource = (DriverManagerDataSource) context.getBean("dataSource");
			// 数据库连接池的初始化连接数大小
			int jdbcPoolInitSize = 20;

			for (int i = 0; i < jdbcPoolInitSize; i++) {
				Connection conn = dataSource.getConnection();
				// 将获取到的数据库连接加入到Connections集合中，Connections此时就是一个存放了数据库连接的连接池
				Connections.addElement(conn);
			}
		} catch (SQLException e) {
			System.out.println(" 创建数据库连接失败！ " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接 getConnection()
	 */
	public static Connection getConnection() throws SQLException {
		// 如果数据库连接池中的连接对象的个数大于0
		if (Connections.size() > 0) {
			// 从Connections集合中获取一个数据库连接
			final Connection conn = (Connection) Connections.remove(0);
			// 返回Connection对象的代理对象
			return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(),
					new InvocationHandler() {

						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (!method.getName().equals("close")) {
								return method.invoke(conn, args);
							} else {
								// 如果调用的是Connection对象的close方法，就把conn还给数据库连接池
								Connections.addElement(conn);
								return null;
							}
						}
					});
		} else {
			throw new RuntimeException("对不起，数据库忙");
		}
	}

	/**
	 * 关闭conn和statement的操作
	 * 
	 * @param statement
	 * @param conn
	 */
	public static void release(Statement statement, Connection conn) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e2) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 关闭conn和statement的操作
	public static void release(ResultSet rs, Statement statement, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e2) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}