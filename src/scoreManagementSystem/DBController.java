package scoreManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBController {
	// 1.학번 입력하기
	public static int StudentInfoInsertTBL(StudentInfo studentinfo) {
		Connection con = DBUtility.getConnection();
		String insertQuery = "call procedure_scoreTBL(?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int count = 0;
		try {
			preparedStatement = con.prepareStatement(insertQuery);
			preparedStatement.setString(1, studentinfo.getStudentnumber());
			preparedStatement.setString(2, studentinfo.getStudentName());
			preparedStatement.setInt(3, studentinfo.getJava());
			preparedStatement.setInt(4, studentinfo.getAndroid());
			preparedStatement.setInt(5, studentinfo.getKotlin());
			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return count;
	}
	public static List<StudentInfo> StudentInfoSelectTBL() {
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		Connection con = DBUtility.getConnection();
		String selectQuery = "select * from scoreTBL";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = con.prepareStatement(selectQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String Studentnumber = resultSet.getString(1);
				String StudentName = resultSet.getString(2);
				int Java = resultSet.getInt(3);
				int Android = resultSet.getInt(4);
				int Kotlin = resultSet.getInt(5);
				int Total = resultSet.getInt(6);
				int Avg = resultSet.getInt(7);
				String Grade = resultSet.getString(8);
				StudentInfo studentinfo = new StudentInfo(Studentnumber, StudentName, Java, Android, Kotlin, Total, Avg,
						Grade);
				list.add(studentinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return list;
	}
	// 3.학번 삭제하기
	public static int StudentInfoDeleteTBL(String studentname) {
		Connection con = DBUtility.getConnection();
		String deleteQuery = "DELETE from scoreTBL where studentnum like ?";
		PreparedStatement preparedStatement = null;
		int count = 0;
		try {
			preparedStatement = con.prepareStatement(deleteQuery);
			String strName = "%" + studentname + "%";
			preparedStatement.setString(1, strName);
			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return count;
	}
	// 4.학번 수정하기
	public static boolean StudentInfoUpdateTBL(String studentnumber, String studentname) {
		Connection con = DBUtility.getConnection();
		String updateQuery = "select function_scoreTBL(?,?)";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = con.prepareStatement(updateQuery);
			preparedStatement.setString(1, studentnumber);
			preparedStatement.setString(2, studentname);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return false;
	}
	// 5.학생정보 검색하기
	public static List<StudentInfo> StudentInfosearchTBL(String searchData, int searchNumber) {
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		Connection con = DBUtility.getConnection();
		String searchQuery = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			switch (searchNumber) {
			case 1:
				searchQuery = "select * from scoreTBL where studentNum like ?";
				break;
			case 2:
				searchQuery = "select * from scoreTBL where studentName like ?";
				break;
			default:
				System.out.println("유효하지 않은 입력값입니다. 원하는 메뉴 번호를 입력해주세요.");
				return list;
			}
			preparedStatement = con.prepareStatement(searchQuery);
			searchData = "%" + searchData + "%";
			preparedStatement.setString(1, searchData);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				return list;
			}
			while (resultSet.next()) {
				String Studentnumber = resultSet.getString(1);
				String Name = resultSet.getString(2);
				int Java = resultSet.getInt(3);
				int Android = resultSet.getInt(4);
				int Kotlin = resultSet.getInt(5);
				int Total = resultSet.getInt(6);
				int Avg = resultSet.getInt(7);
				String Grade = resultSet.getString(8);
				StudentInfo studentinfo = new StudentInfo(Studentnumber, Name, Java, Android, Kotlin, Total, Avg,
						Grade);
				list.add(studentinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return list;
	}
	// 6.학번 검색하기
	public static void StudentNumSearchTBL(String studentNum) {
		Connection con = DBUtility.getConnection();
		ResultSet resultSet = null;
		String strQuery = "select * from scoreTBL where studentnum = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(strQuery);
			preparedStatement.setString(1, studentNum);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String Studentnumber = resultSet.getString(1);
				String Name = resultSet.getString(2);
				int Java = resultSet.getInt(3);
				int Android = resultSet.getInt(4);
				int Kotlin = resultSet.getInt(5);
				int Total = resultSet.getInt(6);
				int Avg = resultSet.getInt(7);
				String Grade = resultSet.getString(8);
				StudentInfo studentinfo = new StudentInfo(Studentnumber, Name, Java, Android, Kotlin, Total, Avg,
						Grade);
				System.out.println(studentinfo.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}

	}
	// 7.학생이름 검색
	public static void StudentNameSearchTBL(String studentName) {
		Connection con = DBUtility.getConnection();
		ResultSet resultSet = null;
		String strQuery = "select * from scoreTBL where studentname = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(strQuery);
			preparedStatement.setString(1, studentName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String Studentnumber = resultSet.getString(1);
				String Name = resultSet.getString(2);
				int Java = resultSet.getInt(3);
				int Android = resultSet.getInt(4);
				int Kotlin = resultSet.getInt(5);
				int Total = resultSet.getInt(6);
				int Avg = resultSet.getInt(7);
				String Grade = resultSet.getString(8);
				StudentInfo studentinfo = new StudentInfo(Studentnumber, Name, Java, Android, Kotlin, Total, Avg,
						Grade);
				System.out.println(studentinfo.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
	}
	// 8.학번 오름차순정렬
	public static void StudentNumberASC() {
		Connection con = DBUtility.getConnection();
		ResultSet resultSet = null;
		String strQuery = "select * from scoreTBL order by studentNum asc";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(strQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String Studentnumber = resultSet.getString(1);
				String Name = resultSet.getString(2);
				int Java = resultSet.getInt(3);
				int Android = resultSet.getInt(4);
				int Kotlin = resultSet.getInt(5);
				int Total = resultSet.getInt(6);
				int Avg = resultSet.getInt(7);
				String Grade = resultSet.getString(8);
				StudentInfo studentinfo = new StudentInfo(Studentnumber, Name, Java, Android, Kotlin, Total, Avg,
						Grade);
				System.out.println(studentinfo.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
	}
	// 9.학번 내림차순정렬
	public static void StudenNumberDESC() {
		Connection con = DBUtility.getConnection();
		ResultSet resultSet = null;
		String strQuery = "select * from scoreTBL order by studentNum desc";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(strQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String Studentnumber = resultSet.getString(1);
				String StudentName = resultSet.getString(2);
				int Java = resultSet.getInt(3);
				int Android = resultSet.getInt(4);
				int Kotlin = resultSet.getInt(5);
				int Total = resultSet.getInt(6);
				int Avg = resultSet.getInt(7);
				String Grade = resultSet.getString(8);
				StudentInfo studentinfo = new StudentInfo(Studentnumber, StudentName, Java, Android, Kotlin, Total, Avg,
						Grade);
				System.out.println(studentinfo.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
	}
	// 10.학생 총점정렬
	public static void StudenNumberTOTAL() {
		Connection con = DBUtility.getConnection();
		ResultSet resultSet = null;
		String strQuery = "select * from scoreTBL order by total desc";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = con.prepareStatement(strQuery);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String Studentnumber = resultSet.getString(1);
				String StudentName = resultSet.getString(2);
				int Java = resultSet.getInt(3);
				int Android = resultSet.getInt(4);
				int Kotlin = resultSet.getInt(5);
				int Total = resultSet.getInt(6);
				int Avg = resultSet.getInt(7);
				String Grade = resultSet.getString(8);
				StudentInfo studentinfo = new StudentInfo(Studentnumber, StudentName, Java, Android, Kotlin, Total, Avg,
						Grade);
				System.out.println(studentinfo.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
	}
}