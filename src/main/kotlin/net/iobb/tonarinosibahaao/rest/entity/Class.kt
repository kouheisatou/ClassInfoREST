package net.iobb.tonarinosibahaao.rest.entity

import jakarta.persistence.*
import java.io.Serializable

@Entity
open class Class(
    @Id @Column(length = 8)
    var classCode: String,
    var name: String,
    var grade: Int,
    var department: String,
    var semester: String,
    var campus: String,
    var unitDivision: String
) : Serializable {

    constructor() : this("", "", 0, "", "", "", "") {}

    override fun toString(): String {
        return "Class{$classCode, $name, $grade, $department, $semester, $campus, $unitDivision}"
    }

    override fun equals(other: Any?): Boolean {
        if(other !is Class){
            return false
        }
        return classCode == other.classCode
    }

    override fun hashCode(): Int {
        var hash = 0
        hash += classCode.hashCode()
        return hash
    }
}