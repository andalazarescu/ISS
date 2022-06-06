package repository;

import model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class RequirementDBRepo implements IRequirementRepo{

    private JdbcUtils dbUtils;

    public RequirementDBRepo(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public void add(Requirement elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into Requirements (titlu, descriere, weight, employeeID, status) values (?, ?, ?, ?, ?)"))
        {
            preparedStatement.setString(1, elem.getTitlu());
            preparedStatement.setString(2, elem.getDescriere());
            preparedStatement.setString(3, elem.getWeight().toString());
            preparedStatement.setInt(4, elem.getEmployeeID());
            preparedStatement.setString(5, elem.getStatus().toString());
            int result = preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public void delete(Requirement elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from Requirements where id=?"))
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
    public void update(Requirement elem) {
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("update Requirements set descriere=?, weight=?, employeeID=?, status=? where titlu=? "))
        {
            preparedStatement.setString(1, elem.getDescriere());
            preparedStatement.setString(2, elem.getWeight().toString());
            preparedStatement.setInt(3, elem.getEmployeeID());
            preparedStatement.setString(4, elem.getStatus().toString());
            preparedStatement.setString(5, elem.getTitlu());
            int result = preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
    }

    @Override
    public Requirement findById(Integer id) {
        Connection connection = dbUtils.getConnection();
        Requirement requirement = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from Requirements where id=?"))
        {
            preparedStatement.setInt(1, id);
            try(ResultSet result = preparedStatement.executeQuery()) {
                String titlu = result.getString("titlu");
                String descriere = result.getString("descriere");
                Weight weight = Weight.StringToWeight(result.getString("weight"));
                int employeeID = result.getInt("employeeID");
                RequirementStatus status = RequirementStatus.StringToStatus(result.getString("status"));
                requirement = new Requirement(titlu, descriere, weight, employeeID);
                requirement.setStatus(status);
                requirement.setID(id);
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return requirement;
    }

    @Override
    public Collection<Requirement> getAll() {
        Connection connection = dbUtils.getConnection();
        ArrayList<Requirement> requirements = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("Select * from Requirements"))
        {
            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next())
                {
                    int id = result.getInt("id");
                    String titlu = result.getString("titlu");
                    String descriere = result.getString("descriere");
                    Weight weight = Weight.StringToWeight(result.getString("weight"));
                    int employeeID = result.getInt("employeeID");
                    RequirementStatus status = RequirementStatus.StringToStatus(result.getString("status"));
                    Requirement requirement = new Requirement(titlu, descriere, weight, employeeID);
                    requirement.setID(id);
                    requirement.setStatus(status);
                    requirements.add(requirement);
                }
            }
        } catch (SQLException ex)
        {
            System.err.println("Error DB" + ex);
        }
        return requirements;
    }
}
