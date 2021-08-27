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
		// �޴�����
		while (!flag) {
			// �޴���� �� ��ȣ����
			selectNumber = displayMenu();
			switch (selectNumber) {
			case INSERT:
				StudentInfoInsert();
				break; // �Է��ϱ�
			case SHOWTB:
				StudentInfoSelect();
				break; // ����ϱ�
			case DELETE:
				StudentInfoDelete();
				break; // �����ϱ�
			case UPDATE:
				StudentInfoUpdate();
				break; // �����ϱ�
			case SEARCH:
				StudentInfoSearch();
				break; // �˻��ϱ�
			case SORTTB:
				StudentInfoSort();
				break; // �����ϱ�
			case FINISH:
				flag = true;
				break;
			default:
				System.out.println("�ùٸ��� ���� �Է��Դϴ� �ٽ��Է����ּ���.");
				break;
			}
		}
		System.out.println("���α׷� ����!");
	}
	
	// 1.�й� �Է��ϱ�
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
			System.out.print("�й� �Է� >>");
			studentnumber = scan.nextLine();
			if (studentnumber.length() != 7) {
				System.out.println("�߸� �Է��ϼ̽��ϴ�.�й� 7�ڸ��� �Է����ּ���.");
				continue;
			}
			boolean checkStudentNum = duplicateStudentNumberCheck(studentnumber);
			if (checkStudentNum == true) {
				System.out.println("�����ϴ� �л���ȣ�Դϴ�. �ٽ� �Է����ּ���.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("�̸� �Է� >>");
			studentname = scan.nextLine();
			if (studentname.length() < 2 || studentname.length() > 7) {
				System.out.println("�̸� �ٽ� �Է����ּ���.");
				continue;
			}
			break;
		}
		while (true) {
			try {
				System.out.print("�ڹ� ������ �Է��ϼ���.>>");
				java = Integer.parseInt(scan.nextLine());
				if (java < 0 || java > 100) {
					System.out.println("�ùٸ� ������ �Է����ּ���.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("���ڸ� �Է����ּ���");
				continue;
			}
			break;
		}
		while (true) {
			try {
				System.out.print("�ȵ���̵� ������ �Է��ϼ���.>>");
				android = Integer.parseInt(scan.nextLine());
				if (android < 0 || android > 100) {
					System.out.println("�ùٸ� ������ �Է����ּ���.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("���ڸ� �Է����ּ���");
				continue;
			}
			break;
		}
		while (true) {
			try {
				System.out.print("��Ʋ�� ������ �Է��ϼ���.>>");
				kotlin = Integer.parseInt(scan.nextLine());
				if (kotlin < 0 || kotlin > 100) {
					System.out.println("�ùٸ� ������ �Է����ּ���.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("���ڸ� �Է����ּ���");
				continue;
			}
			break;
		}
		StudentInfo studentinfo = new StudentInfo(studentnumber, studentname, java, android, kotlin, total, avg, grade);
		int count = DBController.StudentInfoInsertTBL(studentinfo);
		if (count != 0) {
			System.out.println(studentinfo.getStudentName() + "�� �Է¼���");
		} else {
			System.out.println(studentinfo.getStudentName() + "�� �Է½���");
		}
	}
	
	// �й� �Է� Ȯ��
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
	
	// 2.�й� ����ϱ�
	private static void StudentInfoSelect() {
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		list = DBController.StudentInfoSelectTBL();
		if (list.size() <= 0) {
			System.out.println("��� �� �����Ͱ� �����ϴ�.");
			return;
		}
		for (StudentInfo studentinfo : list) {
			System.out.println(studentinfo.toString());
		}
	}
	
	// 3.�й� �����ϱ�
	private static void StudentInfoDelete() {
		String studentnumber = null;
		while (true) {
			System.out.print("������ �й��� �Է����ּ���.>>");
			studentnumber = scan.nextLine();
			if (studentnumber.length() != 7) {
				System.out.println("�й��� �ٽ� �Է����ּ���.");
				continue;
			}
			boolean checkStudentNum = duplicateStudentNumberCheck(studentnumber);
			if (checkStudentNum != true) {
				System.out.println("���������ʴ� �й��Դϴ�. �ٽ� �Է����ּ���.");
				continue;
			}
			break;
		}
		boolean flag = false;
		int count = DBController.StudentInfoDeleteTBL(studentnumber);
		if (count == 1) {
			flag = true;
			System.out.println(studentnumber + "���� ������ �Ϸ��߽��ϴ�.");
		} else {
			System.out.println(studentnumber + "���� ������ �����߽��ϴ�.");
		}
	}
	
	// 4.�й� �����ϱ�
	private static void StudentInfoUpdate() {
		System.out.print("������ �Ϸ��� �й� �Է� >>");
		String studentnumber = scan.nextLine();
		if (studentnumber.length() != 7) {
			System.out.println("�Է� ���� ����");
			return;
		}
		System.out.print("�����ϰ� ���� �̸� �Է� >>");
		String studentname = scan.nextLine();
		if (!studentname.matches("^[��-�R]{2,4}$")) {
			System.out.println("�Է� ���� ����");
			return;
		}
		boolean count = DBController.StudentInfoUpdateTBL(studentnumber, studentname);
		if (count == true) {
			System.out.println(studentnumber + "���� ������ �Ϸ� �Ǿ����ϴ�.");
		} else {
			System.out.println(studentnumber + "���� ������ ���� �Ǿ����ϴ�.");
		}
	}
	
	// 5.�й� �˻��ϱ�
	private static void StudentInfoSearch() {
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		boolean flag = false;
		int selcetNumber = 0;
		final int NUM_SEARCH = 1, NAME_SEARCH = 2, SEARCH_FINISH = 3;
		boolean numberInputContitue = false;
		String studentNum = null;
		String studentName = null;
		while (!flag) {
			// �Ŵ���¹� ��ȣ����
			selcetNumber = displaySearchMenu();
			switch (selcetNumber) {
			case NUM_SEARCH:
				System.out.print("�й��� �Է� ���ּ���. >>");
				studentNum = scan.nextLine();
				if (studentNum.length() != 7) {
					System.out.println("�߸��Է��ϼ̽��ϴ�");
					break;
				}
				DBController.StudentNumSearchTBL(studentNum);
				break;
			case NAME_SEARCH:
				System.out.print("�˻��� �л��̸��� �Է����ּ���.>>");
				studentName = scan.nextLine();
				if (!studentName.matches("^[��-�R]{2,4}$")) {
					System.out.println("�߸��Է��ϼ̽��ϴ�");
					break;
				}
				DBController.StudentNameSearchTBL(studentName);
				break;
			case SEARCH_FINISH:
				return;
			default:
				System.out.println("�˻���ȣ ������ ������ϴ�. (�ٽ��Է¿��)");
				continue;
			}
		}
	}
	
	// 6.�й� �����ϱ�
	private static void StudentInfoSort() {
		// �˻��� ���� �Է¹ޱ�
		boolean flag = false;
		int selcetNumber = 0;
		final int STUDENT_ASC = 1, STUDENT_DESC = 2, STUDENT_TOTAL = 3, FINISH = 4;
		boolean numberInputContitue = false;
		while (!flag) {
			// �Ŵ���¹� ��ȣ����
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
				System.out.println("�ùٸ��� �ʴ� ��ȣ�Դϴ�.");
				break;
			}
		}
	}
	
	// ù �޴� ���
	private static int displayMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("*****************************************");
			System.out.println("1.�Է� 2.��� 3.���� 4.���� 5.�˻� 6.���� 7.����");
			System.out.println("*****************************************");
			System.out.print("��ȣ����>>");
			// ��ȣ����
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("���� �Է� �����߻� ���Է����ּ���.");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
	// ��ȣ�˻��ϱ� �� �޴� ���
	private static int displaySearchMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("********************************");
			System.out.println("�˻����� 1.�й��˻� 2.�̸��˻� 3.�˻�����");
			System.out.println("********************************");
			System.out.print("��ȣ����>>");
			// ��ȣ����
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("���ڸ� �Է����ּ���.");
				continue;
			} catch (Exception e) {
				System.out.println("���� �Է� �����߻� ���Է����ּ���.");
				continue;
			}
			break;
		}
		return selectNumber;
	}
	
	// ���� ���� �� �޴� ���
	private static int displaySortMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("*************************************************");
			System.out.println("�˻����� 1.������������ 2.������������ 3.�������� 4.���ĳ�����");
			System.out.println("*************************************************");
			System.out.print("��ȣ����>>");
			// ��ȣ����
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("���ڸ� �Է����ּ���.");
				continue;
			} catch (Exception e) {
				System.out.println("���� �Է� �����߻� ���Է����ּ���.");
				continue;
			}
			break;
		}
		return selectNumber;
	}
}