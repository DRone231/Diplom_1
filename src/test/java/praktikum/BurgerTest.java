package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    @Mock
    private Ingredient extraSauce;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsSetsBunCorrectly() {
        burger.setBuns(bun);
        assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredientIncreasesSize() {
        burger.addIngredient(sauce);
        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void addIngredientAddsCorrectItem() {
        burger.addIngredient(sauce);
        assertEquals(sauce, burger.ingredients.get(0));
    }

    @Test
    public void addIngredientPreservesOrderFirst() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(extraSauce);

        assertEquals(sauce, burger.ingredients.get(0));
    }

    @Test
    public void addIngredientPreservesOrderSecond() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(extraSauce);

        assertEquals(filling, burger.ingredients.get(1));
    }

    @Test
    public void addIngredientPreservesOrderThird() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(extraSauce);

        assertEquals(extraSauce, burger.ingredients.get(2));
    }

    @Test
    public void removeIngredientDecreasesSize() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(extraSauce);

        burger.removeIngredient(1);

        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void removeIngredientRemovesCorrectItem() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(extraSauce);

        burger.removeIngredient(1);

        assertEquals(extraSauce, burger.ingredients.get(1));
    }

    @Test
    public void removeIngredientFirstDecreasesSize() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.removeIngredient(0);

        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientFirstRemovesCorrectItem() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.removeIngredient(0);

        assertEquals(filling, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientLastDecreasesSize() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.removeIngredient(1);

        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientLastRemovesCorrectItem() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.removeIngredient(1);

        assertEquals(sauce, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientForwardChangesOrder() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(extraSauce);

        burger.moveIngredient(0, 2);

        assertEquals(filling, burger.ingredients.get(0));
        assertEquals(extraSauce, burger.ingredients.get(1));
        assertEquals(sauce, burger.ingredients.get(2));
    }

    @Test
    public void moveIngredientBackwardChangesOrder() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(extraSauce);

        burger.moveIngredient(2, 0);

        assertEquals(extraSauce, burger.ingredients.get(0));
        assertEquals(sauce, burger.ingredients.get(1));
        assertEquals(filling, burger.ingredients.get(2));
    }

    @Test
    public void moveIngredientToMiddleChangesOrder() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(extraSauce);

        burger.moveIngredient(0, 1);

        assertEquals(filling, burger.ingredients.get(0));
        assertEquals(sauce, burger.ingredients.get(1));
        assertEquals(extraSauce, burger.ingredients.get(2));
    }

    @Test
    public void getPriceOnlyBun() {
        when(bun.getPrice()).thenReturn(100.0f);

        burger.setBuns(bun);

        assertEquals(200.0f, burger.getPrice(), 0.001f);
    }

    @Test
    public void getPriceBunAndIngredients() {
        when(bun.getPrice()).thenReturn(100.0f);
        when(sauce.getPrice()).thenReturn(50.0f);
        when(filling.getPrice()).thenReturn(75.0f);

        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        assertEquals(325.0f, burger.getPrice(), 0.001f);
    }

    @Test
    public void getPriceZeroValues() {
        when(bun.getPrice()).thenReturn(0.0f);
        when(sauce.getPrice()).thenReturn(0.0f);

        burger.setBuns(bun);
        burger.addIngredient(sauce);

        assertEquals(0.0f, burger.getPrice(), 0.001f);
    }

    @RunWith(Parameterized.class)
    public static class GetPriceParameterizedTest {

        private float bunPrice;
        private float saucePrice;
        private float fillingPrice;
        private float expectedTotalPrice;

        public GetPriceParameterizedTest(float bunPrice, float saucePrice,
                                         float fillingPrice, float expectedTotalPrice) {
            this.bunPrice = bunPrice;
            this.saucePrice = saucePrice;
            this.fillingPrice = fillingPrice;
            this.expectedTotalPrice = expectedTotalPrice;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {100.0f, 50.0f, 75.0f, 325.0f},
                    {0.0f, 0.0f, 0.0f, 0.0f},
                    {200.0f, 100.0f, 100.0f, 600.0f},
                    {1.5f, 0.5f, 0.25f, 3.75f},
                    {300.0f, 300.0f, 300.0f, 1200.0f}
            });
        }

        @Test
        public void testGetPrice() {
            Burger burger = new Burger();

            Bun bun = org.mockito.Mockito.mock(Bun.class);
            Ingredient ingSauce = org.mockito.Mockito.mock(Ingredient.class);
            Ingredient ingFilling = org.mockito.Mockito.mock(Ingredient.class);

            when(bun.getPrice()).thenReturn(bunPrice);
            when(ingSauce.getPrice()).thenReturn(saucePrice);
            when(ingFilling.getPrice()).thenReturn(fillingPrice);

            burger.setBuns(bun);
            burger.addIngredient(ingSauce);
            burger.addIngredient(ingFilling);

            assertEquals(expectedTotalPrice, burger.getPrice(), 0.001f);
        }
    }

    @Test
    public void getReceiptContainsBunNameTopAndBottom() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100.0f);

        burger.setBuns(bun);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("(==== black bun ====)"));
    }

    @Test
    public void getReceiptContainsSauce() {
        when(bun.getName()).thenReturn("white bun");
        when(bun.getPrice()).thenReturn(200.0f);
        when(sauce.getName()).thenReturn("hot sauce");
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getPrice()).thenReturn(100.0f);

        burger.setBuns(bun);
        burger.addIngredient(sauce);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("hot sauce"));
    }

    @Test
    public void getReceiptContainsFilling() {
        when(bun.getName()).thenReturn("white bun");
        when(bun.getPrice()).thenReturn(200.0f);
        when(filling.getName()).thenReturn("cutlet");
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getPrice()).thenReturn(200.0f);

        burger.setBuns(bun);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("cutlet"));
    }

    @Test
    public void getReceiptIngredientTypeLowerCase() {
        when(bun.getName()).thenReturn("red bun");
        when(bun.getPrice()).thenReturn(300.0f);
        when(sauce.getName()).thenReturn("chili sauce");
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getPrice()).thenReturn(300.0f);

        burger.setBuns(bun);
        burger.addIngredient(sauce);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("= sauce chili sauce ="));
        assertFalse(receipt.contains("SAUCE"));
    }

    @Test
    public void getReceiptContainsPriceLine() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100.0f);
        when(sauce.getName()).thenReturn("hot sauce");
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getPrice()).thenReturn(100.0f);

        burger.setBuns(bun);
        burger.addIngredient(sauce);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("Price:"));
        assertTrue(receipt.contains("300"));
    }

    @Test
    public void getReceiptWithoutIngredients() {
        when(bun.getName()).thenReturn("white bun");
        when(bun.getPrice()).thenReturn(200.0f);

        burger.setBuns(bun);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("white bun"));
        assertTrue(receipt.contains("Price: 400"));
    }

    @Test
    public void getReceiptMultipleIngredientTypes() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100.0f);
        when(sauce.getName()).thenReturn("sour cream");
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getPrice()).thenReturn(200.0f);
        when(filling.getName()).thenReturn("dinosaur");
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getPrice()).thenReturn(200.0f);

        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();

        assertTrue(receipt.contains("= sauce sour cream ="));
        assertTrue(receipt.contains("= filling dinosaur ="));
    }
}
