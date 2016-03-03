import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
  * Created by knodus on 3/3/16.
  */
@RunWith(classOf[JUnitRunner])
class EmployeeControllerSpec extends Specification{

  "Application" should {

    //Test case 1.
    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/abc")) must beSome.which (status(_) == NOT_FOUND)
    }

    // Test casae 2.

    "render the employee list page" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(303)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain ("Your new application is ready.")
    }

    //Test case 3.

    "render the employee form to add more employees " in new WithApplication{

      val home = route(FakeRequest(POST, "/employee/add_emp").withFormUrlEncodedBody("employeeName"->"kanika","employeeAddress"->"nawada","employeeDOB"->"05-12-1991","employeeJoinDate"->"11-21-2015","employeeDesignation"->"software trainee")).get

      status(home) must equalTo(303)

    }

    //Test case 4.

    "add employee with bad request " in new WithApplication{
      val home = route(FakeRequest(POST, "/employee/add_emp  ").withFormUrlEncodedBody("employeeName"->"","employeeAddress"->"nawada","employeeDOB"->"05-12-1991","employeeJoinDate"->"11-21-2015","employeeDesignation"->"software trainee")).get

      status(home) must equalTo(404)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain ("Your new application is ready.")
    }

    //Test case 5.

    "search employee by name " in new WithApplication{
      val home = route(FakeRequest(POST, "/employee/list").withFormUrlEncodedBody("employeeName"->"sangeeta")).get

      status(home) must equalTo(200)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain ("Your new application is ready.")
    }

    //Test case 6.

    "search employee with no such name  " in new WithApplication{
      val home = route(FakeRequest(POST, "/employee/list").withFormUrlEncodedBody("employeeName"->"sangeeta1")).get

      status(home) must equalTo(404)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain ("Your new application is ready.")
    }

    //Test case 7.

    "employee update form " in new WithApplication{
      val home = route(FakeRequest(GET, "employee/update_page")).get

      status(home) must equalTo(200)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain ("Your new application is ready.")
    }

    //Test case 8.

    "employee update form " in new WithApplication{
      val home = route(FakeRequest(POST, "/employee/update ").withFormUrlEncodedBody("employeeName"->"sangeeta","employeeAddress"->"nawadaaaaa","employeeDOB"->"1996-05-9","employeeJoinDate"->"1991-05-23","employeeDesignation"->"software trainee")).get

      status(home) must equalTo(200)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain ("Your new application is ready.")
    }

    //Test case 9.

    "employee update form with bad request" in new WithApplication {
      val home = route(FakeRequest(POST, "/employee/update ").withFormUrlEncodedBody("employeeName" -> "", "employeeAddress" -> "nawadaaaaa", "employeeDOB" -> "1996-05-10", "employeeJoinDate" -> "21/01/1991", "employeeDesignation" -> "software trainee")).get

      status(home) must equalTo(400)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain ("Your new application is ready.")
    }
    //Test case 10.

      "delete employee from the list" in new WithApplication {
        val home = route(FakeRequest(POST, "/employee/delete").withFormUrlEncodedBody("name" -> "sangeeta")).get

        status(home) must equalTo(200)


      }



  }

}
