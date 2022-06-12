import entity.Review
import jakarta.ejb.Stateless
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.postgresql.util.PSQLException

@Stateless
@Path("/review")
class ReviewResource {

    @PersistenceContext
    var em: EntityManager? = null

    @GET
    fun getReviews(): String{
        val r = Review("testtest", "review contents", 4)
        return createClass(r).toString()
    }

    fun createClass(r: Review) : Boolean{
        return try{
            em!!.persist(r)
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