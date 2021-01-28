package TestCases;

//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestUserStories {

    @Test
    void Data_is_stored_in_a_Database()
    {
    	try {
			Connection conn = DriverManager.getConnection(
					"jdbc:postgresql://localhost/bank",
					"postgres",
					"postgrespassword"
					);
			assertTrue(conn.isValid(1));
			String stmt = "SELECT transaction_id, src_account,dest_account, status, amount, act, created_at "
					+ "FROM transaction_logs WHERE dest_account IS NOT NULL";
			PreparedStatement pstmt = conn.prepareStatement(stmt);
			ResultSet rs = pstmt.executeQuery();
			assertTrue(rs.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

