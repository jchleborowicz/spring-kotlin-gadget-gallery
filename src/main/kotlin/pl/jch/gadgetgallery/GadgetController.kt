package pl.jch.gadgetgallery

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
            if (it.isPresent()) ResponseEntity<Gadget>(it.get(), HttpStatus.OK)
            else ResponseEntity<Gadget>(HttpStatus.NOT_FOUND)
        }
}
