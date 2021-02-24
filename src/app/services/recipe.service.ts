import { Injectable } from '@angular/core';
import { Recipe } from '../models/recipe';

import recipeJson from '../data/recipes.json';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  recipes: Recipe[] = recipeJson;

  constructor() {}

  getRecipes() {
    return this.recipes;
  }
}
