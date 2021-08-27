package scoreManagementSystem;

import java.util.Objects;

public class StudentInfo {
	private String studentnumber;
	private String studentname;
	private int java;
	private int android;
	private int kotlin;
	private int total;
	private double avg;
	private String grade;
	
	public StudentInfo(String studentnumber, String studentname, int java, int android, int kotlin, int total,
			double avg, String grade) {
		super();
		this.studentnumber = studentnumber;
		this.studentname = studentname;
		this.java = java;
		this.android = android;
		this.kotlin = kotlin;
		this.total = total;
		this.avg = avg;
		this.grade = grade;
	}
	
	public String getStudentnumber() {
		return studentnumber;
	}
	public void setStudentnumber(String studentnumber) {
		this.studentnumber = studentnumber;
	}
	public String getStudentName() {
		return studentname;
	}
	public void setStudentName(String studentname) {
		this.studentname = studentname;
	}
	public int getJava() {
		return java;
	}
	public void setJava(int java) {
		this.java = java;
	}
	public int getAndroid() {
		return android;
	}
	public void setAndroid(int android) {
		this.android = android;
	}
	public int getKotlin() {
		return kotlin;
	}
	public void setKotlin(int kotlin) {
		this.kotlin = kotlin;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((studentnumber == null) ? 0 : studentnumber.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentInfo other = (StudentInfo) obj;
		if (studentnumber == null) {
			if (other.studentnumber != null)
				return false;
		} else if (!studentnumber.equals(other.studentnumber))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "학번:" + studentnumber + "\t이름:" + studentname + "\t자바점수:" + java + "\t안드로이드점수:" + android + "\t코틀린점수:"
				+ kotlin + "\t 총점:" + total + "\t 평균:" + avg + "\t 등급:" + grade;
	}
}
