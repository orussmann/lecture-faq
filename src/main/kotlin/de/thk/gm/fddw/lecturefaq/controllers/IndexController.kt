package de.thk.gm.fddw.lecturefaq.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class IndexController {
    @GetMapping("/")
    fun index(
        @RequestParam(required = false) emailChanged: Boolean?,
        model: Model
    ): String {
        if (emailChanged == true) {
            model.addAttribute(
                "notification",
                "Email wurde geändert. Bitte melden Sie sich erneut an."
            )
        }
        return "index"
    }
}