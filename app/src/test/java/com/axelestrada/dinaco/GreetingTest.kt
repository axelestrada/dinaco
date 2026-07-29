package com.axelestrada.dinaco

import com.axelestrada.dinaco.viewmodel.helper.getGreetingForHour
import org.junit.Assert.assertEquals
import org.junit.Test

class GreetingTest {

    @Test
    fun `getGreetingForHour retorna 'Buenos dias' en la manana`() {
        val greetingResult = getGreetingForHour(8)
        assertEquals("Buenos días", greetingResult)
    }

    @Test
    fun `getGreetingForHour retorna 'Buenos dias' en el limite superior de la manana (11 hrs)`() {
        val greetingResult = getGreetingForHour(11)
        assertEquals("Buenos días", greetingResult)
    }

    @Test
    fun `getGreetingForHour retorna 'Buenas tardes' en la tarde`() {
        val greetingResult = getGreetingForHour(14)
        assertEquals("Buenas tardes", greetingResult)
    }

    @Test
    fun `getGreetingForHour retorna 'Buenas noches' en la noche`() {
        val greetingResult = getGreetingForHour(21)
        assertEquals("Buenas noches", greetingResult)
    }

    @Test
    fun `getGreetingForHour retorna 'Buenos dias' en la madrugada (2 hrs)`() {
        val greetingResult = getGreetingForHour(2)
        assertEquals("Buenos días", greetingResult)
    }
}