package pl.jch.gadgetgallery

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "GADGET")
data class Gadget(
    @Id val id: Int,
    val name: String,
    val category: String?,
    val availability: Boolean = true,
    val price: Double
)
