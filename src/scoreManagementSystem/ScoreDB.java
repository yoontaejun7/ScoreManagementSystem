package scoreManagementSystem;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ScoreDB {
	public static final Scanner scan = new Scanner(System.in);
	public static final int INSERT = 1, SHOWTB = 2, DELETE = 3, UPDATE = 4, SEARCH = 5, SORTTB = 6, FINISH = 7;
	
	public static void main(String[] args) throws IOException {
		boolean flag = false;
		int selectNumber = 0;
		boolean numberInputContinueFlag = false;
		// ¸Ş´º¼±ÅÃ
		while (!flag) {
			// ¸Ş´ºÃâ·Â ¹× ¹øÈ£¼±ÅÃ
			selectNumber = displayMenu();
			switch (selectNumber) {
			case INSERT:
				StudentInfoInsert();
				break; // ÀÔ·ÂÇÏ±â
			case SHOWTB:
				StudentInfoSelect();
				break; // Ãâ·ÂÇÏ±â
			case DELETE:
				StudentInfoDelete();
				break; // »èÁ¦ÇÏ±â
			case UPDATE:
				StudentInfoUpdate();
				break; // ¼öÁ¤ÇÏ±â
			case SEARCH:
				StudentInfoSearch();
				break; // °Ë»öÇÏ±â
			case SORTTB:
				StudentInfoSort();
				break; // Á¤·ÄÇÏ±â
			case FINISH:
				flag = true;
				break;
			default:
				System.out.println("¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·ÂÀÔ´Ï´Ù ´Ù½ÃÀÔ·ÂÇØÁÖ¼¼¿ä.");
				break;
			}
		}
		System.out.println("ÇÁ·Î±×·¥ Á¾·á!");
	}
	
	// 1.ÇĞ¹ø ÀÔ·ÂÇÏ±â
	private static void StudentInfoInsert() {
		String studentnumber = null;
		String studentname = null;
		int java = 0;
		int android = 0;
		int kotlin = 0;
		int total = 0;
		double avg = 0;
		String grade = null;
		while (true) {
			System.out.print("ÇĞ¹ø ÀÔ·Â >>");
			studentnumber = scan.nextLine();
			if (studentnumber.length() != 7) {
				System.out.println("Àß¸ø ÀÔ·ÂÇÏ¼Ì½À´Ï´Ù.ÇĞ¹ø 7ÀÚ¸®¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			}
			boolean checkStudentNum = duplicateStudentNumberCheck(studentnumber);
			if (checkStudentNum == true) {
				System.out.println("Á¸ÀçÇÏ´Â ÇĞ»ı¹øÈ£ÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("ÀÌ¸§ ÀÔ·Â >>");
			studentname = scan.nextLine();
			if (studentname.length() < 2 || studentname.length() > 7) {
				System.out.println("ÀÌ¸§ ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			}
			break;
		}
		while (true) {
			try {
				System.out.print("ÀÚ¹Ù Á¡¼ö¸¦ ÀÔ·ÂÇÏ¼¼¿ä.>>");
				java = Integer.parseInt(scan.nextLine());
				if (java < 0 || java > 100) {
					System.out.println("¿Ã¹Ù¸¥ Á¡¼ö¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");
				continue;
			}
			break;
		}
		while (true) {
			try {
				System.out.print("¾Èµå·ÎÀÌµå Á¡¼ö¸¦ ÀÔ·ÂÇÏ¼¼¿ä.>>");
				android = Integer.parseInt(scan.nextLine());
				if (android < 0 || android > 100) {
					System.out.println("¿Ã¹Ù¸¥ Á¡¼ö¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");
				continue;
			}
			break;
		}
		while (true) {
			try {
				System.out.print("ÄÚÆ²¸° Á¡¼ö¸¦ ÀÔ·ÂÇÏ¼¼¿ä.>>");
				kotlin = Integer.parseInt(scan.nextLine());
				if (kotlin < 0 || kotlin > 100) {
					System.out.println("¿Ã¹Ù¸¥ Á¡¼ö¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");
				continue;
			}
			break;
		}
		StudentInfo studentinfo = new StudentInfo(studentnumber, studentname, java, android, kotlin, total, avg, grade);
		int count = DBController.StudentInfoInsertTBL(studentinfo);
		if (count != 0) {
			System.out.println(studentinfo.getStudentName() + "´Ô ÀÔ·Â¼º°ø");
		} else {
			System.out.println(studentinfo.getStudentName() + "´Ô ÀÔ·Â½ÇÆĞ");
		}
	}
	
	// ÇĞ¹ø ÀÔ·Â È®ÀÎ
	private static boolean duplicateStudentNumberCheck(String studentnumber) {
		final int STUDENTNUMBER = 1;
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		int searchNumber = 1;
		list = DBController.StudentInfosearchTBL(studentnumber, searchNumber);
		if (list.size() >= 1) {
			return true;
		}
		return false;
	}
	
	// 2.ÇĞ¹ø Ãâ·ÂÇÏ±â
	private static void StudentInfoSelect() {
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		list = DBController.StudentInfoSelectTBL();
		if (list.size() <= 0) {
			System.out.println("Ãâ·Â ÇÒ µ¥ÀÌÅÍ°¡ ¾ø½À´Ï´Ù.");
			return;
		}
		for (StudentInfo studentinfo : list) {
			System.out.println(studentinfo.toString());
		}
	}
	
	// 3.ÇĞ¹ø »èÁ¦ÇÏ±â
	private static void StudentInfoDelete() {
		String studentnumber = null;
		while (true) {
			System.out.print("»èÁ¦ÇÒ ÇĞ¹øÀ» ÀÔ·ÂÇØÁÖ¼¼¿ä.>>");
			studentnumber = scan.nextLine();
			if (studentnumber.length() != 7) {
				System.out.println("ÇĞ¹øÀ» ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			}
			boolean checkStudentNum = duplicateStudentNumberCheck(studentnumber);
			if (checkStudentNum != true) {
				System.out.println("Á¸ÀçÇÏÁö¾Ê´Â ÇĞ¹øÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			}
			break;
		}
		boolean flag = false;
		int count = DBController.StudentInfoDeleteTBL(studentnumber);
		if (count == 1) {
			flag = true;
			System.out.println(studentnumber + "¹ø´Ô »èÁ¦¸¦ ¿Ï·áÇß½À´Ï´Ù.");
		} else {
			System.out.println(studentnumber + "¹ø´Ô »èÁ¦¸¦ ½ÇÆĞÇß½À´Ï´Ù.");
		}
	}
	
	// 4.ÇĞ¹ø ¼öÁ¤ÇÏ±â
	private static void StudentInfoUpdate() {
		System.out.print("¼öÁ¤À» ÇÏ·Á´Â ÇĞ¹ø ÀÔ·Â >>");
		String studentnumber = scan.nextLine();
		if (studentnumber.length() != 7) {
			System.out.println("ÀÔ·Â Çü½Ä ¿À·ù");
			return;
		}
		System.out.print("¼öÁ¤ÇÏ°í ½ÍÀº ÀÌ¸§ ÀÔ·Â >>");
		String studentname = scan.nextLine();
		if (!studentname.matches("^[°¡-ÆR]{2,4}$")) {
			System.out.println("ÀÔ·Â Çü½Ä ¿À·ù");
			return;
		}
		boolean count = DBController.StudentInfoUpdateTBL(studentnumber, studentname);
		if (count == true) {
			System.out.println(studentnumber + "¹ø´Ô ¼öÁ¤ÀÌ ¿Ï·á µÇ¾ú½À´Ï´Ù.");
		} else {
			System.out.println(studentnumber + "¹ø´Ô ¼öÁ¤ÀÌ ½ÇÆĞ µÇ¾ú½À´Ï´Ù.");
		}
	}
	
	// 5.ÇĞ¹ø °Ë»öÇÏ±â
	private static void StudentInfoSearch() {
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		boolean flag = false;
		int selcetNumber = 0;
		final int NUM_SEARCH = 1, NAME_SEARCH = 2, SEARCH_FINISH = 3;
		boolean numberInputContitue = false;
		String studentNum = null;
		String studentName = null;
		while (!flag) {
			// ¸Å´ºÃâ·Â¹× ¹øÈ£¼±ÅÃ
			selcetNumber = displaySearchMenu();
			switch (selcetNumber) {
			case NUM_SEARCH:
				System.out.print("ÇĞ¹øÀ» ÀÔ·Â ÇØÁÖ¼¼¿ä. >>");
				studentNum = scan.nextLine();
				if (studentNum.length() != 7) {
					System.out.println("Àß¸øÀÔ·ÂÇÏ¼Ì½À´Ï´Ù");
					break;
				}
				DBController.StudentNumSearchTBL(studentNum);
				break;
			case NAME_SEARCH:
				System.out.print("°Ë»öÇÒ ÇĞ»ıÀÌ¸§À» ÀÔ·ÂÇØÁÖ¼¼¿ä.>>");
				studentName = scan.nextLine();
				if (!studentName.matches("^[°¡-ÆR]{2,4}$")) {
					System.out.println("Àß¸øÀÔ·ÂÇÏ¼Ì½À´Ï´Ù");
					break;
				}
				DBController.StudentNameSearchTBL(studentName);
				break;
			case SEARCH_FINISH:
				return;
			default:
				System.out.println("°Ë»ö¹øÈ£ ¹üÀ§¿¡ ¹ş¾î³µ½À´Ï´Ù. (´Ù½ÃÀÔ·Â¿ä¸Á)");
				continue;
			}
		}
	}
	
	// 6.ÇĞ¹ø Á¤·ÄÇÏ±â
	private static void StudentInfoSort() {
		// °Ë»öÇÒ ³»¿ë ÀÔ·Â¹Ş±â
		boolean flag = false;
		int selcetNumber = 0;
		final int STUDENT_ASC = 1, STUDENT_DESC = 2, STUDENT_TOTAL = 3, FINISH = 4;
		boolean numberInputContitue = false;
		while (!flag) {
			// ¸Å´ºÃâ·Â¹× ¹øÈ£¼±ÅÃ
			selcetNumber = displaySortMenu();
			switch (selcetNumber) {
			case STUDENT_ASC:
				DBController.StudentNumberASC();
				break;
			case STUDENT_DESC:
				DBController.StudenNumberDESC();
				break;
			case STUDENT_TOTAL:
				DBController.StudenNumberDESC();
				break;
			case FINISH:
				flag = true;
				break;
			default:
				System.out.println("¿Ã¹Ù¸£Áö ¾Ê´Â ¹øÈ£ÀÔ´Ï´Ù.");
				break;
			}
		}
	}
	
	// Ã¹ ¸Ş´º Ãâ·Â
	private static int displayMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("*****************************************");
			System.out.println("1.ÀÔ·Â 2.Ãâ·Â 3.»èÁ¦ 4.¼öÁ¤ 5.°Ë»ö 6.Á¤·Ä 7.Á¾·á");
			System.out.println("*****************************************");
			System.out.print("¹øÈ£¼±ÅÃ>>");
			// ¹øÈ£¼±ÅÃ
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("¼ıÀÚ ÀÔ·Â ¹®Á¦¹ß»ı ÀçÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
	// ¹øÈ£°Ë»öÇÏ±â ÈÄ ¸Ş´º Ãâ·Â
	private static int displaySearchMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("********************************");
			System.out.println("°Ë»ö¼±ÅÃ 1.ÇĞ¹ø°Ë»ö 2.ÀÌ¸§°Ë»ö 3.°Ë»öÁ¾·á");
			System.out.println("********************************");
			System.out.print("¹øÈ£¼±ÅÃ>>");
			// ¹øÈ£¼±ÅÃ
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			} catch (Exception e) {
				System.out.println("¼ıÀÚ ÀÔ·Â ¹®Á¦¹ß»ı ÀçÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
	// Á¤·Ä ¼±ÅÃ ÈÄ ¸Ş´º Ãâ·Â
	private static int displaySortMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("*************************************************");
			System.out.println("°Ë»ö¼±ÅÃ 1.¿À¸§Â÷¼øÁ¤·Ä 2.³»¸²Â÷¼øÁ¤·Ä 3.ÃÑÁ¡Á¤·Ä 4.Á¤·Ä³ª°¡±â");
			System.out.println("*************************************************");
			System.out.print("¹øÈ£¼±ÅÃ>>");
			// ¹øÈ£¼±ÅÃ
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("¼ıÀÚ¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			} catch (Exception e) {
				System.out.println("¼ıÀÚ ÀÔ·Â ¹®Á¦¹ß»ı ÀçÀÔ·ÂÇØÁÖ¼¼¿ä.");
				continue;
			}
			break;
		}
		return selectNumber;
	}
}