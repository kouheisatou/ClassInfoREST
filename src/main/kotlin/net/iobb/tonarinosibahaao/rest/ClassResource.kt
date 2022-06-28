package net.iobb.tonarinosibahaao.rest

import net.iobb.tonarinosibahaao.rest.entity.Class
import jakarta.ejb.Stateless
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Response
import net.iobb.tonarinosibahaao.rest.scraping.loadFromInputStream
import net.iobb.tonarinosibahaao.rest.scraping.scraping
import java.io.IOException
import java.io.InputStreamReader
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
        @QueryParam("semester") semester: String?,
        @QueryParam("campus") campus: String?,
        @QueryParam("unitDivision") unitDivision: String?
    ): Response{

        var query = "select c from Class c "
        query = appendQuery(query, "classCode", classCode)
        query = appendQuery(query, "name", name)
        query = appendQuery(query, "grade", grade)
        query = appendQuery(query, "department", department)
        query = appendQuery(query, "semester", semester)
        query = appendQuery(query, "campus", campus)
        query = appendQuery(query, "unitDivision", unitDivision)

        val result = em?.createQuery(query)?.resultList

        return if(result == null || result.isEmpty()){
            Response.status(Response.Status.NOT_FOUND).build()
        }else{
            Response.ok(result).build()
        }
    }

    fun appendQuery(query: String, queryParamKey: String, queryParamValue: String?): String{

        if(queryParamValue == null || queryParamValue == ""){
            return query
        }

        var newQuery = query
        newQuery += if(query.contains("where")){
            " and "
        }else{
            " where "
        }
        newQuery += " c.$queryParamKey='$queryParamValue'"
        return newQuery
    }

    @GET
    @Path("/post")
    fun createClass(
        @QueryParam("classCode") classCode: String?,
        @QueryParam("name") name: String?,
        @QueryParam("grade") grade: String?,
        @QueryParam("department") department: String?,
        @QueryParam("semester") semester: String?,
        @QueryParam("campus") campus: String?,
        @QueryParam("unitDivision") unitDivision: String?
    ) : Response{
        return try{
            val c = Class(classCode!!, name!!, grade!!.toInt(), department!!, semester!!, campus!!, unitDivision!!)
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

    @GET
    @Path("/post/scraping")
    fun startScraping() : Response{
        print("current_path = " + this.javaClass.protectionDomain.codeSource.location)
        val inputStream = this.javaClass.classLoader.getResourceAsStream("scraping_setting.txt") ?: throw IOException("scraping_setting.txt is not found")
        val departmentInfoList = loadFromInputStream(inputStream)
        scraping(departmentInfoList, false)
        for(d in departmentInfoList){
            for(c in d.classes){
                createClass(c)
            }
        }
        return getClass(null, null, null, null, null, null, null)
    }

    @POST
    @Path("/post")
    fun createClass(c: Class) : Response {
        em!!.persist(c)
        return Response.status(Response.Status.CREATED).build()
    }
}