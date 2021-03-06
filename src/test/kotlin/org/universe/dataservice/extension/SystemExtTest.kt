package org.universe.dataservice.extension

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junitpioneer.jupiter.SetEnvironmentVariable
import org.junitpioneer.jupiter.SetSystemProperty
import org.universe.dataservice.utils.getRandomString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SystemExtTest {

    @Nested
    @DisplayName("Get env or property variable")
    inner class GetPropertyOrEnv {

        @Test
        fun `key not found`() {
            assertNull(getPropertyOrEnv(getRandomString()))
        }

        @Test
        @SetEnvironmentVariable(key = "testEnv", value = "testValueEnv")
        fun `key found from environnement`() {
            assertEquals("testValueEnv", getPropertyOrEnv("testEnv"))
        }

        @Test
        @SetSystemProperty(key = "testProperty", value = "testValueProperty")
        fun `key found from properties`() {
            assertEquals("testValueProperty", getPropertyOrEnv("testProperty"))
        }

        @Test
        @SetEnvironmentVariable(key = "test", value = "testValueEnv")
        @SetSystemProperty(key = "test", value = "testValueProperty")
        fun `key found from properties before env`() {
            assertEquals("testValueProperty", getPropertyOrEnv("test"))
        }
    }
}