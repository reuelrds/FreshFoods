import { Injectable } from '@angular/core';
import { Recipe } from '../models/recipe';

import recipeJson from '../data/recipes.json';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { Observable, ReplaySubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  BACKEND_URL = environment.BACKEND_URL;

  _recipes: Recipe[];
  $recipes = new ReplaySubject<Recipe[]>();

  constructor(private httpClient: HttpClient) {}

  getRecipes(): Observable<Recipe[]> {
    return this.httpClient
      .get<{ recipes: Recipe[]; message: String }>(
        `${this.BACKEND_URL}/recipes`
      )
      .pipe(
        map((result) => {
          if (result.message == 'Items Retrieved Successfully') {
            this._recipes = result.recipes;
            this.$recipes.next(this._recipes);
            return result.recipes;
          }
        })
      );
  }
}
