import { Component, OnInit } from '@angular/core';
import { Recipe, RecipeItem } from 'src/app/models/recipe';
import { RecipeService } from 'src/app/services/recipe.service';

import * as _ from 'lodash';
import { CartService } from 'src/app/services/cart.service';
import { Item } from 'src/app/models/item';
import { CartItem } from 'src/app/models/cart';

@Component({
  selector: 'freshfood-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss'],
})
export class RecipeComponent implements OnInit {
  recipes: Recipe[];
  isLoading: boolean;

  constructor(
    private recipeService: RecipeService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.isLoading = true;
    this.recipeService.getRecipes().subscribe((recipes) => {
      this.recipes = recipes;

      setTimeout(() => {
        this.isLoading = false;
      }, 1500);
    });
  }

  addItemsToCart(ingredients: RecipeItem[]) {
    ingredients.forEach((ingredient) => {
      const newCartItem: CartItem = {
        ...ingredient,
      };
      this.cartService.addItem(newCartItem, ingredient.itemCount);
    });
  }
}
