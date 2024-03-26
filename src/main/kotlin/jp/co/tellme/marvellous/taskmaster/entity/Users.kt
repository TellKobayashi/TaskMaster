package jp.co.tellme.marvellous.taskmaster.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class Users(
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "display_name", nullable = false)
    var displayName: String? = null,

    @Column(name = "password", nullable = false)
    var password: String? = null,

    @Column(name = "created_at",
        nullable = false,
        updatable = false,
        insertable = false,
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    var createdAt: LocalDateTime? = null,

    @Column(name = "updated_at",
        nullable = false,
        updatable = false,
        insertable = false,
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    var updatedAt: LocalDateTime? = null,

    @Column(name = "last_logged_in_at", nullable = true)
    var lastLoggedInAt: LocalDateTime? = null
) {
}
