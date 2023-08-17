package pl.jch.gadgetgallery

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping(path = ["/api/gadgets"])
class GadgetController(private val repository: GadgetRepository) {
    @GetMapping
    fun getGadgets(): ResponseEntity<List<Gadget>> = repository.findAll()
        .let {
            if (it.isEmpty()) ResponseEntity<List<Gadget>>(HttpStatus.NO_CONTENT)
            else ResponseEntity<List<Gadget>>(it, HttpStatus.OK)
        }

    @GetMapping("/{id}")
    fun getGadget(@PathVariable id: Long): ResponseEntity<Gadget> = repository.findById(id)
        .map {
            ResponseEntity<Gadget>(it, HttpStatus.OK)
        }
        .orElseGet {
            ResponseEntity<Gadget>(HttpStatus.NOT_FOUND)
        }

    @PostMapping
    fun addNewGadget(@RequestBody gadget: Gadget, uri: UriComponentsBuilder): ResponseEntity<Gadget> {
        val persistedGadget: Gadget = repository.save(gadget)
        val headers = HttpHeaders().apply {
            location = uri.path("/api/gadgets/${persistedGadget.id}")
                .build()
                .toUri()
        }
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateGadgetById(@PathVariable id: Long, @RequestBody gadget: Gadget): ResponseEntity<Gadget> =
        repository.findById(id)
            .map {
                val updated = repository.save(
                    it.copy(
                        category = gadget.category,
                        name = gadget.name,
                        price = gadget.price,
                        availability = gadget.availability
                    )
                )
                ResponseEntity(updated, HttpStatus.OK)
            }
            .orElseGet {
                ResponseEntity<Gadget>(HttpStatus.NOT_FOUND)
            }
}
