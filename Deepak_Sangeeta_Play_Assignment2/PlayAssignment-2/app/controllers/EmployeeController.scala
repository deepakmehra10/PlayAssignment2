package controllers


import Model.{SearchEmployee, EmpData, EmployeeData}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.i18n.Messages.Implicits._
import play.api.Play.current
import scala.concurrent.Future

/**
  * Created by knodus on 3/3/16.
  */
class EmployeeController extends Controller{

  /**
    * Form to add employee details
    */

  val employeeForm = Form(
    mapping(
     "employeeName" -> nonEmptyText,
      "employeeAddress" -> nonEmptyText,
      "employeeDOB" -> nonEmptyText,
      "employeeJoinDate" -> nonEmptyText,
      "employeeDesignation" -> nonEmptyText
    )(EmployeeData.apply)(EmployeeData.unapply)
  )

  /**
    * Form to list employees filtered by name
    */
  val searchForm = Form(
    mapping(
      "employeeName" -> text
    )(SearchEmployee.apply)(SearchEmployee.unapply)
  )

  /**
    * To display view(form) where employee records can be added
    */
  def renderEmployeeForm = Action { implicit request =>
    Ok(views.html.addemployee(employeeForm))
  }

  /**
    * Controller action to list all the employees of organisation
    */
  def listEmployees = Action { implicit request =>
    val list=EmpData.listEmployee()
    Ok(views.html.listemployee(list,searchForm))
  }

  /**
    * Controller Action to add an employee
    * formWithErrors : In case incorrect data is entered
    */
  def addEmployees = Action.async { implicit request=>
    employeeForm.bindFromRequest.fold(
      formWithErrors => {
        Future{
        Redirect(routes.EmployeeController.renderEmployeeForm).flashing("error"-> "Form contains Error")
      }},
      formData=>{
        Future{
          EmpData.addEmployee(formData.name,formData.address,formData.dob,formData.joiningDate,formData.designation)
          Redirect(routes.EmployeeController.listEmployees).flashing("success"-> "Record successfully Added")
      }}

    )
  }

  /**
    *  Controller action to display list of employee filtered by name
    */
  def searchByName = Action.async { implicit request =>
    searchForm.bindFromRequest.fold(
      formWithErrors =>{
        Future{
        Ok(views.html.listemployee(List(),searchForm))
      }},
      searchData => {
        Future {
          val list = EmpData.searchEmployee(searchData.name)
          Ok(views.html.listemployee(list, searchForm))
        }
      })

  }

  /**
    * It display the record to be updated in a form
    * @param name : name which exist in record, and the record of that name is displayed, which can be updated
    */
  def displayRecord(name:String) =Action { implicit request =>
    val emp=EmpData.getEmployee(name)
    Ok(views.html.updateemployee(emp,employeeForm))
  }

  /**
    * Controller Action to update the record of employee
    * @param oldname: the name of the employee whose record is to be updated
    */
  def updateEmployee(oldname:String) = Action.async{ implicit request =>

    employeeForm.bindFromRequest.fold(
      formWithErrors =>{Future{

        val list=EmpData.listEmployee()
        BadRequest(views.html.listemployee(list,searchForm))
      }},
      updateData=>{

       Future{
        EmpData.updateEmployee(oldname,updateData.name,updateData.address,updateData.dob,updateData.joiningDate,updateData.designation)
        val list=EmpData.listEmployee()
        Ok(views.html.listemployee(list,searchForm))
      }}

    )

  }

  def deleteEmployee(name:String) = Action{ implicit request =>
    val emp=EmpData.getEmployee(name)
    EmpData.deleteEmployee(emp)
    val deletedList=EmpData.listEmployee()
    Ok(views.html.listemployee(deletedList,searchForm))

  }

}
