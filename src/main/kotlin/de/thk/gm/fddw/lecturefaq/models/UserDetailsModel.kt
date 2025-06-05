package de.thk.gm.fddw.lecturefaq.models

import de.thk.gm.fddw.lecturefaq.models.enums.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsModel(
    private val role: Role,
    private val password: String,
    private val username: String
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorityPrefix = "ROLE_"
        val authorities = mutableListOf<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(authorityPrefix + role))
        return authorities
    }

    override fun getPassword() = password

    override fun getUsername() = username
}