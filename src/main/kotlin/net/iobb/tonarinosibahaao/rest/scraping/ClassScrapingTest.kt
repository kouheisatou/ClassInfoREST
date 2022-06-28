package net.iobb.tonarinosibahaao.rest.scraping

import java.io.*

fun main() {
    val departmentInfoList = loadFromText("src/main/resources/scraping_setting.txt")
    scraping(departmentInfoList, true)
}

fun scraping(departmentList: List<DepartmentInfo>, output: Boolean): List<DepartmentInfo> {

    for(d in departmentList){
        d.getClasses()
        if(output){
            d.outputClassesToFile()
        }
        Thread.sleep(1000)
    }
    return departmentList
}

fun loadFromText(textFilePath: String): List<DepartmentInfo>{

    val file = File(textFilePath)
    val fr = FileReader(file)
    val br = BufferedReader(fr)

    return loadFromBufferedReader(br)
}

fun loadFromInputStream(inputStream: InputStream): List<DepartmentInfo>{
    val inputStreamReader = InputStreamReader(inputStream)
    val br = BufferedReader(inputStreamReader)

    return loadFromBufferedReader(br)
}

fun loadFromBufferedReader(bufferedReader: BufferedReader): List<DepartmentInfo>{
    val departmentList = mutableListOf<DepartmentInfo>()

    for(line in bufferedReader.lines()){
        if(line == "") continue
        try {
            val element = line.split(",")
            departmentList += DepartmentInfo(element[0], element[1], element[2])
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }
    return departmentList
}