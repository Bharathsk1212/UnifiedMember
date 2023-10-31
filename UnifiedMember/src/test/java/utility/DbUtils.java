package utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.sql.DriverManager;
import utility.Constants;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.spy.memcached.MemcachedClient;
import junit.framework.Assert;

public class DbUtils{
	/**
	 * 
	 */
	/**
	 * 
	 */
	public enum BackendInstance {
		WEBDB, STOREDB
	};

	private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);
	private static Map<BackendInstance, String> currInstance = new HashMap<BackendInstance, String>();
	private static volatile DbUtils instance;
	private static JdbcTemplate jdbc;
	private NamedParameterJdbcTemplate template;
	static Properties prop = new Properties();

	private DbUtils() {
		if (instance != null) {
			throw new RuntimeException();
		}
	}

	public DbUtils(Map<String, Object> config, String db) {
		// if(instance==null) {
		synchronized (DbUtils.class) {
			if (instance == null) {
				currInstance.put(BackendInstance.WEBDB, db);
				instance = new DbUtils();
				String url = "jdbc:mysql://10.204.2.26:3306/" + db + "?useSSL=false";
				String username = "automation_qa";
				String password = "cex@123";
				DriverManagerDataSource dataSource = new DriverManagerDataSource();
				dataSource.setUrl(url);
				// dataSource.setUrl(database);
				dataSource.setUsername(username);
				dataSource.setPassword(password);
				jdbc = new JdbcTemplate(dataSource);
				template = new NamedParameterJdbcTemplate(dataSource);
				logger.info("init jdbc template: {}", url);
			} else {
				currInstance.clear();
				currInstance.put(BackendInstance.STOREDB, db);
				// instance = new DbUtils();
				String url = "jdbc:mysql://10.204.2.26:3306/" + db + "?useSSL=false";
				String username = "automation_qa";
				String password = "cex@123";
				DriverManagerDataSource dataSource = new DriverManagerDataSource();
				dataSource.setUrl(url);
				// dataSource.setUrl(database);
				dataSource.setUsername(username);
				dataSource.setPassword(password);
				jdbc = new JdbcTemplate(dataSource);
				template = new NamedParameterJdbcTemplate(dataSource);
				logger.info("init jdbc template: {}", url);
			}
		}
	}

	// }
	/*
	 * Below constructor is created to get the database session for accessing store
	 * database
	 */
	public DbUtils(Map<String, Object> config) {
		String url = (String) config.get("url");
		String username = (String) config.get("username");
		String password = (String) config.get("password");
		String driver = (String) config.get("driverClassName");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		jdbc = new JdbcTemplate(dataSource);
		logger.info("init jdbc template: {}", url);
	}

	public static String getvalueFrompropFile(String key) {
		String getVal = null;
		try {
			Properties props = new Properties();
			FileInputStream fs = new FileInputStream(
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
							+ "java" + File.separator + "utility" + File.separator + "storedData.properties");
			props.load(fs);
			getVal = props.getProperty(key);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return getVal;
	}

	public static void sleep(long millis) throws InterruptedException {
		try {
			// thread to sleep for 1000 milliseconds
			Thread.sleep(millis);
		} catch (Exception exce) {
			System.out.println(exce.getMessage());
		}
	}

	public static void storevalueInpropFile(String key, String value) throws IOException {
		FileOutputStream fileOut = null;
		FileInputStream fileIn = null;
		try {
			Properties configProperty = new Properties();
			File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "java" + File.separator + "utility" + File.separator + "storedData.properties");
			fileIn = new FileInputStream(file);
			configProperty.load(fileIn);
			configProperty.setProperty(key, value);
			fileOut = new FileOutputStream(file);
			configProperty.store(fileOut, "utilsEssentials");
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			try {
				fileOut.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void storeDoublevalueInpropFile(String key, Double value) throws IOException {
		FileOutputStream fileOut = null;
		FileInputStream fileIn = null;
		try {
			Properties configProperty = new Properties();
			File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "java" + File.separator + "utility" + File.separator + "storedData.properties");
			fileIn = new FileInputStream(file);
			configProperty.load(fileIn);
			configProperty.setProperty(key, value.toString());
			fileOut = new FileOutputStream(file);
			configProperty.store(fileOut, "utilsEssentials");
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			try {
				fileOut.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void storeDoubleOrIntvalueInpropFile(String key, Object value) throws IOException {
		FileOutputStream fileOut = null;
		FileInputStream fileIn = null;
		try {
			Properties configProperty = new Properties();
			File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "java" + File.separator + "utility" + File.separator + "storedData.properties");
			fileIn = new FileInputStream(file);
			configProperty.load(fileIn);
			configProperty.setProperty(key, value.toString());
			fileOut = new FileOutputStream(file);
			configProperty.store(fileOut, "utilsEssentials");
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			try {
				fileOut.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	
	public static int fullRefundWebOrder(String query1, String db, String webDB)
			throws ClassNotFoundException, SQLException {
		java.sql.Connection connection = null;
		java.sql.PreparedStatement preparedStatement = null;
		Class.forName(Constants.Sqldriver);
		ResultSet rs = null;
		ResultSet rs1 = null;
		int value = 0;
		int value2 = 0;
		String value1 = null;
		try {
			// select web.webordernumber from cex.weborderdetail w inner join cex.weborder
			// web on web.WebOrderNumber = w.WebOrderNumber inner join cex.picklist p on
			// w.webordernumber = p.WebOrderNumber inner join cex.picklistdetails pd on
			// p.picklistid = pd.picklistid inner join cex.boxes b on pd.boxid = b.box_id
			// where b.box_id is not null and pd.refunded = 0 and PaymentType=12 and
			// web.orderstatus = 10 and web.orderstatus <> 13 and w.DeliveryCharge <>0 and
			// pd.DeliveryChargeSettled <> 0 and pd.DeliveryChargeRefunded =0 and
			// p.dispatched =1 and pd.found=1 and p.EposBranchKey is not null and
			// p.EposOrderID is not null order by pd.picklistdetailsid
			connection = DriverManager.getConnection(Constants.webDbURL + webDB + "?useSSL=false",
					Constants.webDbUserName, Constants.webDbPassword);
			preparedStatement = connection.prepareStatement(query1);
			rs = preparedStatement.executeQuery();
			// System.out.println(size);
			while (rs.next()) {
				value = rs.getInt(1);
				System.out.println(value);
				value1 = rs.getString(2);
				value2 = rs.getInt(3);
				connection = DriverManager.getConnection("jdbc:sqlserver://10.204.2.188;databaseName=" + db, "QA_user",
						"Q@uSer1!");
				// preparedStatement = connection.prepareStatement("select eposbranchkey ,
				// eposorderid from "+webDB+" ");
				preparedStatement = connection.prepareStatement("select * from dbo.order_details where order_id= "
						+ value2 + " and BranchKey='" + value1 + "' ");
				rs1 = preparedStatement.executeQuery();
				if (rs1.next() == true) {
					return value;
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	@Test
	public static String readValueFromDB(String query, String column, String db)
			throws ClassNotFoundException, SQLException {
		java.sql.Connection connection = null;
		java.sql.PreparedStatement preparedStatement = null;
		Class.forName(Constants.Sqldriver);
		Class.forName(Constants.mySqldriver);
		;
		String value = null;
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection(Constants.epsoStorDBUrl, Constants.epos2000UserName,
					Constants.epos2000Password);
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				try {
					value = String.valueOf(rs.getInt(column));
				} catch (Exception e) {
				}
				try {
					value = String.valueOf(rs.getString(column));
				} catch (Exception e1) {
					value = String.valueOf(rs.getDouble(column));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	@Test
	public  ResultSet readRowsFromDB(String query, String db)
			throws ClassNotFoundException, SQLException {
		java.sql.Connection connection = null;
		java.sql.PreparedStatement preparedStatement = null;
		Class.forName(Constants.Sqldriver);
		Class.forName(Constants.mySqldriver);
		;
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://10.204.2.26:3306/" + db + "?useSSL=false",
					"automation_qa", "cex@123");
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			}
		 catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return rs;
	}

	@Test
	public void clearMemCache(String country, Integer port) throws JSchException, SftpException, IOException, InterruptedException {
		Thread.sleep(2000);
		String SFTPHOST = "10.204.2.86";
		String SFTPUSER = Constants.uatServerUsrName;
		String home = Constants.uatWSSMasterPath;
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(System.getProperty("user.dir") + "/rj_rsa");
			session = jsch.getSession(SFTPUSER, SFTPHOST);
			session.setPassword(System.getProperty("user.dir") + "/rj_rsa");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(home);
			// System.out.println(channelSftp.ls(home));
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(Constants.UATmemcacheIP, port));
			mcc.flush();
			channel.disconnect();
			session.disconnect();
			System.out.println("Cache cleared successfully");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(channelSftp!=null)
			{
				channelSftp.exit();
				System.out.println("sftp Channel exited.");
				channel.disconnect();
				System.out.println("Channel disconnected.");
				session.disconnect();
				System.out.println("Host Session disconnected.");
			}
			
		}
	}

	@Test
	public boolean dbRecordExist(String query, String db) throws ClassNotFoundException, SQLException {
		boolean rowFound = false;
		java.sql.Connection connection = null;
		ResultSet rs = null;
		java.sql.PreparedStatement preparedStatement = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connection = DriverManager.getConnection("jdbc:mysql://10.204.4.52:3306/" + db + "?useSSL=false", "automation_qa",
				"cex@123");
		preparedStatement = connection.prepareStatement(query);
		rs = preparedStatement.executeQuery(query);
		if (rs.next() == false) {
			System.out.println(query + "does not have  any record in table");
		} else {
			rowFound = true;
			System.out.println("Database has record in the table");
		}
		return rowFound;
	}

	// @Test
	@SuppressWarnings("resource")
	public static void cleanRefundData(String query, String db, int weborder)
			throws ClassNotFoundException, SQLException {
		java.sql.Connection connection = null;
		java.sql.PreparedStatement preparedStatement = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		ResultSet rs = null;
		int value = 0;
		// "jdbc:mysql://10.204.4.52:3306/"
		try {
			connection = DriverManager.getConnection("jdbc:mysql://10.204.4.52:3306/" + db + "?useSSL=false", "automation_qa",
					"cex@123");
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery(query);
			while (rs.next()) {
				value = rs.getInt(1);
				connection.prepareStatement("delete  from " + db + ".weborderpayment where webordernumber =" + value)
						.executeUpdate();
				connection.prepareStatement("delete  from " + db + ".weborderdetail where webordernumber =" + value)
						.executeUpdate();
				connection.prepareStatement("delete  from " + db + ".weborder where webordernumber =" + value)
						.executeUpdate();
				if (rs.next() == false) {
					break;
				} else {
					continue;
				}
			}
			preparedStatement = connection
					.prepareStatement("select picklistid from cex.picklist where webordernumber = " + weborder
							+ " order by webordernumber desc limit 1");
			rs = preparedStatement.executeQuery("select picklistid from cex.picklist where webordernumber = " + weborder
					+ " order by webordernumber desc limit 1");
			while (rs.next()) {
				value = rs.getInt(1);
				connection
						.prepareStatement("update " + db + ".picklistdetails set refunded= 0 where picklistid=" + value)
						.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// return value;j

	}

	@Test
	public Object readValue(String query) {
		return jdbc.queryForObject(query, Object.class);
	}

	@Test
	public Map<String, Object> readRow(String query) {
		return jdbc.queryForMap(query);
		// return jdbc1.queryForMap(query, Object.class);
	}

	@Test
	public int cast(String query, String db) throws ClassNotFoundException {
		java.sql.Connection connection = null;
		java.sql.PreparedStatement preparedStatement = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		ResultSet rs = null;
		int value = 0;
		// "jdbc:mysql://10.204.4.52:3306/"
		try {
			connection = DriverManager.getConnection("jdbc:mysql://10.204.2.26:3306/" + db + "?useSSL=false", "automation_qa",
					"cex@123");
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery(query);
			if (rs.next()) {
				value = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static void verifyWSSLog(String country)
			throws JSchException, SftpException, IOException, InterruptedException {
		String SFTPHOST = "10.204.2.121";
		String SFTPUSER = "ec2-user";
		String home = "/var/log/wss";
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(System.getProperty("user.dir") + "/rj_rsa");
			session = jsch.getSession(SFTPUSER, SFTPHOST);
			session.setPassword(System.getProperty("user.dir") + "/rj_rsa");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(home);
			System.out.println(channelSftp.ls(home));

			if (channel.isClosed()) {
				System.out.println("exit-status: " + channel.getExitStatus());
				if (channel.getExitStatus() == 0) {
					System.out.println("Cron log file is present ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		channel.disconnect();
		session.disconnect();
		System.out.println("DONE");
		Thread.sleep(1000);
	}

	@Test
	public void delete(String query, String db) throws ClassNotFoundException, SQLException {
		java.sql.Connection connection = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		connection = DriverManager.getConnection("jdbc:mysql://10.204.2.26:3306/" + db + "?useSSL=false", "automation_qa",
				"cex@123");
		connection.prepareStatement(query).executeUpdate();
	}

	public static void Picklist(String country) throws JSchException, SftpException, IOException {
		String SFTPHOST = "10.204.2.167";
		String SFTPUSER = "ec2-user";
		String home = "/var/www/CWCM.git/master/www/docroot/utility_scripts";
		String command1 = "php /var/www/CWCM.git/master/www/docroot/utility_scripts/create_picklist.php" + country
				+ "cex";
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(System.getProperty("user.dir") + "/rj_rsa");
			session = jsch.getSession(SFTPUSER, SFTPHOST);
			session.setPassword(System.getProperty("user.dir") + "/rj_rsa");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(home);
			System.out.println(channelSftp.ls(home));
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command1);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			String line = "";
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					line = new String(tmp, 0, i);
					System.out.println(line);
				}
				// String result = CharStreams.toString(new InputStreamReader(in));
				// System.out.println(result);
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				channel.setOutputStream(baos);
				;
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					if (channel.getExitStatus() == 0) {
						System.out.println("cron job has successfully executed");
					} else {
						System.out.println("cron job has not successfully executed, please check");
					}
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
		}
	}

	public static void Bazaarvoiceproductfeed(String country) throws JSchException, SftpException, IOException {
		String SFTPHOST = "10.204.2.36";
		String SFTPUSER = "ec2-user";
		String home = "/var/www/wss.git/master/etc/cron.d";
		String command1 = "/usr/bin/php /var/www/wss.git/master/www/wss2/yii bazaarvoice/product-feed uat cex" + country
				+ "v3";
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(System.getProperty("user.dir") + "/rj_rsa");
			session = jsch.getSession(SFTPUSER, SFTPHOST);
			session.setPassword(System.getProperty("user.dir") + "/rj_rsa");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(home);
			System.out.println(channelSftp.ls(home));
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command1);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
		}
	}

	public static void BarclaysPaymentCron(String country)
			throws JSchException, SftpException, IOException, InterruptedException {
		Thread.sleep(720000);
		String SFTPHOST = "10.204.2.167";
		String SFTPUSER = "ec2-user";
		String home = "/var/www/CWCM.git/master/www/docroot/utility_scripts";
		String command1 = "php /var/www/CWCM.git/master/www/docroot/utility_scripts/process_barclays_notification.php"
				+ country + "cex";
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(System.getProperty("user.dir") + "/rj_rsa");
			session = jsch.getSession(SFTPUSER, SFTPHOST);
			session.setPassword(System.getProperty("user.dir") + "/rj_rsa");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(home);
			System.out.println(channelSftp.ls(home));
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command1);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
		}
	}

	public static void advancPicklist(String country) throws InterruptedException {
		Thread.sleep(780000);
		// Thread.sleep(180000);
		String privateKey = System.getProperty("user.dir") + "rj_rsa";
		String SFTPHOST = "10.204.2.167";
		// int SFTPPORT = 22;
		String SFTPUSER = "ec2-user";
		// String SFTPPASS = "password";
		String home = "/var/www/CWCM.git/master/www/docroot/utility_scripts";
		String command1 = "php /var/www/CWCM.git/master/www/docroot/utility_scripts/advance_picklist.php" + country
				+ "cex";
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(System.getProperty("user.dir") + "/rj_rsa");
			session = jsch.getSession(SFTPUSER, SFTPHOST);
			session.setPassword(System.getProperty("user.dir") + "/rj_rsa");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(home);
			System.out.println(channelSftp.ls(home));
			// channelSftp.run();
			// Thread.sleep(100000);
			channel = session.openChannel("exec");
			// channelSftp.chmod(777, home);
			// ((ChannelExec)channel).setCommand(command1);
			// Thread.sleep(200000);
			((ChannelExec) channel).setCommand(command1);
			// Thread.sleep(200000);
			// ((ChannelExec)channel).setCommand(command3);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// channelSftp.cd(runfile);
		finally {
			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
		}
	}

	public static void storeValueInProperties(String name, String value) {
		prop.setProperty(name, value);
	}

	public static String getValueFromPropertiesFile(String data) {
		return prop.getProperty(data);
	}

	@Test
	public int update(String query, String db) throws ClassNotFoundException, SQLException {
		java.sql.Connection connection = null;
		java.sql.PreparedStatement preparedStatement = null;
		Class.forName(Constants.mySqldriver);
		Class.forName(Constants.Sqldriver);
		String dbUrl = null;
		try {
			if (db == "epos2000") {
				dbUrl = Constants.StorDBUrl + db;
				dbconnect_retry(query, db, dbUrl, Constants.epos2000UserName, Constants.epos2000Password);
			} else if (db == "epos2000National") {
				dbconnect_retry(query, db, Constants.epsoSUPDBUrl, Constants.epos2000UserName, Constants.epos2000Password);
			} else if (db.equalsIgnoreCase("epos2000_kidx")) {
				dbconnect_retry(query, db, Constants.epsoSUPDBUrlKidX, Constants.epos2000UserName, Constants.epos2000Password);
			} else if (db == "epos2000DeX") {
				dbconnect_retry(query, db, Constants.epsoSUPDBUrlDeX, Constants.epos2000UserName, Constants.epos2000Password);
			} else {
				dbUrl = Constants.UATDBUrl + db + "?useSSL=false";
				dbconnect_retry(query, db, dbUrl, Constants.dbUsername, Constants.dbPassword);
						}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return dbconnect_retry(query, db, dbUrl, Constants.dbUsername, Constants.dbPassword);
	}

	public synchronized static int dbconnect_retry(String query, String db, String dbUrl, String Username, String Password)
			throws SQLException, ClassNotFoundException {
		int updateCnt = 0;
		int counter = 1;
		java.sql.Connection connection = null;
		java.sql.PreparedStatement preparedStatement = null;
		Class.forName(Constants.mySqldriver);
		Class.forName(Constants.Sqldriver);
		try {
			connection = DriverManager.getConnection(dbUrl, Username, Password);
		preparedStatement = connection.prepareStatement(query);
		do {
			updateCnt = preparedStatement.executeUpdate();
			if (updateCnt > 0) {
				logger.info("Update is successful on query"+" & query is :"+query);
				break;
			} else {
				logger.info("Update is not successful on query; plz check "+"& query is :"+query);
			}
			counter++;
		} while (counter <= Constants.dbretry);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		 finally {
				if (preparedStatement != null) {
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		 }
		return counter;
	}
	public static void callCronjobs(String cronExtension,String country, String productLine) throws JSchException, SftpException, IOException {
		String SFTPHOST = Constants.uatCWCMServer;
		String SFTPUSER = Constants.uatServerUsrName;
		String home = Constants.getCronFolder;
		String command1 = "php "+home+"/"+cronExtension + country + productLine;
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(System.getProperty("user.dir") + "/rj_rsa");
			session = jsch.getSession(SFTPUSER, SFTPHOST);
			session.setPassword(System.getProperty("user.dir") + "/rj_rsa");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(home);
			System.out.println(channelSftp.ls(home));
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command1);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			String line = "";
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					line = new String(tmp, 0, i);
					System.out.println(line);
				}
				// String result = CharStreams.toString(new InputStreamReader(in));
				// System.out.println(result);
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				channel.setOutputStream(baos);
				;
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					if (channel.getExitStatus() == 0) {
						System.out.println("cron job has successfully executed");
					} else {
						System.out.println("cron job has not successfully executed, please check");
					}
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
		}
	}
	@Test
	public void storeUpdate(String query, String db) throws ClassNotFoundException, SQLException {
		java.sql.Connection connection = null;
		java.sql.PreparedStatement preparedStatement = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		try {
			if (db == "epos2000") {
				connection = DriverManager.getConnection("jdbc:sqlserver://10.204.2.178;databaseName=" + db, "QA_user",
						"Q@uSer1!");
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.executeUpdate();
			} else {
				connection = DriverManager.getConnection("jdbc:mysql://10.204.4.52:3306/" + db + "?useSSL=false",
						"automation_qa", "cex@123");
				preparedStatement = connection.prepareStatement(query);
				// preparedStatement.setInt(1117328, MemberId);
				// preparedStatement.setBoolean(0,Active);
				preparedStatement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public List<Map<String, Object>> readRows(String query) {
		return jdbc.queryForList(query);
	}
	@Test
	public static Object toJSON(Object object) throws Exception {
		if (object instanceof HashMap) {
			JSONObject json = new JSONObject();
			HashMap<?, ?> map = (HashMap<?, ?>) object;
			for (Object key : map.keySet()) {
				json.put(key.toString(), toJSON(map.get(key)));
			}
			return json;
		} else if (object instanceof Iterable) {
			JSONArray json = new JSONArray();
			for (Object value : ((Iterable<?>) object)) {
				json.add(toJSON(value));
			}
			return json;
		} else {
			return object;
		}
	}
	@Test
	public String listmap_to_json_string(List<Map<String, Object>> list) throws JsonGenerationException, JsonMappingException, IOException {
		JSONArray json_arr = new JSONArray();
		Object value = null;
		for (Map<String, Object> map : list) {
			JSONObject json_obj = new JSONObject();

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				value = entry.getValue();
				try {
				//	json_obj.put(key, Integer.parseInt(value.toString()));
					mapToString(map);
				} 
				catch (Exception e) {
			//	json_obj.put(key, value);
				mapToString(map);
				continue;
			}
			}
			json_arr.add(mapToString(map));
		}
		return json_arr.toString();
	}
	
	public static  String mapToString( Map map )
	  {
	      if ( ( map == null ) || ( map.size() == 0 ) )
	      {
	          return "";
	      }
	      StringBuffer sb = new StringBuffer();
	      boolean isFirst = true;
	      Iterator iter = map.keySet().iterator();
	      while ( iter.hasNext() )
	      {
	          if ( isFirst )
	          {
	              isFirst = false;
	          }
	          else
	          {
	              sb.append( ", " );
	          }
	          Object key = iter.next();
	          sb.append( key );
	          sb.append( " : '" ).append( map.get( key ) ).append( "'" );
	      }
	      return sb.toString();
	  }

	public Integer iterateVal(List<Integer> nos) {
		int s = 0;
		for (int i = 0; i < nos.size(); i++) {
			s = nos.get(i);
			System.out.println(s);
		}

		return s;
	}
	@Test
	public static String retrieveAvailableBox(String column, String db, String keyval)
			throws ClassNotFoundException, SQLException {
			java.sql.Connection connection = null;
			java.sql.PreparedStatement preparedStatement = null;
			Class.forName(Constants.Sqldriver);
			Class.forName(Constants.mySqldriver);
			ResultSet rs = null;
			int value = 0;
			String available_box = null;
			String strboxes=getvalueFrompropFileforBoxids(keyval);
			String r1=strboxes.replace('[',' ');
			String r2=r1.replace(']',' ');
			String r3=r2.replace('"',' ');
			String[] boxarr = r3.split(",");
			try {
				connection = DriverManager.getConnection(Constants.atwebDbURL + db + "?useSSL=false",
						Constants.webDbUserName, Constants.webDbPassword);
				
				for(int i = 0; i < boxarr.length; i++)
				{
					String query = "SELECT discontinued FROM boxes WHERE Box_id = " + boxarr[i].trim() + " order by stock_date desc";
					preparedStatement = connection.prepareStatement(query);
					rs = preparedStatement.executeQuery();
					
					if (rs.next()) {
						try {
							value = rs.getInt(column);
							if(value == 0) {
								available_box = boxarr[i];
								break;
							}
						} catch (Exception e) {
						}
					}
				}
				
				}
			 catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (preparedStatement != null) {
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return available_box;
		}
		
	public static String getvalueFrompropFileforBoxids(String key) {
		String getVal = null;
		try {
			Properties props = new Properties();
			FileInputStream fs = new FileInputStream(
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
							+ "java" + File.separator + "utility" + File.separator + "storedData.properties");
			props.load(fs);
			getVal = props.getProperty(key);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return getVal;
	}
	
	@Test
	public static String retrieveAvailableDeXBox(String column, String db, String keyval)
			throws ClassNotFoundException, SQLException {
			java.sql.Connection connection = null;
			java.sql.PreparedStatement preparedStatement = null;
			Class.forName(Constants.Sqldriver);
			Class.forName(Constants.mySqldriver);
			ResultSet rs = null;
			int value = 0;
			String available_box = null;
			String strboxes=getvalueFrompropFileforBoxids(keyval);
			String r1=strboxes.replace('[',' ');
			String r2=r1.replace(']',' ');
			String r3=r2.replace('"',' ');
			String[] boxarr = r3.split(",");
			try {
				connection = DriverManager.getConnection(Constants.atwebDbURL + db + "?useSSL=false",
						Constants.webDbUserName, Constants.webDbPassword);
				
				for(int i = 0; i < boxarr.length; i++)
				{
					String query = "SELECT discontinued FROM boxes WHERE Box_id = " + "'"+boxarr[i].toString().trim()+"'" + " order by stock_date desc";
					System.out.println(query);
					preparedStatement = connection.prepareStatement(query);
					rs = preparedStatement.executeQuery();
					
					if (rs.next()) {
						try {
							value = rs.getInt(column);
							if(value == 0) {
								available_box = boxarr[i];
								break;
							}
						} catch (Exception e) {
						}
					}
				}
				
				}
			 catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (preparedStatement != null) {
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			return available_box;
		}
	
	private static String webHooksUrl = "https://hooks.slack.com/services/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";
	private static String oAuthToken = "xoxb-2571531243-2738976287719-1P8LAERf5pXFZdLdqWN7ScNK";
	private static String slackChannel = "bot-regression-testing";
	
//	public static void main(String args[]) {
//		sendMessageToSlacks("Test Second Message on Slack");
//		
//	}
	public static void sendMessageToSlacks(String endpointName, int count) {
		try {
		StringBuilder msgbuilder = new StringBuilder();
		String errorMessage = endpointName+" endpoint has failed. "+count+" dependent scenarios will be skipped in execution.";
		msgbuilder.append(errorMessage);
	Payload payload = Payload.builder().channel(slackChannel).text(msgbuilder.toString()).build();
	WebhookResponse weResp = Slack.getInstance().send(webHooksUrl, payload);
	Assert.assertEquals(errorMessage, true, false);
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	public static void storeCommonValueInpropFile(String key,int status) throws IOException {
		FileOutputStream fileOut = null;
		FileInputStream fileIn = null;
		String value = String.valueOf(status);
		try {
			Properties configProperty = new Properties();
			File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "java" + File.separator + "utility" + File.separator + "storedData.properties");
			fileIn = new FileInputStream(file);
			configProperty.load(fileIn);
			configProperty.setProperty(key, value);
			fileOut = new FileOutputStream(file);
			configProperty.store(fileOut, "reUsable valriables");
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
		}
	}	
}