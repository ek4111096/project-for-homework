package practice_3;

class Company {
    static String companyName;
    final int employeeID;
    private String employeeName;

    Company(int employeeID, String employeeName) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
    }

    static void printCompanyName() {
        System.out.println("This company name is " + companyName);
    }

    public String getEmployeeName(){
        return this.employeeName;
    }

    public void setEmployeeName(String newEmployeeName) {
        this.employeeName = newEmployeeName;
    }


}
