import entity.Class
import jakarta.ejb.Stateless
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import org.postgresql.util.PSQLException
import values.Department
import values.Semester

@Stateless
@Path("/class")
class ClassResource {

    @PersistenceContext(unitName = "class_db")
    var em: EntityManager? = null

    @GET
    fun post(): String{
        val c = Class("testtest", "info2", 3, Department.AF, Semester.First)
        return createClass(c).toString()
    }

    fun createClass(c: entity.Class) : Boolean{
        return try{
            em!!.persist(c)
            true
        }catch (e: PSQLException){
            false
        }catch (e: NullPointerException){
            e.printStackTrace()
            false
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }


}