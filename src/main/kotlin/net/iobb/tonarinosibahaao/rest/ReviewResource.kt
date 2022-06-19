package net.iobb.tonarinosibahaao.rest

import net.iobb.tonarinosibahaao.rest.entity.Review
import jakarta.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

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


    @POST
    @Path("/post")
    fun createClass(r: Review) : Response{
        em!!.persist(r)
        return Response.status(Response.Status.CREATED).build()
    }
}