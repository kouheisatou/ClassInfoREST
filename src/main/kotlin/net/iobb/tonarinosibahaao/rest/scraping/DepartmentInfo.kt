package net.iobb.tonarinosibahaao.rest.scraping

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import net.iobb.tonarinosibahaao.rest.entity.*
import java.io.File
import java.io.FileWriter

private const val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36"
private const val HEADER_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"
private const val HEADER_ACCEPT_LANG = "ja,en-US;q=0.7,en;q=0.3"
private const val HEADER_ACCEPT_ENCODING = "gzip, deflate, br"
private const val HEADER_REFERER = "https://www.xxxxx/yyyy"


class DepartmentInfo(val departmentName: String, val section: String, val url: String) {

    val classes = mutableListOf<Class>()

    fun getClasses(){

        val doc: Document = Jsoup.connect(url)
            .userAgent(USER_AGENT)
            .header("Accept", HEADER_ACCEPT)
            .header("Accept-Language", HEADER_ACCEPT_LANG)
            .header("Accept-Encoding", HEADER_ACCEPT_ENCODING)
            .header("Referer", HEADER_REFERER)
            .timeout(10 * 1000)
            .get();

        val rows: Elements = doc.select(".subject")
        for (r in rows) {
            val row = r.select("td")
            val classId = row[2].select("a").getOrNull(0)?.text()
            val className = row[3].text()
            val classType = row[0].text()

            var grade: Int
            var semester: String
            when{
                row[6].text() != "" -> {
                    grade = 1
                    semester = "前期"
                }
                row[7].text() != "" -> {
                    grade = 1
                    semester = "後期"
                }
                row[8].text() != "" -> {
                    grade = 2
                    semester = "前期"
                }
                row[9].text() != "" -> {
                    grade = 2
                    semester = "後期"
                }
                row[10].text() != "" -> {
                    grade = 3
                    semester = "前期"
                }
                row[11].text() != "" -> {
                    grade = 3
                    semester = "後期"
                }
                row[12].text() != "" -> {
                    grade = 4
                    semester = "前期"
                }
                row[13].text() != "" -> {
                    grade = 4
                    semester = "後期"
                }
                else -> {
                    continue
                }
            }

            val campus = when{
                (section == "工学部") -> {
                    if(grade == 1 || grade == 2){
                        "大宮"
                    }else{
                        "豊洲"
                    }
                }
                (section == "システム理工学部") -> {
                    "大宮"
                }
                (section == "デザイン工学部") -> {
                    "芝浦"
                }
                (section == "建築学部") -> {
                    "豊洲"
                }
                else -> continue
            }

            if (classId != null) {
                classes.add(Class(classId, className, grade, departmentName, semester, campus, classType))
                println("$classId, $className, $classType, $grade, $semester")
            }
        }
    }

    fun outputClassesToFile(){
        try {
            val file = File("out/$departmentName.txt")
            val fileWriter = FileWriter(file)
            for(c in classes){
                fileWriter.write("${c.classCode}, ${c.name}, ${c.grade}, ${c.department}, ${c.semester}, ${c.campus}, ${c.unitDivision}\n")
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun toString(): String {
        return "DepartmentInfo(departmentName='$departmentName', section='$section', url='$url', classes=$classes)"
    }

}