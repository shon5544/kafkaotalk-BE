package com.beomsu.kafkaotalkbe.app.friends.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private val id: Long? = null,

    var email: String = "",
    var password: String = "",
    var username: String = "",
    var refreshToken: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    var friend: User?,

    @OneToMany
    var friendList: ArrayList<User>?,

    @Column(name = "profile_image")
    var profileImg: String?,

    @Column(name = "background_image")
    var bgImg: String?
)