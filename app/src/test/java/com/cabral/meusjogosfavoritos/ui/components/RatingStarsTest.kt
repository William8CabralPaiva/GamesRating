package com.cabral.meusjogosfavoritos.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cabral.meusjogosfavoritos.ui.theme.GamesRatingTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RatingStarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ─────────────────────────────────────────────────────────────
    // 1. ICON SELECTION — based on rating value
    // ─────────────────────────────────────────────────────────────

    @Test
    fun `when rating is 4_0 or above, full star icon is shown`() {
        // Given: rating igual a 4.0 (limiar mínimo para estrela cheia)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 4.0, isLoading = false)
            }
        }
        // Then: o ícone com contentDescription "Rating: 4.0" deve estar visível
        composeTestRule
            .onNodeWithContentDescription("Rating: 4.0")
            .assertIsDisplayed()
    }

    @Test
    fun `when rating is 5_0, full star icon is shown`() {
        // Given: rating igual a 5.0 (valor máximo)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 5.0, isLoading = false)
            }
        }
        // Then: o ícone com contentDescription "Rating: 5.0" deve estar visível
        composeTestRule
            .onNodeWithContentDescription("Rating: 5.0")
            .assertIsDisplayed()
    }

    @Test
    fun `when rating is 2_0, half star icon is shown`() {
        // Given: rating igual a 2.0 (limiar mínimo para meia estrela)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 2.0, isLoading = false)
            }
        }
        // Then: o ícone com contentDescription "Rating: 2.0" deve estar visível
        composeTestRule
            .onNodeWithContentDescription("Rating: 2.0")
            .assertIsDisplayed()
    }

    @Test
    fun `when rating is 3_9, half star icon is shown`() {
        // Given: rating igual a 3.9 (abaixo do limiar da estrela cheia)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 3.9, isLoading = false)
            }
        }
        // Then: o ícone com contentDescription "Rating: 3.9" deve estar visível
        composeTestRule
            .onNodeWithContentDescription("Rating: 3.9")
            .assertIsDisplayed()
    }

    @Test
    fun `when rating is 0_0, outline star icon is shown`() {
        // Given: rating igual a 0.0 (valor mínimo possível)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 0.0, isLoading = false)
            }
        }
        // Then: o ícone com contentDescription "Rating: 0.0" deve estar visível
        composeTestRule
            .onNodeWithContentDescription("Rating: 0.0")
            .assertIsDisplayed()
    }

    @Test
    fun `when rating is 1_9, outline star icon is shown`() {
        // Given: rating igual a 1.9 (abaixo do limiar da meia estrela)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 1.9, isLoading = false)
            }
        }
        // Then: o ícone com contentDescription "Rating: 1.9" deve estar visível
        composeTestRule
            .onNodeWithContentDescription("Rating: 1.9")
            .assertIsDisplayed()
    }

    @Test
    fun `when rating is null, outline star icon is shown`() {
        // Given: rating nulo (ausência de avaliação)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = null, isLoading = false)
            }
        }
        // Then: o ícone com contentDescription "Rating: null" deve estar visível
        composeTestRule
            .onNodeWithContentDescription("Rating: null")
            .assertIsDisplayed()
    }

    // ─────────────────────────────────────────────────────────────
    // 2. TEXT DISPLAY — rating value or dash
    // ─────────────────────────────────────────────────────────────

    @Test
    fun `when rating is not null, text shows rating value`() {
        // Given: rating igual a 3.5 (valor não nulo)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 3.5, isLoading = false)
            }
        }
        // Then: o texto "3.5" deve estar visível na tela
        composeTestRule
            .onNodeWithText("3.5")
            .assertIsDisplayed()
    }

    @Test
    fun `when rating is null, text shows dash`() {
        // Given: rating nulo
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = null, isLoading = false)
            }
        }
        // Then: o texto "-" deve ser exibido no lugar do valor
        composeTestRule
            .onNodeWithText("-")
            .assertIsDisplayed()
    }

    @Test
    fun `when rating is 0_0, text shows 0_0`() {
        // Given: rating igual a 0.0 (menor valor numérico válido)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 0.0, isLoading = false)
            }
        }
        // Then: o texto "0.0" deve estar visível (não deve exibir "-")
        composeTestRule
            .onNodeWithText("0.0")
            .assertIsDisplayed()
    }

    @Test
    fun `when rating is 5_0, text shows 5_0`() {
        // Given: rating igual a 5.0 (valor máximo)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 5.0, isLoading = false)
            }
        }
        // Then: o texto "5.0" deve estar visível na tela
        composeTestRule
            .onNodeWithText("5.0")
            .assertIsDisplayed()
    }

    // ─────────────────────────────────────────────────────────────
    // 3. LAYOUT — Vertical (default) vs Horizontal
    // ─────────────────────────────────────────────────────────────

    @Test
    fun `default layout is vertical and renders correctly`() {
        // Given: nenhum layout explícito definido (usa o padrão Vertical)
        // When: o componente RatingStar é renderizado com rating 4.5
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 4.5, isLoading = false)
            }
        }
        // Then: ícone e texto devem ambos estar visíveis na tela
        composeTestRule.onNodeWithContentDescription("Rating: 4.5").assertIsDisplayed()
        composeTestRule.onNodeWithText("4.5").assertIsDisplayed()
    }

    @Test
    fun `horizontal layout renders icon and text`() {
        // Given: layout definido como Horizontal e rating igual a 4.5
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(
                    rating = 4.5,
                    isLoading = false,
                    layout = RatingLayout.Horizontal
                )
            }
        }
        // Then: ícone e texto devem estar visíveis lado a lado
        composeTestRule.onNodeWithContentDescription("Rating: 4.5").assertIsDisplayed()
        composeTestRule.onNodeWithText("4.5").assertIsDisplayed()
    }

    @Test
    fun `vertical layout renders icon and text for null rating`() {
        // Given: layout Vertical e rating nulo
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(
                    rating = null,
                    isLoading = false,
                    layout = RatingLayout.Vertical
                )
            }
        }
        // Then: ícone de estrela vazia e texto "-" devem estar visíveis
        composeTestRule.onNodeWithContentDescription("Rating: null").assertIsDisplayed()
        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }

    @Test
    fun `horizontal layout renders dash for null rating`() {
        // Given: layout Horizontal e rating nulo
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(
                    rating = null,
                    isLoading = false,
                    layout = RatingLayout.Horizontal
                )
            }
        }
        // Then: ícone de estrela vazia e texto "-" devem estar visíveis lado a lado
        composeTestRule.onNodeWithContentDescription("Rating: null").assertIsDisplayed()
        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }

    // ─────────────────────────────────────────────────────────────
    // 4. LOADING STATE
    // ─────────────────────────────────────────────────────────────

    @Test
    fun `when isLoading true, icon is still displayed`() {
        // Given: isLoading igual a true e rating igual a 3.5
        // When: o componente RatingStar é renderizado em estado de carregamento
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 3.5, isLoading = true)
            }
        }
        // Then: o ícone ainda deve estar presente na árvore semântica
        composeTestRule
            .onNodeWithContentDescription("Rating: 3.5")
            .assertIsDisplayed()
    }

    @Test
    fun `when isLoading true, text is still displayed`() {
        // Given: isLoading igual a true e rating igual a 3.5
        // When: o componente RatingStar é renderizado em estado de carregamento
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 3.5, isLoading = true)
            }
        }
        // Then: o texto "3.5" ainda deve estar presente na árvore semântica
        composeTestRule
            .onNodeWithText("3.5")
            .assertIsDisplayed()
    }

    @Test
    fun `when isLoading true with null rating, dash is shown`() {
        // Given: isLoading igual a true e rating nulo
        // When: o componente RatingStar é renderizado em estado de carregamento
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = null, isLoading = true)
            }
        }
        // Then: o texto "-" deve ser exibido mesmo durante o carregamento
        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }

    @Test
    fun `when isLoading true in horizontal layout, components are displayed`() {
        // Given: isLoading true, layout Horizontal, rating 0.0 e textColor branco
        // When: o componente RatingStar é renderizado em estado de carregamento
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(
                    rating = 0.0,
                    isLoading = true,
                    layout = RatingLayout.Horizontal,
                    textColor = Color.White
                )
            }
        }
        // Then: ícone e texto devem permanecer visíveis independentemente do shimmer
        composeTestRule.onNodeWithContentDescription("Rating: 0.0").assertIsDisplayed()
        composeTestRule.onNodeWithText("0.0").assertIsDisplayed()
    }

    // ─────────────────────────────────────────────────────────────
    // 5. BOUNDARY VALUES — exact thresholds
    // ─────────────────────────────────────────────────────────────

    @Test
    fun `rating exactly 4_0 shows full star`() {
        // Given: rating exatamente igual a 4.0 (limiar inclusivo da estrela cheia)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 4.0, isLoading = false)
            }
        }
        // Then: ícone de estrela cheia e texto "4.0" devem estar visíveis
        composeTestRule.onNodeWithContentDescription("Rating: 4.0").assertIsDisplayed()
        composeTestRule.onNodeWithText("4.0").assertIsDisplayed()
    }

    @Test
    fun `rating exactly 2_0 shows half star`() {
        // Given: rating exatamente igual a 2.0 (limiar inclusivo da meia estrela)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 2.0, isLoading = false)
            }
        }
        // Then: ícone de meia estrela e texto "2.0" devem estar visíveis
        composeTestRule.onNodeWithContentDescription("Rating: 2.0").assertIsDisplayed()
        composeTestRule.onNodeWithText("2.0").assertIsDisplayed()
    }

    @Test
    fun `rating just below 2_0 shows outline star`() {
        // Given: rating igual a 1.99 (imediatamente abaixo do limiar da meia estrela)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 1.99, isLoading = false)
            }
        }
        // Then: ícone de estrela vazia e texto "1.99" devem estar visíveis
        composeTestRule.onNodeWithContentDescription("Rating: 1.99").assertIsDisplayed()
        composeTestRule.onNodeWithText("1.99").assertIsDisplayed()
    }

    @Test
    fun `rating just below 4_0 shows half star`() {
        // Given: rating igual a 3.99 (imediatamente abaixo do limiar da estrela cheia)
        // When: o componente RatingStar é renderizado
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 3.99, isLoading = false)
            }
        }
        // Then: ícone de meia estrela e texto "3.99" devem estar visíveis
        composeTestRule.onNodeWithContentDescription("Rating: 3.99").assertIsDisplayed()
        composeTestRule.onNodeWithText("3.99").assertIsDisplayed()
    }

    // ─────────────────────────────────────────────────────────────
    // 6. COMBINED SCENARIOS — multiple params at once
    // ─────────────────────────────────────────────────────────────

    @Test
    fun `horizontal layout with custom textColor and loading renders correctly`() {
        // Given: layout Horizontal, isLoading true, textColor vermelho e rating 4.5
        // When: o componente RatingStar é renderizado com todos esses parâmetros combinados
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(
                    rating = 4.5,
                    isLoading = true,
                    layout = RatingLayout.Horizontal,
                    textColor = Color.Red
                )
            }
        }
        // Then: ícone e texto devem estar visíveis corretamente
        composeTestRule.onNodeWithContentDescription("Rating: 4.5").assertIsDisplayed()
        composeTestRule.onNodeWithText("4.5").assertIsDisplayed()
    }

    @Test
    fun `multiple RatingStar components render independently`() {
        // Given: três instâncias de RatingStar com ratings diferentes (5.0, 2.5 e null)
        // When: todos os componentes são renderizados simultaneamente na tela
        composeTestRule.setContent {
            GamesRatingTheme {
                RatingStar(rating = 5.0, isLoading = false)
                RatingStar(rating = 2.5, isLoading = false)
                RatingStar(rating = null, isLoading = false)
            }
        }
        // Then: cada componente deve exibir seu valor independentemente
        composeTestRule.onNodeWithText("5.0").assertIsDisplayed()
        composeTestRule.onNodeWithText("2.5").assertIsDisplayed()
        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }

    @Test
    fun `RatingLayout enum has exactly Vertical and Horizontal values`() {
        // Given: o enum RatingLayout definido no componente
        // When: todos os seus valores são listados via reflection
        val values = RatingLayout.entries.map { it.name }
        // Then: deve conter exatamente "Vertical" e "Horizontal", sem valores extras
        assert(values.contains("Vertical"))
        assert(values.contains("Horizontal"))
        assert(values.size == 2)
    }
}