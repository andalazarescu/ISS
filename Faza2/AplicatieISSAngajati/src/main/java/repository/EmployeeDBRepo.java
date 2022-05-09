package repository;

import model.Employee;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.SimpleTimeZone;

public class EmployeeDBRepo implements IEmployeeRepo{
    private JdbcUtils dbUtils;

    public EmployeeDBRepo(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Employee findOneByUsername(String username) {
        Connection connection = dbUtils.getConnection();
        Employee employee = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from Employees where username=?"))
        {
            preparedStatement.setString(1, username);
            try(ResultSet result = preparedStatement.executeQuery()) {
                int id = result.getInt("id");
                String password = result.getString("password");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                employee = new Employee(username, password, firstName, lastName);
                employee.setID(id);
                LocalTime time = Timestamp.valueOf(result.getString("arrivalTime")).toLocalDateTime().toLocalTime();
                LocalDateTime date = LocalDate.now().atTime(time);
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return employee;
    }

    @Override
    public void add(Employee elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into Employees (username, password, firstName, lastName) values (?, ?, ?, ?)"))
        {
            preparedStatement.setString(1, elem.getUsername());
            preparedStatement.setString(2, elem.getPassword());
            preparedStatement.setString(3, elem.getFirstName());
            preparedStatement.setString(4, elem.getLastName());
            int result = preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public void delete(Employee elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from Employees where id=?"))
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
    public void update(Employee elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("update Employees set username=?, password=?, firstName=?, lastName=? where id=? "))
        {
            preparedStatement.setString(1, elem.getUsername());
            preparedStatement.setString(2, elem.getPassword());
            preparedStatement.setString(3, elem.getFirstName());
            preparedStatement.setString(4, elem.getLastName());
            preparedStatement.setInt(5, elem.getID());
            int result = preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public Employee findById(Integer id) {
        Connection connection = dbUtils.getConnection();
        Employee employee = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from Employees where id=?"))
        {
            preparedStatement.setInt(1, id);
            try(ResultSet result = preparedStatement.executeQuery()) {
                String username = result.getString("username");
                String password = result.getString("password");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                employee = new Employee(username, password, firstName, lastName);
                employee.setID(id);
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return employee;
    }

    @Override
    public ArrayList<Employee> getAll() {
        Connection connection = dbUtils.getConnection();
        ArrayList<Employee> employees = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from Employees"))
        {
            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next())
                {
                    int id = result.getInt("id");
                    String password = result.getString("password");
                    String username = result.getString("username");
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    Employee employee = new Employee(username, password, firstName, lastName);
                    employee.setID(id);
                    employees.add(employee);
                }
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return employees;
    }
}
