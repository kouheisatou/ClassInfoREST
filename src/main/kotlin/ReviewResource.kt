import entity.Review
import jakarta.ejb.Stateless
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.Response

@Stateless
@Path("/review")
class ReviewResource {

    @PersistenceContext
    var em: EntityManager? = null

    @GET
    @Path("{classCode}")
    fun getReview(@PathParam("classCode") classCode: String): Response{
        val query = "select r from Review r where r.classCode = '$classCode'"
        val result = em?.createQuery(query)?.resultList

        return if(result == null || result.isEmpty()){
            Response.status(Response.Status.NOT_FOUND).build()
        }else{
            Response.ok(result).build()
        }
    }

    @POST
    @Path("{classCode}")
    fun createReview(r: Review, @PathParam("classCode") classCode: String): Response {
        r.classCode = classCode
        em?.persist(r)
        return Response.status(Response.Status.CREATED).build()
    }
}