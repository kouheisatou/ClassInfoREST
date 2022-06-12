import entity.Class
import jakarta.ejb.Stateless
import jakarta.ejb.TransactionAttribute
import jakarta.ejb.TransactionAttributeType
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.PersistenceException
import jakarta.transaction.Transactional
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import org.postgresql.util.PSQLException
import java.sql.SQLException

@Stateless
@Path("/class")
class ClassResource {

    @PersistenceContext(unitName = "class_db")
    var em: EntityManager? = null

    @GET
    fun getClass(): Response {
        val jpql = "select e from Class e"
        val list = em?.createQuery(jpql, Class::class.java)?.resultList
        return if(list == null || list.isEmpty()){
            Response.status(Response.Status.NOT_FOUND).build()
        }else{
            Response.ok(list).build()
        }
    }

    @GET
    @Path("{classCode}")
    fun getClass(@PathParam("classCode") classCode: String): Response {
        val jpql = "select e from Class e where e.classCode = '$classCode'"
        val c = em?.createQuery(jpql, entity.Class::class.java)?.resultList?.getOrNull(0)

        return if(c == null){
            Response.noContent().build()
        }else{
            Response.ok(c).build()
        }
    }

    @POST
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    fun createClass(c: entity.Class) : Response{

        return try{
            em!!.persist(c)
            Response.status(Response.Status.CREATED).build()
        }catch (e: Exception){
            when(e){
                is SQLException -> {
                    Response.status(Response.Status.CONFLICT).build()
                }
                else -> {
                    e.printStackTrace()
                    Response.serverError().build()
                }
            }
        }
    }


}