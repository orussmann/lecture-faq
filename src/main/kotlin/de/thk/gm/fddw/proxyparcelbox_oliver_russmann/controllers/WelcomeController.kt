package de.thk.gm.fddw.proxyparcelbox_oliver_russmann.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class WelcomeController {

    @GetMapping("/")
    @ResponseBody
    fun welcome() = "Proxy-Parcelbox from Oliver Russmann"
}
