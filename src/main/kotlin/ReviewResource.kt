import entity.Review
import jakarta.ejb.Stateless
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.Response

@Stateless
@Path("/review")
class ReviewResource {

    @PersistenceContext
    var em: EntityManager? = null

    @GET
    @Path("/get")
    fun getReview(
        @QueryParam("classCode") classCode: String
    ): Response{
        val query = "select r from Review r where r.classCode = '$classCode'"
        val result = em?.createQuery(query)?.resultList

        return if(result == null || result.isEmpty()){
            Response.status(Response.Status.NOT_FOUND).build()
        }else{
            Response.ok(result).build()
        }
    }

    @GET
    @Path("/post")
    fun createReview(
        @QueryParam("classCode") classCode: String,
        @QueryParam("content") content: String,
        @QueryParam("rate") rate: Int
    ): Response {
        val r = Review(classCode, content, rate)
        em?.persist(r)
        return Response.status(Response.Status.CREATED).build()
    }
}