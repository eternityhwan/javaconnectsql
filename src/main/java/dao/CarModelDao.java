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

public class CarModelDao {
    private JdbcTemplate jdbcTemplate;

    public CarModelDao() {
        this.jdbcTemplate = new JdbcTemplate(dataSource());
    }

    public DataSource dataSource() {
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
        List<CarModel> carModels = carModelDao.getall();
        for (CarModel carModel : carModels) {
            System.out.printf("%d %s %n", carModel.getId(), carModel.getName());
        }
    }
}
