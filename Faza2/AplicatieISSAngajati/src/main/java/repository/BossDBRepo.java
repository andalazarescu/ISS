package repository;

import model.Boss;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class BossDBRepo implements IBossRepo{
    private JdbcUtils dbUtils;

    public BossDBRepo(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public Boss findOneByUsername(String username) {
        Connection connection = dbUtils.getConnection();
        Boss boss = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from Bosses where username=?"))
        {
            preparedStatement.setString(1, username);
            try(ResultSet result = preparedStatement.executeQuery()) {
                int id = result.getInt("id");
                String password = result.getString("password");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                boss = new Boss(username, password, firstName, lastName);
                boss.setID(id);
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return boss;
    }

    @Override
    public void add(Boss elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into Bosses (username, password, firstName, lastName) values (?, ?, ?, ?)"))
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
    public void delete(Boss elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from Bosses where id=?"))
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
    public void update(Boss elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("update Bosses set username=?, password=?, firstName=?, lastName=? where id=? "))
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
    public Boss findById(Integer id) {
        Connection connection = dbUtils.getConnection();
        Boss boss = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from Bosses where id=?"))
        {
            preparedStatement.setInt(1, id);
            try(ResultSet result = preparedStatement.executeQuery()) {
                String username = result.getString("username");
                String password = result.getString("password");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                boss = new Boss(username, password, firstName, lastName);
                boss.setID(id);
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return boss;
    }

    @Override
    public ArrayList<Boss> getAll() {
        Connection connection = dbUtils.getConnection();
        ArrayList<Boss> bosses = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from Bosses"))
        {
            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next())
                {
                    int id = result.getInt("id");
                    String password = result.getString("password");
                    String username = result.getString("username");
                    String firstName = result.getString("firstName");
                    String lastName = result.getString("lastName");
                    Boss boss = new Boss(username, password, firstName, lastName);
                    boss.setID(id);
                    bosses.add(boss);
                }
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return bosses;
    }
}
