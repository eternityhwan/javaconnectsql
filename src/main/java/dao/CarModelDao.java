package dao;

import domain.CarModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CarModelDao { // DATA ACCESS OBJECT
    private JdbcTemplate jdbcTemplate; // JDBCTEMPLATE란? JDBC를 위한 틀. -- JDBC 프로그래밍에 특화.



    public CarModelDao() { // 위에서 선언한 jdbcTemplate 를 여기서 초기화 하는 거임.
        this.jdbcTemplate = new JdbcTemplate(dataSource());
    }

    public DataSource dataSource() {
        // 이것은 데이터 소스를 리턴해줄 것이야. 디비와 연결하려면 호스트 아이디 패스워드 디비네임 이것을 하나로 묶은것이
        // 데이터 소스임
        // JDBC 템플릿 - 스프링에서 제공함 - 이 템플릿은 SQL 워크벤치같은거임
        // 워크벤치는 쿼리를 날리수 있음 ,셀렉트 인서트 같은 것들.
        // JDBC(JAVA DATABASES CONNECTOR)의 줄임말.
        // 한마디로 내가 연결해야하는 데이터 베이스 정보를 입력하는 것임.
        // 이 파일에서는 메이븐 XML 파일에 스프링 JDBC 다운을 받아놓았음.
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/carlkim");
        dataSource.setUsername("root");
        dataSource.setPassword("3642");
        return  dataSource;
    }
    public List<CarModel> getall() {
        return this.jdbcTemplate.query("select * from car_model", new RowMapper<CarModel>() {
            @Override
            public CarModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                CarModel carModel = new CarModel();
                carModel.setId(rs.getLong("id"));
                carModel.setName(rs.getString("name"));
                return carModel;
            }
        });
    }

    public static void main(String[] args) {
        CarModelDao carModelDao = new CarModelDao();
        List<CarModel> carModels = carModelDao.getall(); // sql에 select * 이거를 getall로 쓰는거임
        for (CarModel carModel : carModels) {
            System.out.printf("%d %s %n", carModel.getId(), carModel.getName());
        }
    }
}