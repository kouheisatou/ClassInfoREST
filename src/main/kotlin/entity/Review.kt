package entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.io.Serializable

@Entity
open class Review(
    @Column(length = 8)
    var classCode: String,
    var content: String,
    var rate: Int
) : Serializable {

    constructor() : this("", "", 0) {}

    @Id @GeneratedValue
    var id: Int = 0

    override fun toString(): String {
        return "Class{$id, $classCode, $content, $rate}"
    }

    override fun equals(other: Any?): Boolean {
        if(other !is Review){
            return false
        }
        return id == other.id
    }

    override fun hashCode(): Int {
        var hash = 0
        hash += id.hashCode()
        return hash
    }
}