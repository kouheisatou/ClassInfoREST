package tonarinosibahaao.iobb.net.rest.entity

import jakarta.persistence.*
import java.io.Serializable

@Entity
open class Class(
    @Id @Column(length = 8)
    var classCode: String,
    var name: String,
    var grade: Int,
    @Column(length = 2)
    var department: String,
    var semester: Int
) : Serializable {

    constructor() : this("", "", 0, "", 0) {}

    override fun toString(): String {
        return "Class{$classCode, $name, $grade, $department, $semester}"
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