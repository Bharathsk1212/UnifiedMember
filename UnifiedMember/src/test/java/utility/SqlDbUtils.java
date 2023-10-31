package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import static java.lang.Math.abs;
import static java.lang.String.format;
import java.util.Random;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import static java.util.Locale.ENGLISH;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SqlDbUtils {
	private final JdbcTemplate jdbc;
	private static final Logger logger = LoggerFactory.getLogger(SqlDbUtils.class); 
	public SqlDbUtils(Map<String, Object> config) {

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
	
	@Test
	public List<Map<String, Object>> readRows(String query) {
		return jdbc.queryForList(query);
	}
}