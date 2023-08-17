package pl.jch.gadgetgallery

import org.springframework.data.jpa.repository.JpaRepository

interface GadgetRepository : JpaRepository<Gadget, Long>
