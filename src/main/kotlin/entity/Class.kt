package entity

import jakarta.persistence.*
import values.Department
import values.Semester
import java.io.Serializable

@Entity
open class Class(
    @Column(length = 8)
    @Id
    var classCode: String,
    var name: String,
    var grade: Int,
    @Enumerated(EnumType.STRING)
    var department: Department,
    @Enumerated(EnumType.STRING)
    var semester: Semester
) : Serializable {

    constructor() : this("", "", 0, Department.AF, Semester.Second) {}

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