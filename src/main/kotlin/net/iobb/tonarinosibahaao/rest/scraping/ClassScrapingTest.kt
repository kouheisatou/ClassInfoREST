package net.iobb.tonarinosibahaao.rest.scraping

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main() {
    scraping("src/main/resources/scraping_setting.txt", true)
}

fun scraping(settingFilePath: String, output: Boolean) {
    val departmentList = mutableListOf<DepartmentInfo>()

    val file = File(settingFilePath)
    val fr = FileReader(file)
    val br = BufferedReader(fr)

    for(line in br.lines()){
        if(line == "") continue
        try {
            val element = line.split(",")
            departmentList += DepartmentInfo(element[0], element[1], element[2])
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }

    for(d in departmentList){
        d.getClasses()
        if(output){
            d.outputClassesToFile()
        }
        Thread.sleep(1000)
    }
}
