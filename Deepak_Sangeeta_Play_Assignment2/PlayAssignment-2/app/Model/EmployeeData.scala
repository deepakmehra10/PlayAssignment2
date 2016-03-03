package Model
import java.util.Date
/**
  * Created by knodus on 3/3/16.
  */
case class EmployeeData (name:String,address:String,dob:String,joiningDate:String,designation:String)

case class SearchEmployee(name:String)

object EmpData{

  var listOfEmployee=scala.collection.mutable.ListBuffer(EmployeeData("sangeeta","nawada","1991-11-04","2016-01-04","Software Consultant"),EmployeeData("sangi","nawadaaaaa","1991-11-04","2016-01-04","Software Consultant"))

  /**
    * Function to add employee
    * @param name : Name of employee
    * @param address :Address of employee
    * @param dob : Date of Birth
    * @param joiningDate : Joining Date of employee in the organisation
    * @param designation : Post on which the employee is working currently
    */
  def addEmployee(name:String,address:String,dob:String,joiningDate:String,designation:String): Unit ={
    listOfEmployee += EmployeeData(name,address,dob,joiningDate,designation)
  }

  /**
    * It list the employees of organisation
    * @return : List of employee currently working
    */
  def listEmployee():List[EmployeeData]={
    listOfEmployee.toList
  }

  /**
    * Function to search employee filtered by name
    * @return : List of employee whose name contain "input name" given in search textbox
    */
  def searchEmployee(name:String):List[EmployeeData]={
    listOfEmployee.filter(_.name.contains(name)).toList
  }

  /**
    * Function to return an employee whose name exactly matches the given input name
    */
  def getEmployee(name:String):EmployeeData={
    val list=listOfEmployee.filter(_.name==name).toList
    list(0)
  }

  /**
    * Function to update employee record
    * @param oldname : Name with which employee details is stored in Records
    * @param name : Name to be updated
    * @param address : Addresss to be updated
    * @param dob : Date Of Birth to be updated
    * @param joiningDate : Correct joining Date
    * @param designation : Updated Designation
    */
  def updateEmployee(oldname:String,name:String,address:String,dob:String,joiningDate:String,designation:String): Unit ={
    val emp=EmployeeData(name,address,dob,joiningDate,designation)
    listOfEmployee.map( employee =>
    if(employee.name==oldname) {

      listOfEmployee.update(listOfEmployee.indexOf(employee), emp)
    }else{
     
      listOfEmployee.update(listOfEmployee.indexOf(employee),employee)
    }
    )
  }


  def deleteEmployee(emp:EmployeeData): Unit ={
    listOfEmployee -= emp
  }




}