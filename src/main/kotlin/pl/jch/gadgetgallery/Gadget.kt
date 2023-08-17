package pl.jch.gadgetgallery

import jakarta.persistence.*

@Entity
@Table(name = "GADGET")
data class Gadget(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String,
    val category: String?,
    val availability: Boolean = true,
    val price: Double
)
