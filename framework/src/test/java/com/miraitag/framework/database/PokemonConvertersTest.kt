package com.miraitag.framework.database

import com.miraitag.pokedex.data.entities.PokemonEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PokemonConvertersTest {

    private val converters = PokemonConverters()

    @Test
    fun `fromAbilities converts list to json string`() {
        val abilities = listOf(
            PokemonEntity.Abilities(
                isHidden = false,
                slot = 1,
                ability = PokemonEntity.NameBase("overgrow", "url")
            )
        )
        val result = converters.fromAbilities(abilities)
        assertEquals("""[{"isHidden":false,"slot":1,"ability":{"name":"overgrow","url":"url"}}]""", result)
    }

    @Test
    fun `toAbilities converts json string to list`() {
        val json = """[{"isHidden":false,"slot":1,"ability":{"name":"overgrow","url":"url"}}]"""
        val result = converters.toAbilities(json)
        assertEquals(1, result.size)
        assertEquals("overgrow", result[0].ability.name)
    }

    @Test
    fun `fromSpecies converts NameBase to json string`() {
        val species = PokemonEntity.NameBase("bulbasaur", "url")
        val result = converters.fromSpecies(species)
        assertEquals("""{"name":"bulbasaur","url":"url"}""", result)
    }

    @Test
    fun `toSpecies converts json string to NameBase`() {
        val json = """{"name":"bulbasaur","url":"url"}"""
        val result = converters.toSpecies(json)
        assertEquals("bulbasaur", result.name)
    }
    
    @Test
    fun `fromSprites converts Sprites to json string`() {
        val sprites = PokemonEntity.Sprites(frontDefault = "front")
        val result = converters.fromSprites(sprites)
        assertEquals("""{"frontDefault":"front"}""", result)
    }

    @Test
    fun `toSprites converts json string to Sprites`() {
        val json = """{"frontDefault":"front"}"""
        val result = converters.toSprites(json)
        assertEquals("front", result.frontDefault)
    }
}
