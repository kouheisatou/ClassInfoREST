package net.iobb.tonarinosibahaao.rest

import net.iobb.tonarinosibahaao.rest.entity.Class
import jakarta.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.ws.rs.*
import javax.ws.rs.core.Response
import java.sql.SQLException

@Stateless
@Path("/class")
class ClassResource {

    @PersistenceContext(unitName = "class_db")
    var em: EntityManager? = null

    @GET
    @Path("/get")
    fun getClass(
        @QueryParam("classCode") classCode: String?,
        @QueryParam("name") name: String?,
        @QueryParam("grade") grade: String?,
        @QueryParam("department") department: String?,
        @QueryParam("semester") semester: String?
    ): Response{

        var query = "" +
                "select c " +
                "from Class c "

        var whereUse = false
        // クエリパラメータが指定されなかったとき，条件一致項目を全件取得
        if (classCode != null) {
            query += "where c.classCode = '$classCode' "
            whereUse = true
        }
        if (department != null) {
            query += if(!whereUse){
                whereUse = true
                " where "
            }else{
                " and "
            }
            query += "c.department = '$department'"
        }
        if (grade != null) {
            query += if(!whereUse){
                whereUse = true
                " where "
            }else{
                " and "
            }
            query += "c.grade = '$grade'"
        }
        if (name != null) {
            query += if(!whereUse){
                whereUse = true
                " where "
            }else{
                " and "
            }
            query += "c.name = '$name'"
        }
        if (semester != null) {
            query += if(!whereUse){
                " where "
            }else{
                " and "
            }
            query += "c.semester = '$semester'"
        }


        val result = em?.createQuery(query)?.resultList

        return if(result == null || result.isEmpty()){
            Response.status(Response.Status.NOT_FOUND).build()
        }else{
            Response.ok(result).build()
        }
    }

    @GET
    @Path("/post")
    fun createClass(
        @QueryParam("classCode") classCode: String,
        @QueryParam("name") name: String,
        @QueryParam("grade") grade: String,
        @QueryParam("department") department: String,
        @QueryParam("semester") semester: String
    ) : Response{
        return try{
            val c = Class(classCode, name, grade.toInt(), department, semester.toInt())
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

    @POST
    @Path("/post")
    fun createClass(c: Class) : Response {
        em!!.persist(c)
        return Response.status(Response.Status.CREATED).build()
    }
}