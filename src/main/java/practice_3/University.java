package practice_3;

public class University {
    static String universityName;
    final int studentID;
    String studentName;

    University(int studentID, String studentName){
        this.studentID = studentID;
        this.studentName = studentName;
    }

    public static void changeUniversityName(String newName) {
        universityName = newName;
    }

    public String getStudentName(){     //changed the name of getter
        return this.studentName;
    }

    public void printStudentInfo() {
        System.out.println("Name of student is " + this.studentName);
        System.out.println("Student ID is " + this.studentID);
        System.out.println("Name of university is " + universityName);
    }
}
