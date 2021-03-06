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
		// 메뉴선택
		while (!flag) {
			// 메뉴출력 및 번호선택
			selectNumber = displayMenu();
			switch (selectNumber) {
			case INSERT:
				StudentInfoInsert();
				break; // 입력하기
			case SHOWTB:
				StudentInfoSelect();
				break; // 출력하기
			case DELETE:
				StudentInfoDelete();
				break; // 삭제하기
			case UPDATE:
				StudentInfoUpdate();
				break; // 수정하기
			case SEARCH:
				StudentInfoSearch();
				break; // 검색하기
			case SORTTB:
				StudentInfoSort();
				break; // 정렬하기
			case FINISH:
				flag = true;
				break;
			default:
				System.out.println("올바르지 않은 입력입니다 다시입력해주세요.");
				break;
			}
		}
		System.out.println("프로그램 종료!");
	}
	
	// 1.학번 입력하기
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
			System.out.print("학번 입력 >>");
			studentnumber = scan.nextLine();
			if (studentnumber.length() != 7) {
				System.out.println("잘못 입력하셨습니다.학번 7자리를 입력해주세요.");
				continue;
			}
			boolean checkStudentNum = duplicateStudentNumberCheck(studentnumber);
			if (checkStudentNum == true) {
				System.out.println("존재하는 학생번호입니다. 다시 입력해주세요.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("이름 입력 >>");
			studentname = scan.nextLine();
			if (studentname.length() < 2 || studentname.length() > 7) {
				System.out.println("이름 다시 입력해주세요.");
				continue;
			}
			break;
		}
		while (true) {
			try {
				System.out.print("자바 점수를 입력하세요.>>");
				java = Integer.parseInt(scan.nextLine());
				if (java < 0 || java > 100) {
					System.out.println("올바른 점수를 입력해주세요.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요");
				continue;
			}
			break;
		}
		while (true) {
			try {
				System.out.print("안드로이드 점수를 입력하세요.>>");
				android = Integer.parseInt(scan.nextLine());
				if (android < 0 || android > 100) {
					System.out.println("올바른 점수를 입력해주세요.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요");
				continue;
			}
			break;
		}
		while (true) {
			try {
				System.out.print("코틀린 점수를 입력하세요.>>");
				kotlin = Integer.parseInt(scan.nextLine());
				if (kotlin < 0 || kotlin > 100) {
					System.out.println("올바른 점수를 입력해주세요.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요");
				continue;
			}
			break;
		}
		StudentInfo studentinfo = new StudentInfo(studentnumber, studentname, java, android, kotlin, total, avg, grade);
		int count = DBController.StudentInfoInsertTBL(studentinfo);
		if (count != 0) {
			System.out.println(studentinfo.getStudentName() + "님 입력성공");
		} else {
			System.out.println(studentinfo.getStudentName() + "님 입력실패");
		}
	}
	
	// 학번 입력 확인
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
	
	// 2.학번 출력하기
	private static void StudentInfoSelect() {
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		list = DBController.StudentInfoSelectTBL();
		if (list.size() <= 0) {
			System.out.println("출력 할 데이터가 없습니다.");
			return;
		}
		for (StudentInfo studentinfo : list) {
			System.out.println(studentinfo.toString());
		}
	}
	
	// 3.학번 삭제하기
	private static void StudentInfoDelete() {
		String studentnumber = null;
		while (true) {
			System.out.print("삭제할 학번을 입력해주세요.>>");
			studentnumber = scan.nextLine();
			if (studentnumber.length() != 7) {
				System.out.println("학번을 다시 입력해주세요.");
				continue;
			}
			boolean checkStudentNum = duplicateStudentNumberCheck(studentnumber);
			if (checkStudentNum != true) {
				System.out.println("존재하지않는 학번입니다. 다시 입력해주세요.");
				continue;
			}
			break;
		}
		boolean flag = false;
		int count = DBController.StudentInfoDeleteTBL(studentnumber);
		if (count == 1) {
			flag = true;
			System.out.println(studentnumber + "번님 삭제를 완료했습니다.");
		} else {
			System.out.println(studentnumber + "번님 삭제를 실패했습니다.");
		}
	}
	
	// 4.학번 수정하기
	private static void StudentInfoUpdate() {
		System.out.print("수정을 하려는 학번 입력 >>");
		String studentnumber = scan.nextLine();
		if (studentnumber.length() != 7) {
			System.out.println("입력 형식 오류");
			return;
		}
		System.out.print("수정하고 싶은 이름 입력 >>");
		String studentname = scan.nextLine();
		if (!studentname.matches("^[가-힣]{2,4}$")) {
			System.out.println("입력 형식 오류");
			return;
		}
		boolean count = DBController.StudentInfoUpdateTBL(studentnumber, studentname);
		if (count == true) {
			System.out.println(studentnumber + "번님 수정이 완료 되었습니다.");
		} else {
			System.out.println(studentnumber + "번님 수정이 실패 되었습니다.");
		}
	}
	
	// 5.학번 검색하기
	private static void StudentInfoSearch() {
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		boolean flag = false;
		int selcetNumber = 0;
		final int NUM_SEARCH = 1, NAME_SEARCH = 2, SEARCH_FINISH = 3;
		boolean numberInputContitue = false;
		String studentNum = null;
		String studentName = null;
		while (!flag) {
			// 매뉴출력및 번호선택
			selcetNumber = displaySearchMenu();
			switch (selcetNumber) {
			case NUM_SEARCH:
				System.out.print("학번을 입력 해주세요. >>");
				studentNum = scan.nextLine();
				if (studentNum.length() != 7) {
					System.out.println("잘못입력하셨습니다");
					break;
				}
				DBController.StudentNumSearchTBL(studentNum);
				break;
			case NAME_SEARCH:
				System.out.print("검색할 학생이름을 입력해주세요.>>");
				studentName = scan.nextLine();
				if (!studentName.matches("^[가-힣]{2,4}$")) {
					System.out.println("잘못입력하셨습니다");
					break;
				}
				DBController.StudentNameSearchTBL(studentName);
				break;
			case SEARCH_FINISH:
				return;
			default:
				System.out.println("검색번호 범위에 벗어났습니다. (다시입력요망)");
				continue;
			}
		}
	}
	
	// 6.학번 정렬하기
	private static void StudentInfoSort() {
		// 검색할 내용 입력받기
		boolean flag = false;
		int selcetNumber = 0;
		final int STUDENT_ASC = 1, STUDENT_DESC = 2, STUDENT_TOTAL = 3, FINISH = 4;
		boolean numberInputContitue = false;
		while (!flag) {
			// 매뉴출력및 번호선택
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
				System.out.println("올바르지 않는 번호입니다.");
				break;
			}
		}
	}
	
	// 첫 메뉴 출력
	private static int displayMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("*****************************************");
			System.out.println("1.입력 2.출력 3.삭제 4.수정 5.검색 6.정렬 7.종료");
			System.out.println("*****************************************");
			System.out.print("번호선택>>");
			// 번호선택
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("숫자 입력 문제발생 재입력해주세요.");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
	// 번호검색하기 후 메뉴 출력
	private static int displaySearchMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("********************************");
			System.out.println("검색선택 1.학번검색 2.이름검색 3.검색종료");
			System.out.println("********************************");
			System.out.print("번호선택>>");
			// 번호선택
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("숫자를 입력해주세요.");
				continue;
			} catch (Exception e) {
				System.out.println("숫자 입력 문제발생 재입력해주세요.");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
	// 정렬 선택 후 메뉴 출력
	private static int displaySortMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("*************************************************");
			System.out.println("검색선택 1.오름차순정렬 2.내림차순정렬 3.총점정렬 4.정렬나가기");
			System.out.println("*************************************************");
			System.out.print("번호선택>>");
			// 번호선택
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("숫자를 입력해주세요.");
				continue;
			} catch (Exception e) {
				System.out.println("숫자 입력 문제발생 재입력해주세요.");
				continue;
			}
			break;
		}
		return selectNumber;
	}
}