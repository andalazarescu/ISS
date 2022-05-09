package repository;

import model.ArrivalTime;
import model.ArrivalTimeStatus;
import model.Boss;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class ArrivalTimeDBRepo implements IArrivalTimesRepo{

    private JdbcUtils dbUtils;

    public ArrivalTimeDBRepo(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public void add(ArrivalTime elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into ArrivalTimes (idEmployee, time, status) values (?, ?, ?)"))
        {
            preparedStatement.setInt(1, elem.getIdEmployee());
            preparedStatement.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.valueOf(elem.getTime())));
            preparedStatement.setString(3, elem.getStatus().toString());
            int result = preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public void delete(ArrivalTime elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from ArrivalTimes where id=?"))
        {
            preparedStatement.setInt(1, elem.getID());
            int result = preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public void update(ArrivalTime elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("update ArrivalTimes set idEmployee=?, time=?, status=? where id=? "))
        {
            preparedStatement.setInt(1, elem.getIdEmployee());
            preparedStatement.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.valueOf(elem.getTime())));
            preparedStatement.setString(3, elem.getStatus().toString());
            preparedStatement.setInt(4, elem.getID());
            int result = preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public ArrivalTime findById(Integer id) {
        Connection connection = dbUtils.getConnection();
        ArrivalTime arrivalTime = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from ArrivalTimes where id=?"))
        {
            preparedStatement.setInt(1, id);
            try(ResultSet result = preparedStatement.executeQuery()) {
                int idEmployee = result.getInt("idEmployee");
                LocalDateTime time = Timestamp.valueOf(result.getString("time")).toLocalDateTime();
                ArrivalTimeStatus status = ArrivalTimeStatus.StringToStatus(result.getString("status"));
                arrivalTime = new ArrivalTime(idEmployee, time, status);
                arrivalTime.setID(id);
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return arrivalTime;
    }

    @Override
    public ArrayList<ArrivalTime> getAll() {
        Connection connection = dbUtils.getConnection();
        ArrayList<ArrivalTime> arrivalTimes = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from ArrivalTimes"))
        {
            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next())
                {
                    int id = result.getInt("id");
                    int idEmployee = result.getInt("idEmployee");
                    LocalDateTime time = Timestamp.valueOf(result.getString("time")).toLocalDateTime();
                    ArrivalTimeStatus status = ArrivalTimeStatus.StringToStatus(result.getString("status"));
                    ArrivalTime arrivalTime = new ArrivalTime(idEmployee, time, status);
                    arrivalTime.setID(id);
                    arrivalTimes.add(arrivalTime);
                }
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return arrivalTimes;
    }
}
