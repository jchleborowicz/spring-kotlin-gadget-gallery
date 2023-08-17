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
        .let {
            if (it.isPresent) ResponseEntity<Gadget>(it.get(), HttpStatus.OK)
            else ResponseEntity<Gadget>(HttpStatus.NOT_FOUND)
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
}
